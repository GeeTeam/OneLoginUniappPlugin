package com.geetest.onelogin;

import android.view.Gravity;
import android.view.ViewGroup;

public class OneLoginBean {
    /**
     * 状态栏颜色
     */
    public int statusBarColor = 0;
    /**
     * 状态栏字体颜色 true为黑 false为白
     */
    public boolean isLightColor = false;
    /**
     * 底部导航栏颜色
     */
    public int navigationBarColor = 0;

    /**
     * 标题栏高度
     */
    public int authNavHeight = 49;
    /**
     * 标题栏是否去掉
     */
    public boolean authNavGone = false;
    /**
     * 设置标题栏是否透明
     */
    public boolean authNavTransparent = true;

    /**
     * 标题栏颜色
     */
    public int navColor = 0xFF3973FF;
    /**
     * 标题栏标题文字
     */
    public String navText = "";
    /**
     * 授权页标题栏文字颜色
     */
    public int navTextColor = 0xFFFFFFFF;
    /**
     * 授权页标题栏文字字体大小
     */
    public int navTextSize = 17;
    /**
     * 授权页标题栏文字字体样式
     */
    public String navTextTypefaceName = "";
    /**
     * 授权页标题栏文字字体是否加粗
     */
    public boolean navTextTypefaceBold = false;
    /**
     * 授权页标题栏文字字体是否倾斜
     */
    public boolean navTextTypefaceItalic = false;

    /**
     * 是否使用默认隐私条款页，默认是，否则需要用户自定义隐私条款页
     */
    public boolean isUseNormalWebActivity = true;
    /**
     * 隐私条款页是否使用自定义标题
     */
    public boolean navWebTextNormal = false;
    /**
     * 隐私条款页自定义标题
     */
    public String navWebText = "";
    /**
     * 隐私条款页标题栏文字颜色
     */
    public int navWebTextColor = 0xFF000000;
    /**
     * 隐私条款页标题栏文字大小
     */
    public int navWebTextSize = 17;
    /**
     * 隐私条款页标题栏文字字体样式
     */
    public String navWebTextTypefaceName = "";
    /**
     * 隐私条款页标题栏文字字体是否加粗
     */
    public boolean navWebTextTypefaceBold = false;
    /**
     * 隐私条款页标题栏文字字体是否倾斜
     */
    public boolean navWebTextTypefaceItalic = false;

    /**
     * 标题栏标题文本的左右边距，默认不碰到返回按钮
     */
    public int navTextMargin = 36;

    /**
     * 标题栏返回图标
     */
    public String returnImgPath = "gt_one_login_ic_chevron_left_black";
    /**
     * 导航返回图片的宽度
     */
    public int returnImgWidth = 24;

    /**
     * 导航返回图片的高度
     */
    public int returnImgHeight = 24;
    /**
     * 返回按钮相对于x轴的偏移
     */
    public int returnImgOffsetX = 12;

    /**
     * 返回按钮是否垂直居中显示
     */
    public boolean returnImgCenterInVertical = true;

    /**
     * 返回按钮相对于y轴的偏移
     */
    public int returnImgOffsetY = 0;
    /**
     * 设置返回图片是否隐藏
     */
    public boolean returnImgHidden = false;

    /**
     * 授权页背景
     */
    public String authBGImgPath = "gt_one_login_bg";
    /**
     * 授权页视频背景
     */
    public String authBgVideoUri = null;


    /**
     * 是否使用弹窗模式
     */
    public boolean isDialogTheme = false;
    /**
     * 设置协议条款页面是否开启弹窗
     */
    public boolean isWebViewDialogTheme = false;

    /**
     * 授权页弹窗宽度
     */
    public int dialogWidth = 300;

    /**
     * 授权页弹窗高度
     */
    public int dialogHeight = 500;

    /**
     * 授权页弹窗X偏移量（以屏幕中 ⼼为原点）
     */
    public int dialogX = 0;

    /**
     * 授权页弹窗Y偏移量（以屏幕中 ⼼为原点）
     */
    public int dialogY = 0;

    /**
     * 授权页弹窗是否贴于屏幕底部： true：显示到屏幕底部， dialogY参数设置将⽆效 false：不显示到屏幕底部，以 dialogY参数为准
     */
    public boolean isDialogBottom = false;

    /**
     * logo图片
     */
    public String logoImgPath = "gt_one_login_logo";
    /**
     * logo宽度
     */
    public int logoWidth = 71;
    /**
     * logo高度
     */
    public int logoHeight = 71;
    /**
     * 是否隐藏logo
     */
    public boolean logoHidden = false;
    /**
     * logo x轴偏移量
     */
    public int logoOffsetX = 0;
    /**
     * 设置logo相对于标题栏下边缘y偏移
     */
    public int logoOffsetY = 125;
    /**
     * 设置logo相对于底部y偏移
     */
    public int logoOffsetY_B = 0;


    /**
     * 设置号码字体颜色
     */
    public int numberColor = 0xFF3D424C;
    /**
     * 号码栏字体大小
     */
    public int numberSize = 24;
    /**
     * 号码栏字体样式
     */
    public String numberTypefaceName = "";
    /**
     * 号码栏字体是否加粗
     */
    public boolean numberTypefaceBold = false;
    /**
     * 号码栏字体是否倾斜
     */
    public boolean numberTypefaceItalic = false;

    /**
     * 号码栏偏移量
     */
    public int numberOffsetX = 0;
    /**
     * 号码栏相对于标题栏下边缘y偏移
     */
    public int numberOffsetY = 200;
    /**
     * 号码栏相对于底部y偏移
     */
    public int numberOffsetY_B = 0;

    /**
     *  slogan 是否显示，默认显示
     */
    public boolean sloganVisible = true;

    /**
     * slogan文字颜色
     */
    public int sloganColor = 0xFFA8A8A8;
    /**
     * slogan的字体大小
     */
    public int sloganSize = 10;
    /**
     * slogan的字体样式
     */
    public String sloganTypefaceName = "";
    /**
     * slogan的字体是否加粗
     */
    public boolean sloganTypefaceBold = false;
    /**
     * slogan的字体是否倾斜
     */
    public boolean sloganTypefaceItalic = false;

    /**
     * slogin的x轴偏移
     */
    public int sloganOffsetX = 0;
    /**
     * slogan相对于标题栏下边缘y偏移
     */
    public int sloganOffsetY = 382;

    /**
     * slogan相对于底部y偏移
     */
    public int sloganOffsetY_B = 0;
    /**
     * 运营商品牌栏
     */
    public String sloganText = "";
    /**
     * Slogan 宽度，默认自适应
     */
    public int sloganWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * Slogan 高度，默认自适应
     */
    public int sloganHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 登录按钮文字
     */
    public String logBtnText = "";
    /**
     * 登录按钮宽度
     */
    public int logBtnWidth = 268;

    /**
     * 登录按钮高度
     */
    public int logBtnHeight = 36;
    /**
     * 登录按钮文字颜色
     */
    public int logBtnColor = 0xFFFFFFFF;
    /**
     * 登录按钮字体大小
     */
    public int logBtnTextSize = 15;
    /**
     * 登录按钮字体样式
     */
    public String logBtnTextTypefaceName = "";
    /**
     * 登录按钮字体是否加粗
     */
    public boolean logBtnTextTypefaceBold = false;
    /**
     * 登录按钮字体是否倾斜
     */
    public boolean logBtnTextTypefaceItalic = false;

    /**
     * 登录按钮背景图片
     */
    public String logBtnImgPath = "gt_one_login_btn";
    /**
     * 隐私条款未勾选同意时登录按钮的背景图片
     */
    public String logBtnUncheckedImgPath = "gt_one_login_btn_unchecked";
    /**
     * 登录按钮X轴偏移
     */
    public int logBtnOffsetX = 0;
    /**
     * 登录按钮相对于标题栏下边缘y偏移
     */
    public int logBtnOffsetY = 324;
    /**
     * 登录按钮相对于底部y偏移
     */
    public int logBtnOffsetY_B = 0;
    /**
     * 未勾选复选框时登录按钮是否可用
     */
    public boolean disableBtnIfUnChecked = false;


    /**
     * loading图片地址
     */
    public String loadingView = "umcsdk_load_dot_white";
    /**
     * loading图片宽度
     */
    public int loadingViewWidth = 20;
    /**
     * loading图片高度
     */
    public int loadingViewHeight = 20;
    /**
     * loading对右边的偏移
     */
    public int loadingViewOffsetRight = 12;
    /**
     * loading默认居中
     */
    public boolean loadingViewCenterInVertical = true;
    /**
     * loading Y轴偏移
     */
    public int loadingViewOffsetY = 0;

    /**
     * 切换账号文字
     */
    public String switchText = "切换账号";
    /**
     * 切换账号大小
     */
    public int switchSize = 14;
    /**
     * 切换账号字体颜色
     */
    public int switchColor = 0xFF3973FF;
    /**
     * 切换账号字体样式
     */
    public String switchTypefaceName = "";
    /**
     * 切换账号字体是否加粗
     */
    public boolean switchTypefaceBold = false;
    /**
     * 切换账号字体是否倾斜
     */
    public boolean switchTypefaceItalic = false;

    /**
     * 切换账号是否隐藏
     */
    public boolean switchHidden = false;

    /**
     * 切换账号x轴偏移
     */
    public int switchOffsetX = 0;

    /**
     * 切换账号相对于标题栏下边缘y偏移
     */
    public int switchOffsetY = 249;

    /**
     * 切换账号相对于底部y偏移
     */
    public int switchOffsetY_B = 0;

    /**
     * 切换帐号背景图片路径
     */
    public String switchImgPath = "";

    /**
     * 切换帐号背景宽
     */
    public int switchWidth = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 切换帐号背景高
     */
    public int switchHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 隐私协议X轴
     */
    public int privacyOffsetX = 0;
    /**
     * 隐私条款相对于标题栏下边缘y偏移
     */
    public int privacyOffsetY = 0;

    /**
     * 隐私条款相对于底部y偏移
     */
    public int privacyOffsetY_B = 18;
    /**
     * 隐私协议宽度
     */
    public int privacyLayoutWidth = 256;

    /**
     * 隐私条款连接字符1
     */
    public String privacyTextViewTv1 = "";
    /**
     * 隐私条款连接字符2
     */
    public String privacyTextViewTv2 = "";
    /**
     * 隐私条款连接字符3
     */
    public String privacyTextViewTv3 = "";
    /**
     * 隐私条款连接字符4
     */
    public String privacyTextViewTv4 = "";

    /**
     * 多个开发者隐私条款
     * 按顺序设置，长度为 4 的倍数，配置后优先使用该接口的配置
     */
    public String[] privacyClauseTextStrings;
    /**
     * 开发者隐私条款1
     */
    public String clauseNameOne = "";
    /**
     * 开发者隐私URL1
     */
    public String clauseUrlOne = "";
    /**
     * 开发者隐私条款2
     */
    public String clauseNameTwo = "";
    /**
     * 开发者隐私URL2
     */
    public String clauseUrlTwo = "";
    /**
     * 开发者隐私条款3
     */
    public String clauseNameThree = "";
    /**
     * 开发者隐私URL3
     */
    public String clauseUrlThree = "";

    /**
     * 基础协议颜色
     */
    public int baseClauseColor = 0xFFA8A8A8;
    /**
     * 协议颜色
     */
    public int clauseColor = 0xFF3973FF;
    /**
     * 协议字体大小
     */
    public int privacyClauseTextSize = 10;
    /**
     * 协议栏基础文字字体样式
     */
    public String privacyClauseBaseTypefaceName = "";
    /**
     * 协议栏基础文字字体是否加粗
     */
    public boolean privacyClauseBaseTypefaceBold = false;
    /**
     * 协议栏基础文字字体是否倾斜
     */
    public boolean privacyClauseBaseTypefaceItalic = false;
    /**
     * 协议栏条款文字字体样式
     */
    public String privacyClauseTypefaceName = "";
    /**
     * 协议栏条款文字字体是否加粗
     */
    public boolean privacyClauseTypefaceBold = false;
    /**
     * 协议栏条款文字字体是否倾斜
     */
    public boolean privacyClauseTypefaceItalic = false;

    /**
     * 复选框选中图片
     */
    public String checkedImgPath = "gt_one_login_checked";
    /**
     * 复选框未选中图片
     */
    public String unCheckedImgPath = "gt_one_login_unchecked";
    /**
     * 隐私条款check框默认状态
     */
    public boolean privacyState = false;
    /**
     * 隐私条款check框未选择时点击一键登录按钮提示文字
     */
    public String privacyUnCheckedToastText = "";
    /**
     * 复选框宽度
     */
    public int privacyCheckBoxWidth = 9;
    /**
     * 复选框高度
     */
    public int privacyCheckBoxHeight = 9;
    /**
     * 复选框Y轴偏移
     */
    public int privacyCheckBoxOffsetY = 0;
    /**
     * 复选框与隐私文本之间的间距
     */
    public int privacyCheckBoxMarginRight = 5;

    /**
     * 隐私条款是否增加书名号显示
     */
    public boolean privacyAddFrenchQuotes = false;
    /**
     * 隐私协议文字对齐方式
     */
    public int privacyTextGravity = Gravity.TOP | Gravity.START;

    /**
     * 服务条款未勾选时点击一键登录服务条款执行的动画样式，默认无动画
     * 0：无动画 1：水平振动 2：垂直振动
     */
    public int protocolShakeStyle = 0;

    /**
     * 语言设置，默认中文简体
     * 0：中文简体 1：中文繁体 2：英文
     */
    public int languageType = 0;

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public boolean isLightColor() {
        return isLightColor;
    }

    public void setLightColor(boolean lightColor) {
        isLightColor = lightColor;
    }

    public int getNavigationBarColor() {
        return navigationBarColor;
    }

    public void setNavigationBarColor(int navigationBarColor) {
        this.navigationBarColor = navigationBarColor;
    }

    public int getAuthNavHeight() {
        return authNavHeight;
    }

    public void setAuthNavHeight(int authNavHeight) {
        this.authNavHeight = authNavHeight;
    }

    public boolean isAuthNavGone() {
        return authNavGone;
    }

    public void setAuthNavGone(boolean authNavGone) {
        this.authNavGone = authNavGone;
    }

    public boolean isAuthNavTransparent() {
        return authNavTransparent;
    }

    public void setAuthNavTransparent(boolean authNavTransparent) {
        this.authNavTransparent = authNavTransparent;
    }

    public int getNavColor() {
        return navColor;
    }

    public void setNavColor(int navColor) {
        this.navColor = navColor;
    }

    public String getNavText() {
        return navText;
    }

    public void setNavText(String navText) {
        this.navText = navText;
    }

    public int getNavTextColor() {
        return navTextColor;
    }

    public void setNavTextColor(int navTextColor) {
        this.navTextColor = navTextColor;
    }

    public int getNavTextSize() {
        return navTextSize;
    }

    public void setNavTextSize(int navTextSize) {
        this.navTextSize = navTextSize;
    }

    public String getNavTextTypefaceName() {
        return navTextTypefaceName;
    }

    public void setNavTextTypefaceName(String navTextTypefaceName) {
        this.navTextTypefaceName = navTextTypefaceName;
    }

    public boolean isNavTextTypefaceBold() {
        return navTextTypefaceBold;
    }

    public void setNavTextTypefaceBold(boolean navTextTypefaceBold) {
        this.navTextTypefaceBold = navTextTypefaceBold;
    }

    public boolean isNavTextTypefaceItalic() {
        return navTextTypefaceItalic;
    }

    public void setNavTextTypefaceItalic(boolean navTextTypefaceItalic) {
        this.navTextTypefaceItalic = navTextTypefaceItalic;
    }

    public boolean isUseNormalWebActivity() {
        return isUseNormalWebActivity;
    }

    public void setUseNormalWebActivity(boolean useNormalWebActivity) {
        isUseNormalWebActivity = useNormalWebActivity;
    }

    public boolean isNavWebTextNormal() {
        return navWebTextNormal;
    }

    public void setNavWebTextNormal(boolean navWebTextNormal) {
        this.navWebTextNormal = navWebTextNormal;
    }

    public String getNavWebText() {
        return navWebText;
    }

    public void setNavWebText(String navWebText) {
        this.navWebText = navWebText;
    }

    public int getNavWebTextColor() {
        return navWebTextColor;
    }

    public void setNavWebTextColor(int navWebTextColor) {
        this.navWebTextColor = navWebTextColor;
    }

    public int getNavWebTextSize() {
        return navWebTextSize;
    }

    public void setNavWebTextSize(int navWebTextSize) {
        this.navWebTextSize = navWebTextSize;
    }

    public String getNavWebTextTypefaceName() {
        return navWebTextTypefaceName;
    }

    public void setNavWebTextTypefaceName(String navWebTextTypefaceName) {
        this.navWebTextTypefaceName = navWebTextTypefaceName;
    }

    public boolean isNavWebTextTypefaceBold() {
        return navWebTextTypefaceBold;
    }

    public void setNavWebTextTypefaceBold(boolean navWebTextTypefaceBold) {
        this.navWebTextTypefaceBold = navWebTextTypefaceBold;
    }

    public boolean isNavWebTextTypefaceItalic() {
        return navWebTextTypefaceItalic;
    }

    public void setNavWebTextTypefaceItalic(boolean navWebTextTypefaceItalic) {
        this.navWebTextTypefaceItalic = navWebTextTypefaceItalic;
    }

    public int getNavTextMargin() {
        return navTextMargin;
    }

    public void setNavTextMargin(int navTextMargin) {
        this.navTextMargin = navTextMargin;
    }

    public String getReturnImgPath() {
        return returnImgPath;
    }

    public void setReturnImgPath(String returnImgPath) {
        this.returnImgPath = returnImgPath;
    }

    public int getReturnImgWidth() {
        return returnImgWidth;
    }

    public void setReturnImgWidth(int returnImgWidth) {
        this.returnImgWidth = returnImgWidth;
    }

    public int getReturnImgHeight() {
        return returnImgHeight;
    }

    public void setReturnImgHeight(int returnImgHeight) {
        this.returnImgHeight = returnImgHeight;
    }

    public int getReturnImgOffsetX() {
        return returnImgOffsetX;
    }

    public void setReturnImgOffsetX(int returnImgOffsetX) {
        this.returnImgOffsetX = returnImgOffsetX;
    }

    public boolean isReturnImgCenterInVertical() {
        return returnImgCenterInVertical;
    }

    public void setReturnImgCenterInVertical(boolean returnImgCenterInVertical) {
        this.returnImgCenterInVertical = returnImgCenterInVertical;
    }

    public int getReturnImgOffsetY() {
        return returnImgOffsetY;
    }

    public void setReturnImgOffsetY(int returnImgOffsetY) {
        this.returnImgOffsetY = returnImgOffsetY;
    }

    public boolean isReturnImgHidden() {
        return returnImgHidden;
    }

    public void setReturnImgHidden(boolean returnImgHidden) {
        this.returnImgHidden = returnImgHidden;
    }

    public String getAuthBGImgPath() {
        return authBGImgPath;
    }

    public void setAuthBGImgPath(String authBGImgPath) {
        this.authBGImgPath = authBGImgPath;
    }

    public String getAuthBgVideoUri() {
        return authBgVideoUri;
    }

    public void setAuthBgVideoUri(String authBgVideoUri) {
        this.authBgVideoUri = authBgVideoUri;
    }

    public boolean isDialogTheme() {
        return isDialogTheme;
    }

    public void setDialogTheme(boolean dialogTheme) {
        isDialogTheme = dialogTheme;
    }

    public boolean isWebViewDialogTheme() {
        return isWebViewDialogTheme;
    }

    public void setWebViewDialogTheme(boolean webViewDialogTheme) {
        isWebViewDialogTheme = webViewDialogTheme;
    }

    public int getDialogWidth() {
        return dialogWidth;
    }

    public void setDialogWidth(int dialogWidth) {
        this.dialogWidth = dialogWidth;
    }

    public int getDialogHeight() {
        return dialogHeight;
    }

    public void setDialogHeight(int dialogHeight) {
        this.dialogHeight = dialogHeight;
    }

    public int getDialogX() {
        return dialogX;
    }

    public void setDialogX(int dialogX) {
        this.dialogX = dialogX;
    }

    public int getDialogY() {
        return dialogY;
    }

    public void setDialogY(int dialogY) {
        this.dialogY = dialogY;
    }

    public boolean isDialogBottom() {
        return isDialogBottom;
    }

    public void setDialogBottom(boolean dialogBottom) {
        isDialogBottom = dialogBottom;
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(int logoHeight) {
        this.logoHeight = logoHeight;
    }

    public boolean isLogoHidden() {
        return logoHidden;
    }

    public void setLogoHidden(boolean logoHidden) {
        this.logoHidden = logoHidden;
    }

    public int getLogoOffsetX() {
        return logoOffsetX;
    }

    public void setLogoOffsetX(int logoOffsetX) {
        this.logoOffsetX = logoOffsetX;
    }

    public int getLogoOffsetY() {
        return logoOffsetY;
    }

    public void setLogoOffsetY(int logoOffsetY) {
        this.logoOffsetY = logoOffsetY;
    }

    public int getLogoOffsetY_B() {
        return logoOffsetY_B;
    }

    public void setLogoOffsetY_B(int logoOffsetY_B) {
        this.logoOffsetY_B = logoOffsetY_B;
    }

    public int getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(int numberColor) {
        this.numberColor = numberColor;
    }

    public int getNumberSize() {
        return numberSize;
    }

    public void setNumberSize(int numberSize) {
        this.numberSize = numberSize;
    }

    public String getNumberTypefaceName() {
        return numberTypefaceName;
    }

    public void setNumberTypefaceName(String numberTypefaceName) {
        this.numberTypefaceName = numberTypefaceName;
    }

    public boolean isNumberTypefaceBold() {
        return numberTypefaceBold;
    }

    public void setNumberTypefaceBold(boolean numberTypefaceBold) {
        this.numberTypefaceBold = numberTypefaceBold;
    }

    public boolean isNumberTypefaceItalic() {
        return numberTypefaceItalic;
    }

    public void setNumberTypefaceItalic(boolean numberTypefaceItalic) {
        this.numberTypefaceItalic = numberTypefaceItalic;
    }

    public int getNumberOffsetX() {
        return numberOffsetX;
    }

    public void setNumberOffsetX(int numberOffsetX) {
        this.numberOffsetX = numberOffsetX;
    }

    public int getNumberOffsetY() {
        return numberOffsetY;
    }

    public void setNumberOffsetY(int numberOffsetY) {
        this.numberOffsetY = numberOffsetY;
    }

    public int getNumberOffsetY_B() {
        return numberOffsetY_B;
    }

    public void setNumberOffsetY_B(int numberOffsetY_B) {
        this.numberOffsetY_B = numberOffsetY_B;
    }

    public boolean isSloganVisible() {
        return sloganVisible;
    }

    public void setSloganVisible(boolean sloganVisible) {
        this.sloganVisible = sloganVisible;
    }

    public int getSloganColor() {
        return sloganColor;
    }

    public void setSloganColor(int sloganColor) {
        this.sloganColor = sloganColor;
    }

    public int getSloganSize() {
        return sloganSize;
    }

    public void setSloganSize(int sloganSize) {
        this.sloganSize = sloganSize;
    }

    public String getSloganTypefaceName() {
        return sloganTypefaceName;
    }

    public void setSloganTypefaceName(String sloganTypefaceName) {
        this.sloganTypefaceName = sloganTypefaceName;
    }

    public boolean isSloganTypefaceBold() {
        return sloganTypefaceBold;
    }

    public void setSloganTypefaceBold(boolean sloganTypefaceBold) {
        this.sloganTypefaceBold = sloganTypefaceBold;
    }

    public boolean isSloganTypefaceItalic() {
        return sloganTypefaceItalic;
    }

    public void setSloganTypefaceItalic(boolean sloganTypefaceItalic) {
        this.sloganTypefaceItalic = sloganTypefaceItalic;
    }

    public int getSloganOffsetX() {
        return sloganOffsetX;
    }

    public void setSloganOffsetX(int sloganOffsetX) {
        this.sloganOffsetX = sloganOffsetX;
    }

    public int getSloganOffsetY() {
        return sloganOffsetY;
    }

    public void setSloganOffsetY(int sloganOffsetY) {
        this.sloganOffsetY = sloganOffsetY;
    }

    public int getSloganOffsetY_B() {
        return sloganOffsetY_B;
    }

    public void setSloganOffsetY_B(int sloganOffsetY_B) {
        this.sloganOffsetY_B = sloganOffsetY_B;
    }

    public String getSloganText() {
        return sloganText;
    }

    public void setSloganText(String sloganText) {
        this.sloganText = sloganText;
    }

    public int getSloganWidth() {
        return sloganWidth;
    }

    public void setSloganWidth(int sloganWidth) {
        this.sloganWidth = sloganWidth;
    }

    public int getSloganHeight() {
        return sloganHeight;
    }

    public void setSloganHeight(int sloganHeight) {
        this.sloganHeight = sloganHeight;
    }

    public String getLogBtnText() {
        return logBtnText;
    }

    public void setLogBtnText(String logBtnText) {
        this.logBtnText = logBtnText;
    }

    public int getLogBtnWidth() {
        return logBtnWidth;
    }

    public void setLogBtnWidth(int logBtnWidth) {
        this.logBtnWidth = logBtnWidth;
    }

    public int getLogBtnHeight() {
        return logBtnHeight;
    }

    public void setLogBtnHeight(int logBtnHeight) {
        this.logBtnHeight = logBtnHeight;
    }

    public int getLogBtnColor() {
        return logBtnColor;
    }

    public void setLogBtnColor(int logBtnColor) {
        this.logBtnColor = logBtnColor;
    }

    public int getLogBtnTextSize() {
        return logBtnTextSize;
    }

    public void setLogBtnTextSize(int logBtnTextSize) {
        this.logBtnTextSize = logBtnTextSize;
    }

    public String getLogBtnTextTypefaceName() {
        return logBtnTextTypefaceName;
    }

    public void setLogBtnTextTypefaceName(String logBtnTextTypefaceName) {
        this.logBtnTextTypefaceName = logBtnTextTypefaceName;
    }

    public boolean isLogBtnTextTypefaceBold() {
        return logBtnTextTypefaceBold;
    }

    public void setLogBtnTextTypefaceBold(boolean logBtnTextTypefaceBold) {
        this.logBtnTextTypefaceBold = logBtnTextTypefaceBold;
    }

    public boolean isLogBtnTextTypefaceItalic() {
        return logBtnTextTypefaceItalic;
    }

    public void setLogBtnTextTypefaceItalic(boolean logBtnTextTypefaceItalic) {
        this.logBtnTextTypefaceItalic = logBtnTextTypefaceItalic;
    }

    public String getLogBtnImgPath() {
        return logBtnImgPath;
    }

    public void setLogBtnImgPath(String logBtnImgPath) {
        this.logBtnImgPath = logBtnImgPath;
    }

    public String getLogBtnUncheckedImgPath() {
        return logBtnUncheckedImgPath;
    }

    public void setLogBtnUncheckedImgPath(String logBtnUncheckedImgPath) {
        this.logBtnUncheckedImgPath = logBtnUncheckedImgPath;
    }

    public int getLogBtnOffsetX() {
        return logBtnOffsetX;
    }

    public void setLogBtnOffsetX(int logBtnOffsetX) {
        this.logBtnOffsetX = logBtnOffsetX;
    }

    public int getLogBtnOffsetY() {
        return logBtnOffsetY;
    }

    public void setLogBtnOffsetY(int logBtnOffsetY) {
        this.logBtnOffsetY = logBtnOffsetY;
    }

    public int getLogBtnOffsetY_B() {
        return logBtnOffsetY_B;
    }

    public void setLogBtnOffsetY_B(int logBtnOffsetY_B) {
        this.logBtnOffsetY_B = logBtnOffsetY_B;
    }

    public boolean isDisableBtnIfUnChecked() {
        return disableBtnIfUnChecked;
    }

    public void setDisableBtnIfUnChecked(boolean disableBtnIfUnChecked) {
        this.disableBtnIfUnChecked = disableBtnIfUnChecked;
    }

    public String getLoadingView() {
        return loadingView;
    }

    public void setLoadingView(String loadingView) {
        this.loadingView = loadingView;
    }

    public int getLoadingViewWidth() {
        return loadingViewWidth;
    }

    public void setLoadingViewWidth(int loadingViewWidth) {
        this.loadingViewWidth = loadingViewWidth;
    }

    public int getLoadingViewHeight() {
        return loadingViewHeight;
    }

    public void setLoadingViewHeight(int loadingViewHeight) {
        this.loadingViewHeight = loadingViewHeight;
    }

    public int getLoadingViewOffsetRight() {
        return loadingViewOffsetRight;
    }

    public void setLoadingViewOffsetRight(int loadingViewOffsetRight) {
        this.loadingViewOffsetRight = loadingViewOffsetRight;
    }

    public boolean isLoadingViewCenterInVertical() {
        return loadingViewCenterInVertical;
    }

    public void setLoadingViewCenterInVertical(boolean loadingViewCenterInVertical) {
        this.loadingViewCenterInVertical = loadingViewCenterInVertical;
    }

    public int getLoadingViewOffsetY() {
        return loadingViewOffsetY;
    }

    public void setLoadingViewOffsetY(int loadingViewOffsetY) {
        this.loadingViewOffsetY = loadingViewOffsetY;
    }

    public String getSwitchText() {
        return switchText;
    }

    public void setSwitchText(String switchText) {
        this.switchText = switchText;
    }

    public int getSwitchSize() {
        return switchSize;
    }

    public void setSwitchSize(int switchSize) {
        this.switchSize = switchSize;
    }

    public int getSwitchColor() {
        return switchColor;
    }

    public void setSwitchColor(int switchColor) {
        this.switchColor = switchColor;
    }

    public String getSwitchTypefaceName() {
        return switchTypefaceName;
    }

    public void setSwitchTypefaceName(String switchTypefaceName) {
        this.switchTypefaceName = switchTypefaceName;
    }

    public boolean isSwitchTypefaceBold() {
        return switchTypefaceBold;
    }

    public void setSwitchTypefaceBold(boolean switchTypefaceBold) {
        this.switchTypefaceBold = switchTypefaceBold;
    }

    public boolean isSwitchTypefaceItalic() {
        return switchTypefaceItalic;
    }

    public void setSwitchTypefaceItalic(boolean switchTypefaceItalic) {
        this.switchTypefaceItalic = switchTypefaceItalic;
    }

    public boolean isSwitchHidden() {
        return switchHidden;
    }

    public void setSwitchHidden(boolean switchHidden) {
        this.switchHidden = switchHidden;
    }

    public int getSwitchOffsetX() {
        return switchOffsetX;
    }

    public void setSwitchOffsetX(int switchOffsetX) {
        this.switchOffsetX = switchOffsetX;
    }

    public int getSwitchOffsetY() {
        return switchOffsetY;
    }

    public void setSwitchOffsetY(int switchOffsetY) {
        this.switchOffsetY = switchOffsetY;
    }

    public int getSwitchOffsetY_B() {
        return switchOffsetY_B;
    }

    public void setSwitchOffsetY_B(int switchOffsetY_B) {
        this.switchOffsetY_B = switchOffsetY_B;
    }

    public String getSwitchImgPath() {
        return switchImgPath;
    }

    public void setSwitchImgPath(String switchImgPath) {
        this.switchImgPath = switchImgPath;
    }

    public int getSwitchWidth() {
        return switchWidth;
    }

    public void setSwitchWidth(int switchWidth) {
        this.switchWidth = switchWidth;
    }

    public int getSwitchHeight() {
        return switchHeight;
    }

    public void setSwitchHeight(int switchHeight) {
        this.switchHeight = switchHeight;
    }

    public int getPrivacyOffsetX() {
        return privacyOffsetX;
    }

    public void setPrivacyOffsetX(int privacyOffsetX) {
        this.privacyOffsetX = privacyOffsetX;
    }

    public int getPrivacyOffsetY() {
        return privacyOffsetY;
    }

    public void setPrivacyOffsetY(int privacyOffsetY) {
        this.privacyOffsetY = privacyOffsetY;
    }

    public int getPrivacyOffsetY_B() {
        return privacyOffsetY_B;
    }

    public void setPrivacyOffsetY_B(int privacyOffsetY_B) {
        this.privacyOffsetY_B = privacyOffsetY_B;
    }

    public int getPrivacyLayoutWidth() {
        return privacyLayoutWidth;
    }

    public void setPrivacyLayoutWidth(int privacyLayoutWidth) {
        this.privacyLayoutWidth = privacyLayoutWidth;
    }

    public String getPrivacyTextViewTv1() {
        return privacyTextViewTv1;
    }

    public void setPrivacyTextViewTv1(String privacyTextViewTv1) {
        this.privacyTextViewTv1 = privacyTextViewTv1;
    }

    public String getPrivacyTextViewTv2() {
        return privacyTextViewTv2;
    }

    public void setPrivacyTextViewTv2(String privacyTextViewTv2) {
        this.privacyTextViewTv2 = privacyTextViewTv2;
    }

    public String getPrivacyTextViewTv3() {
        return privacyTextViewTv3;
    }

    public void setPrivacyTextViewTv3(String privacyTextViewTv3) {
        this.privacyTextViewTv3 = privacyTextViewTv3;
    }

    public String getPrivacyTextViewTv4() {
        return privacyTextViewTv4;
    }

    public void setPrivacyTextViewTv4(String privacyTextViewTv4) {
        this.privacyTextViewTv4 = privacyTextViewTv4;
    }

    public String[] getPrivacyClauseTextStrings() {
        return privacyClauseTextStrings;
    }

    public void setPrivacyClauseTextStrings(String[] privacyClauseTextStrings) {
        this.privacyClauseTextStrings = privacyClauseTextStrings;
    }

    public String getClauseNameOne() {
        return clauseNameOne;
    }

    public void setClauseNameOne(String clauseNameOne) {
        this.clauseNameOne = clauseNameOne;
    }

    public String getClauseUrlOne() {
        return clauseUrlOne;
    }

    public void setClauseUrlOne(String clauseUrlOne) {
        this.clauseUrlOne = clauseUrlOne;
    }

    public String getClauseNameTwo() {
        return clauseNameTwo;
    }

    public void setClauseNameTwo(String clauseNameTwo) {
        this.clauseNameTwo = clauseNameTwo;
    }

    public String getClauseUrlTwo() {
        return clauseUrlTwo;
    }

    public void setClauseUrlTwo(String clauseUrlTwo) {
        this.clauseUrlTwo = clauseUrlTwo;
    }

    public String getClauseNameThree() {
        return clauseNameThree;
    }

    public void setClauseNameThree(String clauseNameThree) {
        this.clauseNameThree = clauseNameThree;
    }

    public String getClauseUrlThree() {
        return clauseUrlThree;
    }

    public void setClauseUrlThree(String clauseUrlThree) {
        this.clauseUrlThree = clauseUrlThree;
    }

    public int getBaseClauseColor() {
        return baseClauseColor;
    }

    public void setBaseClauseColor(int baseClauseColor) {
        this.baseClauseColor = baseClauseColor;
    }

    public int getClauseColor() {
        return clauseColor;
    }

    public void setClauseColor(int clauseColor) {
        this.clauseColor = clauseColor;
    }

    public int getPrivacyClauseTextSize() {
        return privacyClauseTextSize;
    }

    public void setPrivacyClauseTextSize(int privacyClauseTextSize) {
        this.privacyClauseTextSize = privacyClauseTextSize;
    }

    public String getPrivacyClauseBaseTypefaceName() {
        return privacyClauseBaseTypefaceName;
    }

    public void setPrivacyClauseBaseTypefaceName(String privacyClauseBaseTypefaceName) {
        this.privacyClauseBaseTypefaceName = privacyClauseBaseTypefaceName;
    }

    public boolean isPrivacyClauseBaseTypefaceBold() {
        return privacyClauseBaseTypefaceBold;
    }

    public void setPrivacyClauseBaseTypefaceBold(boolean privacyClauseBaseTypefaceBold) {
        this.privacyClauseBaseTypefaceBold = privacyClauseBaseTypefaceBold;
    }

    public boolean isPrivacyClauseBaseTypefaceItalic() {
        return privacyClauseBaseTypefaceItalic;
    }

    public void setPrivacyClauseBaseTypefaceItalic(boolean privacyClauseBaseTypefaceItalic) {
        this.privacyClauseBaseTypefaceItalic = privacyClauseBaseTypefaceItalic;
    }

    public String getPrivacyClauseTypefaceName() {
        return privacyClauseTypefaceName;
    }

    public void setPrivacyClauseTypefaceName(String privacyClauseTypefaceName) {
        this.privacyClauseTypefaceName = privacyClauseTypefaceName;
    }

    public boolean isPrivacyClauseTypefaceBold() {
        return privacyClauseTypefaceBold;
    }

    public void setPrivacyClauseTypefaceBold(boolean privacyClauseTypefaceBold) {
        this.privacyClauseTypefaceBold = privacyClauseTypefaceBold;
    }

    public boolean isPrivacyClauseTypefaceItalic() {
        return privacyClauseTypefaceItalic;
    }

    public void setPrivacyClauseTypefaceItalic(boolean privacyClauseTypefaceItalic) {
        this.privacyClauseTypefaceItalic = privacyClauseTypefaceItalic;
    }

    public String getCheckedImgPath() {
        return checkedImgPath;
    }

    public void setCheckedImgPath(String checkedImgPath) {
        this.checkedImgPath = checkedImgPath;
    }

    public String getUnCheckedImgPath() {
        return unCheckedImgPath;
    }

    public void setUnCheckedImgPath(String unCheckedImgPath) {
        this.unCheckedImgPath = unCheckedImgPath;
    }

    public boolean isPrivacyState() {
        return privacyState;
    }

    public void setPrivacyState(boolean privacyState) {
        this.privacyState = privacyState;
    }

    public String getPrivacyUnCheckedToastText() {
        return privacyUnCheckedToastText;
    }

    public void setPrivacyUnCheckedToastText(String privacyUnCheckedToastText) {
        this.privacyUnCheckedToastText = privacyUnCheckedToastText;
    }

    public int getPrivacyCheckBoxWidth() {
        return privacyCheckBoxWidth;
    }

    public void setPrivacyCheckBoxWidth(int privacyCheckBoxWidth) {
        this.privacyCheckBoxWidth = privacyCheckBoxWidth;
    }

    public int getPrivacyCheckBoxHeight() {
        return privacyCheckBoxHeight;
    }

    public void setPrivacyCheckBoxHeight(int privacyCheckBoxHeight) {
        this.privacyCheckBoxHeight = privacyCheckBoxHeight;
    }

    public int getPrivacyCheckBoxOffsetY() {
        return privacyCheckBoxOffsetY;
    }

    public void setPrivacyCheckBoxOffsetY(int privacyCheckBoxOffsetY) {
        this.privacyCheckBoxOffsetY = privacyCheckBoxOffsetY;
    }

    public int getPrivacyCheckBoxMarginRight() {
        return privacyCheckBoxMarginRight;
    }

    public void setPrivacyCheckBoxMarginRight(int privacyCheckBoxMarginRight) {
        this.privacyCheckBoxMarginRight = privacyCheckBoxMarginRight;
    }

    public boolean isPrivacyAddFrenchQuotes() {
        return privacyAddFrenchQuotes;
    }

    public void setPrivacyAddFrenchQuotes(boolean privacyAddFrenchQuotes) {
        this.privacyAddFrenchQuotes = privacyAddFrenchQuotes;
    }

    public int getPrivacyTextGravity() {
        return privacyTextGravity;
    }

    public void setPrivacyTextGravity(int privacyTextGravity) {
        this.privacyTextGravity = privacyTextGravity;
    }

    public int getProtocolShakeStyle() {
        return protocolShakeStyle;
    }

    public void setProtocolShakeStyle(int protocolShakeStyle) {
        this.protocolShakeStyle = protocolShakeStyle;
    }

    public int getLanguageType() {
        return languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public OneLoginBean() {
    }
}
