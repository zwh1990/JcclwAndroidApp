package com.zwh.jcclwapplication.widgets;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author by zwh
 * @description：
 * @date 2021/9/17 17:18
 * @邮箱：zhaowh@zgjzd.cn
 */
public class Utilty {

    /**
     * 将图片转化成Base64
     */
    public static String imageToBase64(String Imgpath) {
        InputStream in;
        byte[] data = null;
        try {
            in = new FileInputStream(Imgpath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data,Base64.DEFAULT);
    }
}
