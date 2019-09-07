package org.jeecg.modules.siit.service.impl;

import org.jeecg.modules.siit.entity.News;
import org.jeecg.modules.siit.mapper.NewsMapper;
import org.jeecg.modules.siit.service.INewsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 新闻资讯
 * @Author: jeecg-boot
 * @Date:   2019-08-20
 * @Version: V1.0
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
