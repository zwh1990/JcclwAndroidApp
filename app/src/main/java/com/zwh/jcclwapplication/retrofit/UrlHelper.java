package com.zwh.jcclwapplication.retrofit;


import androidx.collection.ArrayMap;

import com.zwh.jcclwapplication.utils.JSONUitl;
import com.zwh.jcclwapplication.utils.LOGUtils;

/**
 * @author by zwh
 * @description：请求路径配置类
 * @date 2021/2/3 10:33
 * @邮箱：zhaowh@zgjzd.cn
 */
public class UrlHelper {

    public static final String TAG = UrlHelper.class.getSimpleName();

    public static String API_SERVER_URL = "http://192.168.8.110:8080/learnspringboot/";

    /**
     * 提交图片接口  savePicture
     */
    public static final String SAVEPICTURE ="savePictures";

    /**
     * 获取图片接口  getPictures
     */
    public static final String GETPICTURE ="getPictures";

    public static String setParameterJson(Object... strings) {
        ArrayMap<String,Object> data = new ArrayMap<>();
        switch (strings[0].toString()) {
            case SAVEPICTURE:
                data.put("list", strings[1]);
                break;
            case GETPICTURE:
                data.put("key", strings[1]);
                break;
            default:
                break;

        }
        String parameJson = JSONUitl.obj2Json(data);
        LOGUtils.w(TAG, "parameJson--->" + parameJson);
        return parameJson;
    }

}
