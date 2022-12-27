package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.killstan.takeout.entity.vo.OrderVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * @Description: 提交订单
     * @Param: [orderVo]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/27 14:37
     */
    @PostMapping("/submit")
    public ResultVo submit(@RequestBody OrderVo orderVo) {
        ResultVo resultVo = orderService.submitOrder(orderVo);
        return resultVo;
    }

    @GetMapping("/userPage")
    public ResultVo getUserOrder(Integer page, Integer pageSize) {
        IPage userOrders = orderService.getUserOrders(page, pageSize);
        return ResultVo.success(userOrders);

    }

}

