package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.Address;
import com.killstan.takeout.mapper.po.AddressMapper;
import com.killstan.takeout.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
