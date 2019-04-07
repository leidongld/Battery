package com.example.leidong.battery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.leidong.battery.R;

/**
 * Created by Lei Dong on 2019/4/7.
 */
public class Battery extends View {
    private Paint mPaint = new Paint();

    // 自定义View宽度
    private float width;
    // 自定义View高度
    private float height;
    // 自定义View中心x坐标
    private float centerX;
    // 自定义View中心y坐标
    private float centerY;

    // 宽度的1/100
    private float perUnitWidth;
    // 高度的1/100
    private float perUnitHeight;

    // 电量百分比
    private int power = 0;

    // 低电量颜色
    private int lowPowerColor;
    // 中电量颜色
    private int middlePowerColor;
    // 高电量颜色
    private int enoughPowerColor;
    // 边框宽度
    private int strokeWidth;
    // 边框颜色
    private int strokeColor;
    // 字体大小
    private int textSize;
    // 字体颜色
    private int textColor;

    public void setPower(int power) {
        this.power = power;
        invalidate();
    }

    public Battery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 获取参数
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Battery);
        lowPowerColor = typedArray.getColor(R.styleable.Battery_lowPowerColor, Color.parseColor("#000000"));
        middlePowerColor = typedArray.getColor(R.styleable.Battery_middlePowerColor, Color.parseColor("#000000"));
        enoughPowerColor = typedArray.getColor(R.styleable.Battery_enoughPowerColor, Color.parseColor("#000000"));
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.Battery_strokeWidth, 20);
        strokeColor = typedArray.getColor(R.styleable.Battery_strokeColor, Color.parseColor("#000000"));
        textSize = typedArray.getDimensionPixelSize(R.styleable.Battery_textSize, 50);
        textColor = typedArray.getColor(R.styleable.Battery_textColor, Color.parseColor("#000000"));

        // 回收对象
        typedArray.recycle();
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();

        measuredWidth = resolveSize(measuredWidth, widthMeasureSpec);
        measureHeight = resolveSize(measureHeight, heightMeasureSpec);

        this.width = measuredWidth;
        this.height = measureHeight;
        this.centerX = width / 2;
        this.centerY = height / 2;
        this.perUnitHeight = height / 100;
        this.perUnitWidth = width / 100;

        setMeasuredDimension(measuredWidth, measureHeight);
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);

        // 绘制电池边框
        Path path = new Path();
        path.moveTo(centerX - 50 * perUnitWidth, centerY - 45 * perUnitHeight);
        path.lineTo(centerX - 15 * perUnitWidth, centerY - 45 * perUnitHeight);
        path.lineTo(centerX - 15 * perUnitWidth, centerY - 50 * perUnitHeight);
        path.lineTo(centerX + 15 * perUnitWidth, centerY - 50 * perUnitHeight);
        path.lineTo(centerX + 15 * perUnitWidth, centerY - 45 * perUnitHeight);
        path.lineTo(centerX + 50 * perUnitWidth, centerY - 45 * perUnitHeight);
        path.lineTo(centerX + 50 * perUnitWidth, centerY + 50 * perUnitHeight);
        path.lineTo(centerX - 50 * perUnitWidth, centerY + 50 * perUnitHeight);
        path.close();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(strokeColor);

        canvas.drawPath(path, mPaint);

        // 绘制中间填充的颜色
        if (power < 10) {
            mPaint.setColor(lowPowerColor);
        } else if (power < 30) {
            mPaint.setColor(middlePowerColor);
        } else {
            mPaint.setColor(enoughPowerColor);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(centerX + 2 * strokeWidth - 50 * perUnitWidth,
                (centerY + 50 * perUnitHeight - 2 * strokeWidth - power * (height - 5 * perUnitHeight - 4 * strokeWidth) / 100),
                centerX - 2 * strokeWidth + 50 * perUnitWidth,
                centerY + 50 * perUnitHeight - 2 * strokeWidth,
                mPaint);

        // 绘制文字
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setStyle(Paint.Style.FILL);
        String text = power + " %";
        float textWidth = mPaint.measureText(text);
        canvas.drawText(text, centerX - (textWidth / 2), centerY + 10 * perUnitHeight, mPaint);
    }
}
