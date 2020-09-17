package com.geetest.onelogin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonUtils {
    public static com.alibaba.fastjson.JSONObject oJsonToFJson(org.json.JSONObject jsonObject) {
        com.alibaba.fastjson.JSONObject newJson = com.alibaba.fastjson.JSONObject.parseObject(jsonObject.toString());
        return newJson;
    }
}
