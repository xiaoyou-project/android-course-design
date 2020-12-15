package com.xiaoyou.face.utils;

import com.aliyun.facebody20191230.Client;
import com.aliyun.facebody20191230.models.AddFaceAdvanceRequest;
import com.aliyun.facebody20191230.models.AddFaceResponse;
import com.aliyun.facebody20191230.models.SearchFaceAdvanceRequest;
import com.aliyun.tearpc.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.facebody.model.v20191230.SearchFaceRequest;
import com.aliyuncs.facebody.model.v20191230.SearchFaceResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 人脸识别的函数
 * @author 小游
 * @date 2020/12/15
 */
public class FaceRead {
    final static String ACCESS_KEY_ID = "LTAI4GFWoMqrMQMm35jdGJ7P";
    final static String ACCESS_KEY_SECRET = "04MjQtHhsyq3mWwWFqNYPEHXFFAM5q";


    public void checkFace2() throws Exception {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //SearchFaceAdvanceRequest request = new SearchFaceAdvanceRequest();
        //File file = new File("D:\\Huawei Share\\OneHop\\IMG_20201215_141438.jpg");
        SearchFaceRequest request = new SearchFaceRequest();
        //InputStream inputStream = new FileInputStream(file);
        request.setSysRegionId("cn-shanghai");
        request.setImageUrl("https://limingkang.oss-cn-shanghai.aliyuncs.com/images/IMG_20201215_141438.jpg");
        request.setLimit(1);
        request.setDbName("default");

        try {
            SearchFaceResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    /**
     * 上传特征值测试
     * @throws Exception 异常
     */
    public static void uploadFeature() throws Exception {
        //这里是个坑，注意别导错包了，如果采用官网的银行卡测试是会失效的
        //银行卡的Client是在com.aliyun.ocr20191230下
        //人脸识别是在com.aliyun.tearpc.models包下，
        Config config = new Config();
        config.setAccessKeyId(ACCESS_KEY_ID);
        //这里填写个人的ACCESSKEY_ID
        config.setAccessKeySecret(ACCESS_KEY_SECRET);
        //这里填写个人的ACCESSKEY_SECRET
        config.setRegionId("cn-shanghai");
        Client client = new Client(config);
        //填写本地文件的位置
        File file = new File("D:\\tmp\\IMG_7647.jpeg");
        InputStream inputStream = new FileInputStream(file);

        //只有request有Advance才能进行本地上传
        AddFaceAdvanceRequest addFaceAdvanceRequest = new AddFaceAdvanceRequest();
        RuntimeOptions runtimeOptions = new RuntimeOptions();
        //这些是基本信息，必填~
        addFaceAdvanceRequest.setDbName("default");
        addFaceAdvanceRequest.setImageUrlObject(inputStream);
        addFaceAdvanceRequest.setEntityId("1806040103");
        addFaceAdvanceRequest.setExtraData("游磊");
        try {
            AddFaceResponse response = client.addFaceAdvance(addFaceAdvanceRequest,runtimeOptions);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    public static String checkFace(InputStream inputStream) throws Exception {
        Config config = new Config();
        config.setAccessKeyId(ACCESS_KEY_ID);
        //这里填写个人的ACCESSKEY_ID
        config.setAccessKeySecret(ACCESS_KEY_SECRET);
        //这里填写个人的ACCESSKEY_SECRET
        config.setRegionId("cn-shanghai");
        Client client = new Client(config);
        //填写本地文件的位置
//        File file = new File("D:\\tmp\\2.jpg");
//        InputStream inputStream = new FileInputStream(file);

        SearchFaceAdvanceRequest request = new SearchFaceAdvanceRequest();
        request.setImageUrlObject(inputStream);
        request.setLimit(1);
        request.setDbName("default");
        RuntimeOptions runtimeOptions = new RuntimeOptions();
        try {
            com.aliyun.facebody20191230.models.SearchFaceResponse searchFaceResponse = client.searchFaceAdvance(request, runtimeOptions);
            System.out.println(new Gson().toJson(searchFaceResponse));
            return new Gson().toJson(searchFaceResponse);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return "";

    }


}
