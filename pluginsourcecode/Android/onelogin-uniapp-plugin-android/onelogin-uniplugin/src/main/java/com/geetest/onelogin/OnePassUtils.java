package com.geetest.onelogin;

import android.content.Context;

import com.geetest.onepassv2.OnePassHelper;
import com.taobao.weex.bridge.JSCallback;

public class OnePassUtils {
    private String appId;
    private Context context;

    public OnePassUtils() {
    }

    public void init(Context context, String appId, int timeout) {
        this.context = context;
        this.appId = appId;
        OnePassHelper.with().init(context);
        OnePassHelper.with().setConnectTimeout(timeout);
    }

    public void getToken(String phone, boolean encrypt, JSCallback verifyListener) {
        VerifyPhoneListener verifyPhoneListener = new VerifyPhoneListener(verifyListener, encrypt);
        OnePassHelper.with().getToken(phone, appId, verifyPhoneListener);
    }
}
