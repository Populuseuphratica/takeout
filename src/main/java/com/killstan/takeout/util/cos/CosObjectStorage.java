package com.killstan.takeout.util.cos;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/8/2 11:53
 */
@Component
public class CosObjectStorage extends BaseObjectStorage {

    @Autowired
    private CosInfo cosInfo;

    private COSClient init() {
        COSCredentials cred = new BasicCOSCredentials(cosInfo.getAccessKeyId(), cosInfo.getAccessKeySecret());
        Region region = new Region(cosInfo.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        COSClient cosClient = init();
        String newFileName = cosInfo.getRootDirectory() + UUID.randomUUID() + multipartFile.getOriginalFilename();
        try {
            File image = File.createTempFile("temp", null);
            multipartFile.transferTo(image);

            // 存入文件
            PutObjectRequest putObjectRequest = new PutObjectRequest(cosInfo.getBucketName(), newFileName, image);
            cosClient.putObject(putObjectRequest);

            // 返回文件url
            return cosInfo.getPath() + newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return null;
    }

    @Override
    public String authorize(String pathAndName, long time) {
        COSClient cosClient = init();
        try {
            Date expiration = new Date(System.currentTimeMillis() + time);
            URL url = cosClient.generatePresignedUrl(cosInfo.getBucketName(),
                    cosInfo.getRootDirectory() + pathAndName, expiration);
            return url.toString();
        } finally {
            cosClient.shutdown();
        }
    }

    @Override
    public String authorizeAllName(String pathAndName, long time) {
        COSClient cosClient = init();
        try {
            Date expiration = new Date(System.currentTimeMillis() + time);
            URL url = cosClient.generatePresignedUrl(cosInfo.getBucketName(), pathAndName, expiration);
            return url.toString();
        } finally {
            cosClient.shutdown();
        }
    }

    @Override
    public Map<String, Object> tokens(String dir) {
        Map<String, Object> result = new HashMap<>();
        COSClient cosClient = init();
        try {
            long expireTime = 60;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            COSCredentials cred = new BasicCOSCredentials(cosInfo.getAccessKeyId(), cosInfo.getAccessKeySecret());
            COSSigner signer = new COSSigner();
            dir = "frontend/xhh/" + dir;
            String sign = signer.buildAuthorizationStr(HttpMethodName.PUT, dir, cred, expiration);
            result.put("storeType", "cos");
            result.put("storageType", "cos");
            result.put("accessId", cosInfo.getAccessKeyId());
            result.put("signature", sign);
            result.put("expire", String.valueOf(expireEndTime / 1000));
            result.put("dir", dir);
            result.put("host", cosInfo.getPath().split("://")[0] + "://" + cosInfo.getBucketName() + "."
                    + cosInfo.getPath().split("://")[1]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return result;
    }

    @Override
    public void deleteFile(String pathAndName) {
        COSClient cosClient = init();
        try {
            cosClient.deleteObject(cosInfo.getBucketName(), cosInfo.getRootDirectory() + pathAndName);
        } finally {
            cosClient.shutdown();
        }

    }

}
