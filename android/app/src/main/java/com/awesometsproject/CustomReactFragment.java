package com.awesometsproject;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;

import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

public class CustomReactFragment extends Fragment implements PermissionAwareActivity {
    protected static final String ARG_COMPONENT_NAME = "arg_component_name";
    protected static final String ARG_LAUNCH_OPTIONS = "arg_launch_options";
    private CustomReactDelegate mReactDelegate;
    @Nullable
    private PermissionListener mPermissionListener;

    public CustomReactFragment() {
    }

    private static CustomReactFragment newInstance(String componentName, Bundle launchOptions) {
        CustomReactFragment fragment = new CustomReactFragment();
        Bundle args = new Bundle();
        args.putString("arg_component_name", componentName);
        args.putBundle("arg_launch_options", launchOptions);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mainComponentName = null;
        Bundle launchOptions = null;
        if (this.getArguments() != null) {
            mainComponentName = this.getArguments().getString("arg_component_name");
            launchOptions = this.getArguments().getBundle("arg_launch_options");
        }

        if (mainComponentName == null) {
            throw new IllegalStateException("Cannot loadApp if component name is null");
        } else {
            this.mReactDelegate = new CustomReactDelegate(this.getActivity(), this.getReactNativeHost(), mainComponentName, launchOptions);
        }
    }

    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication)this.getActivity().getApplication()).getReactNativeHost();
    }

    protected CustomReactDelegate getReactDelegate() {
        return this.mReactDelegate;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mReactDelegate.loadApp();
        return this.mReactDelegate.getReactRootView();
    }

    public void onResume() {
        super.onResume();
        this.mReactDelegate.onHostResume();
    }

    public void onPause() {
        super.onPause();
        this.mReactDelegate.onHostPause();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mReactDelegate.onHostDestroy();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mReactDelegate.onActivityResult(requestCode, resultCode, data, false);
    }

    public boolean onBackPressed() {
        return this.mReactDelegate.onBackPressed();
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return this.mReactDelegate.shouldShowDevMenuOrReload(keyCode, event);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.mPermissionListener != null && this.mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            this.mPermissionListener = null;
        }

    }

    public int checkPermission(String permission, int pid, int uid) {
        return this.getActivity().checkPermission(permission, pid, uid);
    }

    @TargetApi(23)
    public int checkSelfPermission(String permission) {
        return this.getActivity().checkSelfPermission(permission);
    }

    @TargetApi(23)
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        this.mPermissionListener = listener;
        this.requestPermissions(permissions, requestCode);
    }

    public static class Builder {
        String mComponentName = null;
        Bundle mLaunchOptions = null;

        public Builder() {
        }

        public CustomReactFragment.Builder setComponentName(String componentName) {
            this.mComponentName = componentName;
            return this;
        }

        public CustomReactFragment.Builder setLaunchOptions(Bundle launchOptions) {
            this.mLaunchOptions = launchOptions;
            return this;
        }

        public CustomReactFragment build() {
            return CustomReactFragment.newInstance(this.mComponentName, this.mLaunchOptions);
        }
    }
}
