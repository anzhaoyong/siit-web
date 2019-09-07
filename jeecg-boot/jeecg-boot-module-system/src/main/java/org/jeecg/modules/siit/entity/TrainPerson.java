package org.jeecg.modules.siit.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 报名信息
 * @Author: jeecg-boot
 * @Date:   2019-08-26
 * @Version: V1.0
 */
@Data
@TableName("siit_train_person")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="siit_train_person对象", description="报名信息")
public class TrainPerson {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**name*/
	@Excel(name = "name", width = 15)
    @ApiModelProperty(value = "name")
	private String name;
	/**idno*/
	@Excel(name = "idno", width = 15)
    @ApiModelProperty(value = "idno")
	private String idno;
	/**人员类型 01 ：主要负责人 02 安全生产管理人员*/
	@Excel(name = "人员类型 01 ：主要负责人 02 安全生产管理人员", width = 15)
    @ApiModelProperty(value = "人员类型 01 ：主要负责人 02 安全生产管理人员")
	private String type;
	/**矿山类型 01 露天 02 地下*/
	@Excel(name = "矿山类型 01 露天 02 地下", width = 15)
    @ApiModelProperty(value = "矿山类型 01 露天 02 地下")
	private String mineType;
	/**phone*/
	@Excel(name = "phone", width = 15)
    @ApiModelProperty(value = "phone")
	private String phone;
	/**学历 01 ：小学 02 初中 03 高中 04 大学*/
	@Excel(name = "学历 01 ：小学 02 初中 03 高中 04 大学", width = 15)
    @ApiModelProperty(value = "学历 01 ：小学 02 初中 03 高中 04 大学")
	private String education;
	/**职务*/
	@Excel(name = "职务", width = 15)
    @ApiModelProperty(value = "职务")
	private String post;
	/**公司名称*/
	@Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
	private String company;
	/**户籍地址*/
	@Excel(name = "户籍地址", width = 15)
    @ApiModelProperty(value = "户籍地址")
	private String homeAddr;
	/**从业地址*/
	@Excel(name = "从业地址", width = 15)
    @ApiModelProperty(value = "从业地址")
	private String jobAddr;
	/**接收证书邮寄地址*/
	@Excel(name = "接收证书邮寄地址", width = 15)
    @ApiModelProperty(value = "接收证书邮寄地址")
	private String sendAddr;
	/**电子邮箱*/
	@Excel(name = "电子邮箱", width = 15)
    @ApiModelProperty(value = "电子邮箱")
	private String email;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private Date createTime;
	/**createBy*/
	@Excel(name = "createBy", width = 15)
    @ApiModelProperty(value = "createBy")
	private Integer createBy;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private Date updateTime;
	/**updateBy*/
	@Excel(name = "updateBy", width = 15)
    @ApiModelProperty(value = "updateBy")
	private Integer updateBy;
	/**培训id*/
	@Excel(name = "培训id", width = 15)
    @ApiModelProperty(value = "培训id")
	private Integer trainId;
}
