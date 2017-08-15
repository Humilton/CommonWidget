package com.github.Humilton.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.github.Humilton.R;

/**
 * Created by Hamilton on 2017/8/10.
 */

public class PasswordEditText extends AppCompatEditText {
    private static final String C_DOT = "●";
    private Paint txPaint;
    private int w=0, h=0;
    private Rect r;

    public PasswordEditText(Context context) {
        super(context);
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setPadding(0,0,0,0);
        this.setBackgroundResource(R.mipmap.bg_pay_password);
        this.setCursorVisible(false);
        txPaint = getPaint();
        txPaint.setTextAlign(Paint.Align.CENTER);

        r = new Rect();
        txPaint.getTextBounds(C_DOT, 0, C_DOT.length(), r);
    }

    int len = 0;
    @Override
    public void onDraw(Canvas canvas) {
        if(w == 0 || h == 0) {
            w = canvas.getWidth() / 6;
            h = canvas.getHeight();
        }
        if (w == 0 || h == 0) return;

        len = getText().toString().length();
        for(int i=0;i<len;i++) {
            int xPos = (w *i + w / 2);
            int yPos =h / 2 + (Math.abs(r.height()))/2;
            canvas.drawText(C_DOT, xPos, yPos, txPaint);
        }
    }
}
