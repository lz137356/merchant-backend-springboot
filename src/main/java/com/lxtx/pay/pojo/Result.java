package com.lxtx.pay.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Result {

    private int code;
    private String msg;
    private JSONObject data;
    private int count;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static JSONObject success(String data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        jsonObject.put("data", data);
        return jsonObject;
    }

    public static JSONObject success(String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        return jsonObject;
    }

    public static JSONObject success(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        jsonObject.put("data", data);
        return jsonObject;
    }

    public static JSONObject success(Object data, int count) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "success");
        jsonObject.put("count", count);
        jsonObject.put("data", data);
        return jsonObject;
    }

    public static JSONObject fail(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", -1);
        jsonObject.put("msg", "fail");
        jsonObject.put("data", JSON.toJSONString(data));
        return jsonObject;
    }

    public static JSONObject fail(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", -1);
        jsonObject.put("msg", msg);
        jsonObject.put("data", null);
        return jsonObject;
    }
}
