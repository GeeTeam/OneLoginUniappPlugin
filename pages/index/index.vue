<template>
	<view class="content">
		<view class="result_group">
			<text class="text_attr">code: {{gt_code}}</text>
			<text class="text_attr">result: {{gt_result}}</text>
		</view>
		<view class="tableTitle">
			<text class="midText">一键登录功能</text>
		</view>
		<view class="button_attr">
			<button type="primary" style="background-color: #8C57E3;" @tap="initOneLogin">极验 SDK 初始化</button>
		</view>
		<view class="button_attr">
			<button type="primary" style="background-color: #8C57E3;" @tap="oneLogin">极验 SDK 拉起授权页</button>
		</view>
		<view class="button_attr">
			<button type="primary" style="background-color: #8C57E3;" @tap="popupOneLogin">极验 SDK 拉起弹窗形式授权页</button>
		</view>
		<view class="tableTitle">
			<text class="midText">本机号认证功能</text>
		</view>
		<view class="button_attr">
			<button type="primary" style="background-color: #8C57E3;" @tap="initOnePass">本机认证初始化</button>
		</view>
		<view class="button_attr">
			<button type="primary" style="background-color: #8C57E3;" @tap="onePass">本机认证获取 token</button>
		</view>
	</view>
</template>

<script>
	var gtSDKModule = uni.requireNativePlugin("GTOneLoginSDKModule"); 
	
	export default {
		data() {
			return {
				account: '',
				gt_code: '状态码',
				gt_result: '信息描述'
			}
		},
		onLoad() {

		},
		mounted() {
			Vue.prototype.$customButtonAction = this.customButtonAction;
			Vue.prototype.$qqLogin = this.qqLogin;
			Vue.prototype.$weixinLogin = this.weixinLogin;
			Vue.prototype.$authVCTransitionBlock = this.authVCTransitionBlock;
			Vue.prototype.$tapAuthBackgroundBlock = this.tapAuthBackgroundBlock;
			Vue.prototype.$viewLifeCycleBlock = this.viewLifeCycleBlock;
			Vue.prototype.$clickBackButtonBlock = this.clickBackButtonBlock;
			Vue.prototype.$clickSwitchButtonBlock = this.clickSwitchButtonBlock;
			Vue.prototype.$clickCheckboxBlock = this.clickCheckboxBlock;
		},
		methods: {
			initOneLogin() {				
				//初始化建议在app启动时调用，即App.vue的onLaunch方法中
				uni.showLoading({
					mask: true,
				});
				
				if (gtSDKModule == undefined) {
					uni.hideLoading();
					console.log("=========== load plugin failed =========== ");
					return;
				}
				console.log("=========== load plugin success ======= ");
				
				let platform = uni.getSystemInfoSync().platform;
				var appid;
				if (platform == 'android') {
					appid = 'e4fcb3086ca25bbe2da08a09d75c70e8'; //appID与包名绑定
				} else if (platform == 'ios') {
					appid = 'b41a959b5cac4dd1277183e074630945'; //appID与bundleID绑定
				}
				
				console.log("=========== platform is: ============== " + platform);
				console.log("=========== appid is: ================= " + appid);
				console.log("=========== gtSDKModule is: =========== " + gtSDKModule);
				
				if (platform == 'android') {
					let sdkVersion = gtSDKModule.sdkVersion();
					console.log("=========== sdkVersion: =============== " + sdkVersion);
					
					//极验 SDK 初始化
					gtSDKModule.setLogEnabled(true);
					console.log("=========== set log enabled =========== ");
					
					gtSDKModule.register({'appid': appid, 'timeout': 8000}, (result)=> {
						uni.hideLoading();
						this.gt_code = JSON.stringify(result.jscode);
						this.gt_result = JSON.stringify(result.jsresult);
						console.log(JSON.stringify(result));
					});
					
				} else if (platform == 'ios') {
					let sdkVersion = gtSDKModule.sdkVersion();
					console.log("=========== sdkVersion: =========== " + sdkVersion);
					
					//极验 SDK 初始化
					gtSDKModule.setLogEnabled(true);
					console.log("=========== set log enabled =========== ");
					gtSDKModule.registerWithAppID(appid, (result) => {
						uni.hideLoading();
						this.gt_code = JSON.stringify(result.status);
						this.gt_result = JSON.stringify(result.number);
						console.log(JSON.stringify(result));
					});
					console.log("=========== register with appid =========== ");
				}

			},
			initOnePass() {
				if (gtSDKModule == undefined) {
					console.log("=========== load plugin failed =========== ");
					return;
				}
				
				let platform = uni.getSystemInfoSync().platform;
				var appid;
				if (platform == 'android') {
					appid = 'e18f5f45da5614928f4af0410e886e0a'; //appID与包名绑定
				} else if (platform == 'ios') {
					appid = '3996159873d7ccc36f25803b88dda97a'; //appID与bundleID绑定
				}
				
				console.log("=========== appid is: =========== " + appid);
				
				if (platform == 'android') {
					gtSDKModule.initWithCustomID(appid, 5000, (result)=> {
						uni.hideLoading();
						this.gt_code = JSON.stringify(result.jscode);
						this.gt_result = JSON.stringify(result.jsresult);
						console.log(JSON.stringify(result));
					})
				} else if (platform == 'ios') {
					gtSDKModule.initWithCustomID(appid, 5.0);
					uni.showToast({
						icon: "none",
						title: "OnePass初始化成功",
						duration: 2000
					});
				}
			},
			oneLogin() {
				if (gtSDKModule == undefined) {
					console.log("=========== load plugin failed =========== ");
					return;
				}
				
				uni.showLoading({
					mask: true,
				});

				let systemInfo = uni.getSystemInfoSync();
				let platform = systemInfo.platform;
				if (platform == 'android') {
					let screenInfo = gtSDKModule.getScreenInfo();
					console.log("=========== getScreenInfo =========== " + JSON.stringify(screenInfo));
					/*************************Android*************************/
					//极验SDK拉起授权页方法
					let width = systemInfo.screenWidth;
					let height = systemInfo.screenHeight;
					let density = systemInfo.pixelRatio;
					let popWidth = width * 4 / 5;
					let popHeight = height * 3 / 5;
					console.log("=========== oneLogin  ======= width=" + width + ", height=" + height + ", density=" + density + ", popWidth=" + popWidth + ", popHeight=" + popHeight);
					/**
					 * 授权页主题样式
					 * 0:浮窗式
					 * 1:弹窗式
					 * 2:沉浸式
					 * 3:横屏
					 */
					let themeStyle = 2;
					let themeConfig = {};
					let widgetConfig = {};
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
					console.log("=========== requestToken themeConfig ======= " + JSON.stringify(themeConfig));
					
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
							isFinish: "false"
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
					
					console.log("=========== requestToken isPreGetTokenResultValidate ======= " + JSON.stringify(gtSDKModule.isPreGetTokenResultValidate()));
					
					//一键登录
					gtSDKModule.requestToken({'ThemeConfig':themeConfig, 'CustomView':widgetConfig}, (result) => {
						uni.hideLoading();
						console.log("=========== requestToken result ======= " + JSON.stringify(result));
						
						switch(result.jscode) {
							case 100:// onResult
								let status = result.status;
								if(status == 200) { // 获取 token 成功
									let processId = result.process_id;
									let token = result.token;
									let authcode = result.authcode;
									console.log("=========== requestToken success ======= ");
									console.log("=========== requestToken processId ===== " + processId);
									console.log("=========== requestToken token ========= " + token);
									console.log("=========== requestToken authcode ====== " + authcode);
									console.log("=========== requestToken success ======= ");
									uni.showToast({
										icon: "none",
										title: "授权成功:" + JSON.stringify(result),
										duration: 2000
									});
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
							    console.log("=========== requestToken onPrivacyClick ===== ");
								console.log("=========== requestToken name =============== " + name);
								console.log("=========== requestToken url ================ " + url);
								break;
							case 102:// onPrivacyCheckBoxClick
								let isChecked = result.isChecked;
								console.log("=========== requestToken onPrivacyCheckBoxClick ===== ");
								console.log("=========== requestToken isChecked =============== " + isChecked);
								break;
							case 103:// onLoginButtonClick
								console.log("=========== requestToken onLoginButtonClick ===== ");
								break;
							case 104:// onLoginLoading
								console.log("=========== requestToken onLoginLoading ===== ");
								break;
							case 105:// onAuthActivityCreate
								console.log("=========== requestToken onAuthActivityCreate ===== ");
								break;
							case 106:// onAuthWebActivityCreate
								console.log("=========== requestToken onAuthWebActivityCreate ===== ");
								break;
							case 107:// onRequestTokenSecurityPhone
								let phone = result.phone;
								console.log("=========== requestToken onRequestTokenSecurityPhone ===== ");
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
					})
				} else if (platform == 'ios') {
					/*************************iOS*************************/
					//极验SDK拉起授权页方法
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
					
					//一键登录
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
				}
			},
			popupOneLogin() {
				if (gtSDKModule == undefined) {
					console.log("=========== load plugin failed =========== ");
					return;
				}
				
				uni.showLoading({
					mask: true,
				});
				
				let platform = uni.getSystemInfoSync().platform;
				if (platform == 'android') {
					/*************************Android*************************/
					//极验SDK拉起授权页方法
					themeConfig = {
						isDialogTheme:true, dialogWidth:300, dialogHeight:500, dialogX:0, dialogY:0, isDialogBottom:false, isWebViewDialogTheme:true,
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
					//一键登录
					gtSDKModule.requestToken({'ThemeConfig': themeConfig}, (result) => {
						uni.hideLoading();
						console.log("=========== requestToken result ======= " + JSON.stringify(result));
						
						switch(result.jscode) {
							case 100:// onResult
								let status = result.status;
								if(status == 200) { // 获取 token 成功
									let processId = result.process_id;
									let token = result.token;
									let authcode = result.authcode;
									console.log("=========== requestToken success ======= ");
									console.log("=========== requestToken processId ===== " + processId);
									console.log("=========== requestToken token ========= " + token);
									console.log("=========== requestToken authcode ====== " + authcode);
									console.log("=========== requestToken success ======= ");
									uni.showToast({
										icon: "none",
										title: "授权成功:" + JSON.stringify(result),
										duration: 2000
									});
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
							    console.log("=========== requestToken onPrivacyClick ===== ");
								console.log("=========== requestToken name =============== " + name);
								console.log("=========== requestToken url ================ " + url);
								break;
							case 102:// onPrivacyCheckBoxClick
								let isChecked = result.isChecked;
								console.log("=========== requestToken onPrivacyCheckBoxClick ===== ");
								console.log("=========== requestToken isChecked =============== " + isChecked);
								break;
							case 103:// onLoginButtonClick
								console.log("=========== requestToken onLoginButtonClick ===== ");
								break;
							case 104:// onLoginLoading
								console.log("=========== requestToken onLoginLoading ===== ");
								break;
							case 105:// onAuthActivityCreate
								console.log("=========== requestToken onAuthActivityCreate ===== ");
								break;
							case 106:// onAuthWebActivityCreate
								console.log("=========== requestToken onAuthWebActivityCreate ===== ");
								break;
							case 107:// onRequestTokenSecurityPhone
								let phone = result.phone;
								console.log("=========== requestToken onRequestTokenSecurityPhone ===== ");
								console.log("=========== requestToken phone =================== " + phone);
								break;
						}
					})
				} else if (platform == 'ios') {
					/*************************iOS*************************/
					//极验SDK拉起授权页方法
					let screenWidth = gtSDKModule.screenWidth();
					let screenHeight = gtSDKModule.screenHeight();
					console.log("=========== screenWidth: =========== " + screenWidth);
					console.log("=========== screenHeight: =========== " + screenHeight);
					
					let viewModel = {
						switchButtonHidden: true,
						isPopup: true,
						popupRect: [screenHeight - 340, 0, 0, 0, 0, 0, screenWidth, 340],
						popupCornerRadius: 10,
						popupRectCorners: [1, 2],
						popupAnimationStyle: 0,
						closePopupImage: '',
						closePopupTopOffset: 5,
						closePopupRightOffset: -5,
						canClosePopupFromTapGesture: true,
						notCheckProtocolHint: '请先阅读服务条款',
						modalPresentationStyle: 0,
						authVCTransitionBlock: 'authVCTransitionBlock',
						tapAuthBackgroundBlock: 'tapAuthBackgroundBlock',
						viewLifeCycleBlock: 'viewLifeCycleBlock',
						clickBackButtonBlock: 'clickBackButtonBlock',
						clickSwitchButtonBlock: 'clickSwitchButtonBlock',
						clickCheckboxBlock: 'clickCheckboxBlock'
					}
					
					//一键登录
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
				}
			},
			onePass() {
				if (gtSDKModule == undefined) {
					console.log("=========== load plugin failed =========== ");
					return;
				}
				
				uni.showLoading({
					mask: true,
				});
				
				let platform = uni.getSystemInfoSync().platform;
				if (platform == 'android') {
					/*************************Android*************************/
					//极验SDK本机号码校验
					gtSDKModule.verifyPhoneNumber({'phone':'13888888888', 'encryptPhone':false}, (result) => {
						uni.hideLoading();
						console.log("=========== verifyPhoneNumber result ======= " + JSON.stringify(result));
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
						this.gt_code = result.jscode;
						this.gt_result = JSON.stringify(result);
					});
				} else if (platform == 'ios') {
					/*************************iOS*************************/
					//极验SDK本机号码校验
					gtSDKModule.verifyPhoneNumber('13888888888', (result) => {
						uni.hideLoading();
						// 点一键登录回调	
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
				}
			},
			customButtonAction() {
				console.log("=========== custom button pressed =========== ");
			},
			qqLogin() {
				console.log("=========== qqLogin =========== ");
				gtSDKModule.dismissAuthViewController();
				uni.showToast({
					icon: "none",
					title: "qq登录",
					duration: 2000
				});
			},
			weixinLogin() {
				console.log("=========== weixinLogin =========== ");
				gtSDKModule.dismissAuthViewController();
				uni.showToast({
					icon: "none",
					title: "微信登录",
					duration: 2000
				});
			},
			authVCTransitionBlock() {
				console.log("=========== authVCTransitionBlock =========== ");
			},
			tapAuthBackgroundBlock() {
				console.log("=========== tapAuthBackgroundBlock =========== ");
			},
			viewLifeCycleBlock(viewLifeCycle, animated) {
				console.log("=========== viewLifeCycle: " + viewLifeCycle + "===========, animated: " + animated);
				if (viewLifeCycle == 'viewDidLoad') {
					uni.hideLoading();
				}
			},
			clickBackButtonBlock() {
				console.log("=========== clickBackButtonBlock =========== ");
			},
			clickSwitchButtonBlock() {
				console.log("=========== clickSwitchButtonBlock =========== ");
			},
			clickCheckboxBlock(checked) {
				console.log("=========== clickCheckboxBlock =========== ");
				if (checked == 'true') {
					console.log("=========== checkbox is checked =========== ");
				} else if (checked == 'false') {
					console.log("=========== checkbox is unchecked =========== ");
				}
			}
		}
	}
</script>

<style>
	.result_group {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		background-color: #B9F6CA;
		margin: 0 10upx;
		border-radius: 10upx;

	}

	.action-row {
		display: flex;
		flex-direction: row;
		justify-content: center;
		margin: 0 10upx;
		border-radius: 10upx;
		width: 100%;
	}

	.text_attr {
		width: 95%;
		margin-left: 30upx;
		margin-top: 10upx;
		margin-bottom: 10upx;
		margin-right: 30upx;
		word-wrap: break-word;
		word-break: break-all;
		font-size: 30upx;
	}

	.button_attr {
		justify-content: center;
		margin-top: 30upx;
		margin-left: 15upx;
		margin-right: 15upx;
		margin-bottom: 30upx;
		background-color: #8C57E3;
	}

	.tableTitle {
		position: relative;
		margin-top: 50upx;
		margin-bottom: 50upx;
		width: 100%;
		height: 1px;
		background-color: #d4d4d4;
		text-align: center;
		font-size: 16px;
		color: rgba(101, 101, 101, 1);
	}

	.midText {
		position: absolute;
		left: 50%;
		background-color: #f2f2f5;
		transform: translateX(-50%) translateY(-50%);
	}
</style>
