package com.zgdj.djframe.activity.other;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageButton;

import com.zgdj.djframe.R;
import com.zgdj.djframe.unity3d.MyUnityPlayer;
import com.zgdj.djframe.unity3d.UnityUtil;

/**
 * Unity3D
 */
public class Unity3DActivity extends AppCompatActivity {

    private ImageButton back;//返回键

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
        UnityUtil.mUnityPlayer = new MyUnityPlayer(this);

        setContentView(R.layout.activity_unity3_d);
        UnityUtil.mUnityPlayer.requestFocus();


        back = findViewById(R.id.img_unity_back);
        back.setOnClickListener(v -> finish());



        if (savedInstanceState != null) {
            // Restore value of members from saved state
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString("textview", mTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /************************   Unity3D相关设置   *************************************************/
    @Override
    protected void onResume() {
        super.onResume();
        UnityUtil.mUnityPlayer.resume();// Resume Unity
    }

    @Override
    protected void onPause() {
        super.onPause();
        UnityUtil.mUnityPlayer.pause();// Pause Unity
    }

    @Override
    protected void onDestroy() {
        UnityUtil.mUnityPlayer.quit();// Quit Unity
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UnityUtil.mUnityPlayer.configurationChanged(newConfig); // This ensures the layout will be correct.
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        UnityUtil.mUnityPlayer.windowFocusChanged(hasFocus); // Notify Unity of the focus change.
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // For some reason the multiple keyevent type is not supported by the ndk.
        // Force event injection by overriding dispatchKeyEvent().
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return UnityUtil.mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return UnityUtil.mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return UnityUtil.mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return UnityUtil.mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return UnityUtil.mUnityPlayer.injectEvent(event);
    }
}
