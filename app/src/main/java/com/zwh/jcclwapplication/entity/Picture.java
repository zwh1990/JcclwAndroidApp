package com.zwh.jcclwapplication.entity;

/**
 * @author by zwh
 * @description:图片实体类
 * @date Created in 2021/8/26 16:23
 */
public class Picture {

    /**
     * 主键id
     */
    private String id;
    /**
     * 图片查询key
     */
    private String key;
    /**
     * 查询图片的名字
     */
    private String name;
    /**
     * 图片base64编码
     */
    private String content;
    /**
     * 下载图片的路径
     */
    private String url;

    /**
     * 地理位置
     */
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
