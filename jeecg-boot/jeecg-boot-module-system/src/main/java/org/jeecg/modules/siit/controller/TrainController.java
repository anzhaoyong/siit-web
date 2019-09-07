package org.jeecg.modules.siit.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.siit.entity.Train;
import org.jeecg.modules.siit.service.ITrainService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 培训管理
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags="培训管理")
@RestController
@RequestMapping("/siit/train")
public class TrainController {
	@Autowired
	private ITrainService trainService;
	
	/**
	  * 分页列表查询
	 * @param train
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "培训管理-分页列表查询")
	@ApiOperation(value="培训管理-分页列表查询", notes="培训管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Train>> queryPageList(Train train,
											  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
											  HttpServletRequest req) {
		Result<IPage<Train>> result = new Result<IPage<Train>>();
		QueryWrapper<Train> queryWrapper = QueryGenerator.initQueryWrapper(train, req.getParameterMap());
		Page<Train> page = new Page<Train>(pageNo, pageSize);
		IPage<Train> pageList = trainService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param train
	 * @return
	 */
	@AutoLog(value = "培训管理-添加")
	@ApiOperation(value="培训管理-添加", notes="培训管理-添加")
	@PostMapping(value = "/add")
	public Result<Train> add(@RequestBody Train train) {
		Result<Train> result = new Result<Train>();
		try {
			trainService.save(train);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param train
	 * @return
	 */
	@AutoLog(value = "培训管理-编辑")
	@ApiOperation(value="培训管理-编辑", notes="培训管理-编辑")
	@PutMapping(value = "/edit")
	public Result<Train> edit(@RequestBody Train train) {
		Result<Train> result = new Result<Train>();
		Train trainEntity = trainService.getById(train.getId());
		if(trainEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = trainService.updateById(train);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "培训管理-通过id删除")
	@ApiOperation(value="培训管理-通过id删除", notes="培训管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			trainService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "培训管理-批量删除")
	@ApiOperation(value="培训管理-批量删除", notes="培训管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Train> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Train> result = new Result<Train>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.trainService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "培训管理-通过id查询")
	@ApiOperation(value="培训管理-通过id查询", notes="培训管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Train> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Train> result = new Result<Train>();
		Train train = trainService.getById(id);
		if(train==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(train);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
      // Step.1 组装查询条件
      QueryWrapper<Train> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Train train = JSON.parseObject(deString, Train.class);
              queryWrapper = QueryGenerator.initQueryWrapper(train, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Train> pageList = trainService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "培训管理列表");
      mv.addObject(NormalExcelConstants.CLASS, Train.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("培训管理列表数据", "导出人:Jeecg", "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<Train> listTrains = ExcelImportUtil.importExcel(file.getInputStream(), Train.class, params);
              trainService.saveBatch(listTrains);
              return Result.ok("文件导入成功！数据行数:" + listTrains.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
