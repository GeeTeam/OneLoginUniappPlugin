# DCloud 插件集成文档

# 创建应用

登录极验后台创建应用获取 appId 和 key，具体步骤可参照 [账号创建](https://docs.geetest.com/onelogin/overview/account)

# 引用方式

```js
var gtSDKModule = uni.requireNativePlugin("GTOneLoginSDKModule");
```

# Android

## 前置条件

- 极验 SDK 支持 Android Studio 2.1.3 及以上版本，Android 5.0及以上版本
- 极验 SDK uni-app 插件支持 HBuildX 2.7.5 及以上版本
- 极验 SDK 支持中国移动 4G/3G/2G、联通 4G/3G、电信4G（2G/3G 网络下时延相对较高，成功率相对较低） 的取号能力
- 极验 SDK 支持网络环境为：

1. 纯数据网络
2. 数据网络与 Wifi 网络同时开启

- 对于双卡手机，极验 SDK 取当前流量卡号

## 一键登录 API 使用说明

### 初始化

**方法描述**

SDK 初始化并预取号。

**代码示例**

```js
gtSDKModule.register({
        `appid`: "您在极验后台创建应用时生成的 appid",
        `timeout`: 8000  // 取号超时时间
    }, result => {
        //初始化回调
        this.gt_code = JSON.stringify(result.jscode);
        this.gt_result = JSON.stringify(result.jsresult);
    });
```

**参数说明**

- 接口参数说明：

参数|类型|说明
---|---|---
appid|String|极验后台配置唯一产品 APPID，请在官网申请
timeout|int|超时时间，单位:`ms`，取值范围:`1000~15000`，默认`5000`

- 返回参数描述：

字段|类型|含义
---|---|---
jscode|Number|200:成功；其他：失败
jsresult|String|返回信息

### 拉起授权页

- 拉起授权页`requestToken`

**方法描述**

在需要登录的地方调用`requestToken`接口拉起一键登录授权页，待用户点一键登录授权后获取运营商`token`，获取成功后即可请求服务端换取本机手机号码。（调用该方法前可以调用`isPreGetTokenResultValidate`判断预取号是否成功）建议在调用该方法前，提前一段时间进行预取号。请勿频繁的多次调用。

**代码示例**

```js
gtSDKModule.requestToken({'ThemeConfig':themeConfig, 'CustomView':widgetConfig}, (result) => {
    //拉起授权页与一键登录取号回调
    switch(result.jscode) {
        case 100:// onResult
            let status = result.status;
            // status 取值说明参考一键登录官方文档返回码说明:
            // https://docs.geetest.com/onelogin/deploy/android#1%E3%80%81OneLogin%EF%BC%88%E4%B8%80%E9%94%AE%E7%99%BB%E5%BD%95%EF%BC%89
            if(status == 200) { // 获取 token 成功
                let processId = result.process_id;
                let token = result.token;
                let authcode = result.authcode;
            } else {
                uni.showToast({
                    icon: "none",
                    title: "授权失败" + JSON.stringify(result),
                    duration: 2000
                });
            }
            this.gt_code = status
            this.gt_result = JSON.stringify(result);
            gtSDKModule.dismissAuthActivity();
            break;
        case 101:// onPrivacyClick
            let name = result.name;
            let url = result.url;
            console.log("=========== requestToken name =============== " + name);
            console.log("=========== requestToken url ================ " + url);
            break;
        case 102:// onPrivacyCheckBoxClick
            let isChecked = result.isChecked;
            console.log("=========== requestToken isChecked =============== " + isChecked);
            break;
        case 103:// onLoginButtonClick
            break;
        case 104:// onLoginLoading
            break;
        case 105:// onAuthActivityCreate
            break;
        case 106:// onAuthWebActivityCreate
            break;
        case 107:// onRequestTokenSecurityPhone
            let phone = result.phone;
            console.log("=========== requestToken phone =================== " + phone);
            break;
        case 120:// 自定义控件点击事件回调
            let viewId = result.jsresult;
            switch(viewId) {
                case "weixin_login":
                    console.log("=========== requestToken =================== " + "点击微信");
                    break;
                case "qq_login":
                    console.log("=========== requestToken =================== " + "点击 QQ");
                    break;
                case "weibo_login":
                    console.log("=========== requestToken =================== " + "点击微博");
                    break;
            }
    }
});
```


**参数说明**

- 接口参数说明：

授权页 UI 配置 ThemeConfig 与自定义 UI 配置 CustomView，详细配置说明请参考「授权页 UI 配置」章节。

- 返回参数描述：

字段|类型|含义
---|---|---
jscode|Number|外层返回码
jsresult|String|返回信息
status|Number|jscode=100 时的内层返回码

- jscode 返回码说明

取值|回调|含义|可用参数
---|---|---|---
100| onResult | 一键登录成功、失败、拉起授权页失败、授权页返回、切换账号等回调|`status` 内层返回码
101| onPrivacyClick | 隐私条款点击 | `name`: 隐私条款名称<br>`url`: 隐私条款地址
102| onPrivacyCheckBoxClick | 授权页选择框 CheckBox 点击回调 | `isChecked`：CheckBox 是否勾选
103| onLoginButtonClick | 登录按钮点击回调 |
104| onLoginLoading | 点一键登录时弹出自定义对话框式 loading 时机 |
105| onAuthActivityCreate | 授权页面拉起回调 |
106| onAuthWebActivityCreate | 隐私条款页面拉起回调 |
107| onRequestTokenSecurityPhone | 获取用于界面展示的脱敏手机号 | `phone`：脱敏手机号
120| 自定义控件回调 | 自定义 UI 控件点击事件回调 | `viewId`：自定义控件 Id

- status 返回码说明

当外层返回码为 100 时，返回信息包含一键登录成功、失败、拉起授权页失败、授权页返回、切换账号等回调信息，具体请参考官网一键登录[返回码](https://docs.geetest.com/onelogin/deploy/android#1%E3%80%81OneLogin%EF%BC%88%E4%B8%80%E9%94%AE%E7%99%BB%E5%BD%95%EF%BC%89)说明文档。当返回码为 200 时，返回信息包含一键登录的 token 信息，请将这些参数传递给后端开发人员，并参考「[服务端](https://docs.geetest.com/onelogin/deploy/server)」文档来实现获取手机号码的步骤。

### 销毁授权页

- 关闭授权页`dismissAuthActivity`

**方法描述**

用户主动关闭授权页。原生 SDK 除了返回按钮触发关闭以外，默认是不 finsih 授权页的，需要开发者**在回调结束后自行实现关闭授权页**。

**代码示例**

```java
gtSDKModule.dismissAuthActivity();
```


### 授权页 UI 配置

1. 授权页 UI 配置 ThemeConfig

配置该参数可实现对一键登录授权页进行个性化配置，每次调用拉起授权页方法时都需要传入该参数，否则授权页界面将按照默认配置进行展示。详细配置说明请参考「授权页 UI 配置」章节。

**代码示例**

```js
/**
* 授权页主题样式
* 0:浮窗式
* 1:弹窗式
* 2:沉浸式
* 3:横屏
*/
let themeStyle = 2;
let themeConfig = {};
switch(themeStyle) {
case 0:
    themeConfig = {
        isDialogTheme:true, dialogWidth:width, dialogHeight:500, dialogX:0, dialogY:0, isDialogBottom:true, isWebViewDialogTheme:true,
        authBGImgPath:'gt_one_login_bg',
        statusBarColor:0, navigationBarColor:0, isLightColor:false,
        navColor:0xFF3973FF, authNavHeight:49, authNavTransparent:true, authNavGone:false,
        navText:'一键登录', navTextColor:0xFFFFFFFF, navTextSize:17, navWebTextNormal:false, navWebText:'服务条款', navWebTextColor:0xFF000000, navWebTextSize:17,
        returnImgPath:'gt_one_login_ic_chevron_left_black', returnImgWidth:40, returnImgHeight:40, returnImgHidden:false, returnImgOffsetX:8,
        logoImgPath:'gt_one_login_logo', logoWidth:71, logoHeight:71, logoHidden:false, logoOffsetY:120, logoOffsetY_B:0, logoOffsetX:0,
        sloganColor:0xFFA8A8A8, sloganSize:10, sloganOffsetY:350, sloganOffsetY_B:0, sloganOffsetX:0,
        numberColor:0xFF3D424C, numberSize:24, numberOffsetY:180, numberOffsetY_B:0, numberOffsetX:0,
        switchText:'切换账号', switchColor:0xFF3973FF, switchSize:14, switchHidden:false, switchOffsetY:230, switchOffsetY_B:0, switchOffsetX:0,	
        logBtnImgPath:'gt_one_login_btn_normal', logBtnWidth:290, logBtnHeight:45, logBtnOffsetY:290, logBtnOffsetY_B:0, logBtnOffsetX:0,
        logBtnText:'一键登录', logBtnColor:0xFFFFFFFF, logBtnTextSize:18,
        loadingView:'umcsdk_load_dot_white', loadingViewWidth:20, loadingViewHeight:20, loadingViewOffsetRight:12,
        unCheckedImgPath:'gt_one_login_unchecked', checkedImgPath:'gt_one_login_checked', privacyState: true, privacyCheckBoxWidth:12, privacyCheckBoxHeight:12,
        privacyLayoutWidth:256, privacyOffsetY:0, privacyOffsetY_B:18, privacyOffsetX:0, isUseNormalWebActivity:true,
        baseClauseColor:0xFFA8A8A8, clauseColor:0xFF3973FF, privacyClauseTextSize:10, 
        privacyTextViewTv1:'登录即同意', privacyTextViewTv2:'和', privacyTextViewTv3:'、', privacyTextViewTv4:'并使用本机号码登录'
    }
    break;
case 1:
    themeConfig = {
        isDialogTheme:true, dialogWidth:popWidth, dialogHeight:popHeight, dialogX:0, dialogY:0, isDialogBottom:false, isWebViewDialogTheme:true,
        returnImgPath:'gt_one_login_ic_chevron_left_black', returnImgWidth:40, returnImgHeight:40, returnImgHidden:false, returnImgOffsetX:8,
        logoImgPath:'gt_one_login_logo', logoWidth:71, logoHeight:71, logoHidden:false, logoOffsetY:60, logoOffsetY_B:0, logoOffsetX:0,
        sloganColor:0xFFA8A8A8, sloganSize:10, sloganOffsetY:270, sloganOffsetY_B:0, sloganOffsetX:0,
        numberColor:0xFF3D424C, numberSize:24, numberOffsetY:125, numberOffsetY_B:0, numberOffsetX:0,
        switchText:'切换账号', switchColor:0xFF3973FF, switchSize:14, switchHidden:false, switchOffsetY:165, switchOffsetY_B:0, switchOffsetX:0,	
        logBtnImgPath:'gt_one_login_btn_normal', logBtnWidth:268, logBtnHeight:45, logBtnOffsetY:220, logBtnOffsetY_B:0, logBtnOffsetX:0,
        logBtnText:'一键登录', logBtnColor:0xFFFFFFFF, logBtnTextSize:18,
        loadingView:'umcsdk_load_dot_white', loadingViewWidth:20, loadingViewHeight:20, loadingViewOffsetRight:12,
        unCheckedImgPath:'gt_one_login_unchecked', checkedImgPath:'gt_one_login_checked', privacyState: true, privacyCheckBoxWidth:12, privacyCheckBoxHeight:12,
        privacyLayoutWidth:256, privacyOffsetY:0, privacyOffsetY_B:1, privacyOffsetX:0, isUseNormalWebActivity:true,
    }
    break;
case 2:
    themeConfig = {
        statusBarColor:0xffffffff, navigationBarColor:0xffffffff, isLightColor:true,
        returnImgPath:'gt_one_login_ic_chevron_left_black', returnImgWidth:40, returnImgHeight:40, returnImgHidden:false, returnImgOffsetX:0,
        logBtnImgPath:'gt_one_login_btn_normal', logBtnWidth:290, logBtnHeight:45, logBtnOffsetY:310, logBtnOffsetY_B:0, logBtnOffsetX:0,
        logBtnText:'一键登录', logBtnColor:0xFFFFFFFF, logBtnTextSize:18,
        loadingView:'umcsdk_load_dot_white', loadingViewWidth:20, loadingViewHeight:20, loadingViewOffsetRight:12,
        clauseNameOne:'自定义服务条款1', clauseUrlOne:'https://docs.geetest.com/onelogin/deploy/android', clauseNameTwo:'自定义服务条款2', clauseUrlTwo:'https://docs.geetest.com/onelogin/changelog/android', clauseNameThree:"", clauseUrlThree:"",
        privacyClauseTextStrings:["登录即同意", "应用自定义服务条款一", "https://docs.geetest.com/onelogin/deploy/android", "",
            "和", "应用自定义服务条款二", "https://docs.geetest.com/onelogin/changelog/android", "",
            "和", "应用自定义服务条款三", "https://docs.geetest.com/onelogin/help/tech", "",
            "和", "中国移动认证服务条款", "http://wap.cmpassport.com/resources/html/contract.html", ""]
    }
    break;
case 3:
    //设置当前页面为横屏
    themeConfig = {
        authBGImgPath:'gt_one_login_bg',
        isDialogTheme:false, dialogWidth:300, dialogHeight:500, dialogX:0, dialogY:0, isDialogBottom:false, isWebViewDialogTheme:false,
        statusBarColor:0xffffffff, navigationBarColor:0xffffffff, isLightColor:true,
        navColor:0xFF3973FF, authNavHeight:49, authNavTransparent:true, authNavGone:false,
        navText:'一键登录', navTextColor:0xFFFFFFFF, navTextSize:17, navWebTextNormal:false, navWebText:'服务条款', navWebTextColor:0xFF000000, navWebTextSize:17,
        returnImgPath:'gt_one_login_ic_chevron_left_black', returnImgWidth:45, returnImgHeight:45, returnImgHidden:false, returnImgOffsetX:8,
        logoImgPath:'gt_one_login_logo', logoWidth:71, logoHeight:71, logoHidden:false, logoOffsetY:55, logoOffsetY_B:0, logoOffsetX:0,
        sloganColor:0xFFA8A8A8, sloganSize:10, sloganOffsetY:226, sloganOffsetY_B:0, sloganOffsetX:0,
        numberColor:0xFF3D424C, numberSize:24, numberOffsetY:84, numberOffsetY_B:0, numberOffsetX:0,
        switchText:'切换账号', switchColor:0xFF3973FF, switchSize:14, switchHidden:false, switchOffsetY:128, switchOffsetY_B:0, switchOffsetX:0,	
        logBtnImgPath:'gt_one_login_btn_normal', logBtnWidth:268, logBtnHeight:36, logBtnOffsetY:169, logBtnOffsetY_B:0, logBtnOffsetX:0,
        logBtnText:'一键登录', logBtnColor:0xFFFFFFFF, logBtnTextSize:15,
        loadingView:'umcsdk_load_dot_white', loadingViewWidth:20, loadingViewHeight:20, loadingViewOffsetRight:12,
        unCheckedImgPath:'gt_one_login_unchecked', checkedImgPath:'gt_one_login_checked', privacyState: true, privacyCheckBoxWidth:9, privacyCheckBoxHeight:9,
        privacyLayoutWidth:512, privacyOffsetY:0, privacyOffsetY_B:1, privacyOffsetX:0, isUseNormalWebActivity:true,
        baseClauseColor:0xFFA8A8A8, clauseColor:0xFF3973FF, privacyClauseTextSize:10, 
        privacyTextViewTv1:'登录即同意', privacyTextViewTv2:'和', privacyTextViewTv3:'、', privacyTextViewTv4:'并使用本机号码登录'
    }
    break;
}
```

授权页所有 UI 配置与插件内的 OneLoginBean.java 类关联，该类所有字段说明：

```java
/**
 * 状态栏颜色
 */
public int statusBarColor = 0;
/**
 * 状态栏字体颜色 只能是黑白
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
public String navText = "一键登录";
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
 * 隐私条款页是否使用默认标题
 */
public boolean navWebTextNormal = false;
/**
 * 隐私条款页默认标题
 */
public String navWebText = "服务条款";
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
 * 返回按钮是否垂直居中显示，配置垂直居中后y轴偏移配置失效
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
 * 登录按钮文字
 */
public String logBtnText = "一键登录";
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
public String logBtnImgPath = "gt_one_login_btn_normal";
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
 * loading默认居中，配置垂直居中后y轴偏移配置失效
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
public int switchWidth = 80;
/**
 * 切换帐号背景高
 */
public int switchHeight = 25;

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
public String privacyTextViewTv1 = "登录即同意";
/**
 * 隐私条款连接字符2
 */
public String privacyTextViewTv2 = "和";
/**
 * 隐私条款连接字符3
 */
public String privacyTextViewTv3 = "、";
/**
 * 隐私条款连接字符4
 */
public String privacyTextViewTv4 = "并使用本机号码登录";

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
public boolean privacyState = true;
/**
 * 隐私条款check框未选择时点击一键登录按钮提示文字
 */
public String privacyUnCheckedToastText = "请同意服务条款";
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
public int privacyCheckBoxOffsetY = 2;

/**
 * 隐私条款是否增加书名号显示
 */
public boolean privacyAddFrenchQuotes = false;
/**
 * 隐私协议文字对齐方式
 */
public int privacyTextGravity = Gravity.TOP | Gravity.START;
```

2. 授权页自定义 UI 配置 CustomView

授权页自定义 UI 配置，配置该参数可通过动态添加 View 的形式给授权页添加更多 UI 控件。详细配置说明请参考「授权页 UI 配置」章节。

**代码示例**

```js
widgetConfig = {
    widget1: {
        viewId: "view_line1",
        type: "View",
        left: "84",
        top: "510",
        right: "",
        bottom: "",
        width: "51",
        height: "1",
        clickable: false,
        backgroundColor: "#D8D8D8",
    },
    widget2: {
        viewId: "view_tips",
        type: "TextView",
        left: "152",
        top: "500",
        right: "",
        bottom: "",
        width: "",
        height: "",
        clickable: false,
        text: "其他方式登录",
        font: "16",
        textColor: "#797894",
    },
    widget3: {
        viewId: "view_line2",
        type: "View",
        left: "258",
        top: "510",
        right: "",
        bottom: "",
        width: "51",
        height: "1",
        clickable: false,
        backgroundColor: "#D8D8D8",
    },
    widget4: {
        viewId: "weixin_login",
        type: "ImageView",
        left: "113",
        top: "535",
        right: "",
        bottom: "",
        width: "35",
        height: "35",
        backgroundImgPath: "static/weixin.png"
    },
    widget5: {
        viewId: "qq_login",
        type: "ImageView",
        left: "178",
        top: "535",
        right: "",
        bottom: "",
        width: "35",
        height: "35",
        backgroundImgPath: "static/qq.png"
    },
    widget6: {
        viewId: "weibo_login",
        type: "ImageView",
        left: "243",
        top: "535",
        right: "",
        bottom: "",
        width: "35",
        height: "35",
        backgroundImgPath: "static/weibo.png"
    }
},
```

参数说明：

```java
String viewId;              //自定义控件 ID
String type;                //自定义控件类型，当前只支持 View, ImageView, TextView
int left;                   //控件局屏幕左边缘偏移量，单位 dp
int top;                    //控件局标题栏下边缘偏移量，单位 dp
int right;                  //控件局屏幕右边缘偏移量，单位 dp
int bottom;                 //控件局屏幕底部偏移量，单位 dp
int width;                  //控件宽度，单位 dp
int height;                 //控件高度，单位 dp
boolean clickable;          //控件是否可以点击，默认为可点击
String text;                //type 为 TextView 时控件文本内容
int font;                   //type 为 TextView 时控件文本字体大小，单位:sp
String textColor;           //type 为 TextView 时控件文本颜色
String backgroundImgPath;   //控件背景图片
String backgroundColor;     //type 为 TextView 时控件背景颜色
```


## 本机号码认证 API 使用说明

### 初始化


**方法描述**

SDK 初始化

**代码示例**

```js
gtSDKModule.initWithCustomID(appid, 5000, (result)=>{
    this.gt_code = JSON.stringify(result.jscode);
    this.gt_result = JSON.stringify(result.jsresult);
    console.log(JSON.stringify(result));
});
```

**参数说明**

- 接口参数说明：

参数|类型|说明
---|---|---
appid|String|极验后台配置唯一产品 APPID，请在官网申请
timeout|Number|超时时间，单位:`ms`，取值范围:`1000~15000`，默认`5000`

- 返回参数描述：

字段|类型|含义
---|---|---
jscode|Number|200:成功；其他：失败
jsresult|String|返回信息


### 本机号码认证

在初始化执行之后调用，本机号码认证界面需自行实现，可以在多个需要认证的地方调用。


调用示例：

```js
gtSDKModule.verifyPhoneNumber({'phone':'13888888888', 'encryptPhone':false}, (result) => {
    switch(result.jscode) {
        case 200:
            uni.showToast({
                icon: "none",
                title: JSON.stringify(result),
                duration: 2000
            });
            break;
        case 500:
            uni.showToast({
                icon: "none",
                title: "校验失败:"+JSON.stringify(result),
                duration: 2000
            });
            break;
    }
    this.gt_code = JSON.stringify(result.jscode);
    this.gt_result = JSON.stringify(result.jsresult);
    console.log(JSON.stringify(result));
});
```


**参数说明**

- 接口参数说明：

参数|类型|说明
---|---|---
phone|String|需要校验的手机号
encryptPhone|Boolean|是否加密手机号，默认不加密

- 返回参数描述：

字段|类型|含义
---|---|---
jscode|Number|200:成功；其他：失败
process_id|String|流水号
accesscode|String| 本机号码认证返回的 accessCode
phone|String| 手机号

- 返回参数示例:

```json
{"process_id":"7a03ba67e802ce3f6d34567acd45689c","jscode":200,"accesscode":"a938d50f81d349dc824ba0515b3a1206","phone":"13888888888"}
```


### 校验手机号

当本机号校验外层 jscode 为200 时，或者返回参数中是否有 accesscode，可以判断 verifyPhoneNumber 接口是否返回成功。返回成功您将获取到返回的参数，请将这些参数传递给后端开发人员，并参考「[服务端](https://docs.geetest.com/onelogin/deploy/server)」文档来实现本机号码认证的


# iOS

## 前置条件

- 极验 SDK 支持 Xcode 11+，iOS 8.0+ 版本
- 极验 SDK 支持中国移动 4G/3G/2G、联通 4G/3G、电信4G 的取号能力
- 极验 SDK 支持网络环境为

1. 纯数据网络
2. 数据网络与 Wifi 网络同时开启

- 对于双卡手机，极验 SDK 取当前流量卡号

## 一键登录 API 使用说明

### 初始化

```js
gtSDKModule.registerWithAppID(appid, (result) => {
	uni.hideLoading();
	this.gt_code = JSON.stringify(result.status);
	this.gt_result = JSON.stringify(result.number);
	console.log(JSON.stringify(result));
});
```

**请求参数描述**

|参数名|必选|类型|说明|
|:---- |:---|:----- |----- |
|appId|是|String|极验平台获取到的 appId

**返回参数描述**

|参数名|类型|说明|
|:---- |:----- |----- |
|status|number|初始化状态
|number|string|初始化获取到的脱敏手机号

### 拉起授权页

- 调用拉起授权页方法后将会拉起运营商授权页面
- 每次调用拉起授权页方法前均需先调用授权页配置方法，否则授权页可能会展示异常

调用示例：

```js
gtSDKModule.requestTokenWithViewModel(viewModel, (result) => {
	uni.hideLoading();
	// 点一键登录回调	
	gtSDKModule.dismissAuthViewController();
	if (result.status != 200) {
		uni.showToast({
			icon: "none",
			title: "授权失败",
			duration: 2000
		});
	} else {
		uni.showToast({
			icon: "none",
			title: JSON.stringify(result),
			duration: 2000
		});
	}
		
	this.gt_code = JSON.stringify(result.status);
	this.gt_result = JSON.stringify(result.token);
	console.log(JSON.stringify(result));
});
```

**请求参数描述**

|参数名|必选|类型|说明|
|:---- |:---|:----- |----- |
|viewModel|是|map|授权页面配置

**返回参数描述**

|参数名|类型|说明|
|:---- |:----- |----- |
|status|number|拉起授权页面状态及在授权页面点击一键登录返回结果状态
|token|string|一键登录成功时返回的 token
|processID|string|一键登录成功时返回的流水号
|appID|string|一键登录成功时返回的 appID
|authcode|string|一键登录成功时返回的 authcode
|errorCode|string|拉起授权页面及一键登录失败时返回的错误码

**使用场景**

- 用户进行一键登录操作时调用该方法，如果初始化成功 SDK 将会拉起授权页面
- 可以在多处调用
- 需在调用初始化方法之后调用

**一键登录逻辑说明**

- 根据返回参数中的 status 和 errorCode 可以判断是否成功拉起授权页面和是否成功取号，若 status 不为 200，errorCode 不为 0，表示未成功拉起授权页面，或者取号失败，此时，需降级到您自己的登录页面
- 取号成功后，使用返回的 token、processID、appID、authcode 四个参数去服务端获取手机号，服务端对接请参考「[服务端](https://docs.geetest.com/onelogin/deploy/server)」

**一键登录正确返回示例如下**

```json
{
    "model":"iPhone9,1",
    "authcode":"0000",
    "operatorType":"CU",
    "release":"13.1.2",
    "processID":"685579350a862da205c186ae5757039d",
    "appID":"b41a959b5cac4dd1277183e074630945",
    "pre_token_time":"1106",
    "token":"CU__0__b41a959b5cac4dd1277183e074630945__2.1.4.1__1__5f40014fed784f0d8281c0d2a3ff9610__NOTCUCC",
    "number":"186****6173",
    "preGetTokenSuccessedTime":1591611035.538636,
    "errorCode":"0",
    "msg":"获取accessCode成功",
    "status":200,
    "expire_time":580
}
```

### 手动关闭授权页面

当开发者设置点击一键登录或者自定义控件不自动销毁授权页时，将需要自行调用此方法主动销毁授权页，建议在置换手机号成功后销毁，请不要使用其他方式关闭授权页面

调用示例：

```js
gtSDKModule.dismissAuthViewController();
```

**关闭授权页面时机**

- 在授权页面点击切换账号按钮时
- 在授权页面点击一键登录按钮收到 requestToken 结果回调之后

### 重新开始预取号

在 SDK 初始化之后，调用`requestTokenWithCompletion`获取 token 之前，SDK 内部会一直维护预取号的结果，但是在调用`requestTokenWithCompletion`获取 token 之后，就不再维护预取号结果了，调用该接口，就会重新开始预取号，并维持预取号结果有效，以保证在用户退出登录之后再次登录时能快速进入授权页面

调用示例：

```js
gtSDKModule.renewPreGetToken();
```

**调用时机**

- 建议在用户退出登录时调用该接口

### 授权页面配置

通过设置 viewModel 可实现授权页面的配置

调用示例：

```js
let screenWidth = gtSDKModule.screenWidth();
let screenHeight = gtSDKModule.screenHeight();
console.log("=========== screenWidth: =========== " + screenWidth);
console.log("=========== screenHeight: =========== " + screenHeight);
let viewModel = {
	statusBarStyle: 0,
	naviTitle: '一键登录uni-app',
	naviTitleColor: '#1F90FF',
	naviTitleFont: 20,
	naviBgColor: '#FFFFFF',
	naviBackImage: 'static/close_black.png',
	naviHidden: false,
	backButtonRect: [0, 0, 0, 0, 0, 0, 20, 20],
	backButtonHidden: false,
	appLogo: 'static/logo_icon.png',
	logoRect: [],
	logoHidden: false,
	logoCornerRadius: 5,
	phoneNumColor: '#FF00FF',
	phoneNumFont: 25,
	phoneNumRect: [],
	switchButtonText: '换个方式登录',
	switchButtonColor: '#6500FF',
	switchButtonBackgroundColor: '#FFFFFF',
	switchButtonFont: 12,
	switchButtonRect: [],
	switchButtonHidden: false,
	authButtonImages: [],
	authButtonTitle: '授权登录',
	authButtonTitleColor: '#FF0000',
	authButtonTitleFont: 15,
	authButtonRect: [],
	authButtonCornerRadius: 20,
	sloganRect: [],
	sloganTextColor: '#FFFF00',
	sloganTextFont: 13,
	defaultCheckBoxState: false,
	checkedImage: '',
	uncheckedImage: '',
	checkBoxRect: [],
	privacyTermsColor: '#00FF00',
	privacyTermsFont: 14,
	additionalPrivacyTerms: [['自定义服务条款1', 'https://docs.geetest.com/onelogin/deploy/ios'], ['自定义服务条款2', 'https://docs.geetest.com/onelogin/changelog/ios']],
	termTextColor: '#0000FF',
	termsRect: [],
	auxiliaryPrivacyWords: ['登录表示同意', '与', '&', '并使用本机号码登录'],
	termsAlignment: 0,
	backgroundColor: '#00000000',
	backgroundImage: 'static/background.png',
	landscapeBackgroundImage: '',
	isPopup: false,
	popupRect: [],
	popupCornerRadius: 3,
	popupRectCorners: [],
	popupAnimationStyle: 0,
	closePopupImage: '',
	closePopupTopOffset: 5,
	closePopupRightOffset: -5,
	canClosePopupFromTapGesture: true,
	webNaviTitle: '一键登录uni-app服务条款',
	webNaviTitleColor: '#1F90FF',
	webNaviTitleFont: 20,
	webNaviBgColor: '#0F0F00',
	notCheckProtocolHint: '请先阅读服务条款',
	modalPresentationStyle: 0,
	pullAuthVCStyle: 1,
	userInterfaceStyle: 0,
	widgets: [{
		type: "UIButton", 
		UIButtonType: 0, 
		image: '',
		backgroundImage: "static/button_bg.png",
		title: '自定义按钮',	
		titleColor: '#FFFFFF',
		titleFont: 12,
		cornerRadius: 3,
		action: 'customButtonAction',
		frame: [10, 70, 120, 40]
	}, {
		type: "UILabel",
		textColor: '#D98866',
		font: 15,
		text: '我是自定义 Label',
		textAlignment: 1,
		frame: [10, 120, 120, 20]
	}, {
		type: "UIView",
		backgroundColor: '#48A75B',
		cornerRadius: 5,
		frame: [10, 150, 120, 20]
	}, {
		type: "UIImageView",
		backgroundColor: '#48A75B',
		cornerRadius: 5,
		image: "static/logo.png",
		frame: [10, 180, 40, 40]
	}, {
		type: "UILabel",
		textColor: '#D98866',
		font: 15,
		text: '三方登录',
		textAlignment: 1,
		frame: [(screenWidth - 120)/2, screenHeight - 270, 120, 20]
	}, {
		type: "UIButton", 
		UIButtonType: 0, 
		image: 'static/qq_icon.png',
		action: 'qqLogin',
		frame: [screenWidth/2 - 45 - 10, screenHeight - 200, 45, 45]
	}, {
		type: "UIButton", 
		UIButtonType: 0, 
		image: 'static/weixin_icon.png',
		action: 'weixinLogin',
		frame: [screenWidth/2 + 10, screenHeight - 200, 45, 45]
	}],
	authVCTransitionBlock: 'authVCTransitionBlock',
	tapAuthBackgroundBlock: 'tapAuthBackgroundBlock',
	viewLifeCycleBlock: 'viewLifeCycleBlock',
	clickBackButtonBlock: 'clickBackButtonBlock',
	clickSwitchButtonBlock: 'clickSwitchButtonBlock',
	clickCheckboxBlock: 'clickCheckboxBlock'
}
```

**参数**

|参数名|说明
|:---- |----- 
| statusBarStyle |状态栏，具体值，请参考 iOS 的 UIStatusBarStyle 枚举
| naviTitle | 标题
| naviTitleColor | 标题颜色
| naviTitleFont | 标题字体大小
| naviBgColor | 标题栏背景色
| naviBackImage | 返回按钮对应的图标
| naviHidden | 是否隐藏标题栏
| backButtonRect | 返回按钮的位置大小
| backButtonHidden | 是否隐藏返回按钮
| appLogo | logo 对应的图标
| logoRect | logo 的位置大小
| logoHidden | 是否隐藏 logo
| logoCornerRadius | loog 圆角
| phoneNumColor | 脱敏手机号颜色
| phoneNumFont | 脱敏手机号字体大小
| phoneNumRect | 脱敏手机号位置大小
| switchButtonText | 切换账号按钮文案
| switchButtonColor | 切换账号按钮文案颜色
| switchButtonBackgroundColor | 切换账号按钮背景颜色
| switchButtonFont | 切换账号按钮文案字体大小
| switchButtonRect | 切换账号按钮位置大小
| switchButtonHidden | 是否隐藏切换账号按钮
| authButtonImages | 授权按钮背景图片，需传 3 张图片，依次为默认状态图片、高亮状态图片、失效状态图片
| authButtonTitle | 授权按钮文案
| authButtonTitleColor | 授权按钮文案颜色
| authButtonTitleFont | 授权按钮文案字体大小
| authButtonRect | 授权按钮位置大小
| authButtonCornerRadius | 授权按钮圆角
| sloganRect | slogan 位置大小
| sloganTextColor | slogan 文案颜色
| sloganTextFont | slogan 文案字体大小
| defaultCheckBoxState | 服务条款勾选框默认勾选状态
| checkedImage | 服务条款勾选框勾选时图标
| uncheckedImage | 服务条款勾选框未勾选时图标
| checkBoxRect | 服务条款勾选框位置大小
| privacyTermsColor | 隐私条款颜色
| privacyTermsFont | 隐私条款字体大小
| additionalPrivacyTerms | 自定义隐私条款
| termTextColor | 隐私条款之外的文字的颜色
| termsRect | 隐私条款位置大小
| auxiliaryPrivacyWords | 隐私条款连接符，4 个元素的数组
| termsAlignment | 隐私条款文案对齐方式，具体值请参考 iOS 的 NSTextAlignment 枚举
| backgroundColor | 授权页面背景颜色
| backgroundImage | 授权页面背景图片
| landscapeBackgroundImage | 横屏时授权页面背景图片
| isPopup | 是否为弹窗模式
| popupRect | 弹窗的位置大小
| popupCornerRadius | 弹窗圆角
| popupRectCorners | 弹窗圆角所在位置，1 ~ 4 个元素的数组，具体值请参考 iOS 的 UIRectCorner 枚举
| closePopupImage | 弹窗关闭按钮对应的图标
| closePopupTopOffset | 弹窗关闭按钮距弹窗顶部偏移
| closePopupRightOffset | 弹窗关闭按钮距弹窗右部偏移
| canClosePopupFromTapGesture | 点击弹窗背景处是否能关闭弹窗
| webNaviTitle | 服务条款页面导航栏标题
| webNaviTitleColor | 服务条款页面导航栏标题颜色
| webNaviTitleFont | 服务条款页面导航栏标题字体大小
| webNaviBgColor | 服务条款页面导航栏背景颜色
| notCheckProtocolHint | 未勾选服务条款勾选框时点击一键登录按钮的提示文案
| webNaviBgColor | 服务条款页面导航栏背景颜色
| modalPresentationStyle | present 授权页面时的样式，具体值请参考 iOS 的 UIModalPresentationStyle 枚举
| pullAuthVCStyle | 拉起授权页面的方式，0 表示通过 presentViewController 方式进入授权页面，1 表示通过 pushViewController 方式进入授权页面
| userInterfaceStyle | 系统样式，0 表示不指定样式，跟随系统设置，1 表示明亮模式，2 表示黑夜模式，仅在 iOS 13 及以上的系统有效
| authVCTransitionBlock | 授权页面旋转回调
| tapAuthBackgroundBlock | 点击授权页面背景回调
| viewLifeCycleBlock | 授权页面生命周期回调
| clickBackButtonBlock | 点击返回按钮回调
| clickSwitchButtonBlock | 点击切换账号按钮回调
| clickCheckboxBlock | 点击勾选框回调
| widgets | 自定义控件，支持自定义 UIButton、UILabel、UIView、UIImageView，具体自定义方法请参考下面代码

```js
widgets: [{ 
	type: "UIButton", // 自定义 UIButton
	UIButtonType: 0,  // button 类型
	image: '',     // button 图片
	backgroundImage: "static/button_bg.png", // button 背景图片
	title: '自定义按钮',	// button 标题
	titleColor: '#FFFFFF', // button 标题颜色
	titleFont: 12, // button 标题字体大小
	cornerRadius: 3, // button 圆角
	action: 'customButtonAction', // button 点击事件
	frame: [10, 70, 120, 40] // button 的位置大小，依次表示，x、y、width、height
}, {
	type: "UILabel", // 自定义 UILabel
	textColor: '#D98866', // 文字颜色
	font: 15, // 文字字体大小
	text: '我是自定义 Label', // 具体文案
	textAlignment: 1, // 文字对齐方式
	frame: [10, 120, 120, 20] // label 的位置大小，依次表示，x、y、width、height
}, {
	type: "UIView", // 自定义 UIView
	backgroundColor: '#48A75B', // UIView 的背景颜色
	cornerRadius: 5, // UIView 的圆角
	frame: [10, 150, 120, 20] // UIView 的位置大小，依次表示，x、y、width、height
}, {
	type: "UIImageView", // 自定义 UIImageView
	backgroundColor: '#48A75B', // UIImageView 的背景颜色
	cornerRadius: 5, // UIImageView 的圆角
	image: "static/logo.png", // UIImageView 的图片
	frame: [10, 180, 40, 40] // UIImageView 的位置大小，依次表示，x、y、width、height
}]
```

<br><font color="Red" ><b>注：控件位置大小属性通过数组进行设置，数组中 8 个元素，依次表示的含义为：竖屏时距屏幕顶部偏移、竖屏时距屏幕x轴中点偏移、竖屏时距屏幕左边偏移、横屏时距屏幕顶部偏移、横屏时距屏幕x轴中点偏移、横屏时距屏幕左边偏移、控件宽度、控件高度，具体可参考如下代码：</b></font></br>

```objc
/**
 * @abstract 1、若授权页面只支持竖屏，只设置竖屏方向偏移；
             2、若授权页面只支持横屏，只设置横屏方向偏移；
             3、若授权页面支持旋转自动切换横竖屏，则同时设置竖屏方向和横屏方向偏移
             4、弹窗模式，同以上1、2、3
             5、size默认都可以不用设置，会根据字体大小自适应
             6、x轴方向偏移量有两个值可以设置，portraitCenterXOffset为控件的x轴中点到弹窗x轴中点的距离，portraitLeftXOffset为控件的左边缘到屏幕左边缘的距离，两者选其一即可
 */
typedef struct OLRect {
    /**
     竖屏时
     导航栏隐藏时，为控件顶部到状态栏的距离；导航栏显示时，为控件顶部到导航栏底部的距离
     弹窗时
     为控件顶部到弹窗顶部的距离
     */
    CGFloat portraitTopYOffset;
    
    /**
     竖屏时
     控件的x轴中点到屏幕x轴中点的距离，默认为0
     弹窗时
     控件的x轴中点到弹窗x轴中点的距离，默认为0
     */
    CGFloat portraitCenterXOffset;
    
    /**
     竖屏时
     控件的左边缘到屏幕左边缘的距离，默认为0
     弹窗时
     控件的左边缘到屏幕左边缘的距离，默认为0
     
     portraitLeftXOffset与portraitCenterXOffset设置一个即可，portraitLeftXOffset优先级大于portraitCenterXOffset，
     设置此属性时，portraitCenterXOffset属性失效
     */
    CGFloat portraitLeftXOffset;
    
    /**
     横屏时
     导航栏隐藏时，为控件顶部到屏幕顶部的距离；导航栏显示时，为控件顶部到导航栏底部的距离
     弹窗时
     为控件顶部到弹窗顶部的距离
     */
    CGFloat landscapeTopYOffset;
    
    /**
     横屏时
     控件的x轴中点到屏幕x轴中点的距离，默认为0
     弹窗时
     控件的x轴中点到弹窗x轴中点的距离，默认为0
     */
    CGFloat landscapeCenterXOffset;
    
    /**
     横屏时
     控件的左边缘到屏幕左边缘的距离，默认为0
     弹窗时
     控件的左边缘到屏幕左边缘的距离，默认为0
     
     landscapeLeftXOffset与landscapeCenterXOffset设置一个即可，landscapeLeftXOffset优先级大于landscapeCenterXOffset，
     设置此属性时，landscapeCenterXOffset属性失效
     */
    CGFloat landscapeLeftXOffset;
    
    /**
     控件大小，只有宽度、高度同时大于0，设置的size才会生效，否则为控件默认的size
     */
    CGSize size;
} OLRect;
```

### 设置日志开关

```js
gtSDKModule.setLogEnabled(true);
```

**请求参数描述**

|参数名|必选|类型|说明|
|:---- |:---|:----- |----- |
|enabled|是|boolean|控制是否打开日志

### 获取 SDK 版本号

```js
let sdkVersion = gtSDKModule.sdkVersion();
console.log("=========== sdkVersion: =========== " + sdkVersion);
```

**返回参数描述**

|参数名|类型|说明|
|:---- |:---|:----- |
|sdkVersion|string|sdk 版本号

### 设置请求超时时长

```js
gtSDKModule.setRequestTimeout(5);
```

**请求参数描述**

|参数名|必选|类型|说明|
|:---- |:---|:----- |----- |
|timeout|是|number|取号超时时长，单位为 s

## 本机号码认证 API 使用说明

### 初始化

```js
gtSDKModule.initWithCustomID(appid, 5.0);
```

**请求参数描述**

|参数名|必选|类型|说明|
|:---- |:---|:----- |----- |
|appId|是|String|极验平台获取到的 appId
|timeout|是|number|本机号码认证超时时长，单位为 s

### 本机号码认证

```js
//极验SDK本机号码校验
gtSDKModule.verifyPhoneNumber('13888888888', (result) => {
	uni.hideLoading();
	// 本机号码校验回调	
	if (result.accesscode.length > 0) {
		uni.showToast({
			icon: "none",
			title: JSON.stringify(result),
			duration: 2000
		});
	} else {
		uni.showToast({
			icon: "none",
			title: "校验失败",
			duration: 2000
		});
	}
		
	this.gt_code = '200';
	this.gt_result = JSON.stringify(result.accesscode);
	console.log(JSON.stringify(result));
});
```

**请求参数描述**

|参数名|必选|类型|说明|
|:---- |:---|:----- |----- |
|phoneNumber|是|String|需要校验的手机号

**返回参数描述**

|参数名|类型|说明|
|:---- |:----- |----- |
| accesscode | string | 本机号码认证返回的 accesscode
| operatorType | string | 运营商
| process_id | string | 本机号码认证成功时返回的流水号
| phone | string | 手机号
| expires | number | accesscode 的有效时长


**本机号码认证逻辑说明**

- 根据返回参数中是否有 accesscode 可以判断 verifyPhoneNumber 接口是否返回成功
- verifyPhoneNumber 成功后，使用返回的 process_id、accesscode、operatorType、phone 四个参数去服务端校验是否为本机号码，服务端对接请参考「[服务端](https://docs.geetest.com/onelogin/deploy/server)」

**本机号码认证正确返回示例如下**

```json
{
    "process_id":"c5857676b1a610fe76fcb26ed757e803",
    "accesscode":"9ac4a0e724fb4387a19beee3da63cafb",
    "operatorType":"CU",
    "phone":"18627096173",
    "expires":1800
}
```