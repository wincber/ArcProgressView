package com.example.wincber.titlelayoutdemo;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by wincber on 10/28/2016.
 */

public class EyeProgress extends View {
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private Paint facePaint;
    private Paint eyePaint;
    private Paint glassPaint;
    private Paint primePaint;
    private Paint proPaint;
    private Paint smilePaint;
    private RectF progressRectF;

    private Path mBezierPath;
    //private RectF glassRecF;
   // private Rect eyeRecF;
    private SweepGradient mSweepGradient;
    private SweepGradient faceGradient;
    private ValueAnimator mAnimator;
    private int length;
    private float sweepAngle = 360;
    private float tempLength = 0;
    public EyeProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EyeProgress(Context context) {
        super(context);
    }

    public EyeProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        mMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mMeasuredWidth,mMeasuredHeight);
        length = mMeasuredHeight < mMeasuredWidth ? mMeasuredHeight:mMeasuredWidth;
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mMeasuredWidth/2,mMeasuredHeight/2,length/2-20,facePaint);
        canvas.drawArc(progressRectF,0,360,false,primePaint);
        canvas.drawArc(progressRectF,0,sweepAngle,false,proPaint);
        canvas.drawCircle(mMeasuredWidth/2-length/4,mMeasuredHeight/2,length/5,glassPaint);
        canvas.drawCircle(mMeasuredWidth/2+length/4,mMeasuredHeight/2,length/5,glassPaint);
        canvas.drawCircle(mMeasuredWidth/2,mMeasuredHeight/2+length/6,length/20,glassPaint);

        mBezierPath.moveTo(mMeasuredWidth/2-length/4+length/5,mMeasuredHeight/2+length/4);
        mBezierPath.quadTo(mMeasuredWidth/2,mMeasuredHeight/2+length/4+tempLength,mMeasuredWidth/2+length/4-length/5,mMeasuredHeight/2+length/4);
        canvas.drawPath(mBezierPath,smilePaint);

        canvas.translate(mMeasuredWidth/2-length/4,mMeasuredHeight/2);
        canvas.rotate(sweepAngle);
        canvas.drawCircle(length/10,0,length/10,eyePaint);
        canvas.rotate(-sweepAngle);
        canvas.translate(-(mMeasuredWidth/2-length/4),-mMeasuredHeight/2);
        canvas.translate(mMeasuredWidth/2+length/4,mMeasuredHeight/2);
        canvas.rotate(sweepAngle);
        canvas.drawCircle(length/10,0,length/10,eyePaint);
        canvas.rotate(-sweepAngle);
    }
    void initView() {
        eyePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glassPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        primePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        proPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        facePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBezierPath = new Path();
        primePaint.setStyle(Paint.Style.STROKE);
        smilePaint.setStyle(Paint.Style.STROKE);
        eyePaint.setColor(Color.WHITE);
        proPaint.setStyle(Paint.Style.STROKE);
        glassPaint.setColor(Color.GRAY);
        smilePaint.setColor(Color.GRAY);
        primePaint.setColor(Color.GRAY);
        proPaint.setColor(Color.BLACK);
        primePaint.setStrokeWidth(15);
        proPaint.setStrokeWidth(15);
        eyePaint.setAntiAlias(true);
        glassPaint.setAntiAlias(true);
        primePaint.setAntiAlias(true);
        proPaint.setAntiAlias(true);
        smilePaint.setAntiAlias(true);
        progressRectF = new RectF(mMeasuredWidth/2-length/2+20,mMeasuredHeight/2-length/2+20,mMeasuredWidth/2+length/2-20,mMeasuredHeight/2+length/2-20);
        mSweepGradient = new SweepGradient(mMeasuredWidth/2,mMeasuredHeight/2 , new int[] {Color.LTGRAY,Color.GRAY,Color.LTGRAY,Color.DKGRAY ,Color.LTGRAY}, null);
       faceGradient = new SweepGradient(mMeasuredWidth/2,mMeasuredHeight/2,new int[]{0xFFFFD700,0xFFFFC125,0xFFFFC125,0xFFFFD700},null);
        facePaint.setShader(faceGradient);
        proPaint.setShader(mSweepGradient);
    }
    void startAnimation(float targetProgress) {
        cancerAnimation();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ValueAnimator.ofObject(new FloatEvaluator(),0,targetProgress);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float)mAnimator.getAnimatedValue();
                    setProgress(value);
                }
            });
            mAnimator.setDuration((long)targetProgress * 50);
            mAnimator.start();
        }
    }
    void cancerAnimation() {
        if((mAnimator != null) && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }
    void setProgress(float progress) {
        sweepAngle = (float)(progress * 3.6);
        if(tempLength < length/5){
            tempLength++;
        }
        postInvalidate();
    }
}
