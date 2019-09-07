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
import org.jeecg.modules.siit.entity.Banner;
import org.jeecg.modules.siit.service.IBannerService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: banner图设置
 * @Author: jeecg-boot
 * @Date:   2019-08-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="banner图设置")
@RestController
@RequestMapping("/siit/banner")
public class BannerController {
	@Autowired
	private IBannerService bannerService;
	
	/**
	  * 分页列表查询
	 * @param banner
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "banner图设置-分页列表查询")
	@ApiOperation(value="banner图设置-分页列表查询", notes="banner图设置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Banner>> queryPageList(Banner banner,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Banner>> result = new Result<IPage<Banner>>();
		QueryWrapper<Banner> queryWrapper = QueryGenerator.initQueryWrapper(banner, req.getParameterMap());
		Page<Banner> page = new Page<Banner>(pageNo, pageSize);
		IPage<Banner> pageList = bannerService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param banner
	 * @return
	 */
	@AutoLog(value = "banner图设置-添加")
	@ApiOperation(value="banner图设置-添加", notes="banner图设置-添加")
	@PostMapping(value = "/add")
	public Result<Banner> add(@RequestBody Banner banner) {
		Result<Banner> result = new Result<Banner>();
		try {
			bannerService.save(banner);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param banner
	 * @return
	 */
	@AutoLog(value = "banner图设置-编辑")
	@ApiOperation(value="banner图设置-编辑", notes="banner图设置-编辑")
	@PutMapping(value = "/edit")
	public Result<Banner> edit(@RequestBody Banner banner) {
		Result<Banner> result = new Result<Banner>();
		Banner bannerEntity = bannerService.getById(banner.getId());
		if(bannerEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = bannerService.updateById(banner);
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
	@AutoLog(value = "banner图设置-通过id删除")
	@ApiOperation(value="banner图设置-通过id删除", notes="banner图设置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			bannerService.removeById(id);
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
	@AutoLog(value = "banner图设置-批量删除")
	@ApiOperation(value="banner图设置-批量删除", notes="banner图设置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Banner> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Banner> result = new Result<Banner>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.bannerService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "banner图设置-通过id查询")
	@ApiOperation(value="banner图设置-通过id查询", notes="banner图设置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Banner> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Banner> result = new Result<Banner>();
		Banner banner = bannerService.getById(id);
		if(banner==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(banner);
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
      QueryWrapper<Banner> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Banner banner = JSON.parseObject(deString, Banner.class);
              queryWrapper = QueryGenerator.initQueryWrapper(banner, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Banner> pageList = bannerService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "banner图设置列表");
      mv.addObject(NormalExcelConstants.CLASS, Banner.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("banner图设置列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Banner> listBanners = ExcelImportUtil.importExcel(file.getInputStream(), Banner.class, params);
              bannerService.saveBatch(listBanners);
              return Result.ok("文件导入成功！数据行数:" + listBanners.size());
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
