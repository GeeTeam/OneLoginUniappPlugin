//
//  OneLoginProPlugin.m
//  OneLoginHbuilderPlugin
//
//  Created by noctis on 2020/5/12.
//  Copyright © 2020 com.geetest. All rights reserved.
//

#import "GTOneLoginSDKModule.h"
#import "YYModel.h"
#import <OneLoginSDK/OneLoginSDK.h>
#import "PDRCoreApp.h"
#import "PDRCore.h"
#import "PDRCoreAppManager.h"
#import "H5UniversalApp.h"

@interface GTButton : UIButton

@property (nonatomic, copy) NSString *buttonAction;

@end

@implementation GTButton


@end

@interface GTOneLoginSDKModule () <GOPManagerDelegate>

@property (nonatomic, strong) GOPManager *gopManager;
@property (nonatomic, copy) WXModuleKeepAliveCallback onepassCallback;

@end

@implementation GTOneLoginSDKModule

// MARK: Color From Hex

- (UIColor *)colorFromHexString:(const NSString *)hexString {
    if ([hexString isKindOfClass:[NSString class]] && hexString.length > 0) {
        NSString *tmpHexString = hexString.copy;
        if ([tmpHexString hasPrefix:@"#"]) {
            tmpHexString = [tmpHexString substringFromIndex:[@"#" length]];
        }
        
        if (tmpHexString.length > 0) {
            if (tmpHexString.length >= 8) {             // 大于8位，取前8位
                tmpHexString = [tmpHexString substringToIndex:8];
            } else if (tmpHexString.length >= 6) {      // 大于6位，取前6位
                tmpHexString = [tmpHexString substringToIndex:6];
            } else {                                    // 不足6位，前面补0
                while (tmpHexString.length < 6) {
                    tmpHexString = [@"0" stringByAppendingString:tmpHexString];
                }
            }
            
            NSRange range = NSMakeRange(0, 2);
            NSString *aString = nil;
            if (8 == tmpHexString.length) {
                aString = [tmpHexString substringWithRange:range];
                range.location = range.location + 2;
            }
            NSString *rString = [tmpHexString substringWithRange:range];
            range.location = range.location + 2;
            NSString *gString = [tmpHexString substringWithRange:range];
            range.location = range.location + 2;
            NSString *bString = [tmpHexString substringWithRange:range];
            // 取三种颜色值
            unsigned int r, g, b;
            [[NSScanner scannerWithString:rString] scanHexInt:&r];
            [[NSScanner scannerWithString:gString] scanHexInt:&g];
            [[NSScanner scannerWithString:bString] scanHexInt:&b];
            unsigned int a = 255;
            if (aString.length > 0) {
                [[NSScanner scannerWithString:aString] scanHexInt:&a];
            }
            return [UIColor colorWithRed:((float)r / 255.0f)
                                   green:((float)g / 255.0f)
                                    blue:((float)b / 255.0f)
                                   alpha:((float)a / 255.0f)];
        }
    }
    
    return UIColor.clearColor;
}

// MARK: Find Current ViewController

// 获取当前显示的 UIViewController
- (UIViewController *)findCurrentShowingViewController {
    //获得当前活动窗口的根视图
    UIViewController *vc = [UIApplication sharedApplication].keyWindow.rootViewController;
    UIViewController *currentShowingVC = [self findCurrentShowingViewControllerFrom:vc];
    return currentShowingVC;
}

- (UIViewController *)findCurrentShowingViewControllerFrom:(UIViewController *)vc {
    // 递归方法 Recursive method
    UIViewController *currentShowingVC;
    if ([vc presentedViewController]) {
        // 当前视图是被presented出来的
        UIViewController *nextRootVC = [vc presentedViewController];
        currentShowingVC = [self findCurrentShowingViewControllerFrom:nextRootVC];

    } else if ([vc isKindOfClass:[UITabBarController class]]) {
        // 根视图为UITabBarController
        UIViewController *nextRootVC = [(UITabBarController *)vc selectedViewController];
        currentShowingVC = [self findCurrentShowingViewControllerFrom:nextRootVC];

    } else if ([vc isKindOfClass:[UINavigationController class]]){
        // 根视图为UINavigationController
        UIViewController *nextRootVC = [(UINavigationController *)vc visibleViewController];
        currentShowingVC = [self findCurrentShowingViewControllerFrom:nextRootVC];

    } else {
        // 根视图为非导航类
        currentShowingVC = vc;
    }

    return currentShowingVC;
}

// MARK: Image

- (UIImage * _Nullable)imageWithName:(NSString *)name {
    if ([name isKindOfClass:[NSString class]] && name.length > 0) {
        NSArray *array = [name componentsSeparatedByString:@"."];
        if (2 == array.count) {
            NSString *imagePath = [[NSBundle mainBundle] pathForResource:[NSString stringWithFormat:@"Pandora/apps/__UNI__0D3E2E0/www/%@", array[0]] ofType:array[1]];
            NSData *imageData = [NSData dataWithContentsOfFile:imagePath];
            return [UIImage imageWithData:imageData];
        }
        
    }
    
    return nil;
}

// MARK: Valid String

- (BOOL)isValidString:(NSString *)string {
    return string && [string isKindOfClass:[NSString class]] && string.length > 0 && ![string isEqual:@"NSNull"];
}

// MARK: JS

- (void)invokeJSMethod:(NSString *)js params:(NSString *)params, ... {
    PDRCoreApp *pdrCoreApp = (PDRCoreApp *)[[[PDRCore Instance] appManager] activeApp];
    PDRCoreAppFrame *pMainFrame = [pdrCoreApp valueForKey:@"_weexWebview"];

    if (pMainFrame) {
        NSMutableString *jsStr = [NSMutableString string];
        if (params) {
            va_list arg_list;
            [jsStr appendFormat:@"Vue.prototype.$%@('%@',", js, params];
            va_start(arg_list, params);
            NSString *tempArg = va_arg(arg_list, NSString *);
            while (tempArg) {
                [jsStr appendFormat:@"'%@',", tempArg];
                tempArg = va_arg(arg_list, NSString *);
            }
            va_end(arg_list);
            [jsStr deleteCharactersInRange:NSMakeRange(jsStr.length - 1, 1)];
            [jsStr appendString:@")"];
        } else {
            [jsStr appendFormat:@"Vue.prototype.$%@()", js];
        }
        [pMainFrame stringByEvaluatingJavaScriptFromString:jsStr];
    }
}

// MARK: Actions

- (void)customButtonAction:(GTButton *)button {
    if ([self isValidString:button.buttonAction]) {
        [self invokeJSMethod:button.buttonAction params:nil];
    }
}

// MARK: frameFromArray

- (CGRect)frameFromArray:(NSArray *)array {
    if ([array isKindOfClass:[NSArray class]] && array.count >= 4) {
        CGRect frame = {{[array[0] doubleValue], [array[1] doubleValue]}, {[array[2] doubleValue], [array[3] doubleValue]}};
        return frame;
    }
    
    return CGRectZero;
}

// MARK: Widget

- (UIView *)widgetFromDict:(NSDictionary *)widgetDict {
    if (widgetDict[@"type"]) {
        NSString *widgetType = widgetDict[@"type"];
        if ([widgetType isEqualToString:@"UIButton"]) {
            GTButton *button = [GTButton buttonWithType:widgetDict[@"UIButtonType"] ? [widgetDict[@"UIButtonType"] integerValue] : UIButtonTypeCustom];
            if ([self imageWithName:widgetDict[@"image"]]) {
                [button setImage:[self imageWithName:widgetDict[@"image"]] forState:UIControlStateNormal];
            }
            if ([self imageWithName:widgetDict[@"backgroundImage"]]) {
                [button setBackgroundImage:[self imageWithName:widgetDict[@"backgroundImage"]] forState:UIControlStateNormal];
            }
            if ([self isValidString:widgetDict[@"title"]]) {
                [button setTitle:widgetDict[@"title"] forState:UIControlStateNormal];
            }
            if (widgetDict[@"titleColor"]) {
                [button setTitleColor:[self colorFromHexString:widgetDict[@"titleColor"]] forState:UIControlStateNormal];
            }
            if (widgetDict[@"titleFont"]) {
                button.titleLabel.font = [UIFont systemFontOfSize:[widgetDict[@"titleFont"] doubleValue]];
            }
            if (widgetDict[@"cornerRadius"]) {
                button.layer.masksToBounds = YES;
                button.layer.cornerRadius = [widgetDict[@"cornerRadius"] doubleValue];
            }
            if (widgetDict[@"action"]) {
                button.buttonAction = widgetDict[@"action"];
                [button addTarget:self action:@selector(customButtonAction:) forControlEvents:UIControlEventTouchUpInside];
            }
            if (widgetDict[@"frame"]) {
                button.frame = [self frameFromArray:widgetDict[@"frame"]];
            }
            return button;
        } else if ([widgetType isEqualToString:@"UILabel"]) {
            UILabel *label = [UILabel new];
            if (widgetDict[@"backgroundColor"]) {
                label.backgroundColor = [self colorFromHexString:widgetDict[@"backgroundColor"]];
            }
            if (widgetDict[@"textColor"]) {
                label.textColor = [self colorFromHexString:widgetDict[@"textColor"]];
            }
            if (widgetDict[@"font"]) {
                label.font = [UIFont systemFontOfSize:[widgetDict[@"font"] doubleValue]];
            }
            if (widgetDict[@"textAlignment"]) {
                label.textAlignment = [widgetDict[@"textAlignment"] integerValue];
            }
            if (widgetDict[@"frame"]) {
                label.frame = [self frameFromArray:widgetDict[@"frame"]];
            }
            if ([self isValidString:widgetDict[@"text"]]) {
                label.text = widgetDict[@"text"];
            }
            if (widgetDict[@"cornerRadius"]) {
                label.layer.masksToBounds = YES;
                label.layer.cornerRadius = [widgetDict[@"cornerRadius"] doubleValue];
            }
            return label;
        } else if ([widgetType isEqualToString:@"UIView"]) {
            UIView *view = [UIView new];
            if (widgetDict[@"backgroundColor"]) {
                view.backgroundColor = [self colorFromHexString:widgetDict[@"backgroundColor"]];
            }
            if (widgetDict[@"frame"]) {
                view.frame = [self frameFromArray:widgetDict[@"frame"]];
            }
            if (widgetDict[@"cornerRadius"]) {
                view.layer.masksToBounds = YES;
                view.layer.cornerRadius = [widgetDict[@"cornerRadius"] doubleValue];
            }
            return view;
        } else if ([widgetType isEqualToString:@"UITextField"]) {
            UITextField *textField = [UITextField new];
            if (widgetDict[@"backgroundColor"]) {
                textField.backgroundColor = [self colorFromHexString:widgetDict[@"backgroundColor"]];
            }
            if (widgetDict[@"textColor"]) {
                textField.textColor = [self colorFromHexString:widgetDict[@"textColor"]];
            }
            if (widgetDict[@"font"]) {
                textField.font = [UIFont systemFontOfSize:[widgetDict[@"font"] doubleValue]];
            }
            if ([self isValidString:widgetDict[@"placeholder"]]) {
                textField.placeholder = widgetDict[@"placeholder"];
            }
            if (widgetDict[@"textAlignment"]) {
                textField.textAlignment = [widgetDict[@"textAlignment"] integerValue];
            }
            if (widgetDict[@"frame"]) {
                textField.frame = [self frameFromArray:widgetDict[@"frame"]];
            }
            if (widgetDict[@"cornerRadius"]) {
                textField.layer.masksToBounds = YES;
                textField.layer.cornerRadius = [widgetDict[@"cornerRadius"] doubleValue];
            }
            return textField;
        } else if ([widgetType isEqualToString:@"UITextView"]) {
            UITextView *textView = [UITextView new];
            if (widgetDict[@"backgroundColor"]) {
                textView.backgroundColor = [self colorFromHexString:widgetDict[@"backgroundColor"]];
            }
            if (widgetDict[@"textColor"]) {
                textView.textColor = [self colorFromHexString:widgetDict[@"textColor"]];
            }
            if (widgetDict[@"font"]) {
                textView.font = [UIFont systemFontOfSize:[widgetDict[@"font"] doubleValue]];
            }
            if (widgetDict[@"textAlignment"]) {
                textView.textAlignment = [widgetDict[@"textAlignment"] integerValue];
            }
            if (widgetDict[@"frame"]) {
                textView.frame = [self frameFromArray:widgetDict[@"frame"]];
            }
            if (widgetDict[@"cornerRadius"]) {
                textView.layer.masksToBounds = YES;
                textView.layer.cornerRadius = [widgetDict[@"cornerRadius"] doubleValue];
            }
            return textView;
        } else if ([widgetType isEqualToString:@"UIImageView"]) {
            UIImageView *imageView = [UIImageView new];
            if ([self imageWithName:widgetDict[@"image"]]) {
                imageView.image = [self imageWithName:widgetDict[@"image"]];
            }
            if (widgetDict[@"backgroundColor"]) {
                imageView.backgroundColor = [self colorFromHexString:widgetDict[@"backgroundColor"]];
            }
            if (widgetDict[@"frame"]) {
                imageView.frame = [self frameFromArray:widgetDict[@"frame"]];
            }
            if (widgetDict[@"cornerRadius"]) {
                imageView.layer.masksToBounds = YES;
                imageView.layer.cornerRadius = [widgetDict[@"cornerRadius"] doubleValue];
            }
            return imageView;
        }
    }
    
    return nil;
}

// MARK: Screen Width/Height

WX_EXPORT_METHOD_SYNC(@selector(screenWidth))

- (CGFloat)screenWidth {
    return [UIScreen mainScreen].bounds.size.width;
}

WX_EXPORT_METHOD_SYNC(@selector(screenHeight))

- (CGFloat)screenHeight {
    return [UIScreen mainScreen].bounds.size.height;
}

// MARK: OneLogin

WX_EXPORT_METHOD_SYNC(@selector(setLogEnabled:))

- (void)setLogEnabled:(BOOL)enabled {
    [OneLoginPro setLogEnabled:enabled];
}

WX_EXPORT_METHOD(@selector(registerWithAppID:callback:))

- (void)registerWithAppID:(NSString *)appID
                 callback:(WXModuleKeepAliveCallback)callback {
    [OneLoginPro registerWithAppID:appID];
    [OneLoginPro getPreGetTokenResult:^(NSDictionary * _Nullable result) {
        if (callback) {
            callback(result, NO);
        }
    }];
}

WX_EXPORT_METHOD_SYNC(@selector(setRequestTimeout:))

- (void)setRequestTimeout:(NSTimeInterval)timeout {
    [OneLoginPro setRequestTimeout:timeout];
}

WX_EXPORT_METHOD(@selector(requestTokenWithViewModel:callback:))

- (void)requestTokenWithViewModel:(NSDictionary *)viewModelDict
                         callback:(WXModuleKeepAliveCallback)callback {
    OLAuthViewModel *viewModel = [OLAuthViewModel new];
    if ([viewModelDict isKindOfClass:[NSDictionary class]] && viewModelDict.count > 0) {
        // *************** statusBarStyle *************** //
        if (viewModelDict[@"statusBarStyle"]) {
            viewModel.statusBarStyle = [viewModelDict[@"statusBarStyle"] integerValue];
        }
        
        // *************** naviTitle *************** //
        if (viewModelDict[@"naviTitle"]) {
            NSString *naviTitleString = [NSString stringWithFormat:@"%@", viewModelDict[@"naviTitle"]];
            NSMutableAttributedString *naviTitle = [[NSMutableAttributedString alloc] initWithString:naviTitleString];
            if (viewModelDict[@"naviTitleColor"]) {
                [naviTitle addAttributes:@{NSForegroundColorAttributeName: [self colorFromHexString:viewModelDict[@"naviTitleColor"]] ?: UIColor.blackColor} range:NSMakeRange(0, naviTitleString.length)];
            }
            if (viewModelDict[@"naviTitleFont"]) {
                [naviTitle addAttributes:@{NSFontAttributeName: [UIFont systemFontOfSize:[viewModelDict[@"naviTitleFont"] doubleValue]]} range:NSMakeRange(0, naviTitleString.length)];
            }
            viewModel.naviTitle = naviTitle.copy;
        }
        
        if (viewModelDict[@"naviBgColor"]) {
            viewModel.naviBgColor = [self colorFromHexString:viewModelDict[@"naviBgColor"]];
        }
        
        if (viewModelDict[@"naviBackImage"]) {
            viewModel.naviBackImage = [self imageWithName:viewModelDict[@"naviBackImage"]];
        }
        
        if (viewModelDict[@"naviHidden"]) {
            viewModel.naviHidden = [viewModelDict[@"naviHidden"] boolValue];
        }
        
        if (viewModelDict[@"backButtonRect"]) {
            NSArray *rectArray = viewModelDict[@"backButtonRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.backButtonRect = rect;
            }
        }
        
        if (viewModelDict[@"backButtonHidden"]) {
            viewModel.backButtonHidden = [viewModelDict[@"backButtonHidden"] boolValue];
        }
        
        // *************** appLogo *************** //
        if (viewModelDict[@"appLogo"]) {
            viewModel.appLogo = [UIImage imageNamed:[NSString stringWithFormat:@"%@", viewModelDict[@"appLogo"]]];
        }
        
        if (viewModelDict[@"logoRect"]) {
            NSArray *rectArray = viewModelDict[@"logoRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.logoRect = rect;
            }
        }
        
        if (viewModelDict[@"logoHidden"]) {
            viewModel.logoHidden = [viewModelDict[@"logoHidden"] boolValue];
        }
        
        if (viewModelDict[@"logoCornerRadius"]) {
            viewModel.logoCornerRadius = [viewModelDict[@"logoCornerRadius"] doubleValue];
        }
        
        // *************** phoneNum *************** //
        if (viewModelDict[@"phoneNumColor"]) {
            viewModel.phoneNumColor = [self colorFromHexString:viewModelDict[@"phoneNumColor"]];
        }
        
        if (viewModelDict[@"phoneNumFont"]) {
            viewModel.phoneNumFont = [UIFont systemFontOfSize:[viewModelDict[@"phoneNumFont"] doubleValue]];
        }
        
        if (viewModelDict[@"phoneNumRect"]) {
            NSArray *rectArray = viewModelDict[@"phoneNumRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.phoneNumRect = rect;
            }
        }
        
        // *************** Switch Button *************** //
        if (viewModelDict[@"switchButtonText"]) {
            viewModel.switchButtonText = [NSString stringWithFormat:@"%@", viewModelDict[@"switchButtonText"]];
        }
        
        if (viewModelDict[@"switchButtonColor"]) {
            viewModel.switchButtonColor = [self colorFromHexString:viewModelDict[@"switchButtonColor"]];
        }
        
        if (viewModelDict[@"switchButtonBackgroundColor"]) {
            viewModel.switchButtonBackgroundColor = [self colorFromHexString:viewModelDict[@"switchButtonBackgroundColor"]];
        }
        
        if (viewModelDict[@"switchButtonFont"]) {
            viewModel.switchButtonFont = [UIFont systemFontOfSize:[viewModelDict[@"switchButtonFont"] doubleValue]];
        }
        
        if (viewModelDict[@"switchButtonRect"]) {
            NSArray *rectArray = viewModelDict[@"switchButtonRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.switchButtonRect = rect;
            }
        }
        
        if (viewModelDict[@"switchButtonHidden"]) {
            viewModel.switchButtonHidden = [viewModelDict[@"switchButtonHidden"] boolValue];
        }
        
        // *************** Auth Button *************** //
        if (viewModelDict[@"authButtonImages"]) {
            NSArray *imageArray = viewModelDict[@"authButtonImages"];
            if (imageArray.count >= 3) {
                UIImage *image0 = [self imageWithName:imageArray[0]];
                UIImage *image1 = [self imageWithName:imageArray[1]];
                UIImage *image2 = [self imageWithName:imageArray[2]];
                if (image0 && image1 && image2) {
                    viewModel.authButtonImages = @[image0, image1, image2];
                }
            }
        }
        
        if (viewModelDict[@"authButtonTitle"]) {
            NSString *authButtonTitleString = [NSString stringWithFormat:@"%@", viewModelDict[@"authButtonTitle"]];
            NSMutableAttributedString *authButtonTitle = [[NSMutableAttributedString alloc] initWithString:authButtonTitleString];
            if (viewModelDict[@"authButtonTitleColor"]) {
                [authButtonTitle addAttributes:@{NSForegroundColorAttributeName: [self colorFromHexString:viewModelDict[@"authButtonTitleColor"]] ?: UIColor.blackColor} range:NSMakeRange(0, authButtonTitleString.length)];
            }
            if (viewModelDict[@"authButtonTitleFont"]) {
                [authButtonTitle addAttributes:@{NSFontAttributeName: [UIFont systemFontOfSize:[viewModelDict[@"authButtonTitleFont"] doubleValue]]} range:NSMakeRange(0, authButtonTitleString.length)];
            }
            viewModel.authButtonTitle = authButtonTitle.copy;
        }
        
        if (viewModelDict[@"authButtonRect"]) {
            NSArray *rectArray = viewModelDict[@"authButtonRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.authButtonRect = rect;
            }
        }
        
        if (viewModelDict[@"authButtonCornerRadius"]) {
            viewModel.authButtonCornerRadius = [viewModelDict[@"authButtonCornerRadius"] doubleValue];
        }
        
        // *************** slogan *************** //
        if (viewModelDict[@"sloganRect"]) {
            NSArray *rectArray = viewModelDict[@"sloganRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.sloganRect = rect;
            }
        }
        
        if (viewModelDict[@"sloganTextColor"]) {
            viewModel.sloganTextColor = [self colorFromHexString:viewModelDict[@"sloganTextColor"]];
        }
        
        if (viewModelDict[@"sloganTextFont"]) {
            viewModel.sloganTextFont = [UIFont systemFontOfSize:[viewModelDict[@"sloganTextFont"] doubleValue]];
        }
        
        // *************** Privacy Terms *************** //
        if (viewModelDict[@"defaultCheckBoxState"]) {
            viewModel.defaultCheckBoxState = [viewModelDict[@"defaultCheckBoxState"] boolValue];
        }
        
        if ([self imageWithName:viewModelDict[@"checkedImage"]]) {
            viewModel.checkedImage = [self imageWithName:viewModelDict[@"checkedImage"]];
        }
        
        if ([self imageWithName:viewModelDict[@"uncheckedImage"]]) {
            viewModel.uncheckedImage = [self imageWithName:viewModelDict[@"uncheckedImage"]];
        }
        
        if (viewModelDict[@"checkBoxRect"]) {
            NSArray *rectArray = viewModelDict[@"checkBoxRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.checkBoxRect = rect;
            }
        }
        
        NSMutableDictionary *privacyTermsAttributes = [NSMutableDictionary dictionary];
        if (viewModelDict[@"privacyTermsColor"]) {
            UIColor *privacyTermsColor = [self colorFromHexString:viewModelDict[@"privacyTermsColor"]];
            if (privacyTermsColor) {
                [privacyTermsAttributes setValue:privacyTermsColor forKey:NSForegroundColorAttributeName];
            }
        }
        if (viewModelDict[@"privacyTermsFont"]) {
            UIFont *privacyTermsFont = [UIFont systemFontOfSize:[viewModelDict[@"privacyTermsFont"] doubleValue]];
            if (privacyTermsFont) {
                [privacyTermsAttributes setValue:privacyTermsFont forKey:NSFontAttributeName];
            }
        }
        if (privacyTermsAttributes.count > 0) {
            viewModel.privacyTermsAttributes = privacyTermsAttributes.copy;
        }
        
        if (viewModelDict[@"additionalPrivacyTerms"]) {
            NSArray *additionalPrivacyTerms = viewModelDict[@"additionalPrivacyTerms"];
            if (additionalPrivacyTerms.count > 0) {
                NSMutableArray<OLPrivacyTermItem *> *items = [NSMutableArray arrayWithCapacity:additionalPrivacyTerms.count];
                for (NSInteger i = 0; i < additionalPrivacyTerms.count; i++) {
                    NSArray *itemArray = additionalPrivacyTerms[i];
                    if (itemArray.count >= 2) {
                        OLPrivacyTermItem *item = [[OLPrivacyTermItem alloc] initWithTitle:itemArray[0]
                                                                                   linkURL:[NSURL URLWithString:itemArray[1]]
                                                                                     index:itemArray.count > 2 ? [itemArray[2] integerValue] : 0];
                        [items addObject:item];
                    }
                }
                
                if (items.count > 0) {
                    viewModel.additionalPrivacyTerms = items.copy;
                }
            }
        }
        
        if (viewModelDict[@"termTextColor"]) {
            viewModel.termTextColor = [self colorFromHexString:viewModelDict[@"termTextColor"]];
        }
        
        if (viewModelDict[@"termsRect"]) {
            NSArray *rectArray = viewModelDict[@"termsRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.termsRect = rect;
            }
        }
        
        if (viewModelDict[@"auxiliaryPrivacyWords"]) {
            NSArray *auxiliaryPrivacyWords = viewModelDict[@"auxiliaryPrivacyWords"];
            if (4 == auxiliaryPrivacyWords.count) {
                viewModel.auxiliaryPrivacyWords = auxiliaryPrivacyWords;
            }
        }
        
        if (viewModelDict[@"termsAlignment"]) {
            viewModel.termsAlignment = [viewModelDict[@"termsAlignment"] integerValue];
        }
        
        // *************** Background *************** //
        if (viewModelDict[@"backgroundColor"]) {
            viewModel.backgroundColor = [self colorFromHexString:viewModelDict[@"backgroundColor"]];
        }
        
        if (viewModelDict[@"backgroundImage"]) {
            viewModel.backgroundImage = [self imageWithName:viewModelDict[@"backgroundImage"]];
        }
        
        if (viewModelDict[@"landscapeBackgroundImage"]) {
            viewModel.landscapeBackgroundImage = [self imageWithName:viewModelDict[@"landscapeBackgroundImage"]];
        }
        
        // *************** Popup *************** //
        if (viewModelDict[@"isPopup"]) {
            viewModel.isPopup = [viewModelDict[@"isPopup"] boolValue];
        }
        
        if (viewModelDict[@"popupRect"]) {
            NSArray *rectArray = viewModelDict[@"popupRect"];
            if (rectArray.count >= 8) {
                OLRect rect = {[rectArray[0] doubleValue], [rectArray[1] doubleValue], [rectArray[2] doubleValue], [rectArray[3] doubleValue], [rectArray[4] doubleValue], [rectArray[5] doubleValue], {[rectArray[6] doubleValue], [rectArray[7] doubleValue]}};
                viewModel.popupRect = rect;
            }
        }
        
        if (viewModelDict[@"popupCornerRadius"]) {
            viewModel.popupCornerRadius = [viewModelDict[@"popupCornerRadius"] doubleValue];
        }
        
        if (viewModelDict[@"popupRectCorners"]) {
            NSArray *popupRectCorners = viewModelDict[@"popupRectCorners"];
            if (popupRectCorners.count > 0) {
                viewModel.popupRectCorners = popupRectCorners;
            }
        }
        
        if (viewModelDict[@"popupAnimationStyle"]) {
            viewModel.popupAnimationStyle = [viewModelDict[@"popupAnimationStyle"] integerValue];
        }
        
        if ([self imageWithName:viewModelDict[@"closePopupImage"]]) {
            viewModel.closePopupImage = [self imageWithName:viewModelDict[@"closePopupImage"]];
        }
        
        if (viewModelDict[@"closePopupTopOffset"]) {
            viewModel.closePopupTopOffset = [viewModelDict[@"closePopupTopOffset"] isKindOfClass:[NSNumber class]] ? viewModelDict[@"closePopupTopOffset"] : [NSNumber numberWithDouble:[viewModelDict[@"closePopupTopOffset"] doubleValue]];
        }
        
        if (viewModelDict[@"closePopupRightOffset"]) {
            viewModel.closePopupRightOffset = [viewModelDict[@"closePopupRightOffset"] isKindOfClass:[NSNumber class]] ? viewModelDict[@"closePopupRightOffset"] : [NSNumber numberWithDouble:[viewModelDict[@"closePopupRightOffset"] doubleValue]];
        }
        
        if (viewModelDict[@"canClosePopupFromTapGesture"]) {
            viewModel.canClosePopupFromTapGesture = [viewModelDict[@"canClosePopupFromTapGesture"] boolValue];
        }
        
        // *************** WebController NavigationBar *************** //
        if (viewModelDict[@"webNaviTitle"]) {
            NSString *webNaviTitleString = [NSString stringWithFormat:@"%@", viewModelDict[@"webNaviTitle"]];
            NSMutableAttributedString *webNaviTitle = [[NSMutableAttributedString alloc] initWithString:webNaviTitleString];
            if (viewModelDict[@"webNaviTitleColor"]) {
                [webNaviTitle addAttributes:@{NSForegroundColorAttributeName: [self colorFromHexString:viewModelDict[@"webNaviTitleColor"]] ?: UIColor.blackColor} range:NSMakeRange(0, webNaviTitleString.length)];
            }
            if (viewModelDict[@"webNaviTitleFont"]) {
                [webNaviTitle addAttributes:@{NSFontAttributeName: [UIFont systemFontOfSize:[viewModelDict[@"webNaviTitleFont"] doubleValue]]} range:NSMakeRange(0, webNaviTitleString.length)];
            }
            viewModel.webNaviTitle = webNaviTitle.copy;
        }
        
        if (viewModelDict[@"webNaviBgColor"]) {
            viewModel.webNaviBgColor = [self colorFromHexString:viewModelDict[@"webNaviBgColor"]];
        }
        
        // *************** Hint *************** //
        if (viewModelDict[@"notCheckProtocolHint"]) {
            viewModel.notCheckProtocolHint = viewModelDict[@"notCheckProtocolHint"];
        }
        
        // *************** UIModalPresentationStyle *************** //
        if (viewModelDict[@"modalPresentationStyle"]) {
            viewModel.modalPresentationStyle = [viewModelDict[@"modalPresentationStyle"] integerValue];
        }
        
        // *************** OLPullAuthVCStyle *************** //
        if (viewModelDict[@"pullAuthVCStyle"]) {
            viewModel.pullAuthVCStyle = [viewModelDict[@"pullAuthVCStyle"] integerValue];
        }
        
        // *************** UIUserInterfaceStyle *************** //
        if (viewModelDict[@"userInterfaceStyle"]) {
            viewModel.userInterfaceStyle = [viewModelDict[@"userInterfaceStyle"] isKindOfClass:[NSNumber class]] ? viewModelDict[@"userInterfaceStyle"] : [NSNumber numberWithInteger:[viewModelDict[@"userInterfaceStyle"] integerValue]];
        }
        
        // *************** block *************** //
        if (viewModelDict[@"widgets"]) {
            NSArray *widgets = viewModelDict[@"widgets"];
            if (widgets.count > 0) {
                viewModel.customUIHandler = ^(UIView * _Nonnull customAreaView) {
                    for (NSInteger i = 0; i < widgets.count; i++) {
                        NSDictionary *widgetDict = widgets[i];
                        UIView *view = [self widgetFromDict:widgetDict];
                        if (view && !CGRectEqualToRect(CGRectZero, view.frame)) {
                            [customAreaView addSubview:view];
                        }
                    }
                };
            }
        }
        
        __weak typeof(self) wself = self;
        if (viewModelDict[@"authVCTransitionBlock"]) {
            viewModel.authVCTransitionBlock = ^(CGSize size, id<UIViewControllerTransitionCoordinator>  _Nonnull coordinator, UIView * _Nonnull customAreaView) {
                [wself invokeJSMethod:viewModelDict[@"authVCTransitionBlock"] params:nil];
            };
        }
        
        if (viewModelDict[@"tapAuthBackgroundBlock"]) {
            viewModel.tapAuthBackgroundBlock = ^{
                [wself invokeJSMethod:viewModelDict[@"tapAuthBackgroundBlock"] params:nil];
            };
        }
        
        if (viewModelDict[@"viewLifeCycleBlock"]) {
            viewModel.viewLifeCycleBlock = ^(NSString * _Nonnull viewLifeCycle, BOOL animated) {
                [wself invokeJSMethod:viewModelDict[@"viewLifeCycleBlock"] params:viewLifeCycle, (animated ? @"true" : @"false"), nil];
            };
        }
        
        if (viewModelDict[@"clickBackButtonBlock"]) {
            viewModel.clickBackButtonBlock = ^{
                [wself invokeJSMethod:viewModelDict[@"clickBackButtonBlock"] params:nil];
            };
        }
        
        if (viewModelDict[@"clickSwitchButtonBlock"]) {
            viewModel.clickSwitchButtonBlock = ^{
                [wself invokeJSMethod:viewModelDict[@"clickSwitchButtonBlock"] params:nil];
            };
        }
        
        if (viewModelDict[@"clickCheckboxBlock"]) {
            viewModel.clickCheckboxBlock = ^(BOOL isChecked) {
                [wself invokeJSMethod:viewModelDict[@"clickCheckboxBlock"] params:(isChecked ? @"true" : @"false"), nil];
            };
        }
    }
    
    [OneLoginPro requestTokenWithViewController:[self findCurrentShowingViewController] viewModel:viewModel completion:^(NSDictionary * _Nullable result) {
        if (callback) {
            callback(result, NO);
        }
    }];
}

WX_EXPORT_METHOD_SYNC(@selector(dismissAuthViewController))

- (void)dismissAuthViewController {
    [OneLoginPro dismissAuthViewController:nil];
}

WX_EXPORT_METHOD_SYNC(@selector(renewPreGetToken))

- (void)renewPreGetToken {
    [OneLoginPro renewPreGetToken];
}

WX_EXPORT_METHOD_SYNC(@selector(sdkVersion))

- (NSString *)sdkVersion {
    return [OneLoginPro sdkVersion];
}

WX_EXPORT_METHOD_SYNC(@selector(currentNetworkInfo))

- (NSDictionary *)currentNetworkInfo {
    OLNetworkInfo *networkInfo = [OneLoginPro currentNetworkInfo];
    NSMutableDictionary *infoDict = [NSMutableDictionary dictionary];
    [infoDict setValue:networkInfo.carrierName forKey:@"carrierName"];
    [infoDict setValue:networkInfo.detailNetworkType forKey:@"detailNetworkType"];
    [infoDict setValue:@(networkInfo.networkType) forKey:@"networkType"];
    return infoDict.copy;
}

// MARK: OnePass

WX_EXPORT_METHOD_SYNC(@selector(initWithCustomID:timeout:))

- (void)initWithCustomID:(NSString *)customID
                 timeout:(NSTimeInterval)timeout {
    self.gopManager = [[GOPManager alloc] initWithCustomID:customID timeout:timeout];
    self.gopManager.delegate = self;
}

WX_EXPORT_METHOD(@selector(verifyPhoneNumber:callback:))

- (void)verifyPhoneNumber:(NSString *)phoneNumber
                 callback:(WXModuleKeepAliveCallback)callback {
    self.onepassCallback = callback;
    [self.gopManager verifyPhoneNumber:phoneNumber];
}

// MARK: GOPManagerDelegate

- (void)gtOnePass:(GOPManager *)manager didReceiveDataToVerify:(NSDictionary *)data {
    if (self.onepassCallback) {
        self.onepassCallback(data, NO);
    }
}

- (void)gtOnePass:(GOPManager *)manager errorHandler:(GOPError *)error {
    if (self.onepassCallback) {
        self.onepassCallback(@{@"error": error.localizedDescription ?: @""}, NO);
    }
}

@end
