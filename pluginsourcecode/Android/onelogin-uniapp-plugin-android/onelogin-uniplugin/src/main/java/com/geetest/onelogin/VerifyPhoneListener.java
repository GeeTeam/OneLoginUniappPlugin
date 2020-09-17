package com.geetest.onelogin;

import com.alibaba.fastjson.JSONObject;
import com.geetest.onepassv2.listener.OnePassListener;
import com.taobao.weex.bridge.JSCallback;

import static com.geetest.onelogin.Constants.FAILURE;
import static com.geetest.onelogin.Constants.JS_RESULT_CODE;
import static com.geetest.onelogin.Constants.SUCCESS;

public class VerifyPhoneListener extends OnePassListener {

    private JSCallback verifyListener;
    private boolean encryptPhone;

    public VerifyPhoneListener(JSCallback callback, boolean encryptPhone) {
        super();
        this.verifyListener = callback;
        this.encryptPhone = encryptPhone;
    }

    @Override
    public boolean onAlgorithm() {
        return encryptPhone;
    }

    @Override
    public boolean onAlgorithmSelf() {
        return super.onAlgorithmSelf();
    }

    @Override
    public String onAlgorithmPhone(String s, String s1) {
        return super.onAlgorithmPhone(s, s1);
    }

    @Override
    public void onTokenFail(org.json.JSONObject jsonObject) {
        JSONObject fJson = JsonUtils.oJsonToFJson(jsonObject);
        try {
            fJson.put(JS_RESULT_CODE, FAILURE);
        } catch (Exception e) {
        }
        verifyListener.invokeAndKeepAlive(fJson);
    }

    @Override
    public void onTokenSuccess(org.json.JSONObject jsonObject) {
        JSONObject fJson = JsonUtils.oJsonToFJson(jsonObject);
        try {
            fJson.put(JS_RESULT_CODE, SUCCESS);
        } catch (Exception e) {
        }
        verifyListener.invokeAndKeepAlive(fJson);
    }
}
