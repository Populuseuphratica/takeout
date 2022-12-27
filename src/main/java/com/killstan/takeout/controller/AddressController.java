package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.killstan.takeout.entity.po.Address;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.AddressService;
import com.killstan.takeout.util.ThreadLocalForId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * @Description: 获取当前用户地址列表
     * @Param: []
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/1 23:14
     */
    @GetMapping("/list")
    public ResultVo listAddress() {
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long userid = ThreadLocalForId.get();
        lambdaQueryWrapper.eq(Address::getUserId, userid)
                .eq(Address::getIsDeleted, 0).
                orderByDesc(Address::getIsDefault).
                orderByDesc(Address::getUpdateTime);
        List<Address> addressList = addressService.list(lambdaQueryWrapper);

        return ResultVo.success(addressList);
    }

    /**
     * @Description: 添加地址
     * @Param: [address]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/1 21:17
     */
    @PostMapping
    public ResultVo addAddress(@RequestBody Address address) {
        Address defaultAddress = getDefaultAddressByUserId();
        address.setUserId(ThreadLocalForId.get());
        // 没有默认地址时设为默认地址
        if (defaultAddress == null) {
            address.setIsDefault(1);
        } else {
            address.setIsDefault(0);
        }
        addressService.save(address);

        return ResultVo.success(null);
    }

    /**
     * @Description: 根据地址 id 取得地址
     * @Param: [addressId]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/1 21:36
     */
    @GetMapping("/{addressId}")
    public ResultVo getAddressById(@PathVariable Long addressId) {
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Address::getAddressId, addressId)
                .eq(Address::getIsDeleted, 0);
        Address address = addressService.getOne(lambdaQueryWrapper);
        return ResultVo.success(address);
    }

    /**
     * @Description: 更新地址
     * @Param: [address]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/1 21:37
     */
    @PutMapping
    public ResultVo updateAddress(@RequestBody Address address) {
        addressService.updateById(address);
        return ResultVo.success(null);
    }

    /**
     * @Description: 设置默认地址
     * @Param: [map] 地址 id
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/1 21:37
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/default")
    public ResultVo setDefault(@RequestBody Map map) {

        String id = (String) map.get("addressId");
        long addressId = Long.parseLong(id);

        // 将之前的默认地址设为不默认
        long userId = ThreadLocalForId.get();
        LambdaUpdateWrapper<Address> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Address::getIsDefault, 0)
                // .eq(Address::getIsDefault, 1)
                .eq(Address::getUserId, userId);
        addressService.update(lambdaUpdateWrapper);

        // 将地址设为默认地址
        lambdaUpdateWrapper.clear();
        lambdaUpdateWrapper.set(Address::getIsDefault, 1)
                .eq(Address::getAddressId, addressId);
        addressService.update(lambdaUpdateWrapper);

        return ResultVo.success(null);
    }

    /**
     * @Description: 获取当前用户默认地址
     * @Param: []
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/1 22:37
     */
    @GetMapping("/default")
    public ResultVo getDefaultAddress() {

        Address address = getDefaultAddressByUserId();
        if (address == null) {
            return ResultVo.fail("当前用户没有默认地址");
        }
        return ResultVo.success(address);
    }

    /**
     * @Description: 获取当前用户默认地址
     * @Param: []
     * @Return: com.killstan.takeout.entity.po.Address
     * 不存在则为null
     * @Author Kill_Stan
     * @Date 2022/12/25 23:13
     */
    private Address getDefaultAddressByUserId() {
        long userId = ThreadLocalForId.get();
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Address::getIsDefault, 1)
                .eq(Address::getUserId, userId)
                .eq(Address::getIsDeleted, 0);
        Address address = addressService.getOne(lambdaQueryWrapper);
        return address;
    }


}

