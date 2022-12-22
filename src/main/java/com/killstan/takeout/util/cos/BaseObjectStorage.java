package com.killstan.takeout.util.cos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/8/2 11:53
 */
public abstract class BaseObjectStorage {

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return 文件url
     */
    public abstract String upload(MultipartFile multipartFile);

    /**
     * 授权
     *
     * @param pathAndName
     * @param time
     * @return
     */
    public abstract String authorize(String pathAndName, long time);

    /**
     * 授权(路径全)
     *
     * @param pathAndName
     * @param time
     * @return
     */
    public abstract String authorizeAllName(String pathAndName, long time);

    /**
     * 临时上传文件授权
     *
     * @param dir
     * @return
     */
    public abstract Map<String, Object> tokens(String dir);

    /**
     * 删除文件
     *
     * @param pathAndName
     */
    public abstract void deleteFile(String pathAndName);

}
