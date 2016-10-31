package com.example.wincber.titlelayoutdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    TitleLayout tl_title;
    PopCircle popCircle;
    ImageView iv_setting;
    MyArcProgress ap_progress;
    MyArcProgress ap_progress_2;
    EyeProgress ep_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tl_title = (TitleLayout)findViewById(R.id.title_tl);
        iv_setting = (ImageView)tl_title.findViewById(R.id.title_setting_iv);
        ap_progress = (MyArcProgress) findViewById(R.id.arc_progress_ap);
        ep_progress = (EyeProgress)findViewById(R.id.progress_ep);
       // ap_progress_2 = (MyArcProgress)findViewById(R.id.arc_progress_ap_2);
      //  ap_progress_2.setProgressColor(Color.BLUE);
        ap_progress.setAnimation(40);
    //    ap_progress_2.setAnimation(30);
        ep_progress.startAnimation(100);
       /* Animation animation = AnimationUtils.loadAnimation(this,R.anim.scaleanim);
        iv_setting.startAnimation(animation);*/
    }
}
