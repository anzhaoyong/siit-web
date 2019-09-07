package org.jeecg.modules.siit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.siit.entity.TrainPerson;

/**
 * @Description: 报名信息
 * @Author: jeecg-boot
 * @Date:   2019-08-26
 * @Version: V1.0
 */
public interface ITrainPersonService extends IService<TrainPerson> {
	
	//根据身份证号码判断该培训用户是否已经报名
	boolean queryIfExistsByIdNo(String idno,int trainId);

}
