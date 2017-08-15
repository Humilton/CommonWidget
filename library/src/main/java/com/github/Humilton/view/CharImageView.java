package com.github.Humilton.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

/*
 * @Description: TODO:<请描述此文件是做什么的>
 * @author:      Hamilton
 * @data:        2016-09-08 10:30
 * @version:     V1.0
 */

public class CharImageView extends AppCompatImageView {
    private String ch;
    private Paint borderPaint, txPaint;
    private int w=0, h=0;
    private int fillColor = Color.parseColor("#F8F8F9");

    public CharImageView(Context context) {
        super(context);
        init(context);
    }

    public CharImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CharImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        int dp1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        borderPaint = new Paint();
        borderPaint.setColor( Color.parseColor("#E1E1E1") );
        borderPaint.setStrokeWidth( dp1 );
        borderPaint.setStyle(Paint.Style.STROKE);

        txPaint = new Paint();
        txPaint.setColor( Color.parseColor("#4C1D1D26") );
        txPaint.setTextSize(fontSize);
        txPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setText(String ch) {
        this.ch = ch;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(fillColor);

        if(w == 0 || h == 0) {
            w = this.getMeasuredWidth();
            h = this.getMeasuredHeight();
        }
        if (w == 0 || h == 0) return;

        int xPos = (w / 2);
        int yPos = (int) ((h / 2) - ((txPaint.descent() + txPaint.ascent()) / 2)) ;

        canvas.drawRect(0, 0, w, h, borderPaint);
        canvas.drawText(ch, xPos, yPos, txPaint);
    }
}
