package org.jeecg.modules.siit.service.impl;

import org.jeecg.modules.siit.entity.Msg;
import org.jeecg.modules.siit.mapper.MsgMapper;
import org.jeecg.modules.siit.service.IMsgService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 留言管理
 * @Author: jeecg-boot
 * @Date:   2019-08-22
 * @Version: V1.0
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

}
