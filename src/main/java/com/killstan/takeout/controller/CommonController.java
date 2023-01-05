package com.killstan.takeout.controller;

import com.killstan.takeout.entity.vo.ResultVo;
import com.killstan.takeout.util.cos.BaseObjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/10/20 12:23
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    private final BaseObjectStorage objectStorage;

    @Autowired
    public CommonController(BaseObjectStorage objectStorage) {
        this.objectStorage = objectStorage;
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResultVo<String> upload(@RequestPart("file") MultipartFile file) {
        // 将图片上传到 cos 服务器，返回图片的 Url
        String imageUrl = objectStorage.upload(file);

        return ResultVo.success(imageUrl);
    }

}
