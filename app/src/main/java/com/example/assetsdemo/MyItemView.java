package com.example.assetsdemo;

import android.content.Context;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by wangxiaoyan1 on 4/13/21
 */
public class MyItemView extends FrameLayout {
    private TextView mTxt;

    public MyItemView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MyItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, this);
        mTxt = view.findViewById(R.id.txt);
        Log.i("wxy", "---mTxt.isFocusable()--" + mTxt.isFocusable());
        Log.i("wxy", "---mTxt.isClickable()---" + mTxt.isClickable());
        Log.i("wxy", "---mTxt.isEnabled()---" + mTxt.isEnabled());
        mTxt.setClickable(true);
        mTxt.setFocusable(true);
        mTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wxy", "---onClick--------------------");
            }
        });
        setClickable(true);
        Log.i("wxy", "------" + isFocusable());
        Log.i("wxy", "------" + isClickable());
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("wxy", "OnKeyListener" + event.getAction() + "******" + event.getKeyCode());
                return false;
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("wxy", "setOnClickListener");
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.i("wxy", "dispatchKeyEvent" + event.getAction() + "******" + event.getKeyCode());
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("wxy", "onKeyDown" + event.getAction() + "******" + event.getKeyCode());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("wxy", "onKeyUp" + event.getAction() + "******" + event.getKeyCode());
        return super.onKeyUp(keyCode, event);
    }
}
