package com.awesometsproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactFragment;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;

public class MainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
//
//    /**
//     * Returns the name of the main component registered from JavaScript. This is used to schedule
//     * rendering of the component.
//     */
//    @Override
//    protected String getMainComponentName() {
//        return "AwesomeTSProject";
//    }
//
//    @Override
//    protected ReactActivityDelegate createReactActivityDelegate() {
//        return new ReactActivityDelegate(this, getMainComponentName()) {
//            @Override
//            protected ReactRootView createRootView() {
//                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.activity_main, null);
//                return vg.findViewById(R.id.container);
//            }
//        };
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.background).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("leon", "background clicked");
            }
        });
//        findViewById(R.id.background).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("leon", "background touched: "+ event.toString());
//                return false;
//            }
//        });
//
//
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("leon", "Button clicked");
//            }
//        });

        findViewById(R.id.circle).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    v.setX(event.getX());
                    v.setY(event.getY());
                }
                return true;
            }
        });

        Fragment reactNativeFragment = new CustomReactFragment.Builder()
                .setComponentName("AwesomeTSProject")
                .build();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, reactNativeFragment,"").commitAllowingStateLoss();
    }

    @Override
    public void invokeDefaultOnBackPressed() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
