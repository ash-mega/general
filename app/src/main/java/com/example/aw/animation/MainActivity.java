package com.example.aw.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    
    private ViewGroup dmPanel,sizePanel;
    
    private RecyclerView cListView;
    
    private List<String> conditions = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cListView = findViewById(R.id.search_conditions_list);
        setView();
    }
    
    private void setView() {
        final View dmHeader = findViewById(R.id.search_dm_header);
        final ImageView dateArrow = findViewById(R.id.date_modified_arrow);
        dmPanel = findViewById(R.id.dm_options_panel);
        
        final View sizeHeader = findViewById(R.id.search_size_header);
        final ImageView sizeArrow = findViewById(R.id.size_arrow);
        sizePanel = findViewById(R.id.size_options_panel);
        
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_translate_in);
        final Animation fadeOutAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_translate_out);
        final Animation rotateUpAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.arrow_rotate_up);
        final Animation rotateDownAnimation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.arrow_rotate_down);
        
        fadeOutAnimation.setAnimationListener(new AnimListenerAdapter() {
            
            @Override
            public void onAnimationEnd(Animation animation) {
                if(dmPanel.getVisibility() != View.GONE) {
                    dmPanel.setVisibility(View.GONE);
                }
                if(sizePanel.getVisibility() != View.GONE) {
                    sizePanel.setVisibility(View.GONE);
                }
            }
        });
        
        dmHeader.setOnClickListener(new View.OnClickListener() {
            
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
        
        sizeHeader.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (sizePanel.getVisibility() == View.GONE) {
                    sizePanel.startAnimation(fadeInAnimation);
                    sizePanel.setVisibility(View.VISIBLE);
                    
                    sizeArrow.startAnimation(rotateUpAnimation);
                } else {
                    sizeArrow.startAnimation(rotateDownAnimation);
                    sizePanel.startAnimation(fadeOutAnimation);
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
    
    public void selectType(View v) {
        log("select condition:" + v.getId());
        switch (v.getId()) {
        
        }
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("17-Aug"," ---> :" + msg);
    }
}
