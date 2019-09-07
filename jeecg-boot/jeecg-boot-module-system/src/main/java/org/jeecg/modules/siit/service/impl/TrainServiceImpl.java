package org.jeecg.modules.siit.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jeecg.modules.siit.entity.Train;
import org.jeecg.modules.siit.mapper.TrainMapper;
import org.jeecg.modules.siit.service.ITrainService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 培训管理
 * @Author: jeecg-boot
 * @Date:   2019-08-24
 * @Version: V1.0
 */
@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements ITrainService {

	
	public Train queryNewTrainInfo() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QueryWrapper<Train> queryWrapper = new QueryWrapper<Train>();
		queryWrapper.ge("entry_end_date", sdf.format(new Date()));
		
		
		List<Train> trainList = baseMapper.selectList(queryWrapper);
		
		return (trainList!=null && trainList.size()>0) ? trainList.get(0):null;
		
	}
}
