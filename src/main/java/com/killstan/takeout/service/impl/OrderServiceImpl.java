package com.killstan.takeout.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.killstan.takeout.entity.po.Address;
import com.killstan.takeout.entity.po.OrderDetail;
import com.killstan.takeout.entity.po.Orders;
import com.killstan.takeout.entity.vo.OrderVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.entity.vo.ShoppingCartVo;
import com.killstan.takeout.mapper.po.OrderMapper;
import com.killstan.takeout.service.AddressService;
import com.killstan.takeout.service.OrderDetailService;
import com.killstan.takeout.service.OrderService;
import com.killstan.takeout.service.ShoppingCartService;
import com.killstan.takeout.util.ConstantUtil;
import com.killstan.takeout.util.ThreadLocalForId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    private final RedisTemplate redisTemplate;

    private final AddressService addressService;

    private final OrderDetailService orderDetailService;

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public OrderServiceImpl(RedisTemplate redisTemplate, AddressService addressService, OrderDetailService orderDetailService, ShoppingCartService shoppingCartService) {
        this.redisTemplate = redisTemplate;
        this.addressService = addressService;
        this.orderDetailService = orderDetailService;
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * @Description: 提交订单
     * @Param: [orderVo]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/27 14:36
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo submitOrder(OrderVo orderVo) {
        // 根据 userId 获取购物车信息
        Long userId = ThreadLocalForId.get();
        Object obj = redisTemplate.opsForValue().get(ConstantUtil.REDIS_SHOP_CART + userId);
        List<ShoppingCartVo> shoppingCartVoList = null;
        LocalDateTime now = LocalDateTime.now();
        if (obj == null) {
            // redis 中不存在时，从数据库中取
            shoppingCartVoList = shoppingCartService.listShoppingCart();
            if (shoppingCartVoList.size() == 0) {
                log.warn(now + ConstantUtil.SEPARATOR_LINE + "提交订单是购物车无数据，用户id：" + userId);
                return ResultVo.fail("购物车中无商品数据，请添加后重试");
            }
        } else {
            shoppingCartVoList = (List<ShoppingCartVo>) obj;
        }

        // 获取地址信息
        Long addressId = orderVo.getAddressId();
        Address address = addressService.getById(addressId);

        // 根据购物车、地址信息，算出价格并填充订单信息
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setOrderTime(now);
        String consignee = address.getConsignee();
        String consigneePhone = address.getConsigneePhone();
        String userAddress = address.getAddress();
        orders.setConsignee(consignee);
        orders.setConsigneePhone(consigneePhone);
        orders.setAddress(userAddress);
        orders.setRemark(orderVo.getRemark());
        // 设置状态为未付款
        orders.setStatus(0);
        orders.setPayMethod(orderVo.getPayMethod());
        Long orderId = IdWorker.getId(orders);
        orders.setOrderId(orderId);
        List<OrderDetail> orderDetailList = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal("0");
        for (ShoppingCartVo shoppingCartVo : shoppingCartVoList) {
            // 计算总价钱
            Integer count = shoppingCartVo.getCount();
            BigDecimal countBd = BigDecimal.valueOf(count);
            BigDecimal price = shoppingCartVo.getPrice();
            totalPrice = totalPrice.add(price.multiply(countBd));
            // 给订单详细赋值
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            if (shoppingCartVo.getDishId() != null) {
                orderDetail.setDishId(shoppingCartVo.getDishId());
                orderDetail.setDishName(shoppingCartVo.getDishName());
            } else {
                orderDetail.setComboId(shoppingCartVo.getComboId());
                orderDetail.setComboName(shoppingCartVo.getComboName());
            }
            orderDetail.setCount(shoppingCartVo.getCount());
            orderDetail.setPrice(price);
            orderDetail.setFlavor(shoppingCartVo.getFlavor());
            orderDetail.setImageUrl(shoppingCartVo.getImageUrl());

            orderDetailList.add(orderDetail);
        }
        orders.setPrice(totalPrice);

        // 更新订单信息，提交订单
        save(orders);
        orderDetailService.saveBatch(orderDetailList);

        // 删除购物车信息
        shoppingCartService.deleteUserShoppingCart();

        return ResultVo.success(null);
    }
}
