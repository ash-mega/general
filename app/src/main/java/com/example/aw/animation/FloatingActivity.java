package com.example.aw.animation;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class FloatingActivity extends MainActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout container = (FrameLayout)getWindow().getDecorView();
        int w1 = container.getLayoutParams().width;
        int h1 = container.getLayoutParams().height;
        Log.e("@#@",w1 + "/" + h1);
        View content = getLayoutInflater().inflate(R.layout.fragment_search_panel_type,null);
        container.addView(content);
        int w2 = container.getLayoutParams().width;
        int h2 = container.getLayoutParams().height;
        Log.e("@#@",w2 + "/" + h2);

//        setContentView(R.layout.fragment_search_panel_type);
//        WindowManager.LayoutParams wlp = getWindow().getAttributes();
//        wlp.dimAmount = 0;
//        wlp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        wlp.x = 200;
//        getWindow().setAttributes(wlp);
    }
    
    @Override
    public void onAttachedToWindow() {
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams)view
                .getLayoutParams();
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        lp.x = 60;
        lp.y = 100;
        getWindowManager().updateViewLayout(view,lp);
    }
    
}
