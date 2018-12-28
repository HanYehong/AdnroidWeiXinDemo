package com.example.administrator.weixin.myView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.Random;

/**
 * Created by SuperD on 2017/2/15.
 * 小球弹起的Loading加载动画
 */

public class CircleLoadingView extends View implements View.OnClickListener {
    //起始点
    private int mStartPointX;
    private int mStartPointY;
    //结束点
    private int mEndPointX;
    private int mEndPointY;
    //小球的运动点
    private int mMovePointX;
    private int mMovePointY;

    //小球的半径
    private int mRadius = 100;
    //运动轨迹的长度
    private int mHeight;

    //阴影区域的线
    private Paint mPaint;
    private Paint mHelperPaint;
    private Path mHelpPath;

    private int direction = 1;

    private ValueAnimator mStartAnim;

    private int[] colors = {Color.BLACK, Color.BLUE, Color.DKGRAY, Color.GREEN, Color.RED, Color.YELLOW};


    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHelpPath = new Path();
        //绘制辅助线和辅助点的画笔
        mHelperPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelperPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mHelperPaint.setStrokeWidth(5);
        mHelperPaint.setColor(Color.GREEN);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.parseColor("#3F3B2D"));
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //取起始点在屏幕中间
        mStartPointX = w / 2;
        mEndPointX = w / 2;
        mEndPointY = h / 2;
        mStartPointY = mEndPointY * 1 / 2;
        //设置小球的初始点
        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;
        //获得运动轨迹的高度
        mHeight = mEndPointY - mStartPointY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // //绘制小球的运动轨迹
        //在下落到一定时刻存在椭圆效果
        int mDistance = mEndPointY - mMovePointY;
        if (mDistance >= mRadius) {   //距离大于半径 还没有落地
            canvas.drawCircle(mMovePointX, mMovePointY, mRadius, mHelperPaint);
        } else {
            //距离底端距离的碰撞比例
            float ratio = mDistance / mRadius;
            //将左右位置稍微拉伸一点,形成扁状效果
            RectF oval = new RectF(
                    mMovePointX - mRadius - 7,
                    mMovePointY - mRadius - ratio * mRadius + 5,
                    mMovePointX + mRadius + 7,
                    mEndPointY + 12);
            canvas.drawOval(oval, mHelperPaint);
        }

        //绘制底部阴影(注意阴影也是椭圆)
        float downRatio = (mMovePointY - mStartPointY) / (float) mHeight;
        //在行驶路径后半段 出现阴影
        if (downRatio > 0.3) {
            RectF rectF = new RectF(
                    mEndPointX - mRadius * downRatio,
                    mEndPointY + 10,
                    mEndPointX + mRadius * downRatio,
                    mEndPointY + 15
            );
            canvas.drawOval(rectF, mPaint);
        }
    }

    @Override
    public void onClick(View view) {
        //初始化动画
        mStartAnim = ValueAnimator.ofInt(mStartPointY, mEndPointY);
        mStartAnim.setInterpolator(new AccelerateInterpolator(1.2f));
        mStartAnim.setDuration(2000);
        mStartAnim.setRepeatCount(ValueAnimator.INFINITE);
        mStartAnim.setRepeatMode(ValueAnimator.REVERSE);
        mStartAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mMovePointY = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mStartAnim.start();
    }


    /**
     * 设置圆球的颜色
     */
    public void setCircleColor() {
        if (null != mHelperPaint && null != colors) {
            mHelperPaint.setColor(colors[new Random().nextInt(6)]);
        }
    }
}
