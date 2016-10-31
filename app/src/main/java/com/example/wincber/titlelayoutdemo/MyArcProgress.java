package com.example.wincber.titlelayoutdemo;

import android.animation.Animator;
import android.animation.FloatArrayEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

/**
 * Created by wincber on 10/24/2016.
 */

public class MyArcProgress extends View {
    private int mMeasureWidth,mMeasureHeight;

    private Paint arcPaint;
    private Paint proPaint;
    private Paint textPaint;
    private Paint ballPaint;
    private float mAcrAngle = 180;
    private float setAngle = 60;
    private RectF mArcRectF;
    private float maxProgress = 100;
    private String mProgressText = "0%";
    private int progressColor = Color.RED;
    private SweepGradient mSweepGradient;

    private ValueAnimator mAnimator;
    public MyArcProgress(Context context) {
        super(context);
    }

    public MyArcProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mMeasureWidth,mMeasureHeight);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mArcRectF,180,mAcrAngle,false,arcPaint);
        canvas.drawArc(mArcRectF,180,setAngle,false,proPaint);
        canvas.drawText(mProgressText,mMeasureWidth/2,mMeasureHeight/4*3,textPaint);
        canvas.translate(getWidth()/2, getHeight());
        canvas.rotate(setAngle);
        canvas.drawCircle(-(mMeasureWidth-40)/2,0,20,ballPaint);
        canvas.rotate(-setAngle);

    }

    void initView(){
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        proPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        arcPaint.setColor(Color.GRAY);
        proPaint.setColor(progressColor);
        textPaint.setColor(Color.BLUE);
        ballPaint.setColor(Color.WHITE);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(15);
        proPaint.setStyle(Paint.Style.STROKE);
        proPaint.setStrokeWidth(15);
        textPaint.setTextSize(40);
        mSweepGradient = new SweepGradient(mMeasureWidth,mMeasureHeight,new int[] {Color.GRAY,Color.TRANSPARENT,Color.MAGENTA,Color.BLUE,Color.YELLOW,Color.GREEN,Color.RED,Color.RED }, null);
        proPaint.setShader(mSweepGradient);
        if(mMeasureHeight > mMeasureWidth) {
            mArcRectF = new RectF(20,mMeasureHeight/2-mMeasureWidth/2+20,mMeasureWidth-20,2*(mMeasureHeight/2+mMeasureWidth/2)-20);
            //mArcRectF = new RectF(20,mMeasureHeight/2-mMeasureWidth/2,mMeasureWidth-20,mMeasureHeight/2+mMeasureWidth/2);
        }else {
           // mArcRectF = new RectF(mMeasureWidth/2-mMeasureHeight/2,20,mMeasureWidth/2+mMeasureHeight/2,mMeasureHeight-20);
            mArcRectF = new RectF(20,mMeasureHeight-mMeasureWidth/2+20,mMeasureWidth-20,mMeasureHeight+mMeasureWidth/2-20);
        }

    }
    void setAnimation(float targetProgress) {
        cancelAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ValueAnimator.ofObject(new FloatEvaluator(),0,targetProgress);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value =(float) mAnimator.getAnimatedValue();
                    setProgress(value);
                }
            });
            mAnimator.setDuration((long)targetProgress * 50);
            mAnimator.start();
        }
    }
    void cancelAnimation() {
        if(mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }
    public void setProgress(float progress) {
        setAngle = progress / maxProgress*360;
        mProgressText = (int)(setAngle / 3.6) + "%";
        if(progress == 50) {
            mProgressText = "满了>_<";
        }
        postInvalidate();
    }
    public void setMaxProgress(float progress) {
        maxProgress = progress;
        postInvalidate();
    }
    public void setProgressColor(int color) {
        progressColor = color;
        postInvalidate();
    }
}
