package com.killstan.takeout.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.killstan.takeout.entity.po.Combo;
import com.killstan.takeout.entity.vo.ComboVo;
import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 除掉已删除的套餐
     *
     * @param page      当前页
     * @param pageSize  页面大小
     * @param comboName 套餐名，可为空
     * @return
     */
    @GetMapping("/page")
    public ResultVo listCombo(Integer page, Integer pageSize, @RequestParam(required = false) String comboName) {

        ResultVo<IPage<ComboVo>> iPageResultVo = comboService.listCombo(page, pageSize, comboName);

        return iPageResultVo;
    }

    /**
     * 添加套餐
     *
     * @param comboVo
     * @return
     */
    @PostMapping
    public ResultVo addCombo(@RequestBody ComboVo comboVo) {
        ResultVo resultVo = comboService.addCombo(comboVo);
        return resultVo;
    }

    /**
     * @param status
     * @param comboIds
     * @return
     */
    @PostMapping("/status/{status}")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo updateStatus(@PathVariable("status") Integer status, @RequestParam List<Long> comboIds) {

        if (comboIds.size() == 0) {
            return ResultVo.fail("停售失败，参数错误，请刷新后重试");
        }
        // TODO 采用updateBatch会快一点（减少数据库连接的使用），采用真正的批量更新会更快
        LambdaUpdateWrapper<Combo> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        comboIds.forEach(comboId -> {
            if (!"".equals(comboId)) {
                lambdaUpdateWrapper.eq(Combo::getComboId, comboId);
                lambdaUpdateWrapper.set(Combo::getStatus, status);
                comboService.update(lambdaUpdateWrapper);
                lambdaUpdateWrapper.clear();
            }
        });

        return ResultVo.success(null);
    }

    /**
     * 删除套餐
     *
     * @param comboIds
     * @return
     */
    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public ResultVo deleteCombo(@RequestParam List<Long> comboIds) {

        // TODO 采用updateBatch会快一点（减少数据库连接的使用），采用真正的批量更新会更快
        LambdaUpdateWrapper<Combo> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        comboIds.forEach(comboId -> {
            if (!"".equals(comboId)) {
                lambdaUpdateWrapper.eq(Combo::getComboId, comboId);
                lambdaUpdateWrapper.set(Combo::getIsDeleted, 1);
                comboService.update(lambdaUpdateWrapper);
                lambdaUpdateWrapper.clear();
            }
        });

        return ResultVo.success(null);
    }

    // TODO 修改套餐
    // @GetMapping("{comboId}")
    // public ResultVo getComboById(@PathVariable Long comboId){
    //     Combo combo = comboService.getById(comboId);
    //     return ResultVo.success(combo);
    // }

    /**
     * @Description: 根据分类 id 获取套餐
     * @Param: [categoryId, status]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/5 21:31
     */
    @GetMapping("/list")
    public ResultVo getComboByCategoryId(Long categoryId, Integer status) {

        LambdaQueryWrapper<Combo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Combo::getCategoryId, categoryId)
                .eq(Combo::getStatus, status)
                .eq(Combo::getIsDeleted, 0);
        List<Combo> combos = comboService.list(lambdaQueryWrapper);
        return ResultVo.success(combos);
    }

    /**
     * @Description: 获取套餐内的菜品
     * @Param: [comboId]
     * @Return: com.killstan.takeout.entity.vo.ResultVo
     * @Author Kill_Stan
     * @Date 2022/12/25 15:51
     */
    @GetMapping("/dish/{comboId}")
    public ResultVo getDishByComboId(@PathVariable Long comboId) {

        ResultVo dishByComboId = comboService.getDishByComboId(comboId);
        return dishByComboId;
    }

}

