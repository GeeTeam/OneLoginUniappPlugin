package com.geetest.onelogin;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.geetest.onelogin.listener.AbstractOneLoginListener;
import com.taobao.weex.bridge.JSCallback;

import static com.geetest.onelogin.Constants.*;

public class RequestTokenListener extends AbstractOneLoginListener {

    private JSCallback requestTokenListener;

    public RequestTokenListener(JSCallback callback) {
        this.requestTokenListener = callback;
    }

    @Override
    public void onResult(org.json.JSONObject jsonObject) {
        JSONObject fJson = JsonUtils.oJsonToFJson(jsonObject);
        try {
            fJson.put(JS_RESULT_CODE, JS_RESULT_ID_ON_RESULT);
        } catch (Exception e) {
        }
        requestTokenListener.invokeAndKeepAlive(fJson);
    }

    @Override
    public void onPrivacyClick(String name, String url) {
        super.onPrivacyClick(name, url);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_PRIVACY_CLICK);
            jsonObject.put("name", name);
            jsonObject.put("url", url);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }

    @Override
    public void onPrivacyCheckBoxClick(boolean isChecked) {
        super.onPrivacyCheckBoxClick(isChecked);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_PRIVACY_CHECKBOX_CLICK);
            jsonObject.put("isChecked", isChecked);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }

    @Override
    public void onLoginButtonClick() {
        super.onLoginButtonClick();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_LOGIN_BUTTON_CLICK);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }

    @Override
    public void onLoginLoading() {
        super.onLoginLoading();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_LOGIN_LOADING);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }

    @Override
    public void onAuthActivityCreate(Activity activity) {
        super.onAuthActivityCreate(activity);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_AUTH_ACTIVITY_CREATE);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }

    @Override
    public void onAuthWebActivityCreate(Activity activity) {
        super.onAuthWebActivityCreate(activity);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_AUTH_WEB_ACTIVITY_CREATE);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestTokenSecurityPhone(String phone) {
        super.onRequestTokenSecurityPhone(phone);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JS_RESULT_CODE, JS_RESULT_ID_ON_REQUEST_SECURITY_PHONE);
            jsonObject.put("phone", phone);
            requestTokenListener.invokeAndKeepAlive(jsonObject);
        } catch (Exception e) {
        }
    }
}
