package com.example.aw.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    
    private ViewGroup dmPanel, sizePanel;
    
    private int lastClickId;
    
    private RecyclerView cListView;
    
    private static final SparseArray<String> conditionsMapping = new SparseArray<>();
    
    private static final Map<Integer, String> conditions = new HashMap<>();

//    public static final List<String> conditions = new LinkedList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMapping();
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
                if (dmPanel.getVisibility() != View.GONE) {
                    dmPanel.setVisibility(View.GONE);
                }
                if (sizePanel.getVisibility() != View.GONE) {
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
    
    /**
     * Condition click listener.
     *
     * @param v Condition layout(RelativeLayout).
     */
    public void selectCondition(View v) {
        int id = v.getId();
        if (id == -1) {
            id = ((View)v.getParent()).getId();
        }
        if (conditions.containsKey(id)) {
            conditions.remove(id);
            unSelect(id);
        } else {
            conditions.put(id,conditionsMapping.get(id));
            select(id);
        }
        log(conditions.values());
    }
    
    private void select(int id) {
        Animation flipAnimation = AnimationUtils.loadAnimation(this,R.anim.multiselect_flip);
        ImageView icon = getIcon(id);
        icon.startAnimation(flipAnimation);
        icon.setImageResource(R.drawable.ic_select_folder);
    }
    
    private void unSelect(int id) {
        ImageView icon = getIcon(id);
        Animation flipAnimation = AnimationUtils.loadAnimation(this,R.anim.multiselect_flip);
        icon.startAnimation(flipAnimation);
        icon.setImageResource(getIconDrawableId(icon.getTag()));
    }
    
    private ImageView getIcon(int parentId) {
        return (ImageView)((ViewGroup)findViewById(parentId)).getChildAt(0);
    }
    
    private void loadMapping() {
        //Types
        conditionsMapping.put(R.id.search_type_video,getString(R.string.search_type_video));
        conditionsMapping.put(R.id.search_type_audio,getString(R.string.search_type_audio));
        conditionsMapping.put(R.id.search_type_folder,getString(R.string.search_type_folder));
        conditionsMapping.put(R.id.search_type_image,getString(R.string.search_type_image));
        conditionsMapping.put(R.id.search_type_pdf,getString(R.string.search_type_pdf));
        conditionsMapping.put(R.id.search_type_powerpoint,getString(R.string.search_type_powerpoint));
        conditionsMapping.put(R.id.search_type_spreadsheet,getString(R.string.search_type_spreadsheet));
        conditionsMapping.put(R.id.search_type_text,getString(R.string.search_type_text));
        
        //Date modified
        conditionsMapping.put(R.id.search_dm_anytime,getString(R.string.search_dm_anytime));
        conditionsMapping.put(R.id.search_dm_today,getString(R.string.search_dm_today));
        conditionsMapping.put(R.id.search_dm_lastweek,getString(R.string.search_dm_lastweek));
        conditionsMapping.put(R.id.search_dm_lastmonth,getString(R.string.search_dm_lastmonth));
        conditionsMapping.put(R.id.search_dm_lastyear,getString(R.string.search_dm_lastyear));
        
        //Size
        conditionsMapping.put(R.id.search_size_anysize,getString(R.string.search_size_anysize));
        conditionsMapping.put(R.id.search_size_05,getString(R.string.search_size_05));
        conditionsMapping.put(R.id.search_size_525,getString(R.string.search_size_525));
        conditionsMapping.put(R.id.search_size_1001,getString(R.string.search_size_1001));
        conditionsMapping.put(R.id.search_size_25100,getString(R.string.search_size_25100));
    }
    
    private int getIconDrawableId(Object tag) {
        Class<R.drawable> drawableClass = R.drawable.class;
        try {
            Field field = drawableClass.getField(tag.toString());
            field.setAccessible(true);
            return (int)field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return R.drawable.ic_folder_list;
    }
    
    private void log(Object anything) {
        String msg = (anything == null) ? "NULL" : anything.toString();
        Log.e("@#@"," ---> :" + msg);
    }
}
