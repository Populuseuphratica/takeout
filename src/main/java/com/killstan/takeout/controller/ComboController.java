package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.killstan.takeout.entity.vo.ComboVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 套餐表 前端控制器
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/combo")
public class ComboController {

    @Autowired
    private ComboService comboService;


    /**
     * 获取套餐 list
     * @param page 当前页
     * @param pageSize 页面大小
     * @param comboName 套餐名，可为空
     * @return
     */
    @GetMapping("/page")
    public ResultVo listCombo(Integer page, Integer pageSize, @RequestParam(required = false) String comboName) {

        ResultVo<IPage<ComboVo>> iPageResultVo = comboService.listCombo(page, pageSize, comboName);

        return iPageResultVo;
    }

    @PostMapping
    public ResultVo addCombo(@RequestBody ComboVo comboVo){
        ResultVo resultVo = comboService.addCombo(comboVo);
        return resultVo;
    }

//    @DeleteMapping
//    public ResultVo deleteCombo() {
//
//        ResultVo<IPage<ComboVo>> iPageResultVo = comboService.listCombo(page, pageSize, comboName);
//
//        return iPageResultVo;
//    }

}

