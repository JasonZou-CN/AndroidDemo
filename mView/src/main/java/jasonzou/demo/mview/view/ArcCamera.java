package jasonzou.demo.mview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import jasonzou.demo.view.R;

/**
 * TODO: document your custom view class.
 */
public class ArcCamera extends View {
    private Paint mPaint;
    private int startAngle;
    private int sweepAngel;
    private int arcColor;


    public int getArcColor() {
        return arcColor;
    }

    public void setArcColor(int arcColor) {
        this.arcColor = arcColor;
        mPaint.setColor(arcColor);
    }


    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getSweepAngel() {
        return sweepAngel;
    }

    public void setSweepAngel(int sweepAngel) {
        this.sweepAngel = sweepAngel;

    }


    public ArcCamera(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ArcCamera(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ArcCamera(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        //attrs为null也无碍
        TypedArray attributes = context.obtainStyledAttributes(attrs,
                R.styleable.ArcCamera);
        startAngle = attributes.getInt(
                R.styleable.ArcCamera_startAngle,
                0);
        sweepAngel = attributes.getInt(
                R.styleable.ArcCamera_sweepAngle,
                180);
        arcColor = attributes.getColor(R.styleable.ArcCamera_arcColor, Color.RED);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setStyle(Paint.Style.FILL);  //画笔风格
        mPaint.setStrokeWidth(5);           //画笔粗细
        mPaint.setColor(arcColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(new RectF(0, 0, 100, 100), startAngle, sweepAngel, false, mPaint);  //绘制弧形区域
    }
}
