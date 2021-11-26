package com.zwh.jcclwapplication.retrofit;

/**
 * @author by zwh
 * @description：请求完成返回的数据
 * @date 2021/2/3 10:45
 * @邮箱：zhaowh@zgjzd.cn
 */

public class BaseResponseData<T> {
    /**
     * 0代表成功
     */
    private int code;

    /**
     * reponse返回的数据
     */
    private T data;

    /**
     * 信息
     */
    private String message;


    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
