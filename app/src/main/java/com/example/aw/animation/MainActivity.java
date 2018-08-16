package com.example.aw.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
    }
    
    private void setView() {
        final ImageView dateArrow = findViewById(R.id.date_modified_arrow);
        final View dmPanel = findViewById(R.id.dm_options_panel);
        
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_translate_in);
        final Animation fadeOutAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_translate_out);
        final Animation rotateUpAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.arrow_rotate_up);
        final Animation rotateDownAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.arrow_rotate_down);
        
        fadeOutAnimation.setAnimationListener(new AnimListenerAdapter() {
            
            @Override
            public void onAnimationEnd(Animation animation) {
                dmPanel.setVisibility(View.GONE);
            }
        });
        
        dateArrow.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (dmPanel.getVisibility() == View.GONE) {
                    dmPanel.startAnimation(fadeInAnimation);
                    dmPanel.setVisibility(View.VISIBLE);
                    
                    dateArrow.startAnimation(rotateUpAnimation);
                } else {
                    dateArrow.startAnimation(rotateDownAnimation);
                    dmPanel.startAnimation(fadeOutAnimation);
                }
            }
        });
    }
    
    private class AnimListenerAdapter implements Animation.AnimationListener {
        
        @Override
        public void onAnimationStart(Animation animation) {
        
        }
        
        @Override
        public void onAnimationEnd(Animation animation) {
        
        }
        
        @Override
        public void onAnimationRepeat(Animation animation) {
        
        }
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("16-Aug"," ---> :" + msg);
    }
}
