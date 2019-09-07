package org.jeecg.modules.siit.service.impl;

import org.jeecg.modules.siit.entity.Product;
import org.jeecg.modules.siit.mapper.ProductMapper;
import org.jeecg.modules.siit.service.IProductService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 产品管理
 * @Author: jeecg-boot
 * @Date:   2019-08-22
 * @Version: V1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
