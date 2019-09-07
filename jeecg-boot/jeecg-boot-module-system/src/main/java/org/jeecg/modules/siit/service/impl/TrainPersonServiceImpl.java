package org.jeecg.modules.siit.service.impl;


import java.util.List;

import org.jeecg.modules.siit.entity.TrainPerson;
import org.jeecg.modules.siit.mapper.TrainPersonMapper;
import org.jeecg.modules.siit.service.ITrainPersonService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 报名信息
 * @Author: jeecg-boot
 * @Date:   2019-08-26
 * @Version: V1.0
 */
@Service
public class TrainPersonServiceImpl extends ServiceImpl<TrainPersonMapper, TrainPerson> implements ITrainPersonService {

	@Override
	public boolean queryIfExistsByIdNo(String idno, int trainId) {
		// TODO Auto-generated method stub
		QueryWrapper<TrainPerson> queryWrapper = new QueryWrapper<TrainPerson>();
		queryWrapper.eq("idno", idno.trim());
		queryWrapper.eq("train_id", trainId);
		List list = baseMapper.selectList(queryWrapper) ;
		return (list != null && list.size()>0) ? true:false;
	}

	
	
}
