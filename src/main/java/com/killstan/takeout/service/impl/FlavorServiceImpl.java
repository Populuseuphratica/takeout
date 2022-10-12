package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.Flavor;
import com.killstan.takeout.mapper.po.FlavorMapper;
import com.killstan.takeout.service.IFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 口味表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class FlavorServiceImpl extends ServiceImpl<FlavorMapper, Flavor> implements IFlavorService {

}
