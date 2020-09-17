package com.geetest.onelogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geetest.onelogin.config.AuthRegisterViewConfig;
import com.geetest.onelogin.config.OneLoginThemeConfig;

import com.alibaba.fastjson.JSONObject;
import com.geetest.onelogin.listener.CustomInterface;
import com.taobao.weex.bridge.JSCallback;

import java.io.InputStream;
import java.lang.reflect.Field;

import static com.geetest.onelogin.Constants.*;

/**
 * Created by 谷闹年 on 2019/4/1.
 * OneLogin工具类
 */
public class OneLoginUtils {
    private String baseUrl;
    private Context context;

    public OneLoginUtils(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void init(Context context, String appId) {
        this.context = context;
        OneLoginHelper.with().init(context, appId);
    }

    public void register(int timeout) {
        OneLoginHelper.with().register("", timeout);
    }

    public void requestToken(JSONObject jsonObject, JSCallback callback) {
        OneLoginThemeConfig oneLoginThemeConfig = initConfig(jsonObject, callback);
//        printConfig(oneLoginThemeConfig);
//        Log.d(TAG, "***********************************");
//        printConfig(new OneLoginThemeConfig.Builder().build());

        RequestTokenListener tokenListener = new RequestTokenListener(callback);
        OneLoginHelper.with().requestToken(oneLoginThemeConfig, tokenListener);
    }

    private void printConfig(OneLoginThemeConfig config) {
        StringBuffer buffer = new StringBuffer();
        Field[] fields = OneLoginThemeConfig.class.getDeclaredFields();
        int len = fields.length;
        try {
            for (int i = 0; i < len; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                buffer.append(field.getName() + "=" + field.get(config) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "OneLoginThemeConfig:" + buffer.toString());
    }

    /**
     * 关闭 需在页面关闭时候调用
     */
    public void cancel() {
        OneLoginHelper.with().cancel();
    }

//    private OneLoginThemeConfig getThemeConfig(JSONObject jsonObject) {
//        int statusBarColor = getInt(jsonObject, "statusBarColor");
//        int navigationBarColor = getInt(jsonObject, "navigationBarColor");
//        boolean isLightColor  = getBoolean(jsonObject, "isLightColor");
//
//        int navColor = getInt(jsonObject, "navColor");
//        int authNavHeight = getInt(jsonObject, "authNavHeight");
//        boolean authNavTransparent = getBoolean(jsonObject, "authNavTransparent");
//        boolean authNavGone = getBoolean(jsonObject, "authNavGone");
//
//        String navText = getString(jsonObject, "navText");
//        int navTextColor = getInt(jsonObject, "navTextColor");
//        int navTextSize = getInt(jsonObject, "navTextSize");
//        boolean navTextNormal = getBoolean(jsonObject, "navTextNormal");
//        String navWebViewText = getString(jsonObject, "navWebViewText");
//        int navWebViewTextColor = getInt(jsonObject, "navWebViewTextColor");
//        int navWebViewTextSize = getInt(jsonObject, "navWebViewTextSize");
//
////        String navTextTypeface = getString(jsonObject, "navTextTypeface");
////        String navWebViewTextTypeface = getString(jsonObject, "navWebViewTextTypeface");
//
//        String authBGImgPath = getString(jsonObject, "authBGImgPath");
//        String authBgVideoUri = getString(jsonObject, "authBgVideoUri");
//
//        boolean isDialogTheme = getBoolean(jsonObject, "isDialogTheme");
//        int dialogWidth = getInt(jsonObject, "dialogWidth");
//        int dialogHeight = getInt(jsonObject, "dialogHeight");
//        int dialogX = getInt(jsonObject, "dialogX");
//        int dialogY = getInt(jsonObject, "dialogY");
//        boolean isDialogBottom = getBoolean(jsonObject, "isDialogBottom");
//        boolean isWebViewDialogTheme = getBoolean(jsonObject, "isWebViewDialogTheme");
//
//        String navReturnImgPath = getString(jsonObject, "navReturnImgPath");
//        int returnImgWidth = getInt(jsonObject, "returnImgWidth");
//        int returnImgHeight = getInt(jsonObject, "returnImgHeight");
//        boolean navReturnImgHidden = getBoolean(jsonObject, "navReturnImgHidden");
//        int returnImgOffsetX = getInt(jsonObject, "returnImgOffsetX");
//
//        boolean returnImgCenterInVertical;
//        int returnImgOffsetY;
//        if(jsonObject.containsKey("returnImgOffsetY")) {
//            returnImgCenterInVertical = false;
//            returnImgOffsetY = getInt(jsonObject, "returnImgOffsetY");
//        } else {
//            returnImgCenterInVertical = true;
//            returnImgOffsetY = 0;
//        }
//
//        String logoImgPath = getString(jsonObject, "logoImgPath");
//        int logoWidth = getInt(jsonObject, "logoWidth");
//        int logoHeight = getInt(jsonObject, "logoHeight");
//        boolean logoHidden = getBoolean(jsonObject, "logoHidden");
//        int logoOffsetY = getInt(jsonObject, "logoOffsetY");
//        int logoOffsetY_B = getInt(jsonObject, "logoOffsetY_B");
//        int logoOffsetX = getInt(jsonObject, "logoOffsetX");
//
//        int numberColor = getInt(jsonObject, "numberColor");
//        int numberSize = getInt(jsonObject, "numberSize");
//        int numFieldOffsetY = getInt(jsonObject, "numFieldOffsetY");
//        int numFieldOffsetY_B = getInt(jsonObject, "numFieldOffsetY_B");
//        int numFieldOffsetX = getInt(jsonObject, "numFieldOffsetX");
//
////        String numberViewTypeface = getString(jsonObject, "numberViewTypeface");
//
//        String switchText = getString(jsonObject, "switchText");
//        int switchColor = getInt(jsonObject, "switchColor");
//        int switchSize = getInt(jsonObject, "switchSize");
//        boolean switchAccHidden = getBoolean(jsonObject, "switchAccHidden");
//        int switchAccOffsetY = getInt(jsonObject, "switchAccOffsetY");
//        int switchOffsetY_B = getInt(jsonObject, "switchOffsetY_B");
//        int switchOffsetX = getInt(jsonObject, "switchOffsetX");
//
////        String switchViewTypeface = getString(jsonObject, "switchViewTypeface");
//
//        String switchImgPath = getString(jsonObject, "switchImgPath");
//        int switchWidth = getInt(jsonObject, "switchWidth");
//        int switchHeight = getInt(jsonObject, "switchHeight");
//
//        String loginImgPath = getString(jsonObject, "loginImgPath");
//        int logBtnWidth = getInt(jsonObject, "logBtnWidth");
//        int logBtnHeight = getInt(jsonObject, "logBtnHeight");
//        int logBtnOffsetY = getInt(jsonObject, "logBtnOffsetY");
//        int logBtnOffsetY_B = getInt(jsonObject, "logBtnOffsetY_B");
//        int logBtnOffsetX = getInt(jsonObject, "logBtnOffsetX");
//
//        String loginButtonText = getString(jsonObject, "loginButtonText");
//        int loginButtonColor = getInt(jsonObject, "loginButtonColor");
//        int logBtnTextSize = getInt(jsonObject, "logBtnTextSize");
//
////        String logBtnTextViewTypeface = getString(jsonObject, "logBtnTextViewTypeface");
//
//        boolean disableBtnIfUnChecked = getBoolean(jsonObject, "disableBtnIfUnChecked");
//
//        String loadingView = getString(jsonObject, "loadingView");
//        int loadingViewWidth = getInt(jsonObject, "loadingViewWidth");
//        int loadingViewHeight = getInt(jsonObject, "loadingViewHeight");
//        int loadingViewOffsetRight = getInt(jsonObject, "loadingViewOffsetRight");
//
//        boolean loadingViewCenterInVertical;
//        int loadingViewOffsetY;
//        if(jsonObject.containsKey("loadingViewOffsetY")) {
//            loadingViewCenterInVertical = false;
//            loadingViewOffsetY = getInt(jsonObject, "loadingViewOffsetY");
//        } else {
//            loadingViewCenterInVertical = true;
//            loadingViewOffsetY = 0;
//        }
//
//        int sloganColor = getInt(jsonObject, "sloganColor");
//        int sloganSize = getInt(jsonObject, "sloganSize");
//        int sloganOffsetY = getInt(jsonObject, "sloganOffsetY");
//        int sloganOffsetY_B = getInt(jsonObject, "sloganOffsetY_B");
//        int sloganOffsetX = getInt(jsonObject, "sloganOffsetX");
//
////        String sloganViewTypeface = getString(jsonObject, "sloganViewTypeface");
//
//        int privacyLayoutWidth = getInt(jsonObject, "privacyLayoutWidth");
//        int privacyOffsetY = getInt(jsonObject, "privacyOffsetY");
//        int privacyOffsetY_B = getInt(jsonObject, "privacyOffsetY_B");
//        int privacyOffsetX = getInt(jsonObject, "privacyOffsetX");
//        boolean isUseNormalWebActivity = getBoolean(jsonObject, "isUseNormalWebActivity");
//
//        String unCheckedImgPath = getString(jsonObject, "unCheckedImgPath");
//        String checkedImgPath = getString(jsonObject, "checkedImgPath");
//        boolean privacyState = getBoolean(jsonObject, "privacyState");
//        int privacyCheckBoxWidth = getInt(jsonObject, "privacyCheckBoxWidth");
//        int privacyCheckBoxHeight = getInt(jsonObject, "privacyCheckBoxHeight");
//        int privacyCheckBoxOffsetY = 0;
//        if(jsonObject.containsKey("privacyCheckBoxOffsetY")) {
//            privacyCheckBoxOffsetY = getInt(jsonObject, "privacyCheckBoxOffsetY");
//        }
//
//        String privacyTextViewTv1 = getString(jsonObject, "privacyTextViewTv1");
//        String privacyTextViewTv2 = getString(jsonObject, "privacyTextViewTv2");
//        String privacyTextViewTv3 = getString(jsonObject, "privacyTextViewTv3");
//        String privacyTextViewTv4 = getString(jsonObject, "privacyTextViewTv4");
//
//        String clauseNameOne = getString(jsonObject, "clauseNameOne");
//        String clauseUrlOne = getString(jsonObject, "clauseUrlOne");
//        String clauseNameTwo = getString(jsonObject, "clauseNameTwo");
//        String clauseUrlTwo = getString(jsonObject, "clauseUrlTwo");
//        String clauseNameThree = getString(jsonObject, "clauseNameThree");
//        String clauseUrlThree = getString(jsonObject, "clauseUrlThree");
//
////        String privacyClauseTextStrings = getString(jsonObject, "privacyClauseTextStrings");
//
//        int baseClauseColor = getInt(jsonObject, "baseClauseColor");
//        int clauseColor = getInt(jsonObject, "clauseColor");
//        int privacyClauseTextSize = getInt(jsonObject, "privacyClauseTextSize");
//
////        String privacyClauseBaseTextViewTypeface = getString(jsonObject, "privacyClauseBaseTextViewTypeface");
////        String privacyClauseTextViewTypeface = getString(jsonObject, "privacyClauseTextViewTypeface");
//
//        String privacyUnCheckedToastText = getString(jsonObject, "privacyUnCheckedToastText");
//
//        boolean privacyAddFrenchQuotes = getBoolean(jsonObject, "privacyAddFrenchQuotes");
//
//        int privacyTextGravity = getInt(jsonObject, "privacyTextGravity");
//
//
//        OneLoginThemeConfig.Builder builder = new OneLoginThemeConfig.Builder();
//
//        builder.setStatusBar(statusBarColor, navigationBarColor, isLightColor);
//        builder.setAuthNavLayout(navColor, authNavHeight, authNavTransparent, authNavGone);
//        builder.setAuthNavTextView(navText, navTextColor, navTextSize, navTextNormal, navWebViewText, navWebViewTextColor,navWebViewTextSize);
////        builder.setAuthNavTextViewTypeface(navTextTypeface, navWebViewTextTypeface);
//        builder.setAuthBGImgPath(authBGImgPath);
//        builder.setAuthBgVideoUri(authBgVideoUri);
//        builder.setDialogTheme(isDialogTheme, dialogWidth,dialogHeight, dialogX, dialogY, isDialogBottom, isWebViewDialogTheme);
//        builder.setAuthNavReturnImgView(navReturnImgPath, returnImgWidth, returnImgHeight, navReturnImgHidden, returnImgOffsetX);
//        builder.setAuthNavReturnImgView(navReturnImgPath, returnImgWidth, returnImgHeight, navReturnImgHidden, returnImgOffsetX, returnImgOffsetY);
//        builder.setLogoImgView(logoImgPath, logoWidth, logoHeight, logoHidden, logoOffsetY, logoOffsetY_B, logoOffsetX);
//        builder.setNumberView(numberColor, numberSize,numFieldOffsetY, numFieldOffsetY_B, numFieldOffsetX);
////        builder.setNumberViewTypeface(numberViewTypeface);
//        builder.setSwitchView(switchText, switchColor, switchSize, switchAccHidden, switchAccOffsetY,switchOffsetY_B, switchOffsetX);
////        builder.setSwitchViewTypeface(switchViewTypeface);
//        builder.setSwitchViewLayout(switchImgPath, switchWidth, switchHeight);
//        builder.setLogBtnLayout(loginImgPath, logBtnWidth, logBtnHeight, logBtnOffsetY, logBtnOffsetY_B, logBtnOffsetX);
//        builder.setLogBtnTextView(loginButtonText, loginButtonColor, logBtnTextSize);
////        builder.setLogBtnTextViewTypeface(logBtnTextViewTypeface);
//        builder.setLogBtnDisableIfUnChecked(disableBtnIfUnChecked);
//        builder.setLogBtnLoadingView(loadingView, loadingViewWidth, loadingViewHeight, loadingViewOffsetRight);
//        builder.setLogBtnLoadingView(loadingView, loadingViewWidth, loadingViewHeight, loadingViewOffsetRight, loadingViewOffsetY);
//        builder.setSloganView(sloganColor, sloganSize, sloganOffsetY, sloganOffsetY_B, sloganOffsetX);
////        builder.setSloganViewTypeface(sloganViewTypeface);
//        builder.setPrivacyLayout(privacyLayoutWidth, privacyOffsetY, privacyOffsetY_B, privacyOffsetX, isUseNormalWebActivity);
//        builder.setPrivacyCheckBox(unCheckedImgPath, checkedImgPath, privacyState, privacyCheckBoxWidth, privacyCheckBoxHeight);
//        builder.setPrivacyCheckBox(unCheckedImgPath, checkedImgPath, privacyState, privacyCheckBoxWidth, privacyCheckBoxHeight, privacyCheckBoxOffsetY);
//        builder.setPrivacyTextView(privacyTextViewTv1, privacyTextViewTv2, privacyTextViewTv3, privacyTextViewTv4);
//        builder.setPrivacyClauseText(clauseNameOne, clauseUrlOne, clauseNameTwo, clauseUrlTwo, clauseNameThree, clauseUrlThree);
////        builder.setPrivacyClauseTextStrings(String... privacyClauseTextStrings);
//        builder.setPrivacyClauseView(baseClauseColor, clauseColor, privacyClauseTextSize);
////        builder.setPrivacyClauseViewTypeface(privacyClauseBaseTextViewTypeface, privacyClauseTextViewTypeface);
//        builder.setPrivacyUnCheckedToastText(privacyUnCheckedToastText);
//        builder.setPrivacyAddFrenchQuotes(privacyAddFrenchQuotes);
//        builder.setPrivacyTextGravity(privacyTextGravity);
//
//        return builder.build();
//    }

    private OneLoginThemeConfig getThemeConfig(JSONObject jsonObject) {
        OneLoginBean b = null;
        try {
            b = JSONObject.toJavaObject(jsonObject, OneLoginBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getThemeConfig error, convert jsonObject to OneLoginBean failed, jsonObject=" + jsonObject);
        }
        if (b == null) {
            b = new OneLoginBean();
        }
        OneLoginThemeConfig.Builder builder = new OneLoginThemeConfig.Builder();

        builder.setStatusBar(b.statusBarColor, b.navigationBarColor, b.isLightColor)
                .setAuthNavLayout(b.navColor, b.authNavHeight, b.authNavTransparent, b.authNavGone)
                .setAuthNavTextView(b.navText, b.navTextColor, b.navTextSize, b.navWebTextNormal, b.navWebText, b.navWebTextColor, b.navWebTextSize)
                .setAuthNavTextViewTypeface(getTypeface(b.navTextTypefaceName, b.navTextTypefaceBold, b.navTextTypefaceItalic), getTypeface(b.navWebTextTypefaceName, b.navWebTextTypefaceBold, b.navWebTextTypefaceItalic))
                .setAuthBGImgPath(b.authBGImgPath)
                .setAuthBgVideoUri(b.authBgVideoUri)
                .setDialogTheme(b.isDialogTheme, b.dialogWidth, b.dialogHeight, b.dialogX, b.dialogY, b.isDialogBottom, b.isWebViewDialogTheme);
        if (b.returnImgCenterInVertical) {
            builder.setAuthNavReturnImgView(b.returnImgPath, b.returnImgWidth, b.returnImgHeight, b.returnImgHidden, b.returnImgOffsetX);
        } else {
            builder.setAuthNavReturnImgView(b.returnImgPath, b.returnImgWidth, b.returnImgHeight, b.returnImgHidden, b.returnImgOffsetX, b.returnImgOffsetY);
        }
        builder.setLogoImgView(b.logoImgPath, b.logoWidth, b.logoHeight, b.logoHidden, b.logoOffsetY, b.logoOffsetY_B, b.logoOffsetX)
                .setNumberView(b.numberColor, b.numberSize, b.numberOffsetY, b.numberOffsetY_B, b.numberOffsetX)
                .setNumberViewTypeface(getTypeface(b.numberTypefaceName, b.numberTypefaceBold, b.numberTypefaceItalic))
                .setSwitchView(b.switchText, b.switchColor, b.switchSize, b.switchHidden, b.switchOffsetY, b.switchOffsetY_B, b.switchOffsetX)
                .setSwitchViewTypeface(getTypeface(b.switchTypefaceName, b.switchTypefaceBold, b.switchTypefaceItalic))
                .setSwitchViewLayout(b.switchImgPath, b.switchWidth, b.switchHeight)
                .setLogBtnLayout(b.logBtnImgPath, b.logBtnWidth, b.logBtnHeight, b.logBtnOffsetY, b.logBtnOffsetY_B, b.logBtnOffsetX)
                .setLogBtnTextView(b.logBtnText, b.logBtnColor, b.logBtnTextSize)
                .setLogBtnTextViewTypeface(getTypeface(b.logBtnTextTypefaceName, b.logBtnTextTypefaceBold, b.logBtnTextTypefaceItalic))
                .setLogBtnDisableIfUnChecked(b.disableBtnIfUnChecked);
        if (b.loadingViewCenterInVertical) {
            builder.setLogBtnLoadingView(b.loadingView, b.loadingViewWidth, b.loadingViewHeight, b.loadingViewOffsetRight);
        } else {
            builder.setLogBtnLoadingView(b.loadingView, b.loadingViewWidth, b.loadingViewHeight, b.loadingViewOffsetRight, b.loadingViewOffsetY);
        }
        builder.setSloganView(b.sloganColor, b.sloganSize, b.sloganOffsetY, b.sloganOffsetY_B, b.sloganOffsetX)
                .setSloganViewTypeface(getTypeface(b.sloganTypefaceName, b.sloganTypefaceBold, b.sloganTypefaceItalic))
                .setPrivacyLayout(b.privacyLayoutWidth, b.privacyOffsetY, b.privacyOffsetY_B, b.privacyOffsetX, b.isUseNormalWebActivity)
                .setPrivacyCheckBox(b.unCheckedImgPath, b.checkedImgPath, b.privacyState, b.privacyCheckBoxWidth, b.privacyCheckBoxHeight, b.privacyCheckBoxOffsetY)
                .setPrivacyTextView(b.privacyTextViewTv1, b.privacyTextViewTv2, b.privacyTextViewTv3, b.privacyTextViewTv4)
                .setPrivacyClauseText(b.clauseNameOne, b.clauseUrlOne, b.clauseNameTwo, b.clauseUrlTwo, b.clauseNameThree, b.clauseUrlThree)
                .setPrivacyClauseTextStrings(b.privacyClauseTextStrings)
                .setPrivacyClauseView(b.baseClauseColor, b.clauseColor, b.privacyClauseTextSize)
                .setPrivacyClauseViewTypeface(getTypeface(b.privacyClauseBaseTypefaceName, b.privacyClauseBaseTypefaceBold, b.privacyClauseBaseTypefaceItalic), getTypeface(b.privacyClauseTypefaceName, b.privacyClauseTypefaceBold, b.privacyClauseTypefaceItalic))
                .setPrivacyUnCheckedToastText(b.privacyUnCheckedToastText)
                .setPrivacyAddFrenchQuotes(b.privacyAddFrenchQuotes)
                .setPrivacyTextGravity(b.privacyTextGravity);

        return builder.build();
    }

    private Typeface getTypeface(String fontName, boolean bold, boolean italic) {
        int style = Typeface.NORMAL;
        if (bold) {
            style |= Typeface.BOLD;
        }
        if (italic) {
            style |= Typeface.ITALIC;
        }
        Typeface typeface = Typeface.DEFAULT;
        try {
            typeface = Typeface.create(fontName, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeface;
    }

    private void addCustomView(Context context, JSONObject widgetList, JSCallback customViewCallback) {
        if (widgetList == null) {
            return;
        }
        for (int i = 1; i <= widgetList.size(); i++) {
            JSONObject widget = widgetList.getJSONObject("widget" + i);
            addCustomView(widget, customViewCallback);
        }
    }

    private ViewRect getViewRect(JSONObject viewInfo) {
        int left = getInt(viewInfo, "left");
        int top = getInt(viewInfo, "top");
        int right = getInt(viewInfo, "right");
        int bottom = getInt(viewInfo, "bottom");
        int width = getInt(viewInfo, "width");
        int height = getInt(viewInfo, "height");
        ViewRect rect = new ViewRect(left, top, right, bottom, width, height);
        return rect;
    }

    private RelativeLayout.LayoutParams getLayout(ViewRect rect) {
        return getLayout(rect.left, rect.top, rect.right, rect.bottom, rect.width, rect.height);
    }

    private RelativeLayout.LayoutParams getLayout(int left, int top, int right, int bottom, int width, int height) {
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(-2, -2);
        layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (left >= 0) {
            layout.leftMargin = DensityUtils.dip2px(context, left);
            layout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        if (top >= 0) {
            layout.topMargin = DensityUtils.dip2px(context, top);
        }
        if (right >= 0) {
            layout.rightMargin = DensityUtils.dip2px(context, right);
            layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        if (bottom >= 0) {
            layout.bottomMargin = DensityUtils.dip2px(context, bottom);
            layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        if (width >= 0) {
            layout.width = DensityUtils.dip2px(context, width);
        }
        if (height >= 0) {
            layout.height = DensityUtils.dip2px(context, height);
        }
        return layout;
    }

    private void addCustomView(JSONObject widget, final JSCallback customViewCallback) {
        String type = widget.getString("type");
        final String viewId = getString(widget, "viewId");
        final View customView;
        if ("TextView".equals(type)) {
            customView = getCustomTextView(context, widget, customViewCallback);
        } else if ("ImageView".equals(type)) {
            customView = getCustomImageView(context, widget, customViewCallback);
        } else if ("View".equals(type)) {
            customView = getCustomView(context, widget, customViewCallback);
        } else {
            Log.e(TAG, "don't support widgetType-->" + type);
            return;
        }
        final boolean isClickable = customView.isClickable();
        AuthRegisterViewConfig authRegisterViewConfig = new AuthRegisterViewConfig.Builder()
                .setView(customView)
                .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(new CustomInterface() {
                    @Override
                    public void onClick(Context context) {
                        if(!isClickable) {
                            return;
                        }
                        JSONObject result = new JSONObject();
                        result.put(JS_RESULT_CODE, JS_RESULT_ID_ON_CUSTOM_VIEW_CLICK);
                        result.put(JS_RESULT_MESSAGE, viewId);
                        Log.d(TAG, "View:"+viewId+" is clicked, result=" + result);
                        customViewCallback.invokeAndKeepAlive(result);
                    }
                }).build();

        OneLoginHelper.with().addOneLoginRegisterViewConfig("custom_view_" + viewId, authRegisterViewConfig);
    }

    @SuppressLint("NewApi")
    private View getCustomView(Context context, JSONObject viewInfo, final JSCallback customViewCallback) {
        ViewRect rect = getViewRect(viewInfo);
        Log.d(TAG, "rect=" + rect);
        String backgroundColor = getString(viewInfo, "backgroundColor");
        boolean isClickable = getBoolean(viewInfo, "clickable", true);
        ImageView customView = new ImageView(context);
        customView.setClickable(isClickable);
        if (!TextUtils.isEmpty(backgroundColor)) {
            customView.setBackgroundColor(toColor(backgroundColor));
        }
        customView.setLayoutParams(getLayout(rect));

        return customView;
    }

    @SuppressLint("NewApi")
    private View getCustomImageView(Context context, JSONObject viewInfo, final JSCallback customViewCallback) {
        ViewRect rect = getViewRect(viewInfo);
        Log.d(TAG, "rect=" + rect);
        String backgroundColor = getString(viewInfo, "backgroundColor");
        String backgroundImgPath = getString(viewInfo, "backgroundImgPath");
        boolean isClickable = getBoolean(viewInfo, "clickable", true);
        ImageView customView = new ImageView(context);
        customView.setClickable(isClickable);
        if (!TextUtils.isEmpty(backgroundColor)) {
            customView.setBackgroundColor(toColor(backgroundColor));
        }
        if (!TextUtils.isEmpty(backgroundImgPath)) {
            Drawable drawable = toDrawable(backgroundImgPath, context);
            if (drawable != null) {
                customView.setBackground(toDrawable(backgroundImgPath, context));
            }
        }
        customView.setLayoutParams(getLayout(rect));

        return customView;
    }

    @SuppressLint("NewApi")
    private TextView getCustomTextView(Context context, JSONObject viewInfo, final JSCallback customViewCallback) {
        ViewRect rect = getViewRect(viewInfo);
        Log.d(TAG, "rect=" + rect);
        String textContent = getString(viewInfo, "text");
        String font = getString(viewInfo, "font");
        String textColor = getString(viewInfo, "textColor");
        String backgroundColor = getString(viewInfo, "backgroundColor");
        String backgroundImgPath = getString(viewInfo, "backgroundImgPath");
        boolean isClickable = getBoolean(viewInfo, "clickable", true);
        TextView customView = new TextView(context);
        customView.setClickable(isClickable);
        customView.setText(textContent);
        if (!TextUtils.isEmpty(textColor)) {
            customView.setTextColor(toColor(textColor));
        }
        if (!TextUtils.isEmpty(font)) {
            double titleFont = Double.parseDouble(font);
            if (titleFont >= 0.0D) {
                customView.setTextSize((float) titleFont);
            }
        }
        if (!TextUtils.isEmpty(backgroundColor)) {
            customView.setBackgroundColor(toColor(backgroundColor));
        }
        if (!TextUtils.isEmpty(backgroundImgPath)) {
            Drawable drawable = toDrawable(backgroundImgPath, context);
            if (drawable != null) {
                customView.setBackground(toDrawable(backgroundImgPath, context));
            }
        }
        customView.setGravity(17);
        customView.setLayoutParams(getLayout(rect));

        return customView;
    }

    /**
     * 配置页面布局
     *
     * @return config
     */
    private OneLoginThemeConfig initConfig(JSONObject jsonObject, JSCallback customViewCallback) {
        JSONObject themeConfig = jsonObject.getJSONObject("ThemeConfig");
        JSONObject customList = jsonObject.getJSONObject("CustomView");

        OneLoginThemeConfig config = getThemeConfig(themeConfig);

        addCustomView(context, customList, customViewCallback);
        return config;
    }

    private static String getString(JSONObject json, String key) {
        if (null != json && json.containsKey(key)) {
            return json.getString(key);
        }
        return null;
    }

    private static int getInt(JSONObject json, String key) {
        if (null != json && json.containsKey(key) && Utils.isNotEmpty(json.getString(key))) {
            return Integer.parseInt(json.getString(key));
        }
        return -1;
    }

    private static boolean getBoolean(JSONObject json, String key) {
        if (null != json && json.containsKey(key) && Utils.isNotEmpty(json.getString(key))) {
            return Boolean.parseBoolean(json.getString(key));
        }
        return false;
    }

    private static boolean getBoolean(JSONObject json, String key, boolean defaultValue) {
        if (null != json && json.containsKey(key) && Utils.isNotEmpty(json.getString(key))) {
            return Boolean.parseBoolean(json.getString(key));
        }
        return defaultValue;
    }

    private static int toColor(String s) {
        return Color.parseColor(s);
    }

    private Drawable toDrawable(String str, Context context) {
        BitmapDrawable bitmapDrawable = null;
        Bitmap bitmap = null;
        try {
            String imgUrl = baseUrl.substring(baseUrl.indexOf("apps/"), baseUrl.indexOf("www/") + 4) + str;
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(imgUrl);
            bitmap = BitmapFactory.decodeStream(inputStream);
            bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmapDrawable;
    }

    private static JSONObject getJSONObject(JSONObject json, String key) {
        if (null != json && json.containsKey(key)) {
            return json.getJSONObject(key);
        }
        return null;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
