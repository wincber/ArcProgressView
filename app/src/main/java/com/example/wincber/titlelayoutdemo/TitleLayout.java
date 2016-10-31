package com.example.wincber.titlelayoutdemo;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wincber on 10/23/2016.
 */

public class TitleLayout extends LinearLayout {
    private TextView tv_title;
    private ImageView iv_back;
    private ImageView iv_setting;

    public TitleLayout(Context context) {
        super(context);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titlelayout,this);
        tv_title = (TextView)findViewById(R.id.title_tv);
        iv_back = (ImageView)findViewById(R.id.title_back_iv);
        iv_setting = (ImageView)findViewById(R.id.title_setting_iv);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /* public TitleLayout(final Context context) {
         super(context);

     }*/
    public void setTitleText(CharSequence title) {
        tv_title.setText(title);
    }
    public void setTitleTextSize(float textSize) {
        tv_title.setTextSize(textSize);
    }
    public void setSettingOnClickListener(OnClickListener ol) {
        iv_setting.setOnClickListener(ol);
    }
}
