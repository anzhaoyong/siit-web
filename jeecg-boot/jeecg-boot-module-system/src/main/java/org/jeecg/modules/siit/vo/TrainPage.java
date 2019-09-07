package org.jeecg.modules.siit.vo;

import java.util.List;
import org.jeecg.modules.siit.entity.Train;
import org.jeecg.modules.siit.entity.TrainPerson;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @Description: 培训管理
 * @Author: jeecg-boot
 * @Date:   2019-08-22
 * @Version: V1.0
 */
@Data
public class TrainPage {
	
	/**详细信息*/
  	@Excel(name = "详细信息", width = 15)
	private byte[] content;
	/**创建人*/
  	@Excel(name = "创建人", width = 15)
	private java.lang.Integer createBy;
	/**创建时间*/
  	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**报名开始时间*/
  	@Excel(name = "报名开始时间", width = 15, format = "yyyy-MM-dd")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date entryBeginDate;
	/**报名截止时间*/
  	@Excel(name = "报名截止时间", width = 15, format = "yyyy-MM-dd")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date entryEndDate;
	/**费用*/
  	@Excel(name = "费用", width = 15)
	private java.math.BigDecimal fee;
	/**id*/
	private java.lang.Integer id;
	/**培训名称*/
  	@Excel(name = "培训名称", width = 15)
	private java.lang.String title;
	/**更新人*/
  	@Excel(name = "更新人", width = 15)
	private java.lang.Integer updateBy;
	/**更新时间*/
  	@Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
  	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	
	@ExcelCollection(name="培训报名人员明细")
	private List<TrainPerson> trainPersonList;
	
}
