package com.awesometsproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.CustomReactRootView;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

public class CustomReactDelegate {
    private final Activity mActivity;
    private CustomReactRootView mReactRootView;
    @Nullable
    private final String mMainComponentName;
    @Nullable
    private Bundle mLaunchOptions;
    @Nullable
    private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
    private ReactNativeHost mReactNativeHost;

    public CustomReactDelegate(Activity activity, ReactNativeHost reactNativeHost, @Nullable String appKey, @Nullable Bundle launchOptions) {
        this.mActivity = activity;
        this.mMainComponentName = appKey;
        this.mLaunchOptions = launchOptions;
        this.mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
        this.mReactNativeHost = reactNativeHost;
    }

    public void onHostResume() {
        if (this.getReactNativeHost().hasInstance()) {
            if (!(this.mActivity instanceof DefaultHardwareBackBtnHandler)) {
                throw new ClassCastException("Host Activity does not implement DefaultHardwareBackBtnHandler");
            }

            this.getReactNativeHost().getReactInstanceManager().onHostResume(this.mActivity, (DefaultHardwareBackBtnHandler)this.mActivity);
        }

    }

    public void onHostPause() {
        if (this.getReactNativeHost().hasInstance()) {
            this.getReactNativeHost().getReactInstanceManager().onHostPause(this.mActivity);
        }

    }

    public void onHostDestroy() {
        if (this.mReactRootView != null) {
            this.mReactRootView.unmountReactApplication();
            this.mReactRootView = null;
        }

        if (this.getReactNativeHost().hasInstance()) {
            this.getReactNativeHost().getReactInstanceManager().onHostDestroy(this.mActivity);
        }

    }

    public boolean onBackPressed() {
        if (this.getReactNativeHost().hasInstance()) {
            this.getReactNativeHost().getReactInstanceManager().onBackPressed();
            return true;
        } else {
            return false;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, boolean shouldForwardToReactInstance) {
        if (this.getReactNativeHost().hasInstance() && shouldForwardToReactInstance) {
            this.getReactNativeHost().getReactInstanceManager().onActivityResult(this.mActivity, requestCode, resultCode, data);
        }

    }

    public void loadApp() {
        this.loadApp(this.mMainComponentName);
    }

    public void loadApp(String appKey) {
        if (this.mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        } else {
            this.mReactRootView = this.createRootView();
            this.mReactRootView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            this.mReactRootView.startReactApplication(this.getReactNativeHost().getReactInstanceManager(), appKey, this.mLaunchOptions);
        }
    }

    public CustomReactRootView getReactRootView() {
        return this.mReactRootView;
    }

    protected CustomReactRootView createRootView() {
        return new CustomReactRootView(this.mActivity);
    }

    public boolean shouldShowDevMenuOrReload(int keyCode, KeyEvent event) {
        if (this.getReactNativeHost().hasInstance() && this.getReactNativeHost().getUseDeveloperSupport()) {
            if (keyCode == 82) {
                this.getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
                return true;
            }

            boolean didDoubleTapR = ((DoubleTapReloadRecognizer) Assertions.assertNotNull(this.mDoubleTapReloadRecognizer)).didDoubleTapR(keyCode, this.mActivity.getCurrentFocus());
            if (didDoubleTapR) {
                this.getReactNativeHost().getReactInstanceManager().getDevSupportManager().handleReloadJS();
                return true;
            }
        }

        return false;
    }

    private ReactNativeHost getReactNativeHost() {
        return this.mReactNativeHost;
    }

    public ReactInstanceManager getReactInstanceManager() {
        return this.getReactNativeHost().getReactInstanceManager();
    }
}
