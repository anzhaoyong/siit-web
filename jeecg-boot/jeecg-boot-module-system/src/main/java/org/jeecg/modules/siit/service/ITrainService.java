package org.jeecg.modules.siit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.siit.entity.Train;

/**
 * @Description: 培训管理
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
public interface ITrainService extends IService<Train> {
	
	
	//查询最新的培训信息
	Train queryNewTrainInfo();


}
