package com.geetest.onelogin;

import android.content.Context;

import com.geetest.onepassv2.OnePassHelper;
import com.taobao.weex.bridge.JSCallback;

public class OnePassUtils {

    public OnePassUtils() {
    }

    public void init(Context context, String appId, int timeout) {
        OnePassHelper.with().init(context, appId, timeout);
    }

    public void getToken(String phone, boolean encrypt, JSCallback verifyListener) {
        VerifyPhoneListener verifyPhoneListener = new VerifyPhoneListener(verifyListener, encrypt);
        OnePassHelper.with().getToken(phone, verifyPhoneListener);
    }
}
