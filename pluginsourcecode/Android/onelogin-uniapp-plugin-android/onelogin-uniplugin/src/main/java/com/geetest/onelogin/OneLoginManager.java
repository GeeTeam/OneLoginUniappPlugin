package com.geetest.onelogin;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.geetest.onepassv2.OnePassHelper;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import com.alibaba.fastjson.JSONObject;

import static com.geetest.onelogin.Constants.*;

public class OneLoginManager extends WXSDKEngine.DestroyableModule {

    private OneLoginUtils oneLoginUtils;
    private OnePassUtils onePassUtils;

    public OneLoginManager() {
        super();
    }

    @JSMethod(uiThread = false)
    public JSONObject getScreenInfo() {
        Context context = this.mWXSDKInstance.getContext().getApplicationContext();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("screenWidth", screenWidth);
        jsonObject.put("screenHeight", screenHeight);
        jsonObject.put("density", density);
        Log.d(TAG, "getScreenInfo jsonObject=" + jsonObject);
        return jsonObject;
    }

    @JSMethod(uiThread = true)
    public void setLogEnabled(boolean openDebug) {
        Log.i(TAG, "setLogEnabled openDebug=" + openDebug);
        OneLoginHelper.with().setLogEnable(openDebug);
        OnePassHelper.with().openDebug(openDebug);
    }

    @JSMethod(uiThread = false)
    public String sdkVersion() {
        Log.i(TAG, "sdkVersion");
        return OneLoginHelper.with().sdkVersion();
    }

    @JSMethod(uiThread = true)
    public void register(JSONObject jsonObject, JSCallback registerListener) {
        Log.i(TAG, "register jsonObject=" + jsonObject);
        boolean result = false;
        String msg;
        try {
            String appId = jsonObject.getString("appid");
            int timeout = jsonObject.getInteger("timeout");
            Context context = this.mWXSDKInstance.getContext().getApplicationContext();
            Log.i(TAG, "register context=" + context + ", pkgName=" + context.getPackageName());
            Log.i(TAG, "register appId=" + appId + ", timeout=" + timeout);
            oneLoginUtils = new OneLoginUtils(this.mWXSDKInstance.getBundleUrl());
            oneLoginUtils.init(context, appId);
            oneLoginUtils.register(timeout);
            result = true;
            msg = "register success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "register error:" + e.toString();
        }

        JSONObject resultJson = new JSONObject();
        if (result) {
            resultJson.put(JS_RESULT_CODE, SUCCESS);
            resultJson.put(JS_RESULT_MESSAGE, msg);
        } else {
            resultJson.put(JS_RESULT_CODE, FAILURE);
            resultJson.put(JS_RESULT_MESSAGE, msg);
        }
        registerListener.invoke(resultJson);
    }

    @JSMethod(uiThread = true)
    public void requestToken(JSONObject jsonObject, JSCallback requestTokenListener) {
        Log.i(TAG, "requestToken jsonObject=" + jsonObject + ", oneLoginUtils=" + oneLoginUtils);
        if (oneLoginUtils == null) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(JS_RESULT_CODE, FAILURE);
            resultJson.put("msg", "Please call register before calling requestToken");
            requestTokenListener.invoke(resultJson);
            return;
        }
        oneLoginUtils.requestToken(jsonObject, requestTokenListener);
    }

    @JSMethod(uiThread = true)
    public void dismissAuthActivity() {
        Log.i(TAG, "dismissAuthActivity");
        OneLoginHelper.with().dismissAuthActivity();
    }

    @JSMethod(uiThread = false)
    public boolean isPreGetTokenResultValidate() {
        Log.i(TAG, "isPreGetTokenResultValidate");
        return OneLoginHelper.with().isPreGetTokenResultValidate();
    }

    @JSMethod(uiThread = true)
    public void initWithCustomID(String appId, int timeout, JSCallback initListener) {
        boolean result = false;
        String msg;
        try {
            Context context = this.mWXSDKInstance.getContext().getApplicationContext();
            Log.i(TAG, "register context=" + context + ", pkgName=" + context.getPackageName());
            onePassUtils = new OnePassUtils();
            onePassUtils.init(context, appId, timeout);
            result = true;
            msg = "init success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "init error:" + e.toString();
        }

        JSONObject resultJson = new JSONObject();
        if (result) {
            resultJson.put(JS_RESULT_CODE, SUCCESS);
            resultJson.put(JS_RESULT_MESSAGE, msg);
        } else {
            resultJson.put(JS_RESULT_CODE, FAILURE);
            resultJson.put(JS_RESULT_MESSAGE, msg);
        }
        initListener.invoke(resultJson);
    }

    @JSMethod(uiThread = true)
    public void verifyPhoneNumber(JSONObject jsonObject, final JSCallback verifyListener) {
        if (onePassUtils == null) {
            JSONObject resultJson = new JSONObject();
            resultJson.put(JS_RESULT_CODE, FAILURE);
            resultJson.put("msg", "Please call initWithCustomID before calling verifyPhoneNumber");
            verifyListener.invoke(resultJson);
            return;
        }
        String phone = jsonObject.getString("phone");
        boolean isEncryptPhone = jsonObject.getBoolean("encryptPhone");
        onePassUtils.getToken(phone, isEncryptPhone, verifyListener);
    }

    @Override
    public void destroy() {
        oneLoginUtils = null;
        onePassUtils = null;
    }
}
