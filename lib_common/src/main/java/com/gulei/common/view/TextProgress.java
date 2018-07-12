package com.gulei.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.gulei.lib_common.R;


/**
 * Created by gl152 on 2018/5/29.
 */

public class TextProgress extends ProgressBar {
    int DefaultColor = getResources().getColor(R.color.colorPrimary);
    int DefaultSize = dp2px(3);
    int Default_Text_Offset = dp2px(2);

    private int progress_unreach_height;//未读条时的背景大小
    private int progress_reach_color;//读条时的进度颜色
    private int progress_reach_height;
    private int progress_text_color;
    private int progress_text_size;
    private int progress_text_offset;

    private int maxProgress;
    private int progress;

    private int mRealWidth, mRealHeight, width, height;
    private Paint unReachPaint, reachPaint, mTextPaint;

    private boolean isCircle = false;
    private int radius;
    private RectF rectF;
    private boolean showTextProgress;

    public TextProgress(Context context) {
        this(context, null);
    }

    public TextProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextProgress);
        int progress_unreach_color = a.getColor(R.styleable.TextProgress_progress_unreach_color, DefaultColor);
        progress_unreach_height = a.getDimensionPixelSize(R.styleable.TextProgress_progress_unreach_height, DefaultSize);
        progress_reach_color = a.getColor(R.styleable.TextProgress_progress_reach_color, DefaultColor);
        progress_reach_height = a.getDimensionPixelSize(R.styleable.TextProgress_progress_reach_height, DefaultSize);
        progress_text_color = a.getColor(R.styleable.TextProgress_progress_text_color, DefaultColor);
        progress_text_size = a.getDimensionPixelSize(R.styleable.TextProgress_progress_text_size, sp2px(10));
        progress_text_offset = a.getDimensionPixelSize(R.styleable.TextProgress_progress_text_offset, Default_Text_Offset);
        maxProgress = a.getInteger(R.styleable.TextProgress_maxProgress, 100);
        isCircle = a.getBoolean(R.styleable.TextProgress_style_circle, false);
        radius = a.getDimensionPixelOffset(R.styleable.TextProgress_circle_radius, dp2px(15));
        showTextProgress = a.getBoolean(R.styleable.TextProgress_show_text_progress, true);
        a.recycle();

        unReachPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unReachPaint.setStyle(Paint.Style.STROKE);
        unReachPaint.setColor(progress_unreach_color);
        unReachPaint.setStrokeWidth(progress_unreach_height);
        reachPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        reachPaint.setStyle(Paint.Style.STROKE);
        reachPaint.setColor(progress_reach_color);
        reachPaint.setStrokeWidth(progress_reach_height);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(progress_text_size);
        mTextPaint.setColor(progress_text_color);
    }

    public Boolean getCircleStyle() {
        return isCircle;
    }

    public void setCircleStyle(boolean isCircle) {
        this.isCircle = isCircle;
        invalidate();
    }


    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mRealWidth = w - getPaddingRight() - getPaddingLeft();
        mRealHeight = h = getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (isCircle) {
            CircleStyle(canvas);
        } else {
            HorizontalStyle(canvas);
        }
    }

    private void CircleStyle(Canvas canvas) {
        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.drawCircle(0, 0, radius, unReachPaint);
        rectF = new RectF(-radius, -radius, radius, radius);
        float sweepAngle = progress * 1.0F / maxProgress * 360;
        canvas.drawArc(rectF, 270, sweepAngle, false, reachPaint);
        String text = "";
        if (showTextProgress) {
            text = progress + "%";
        }
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, -textWidth / 2, (-fontMetrics.descent - fontMetrics.ascent) / 2, mTextPaint);
        canvas.restore();
    }

    private void HorizontalStyle(Canvas canvas) {
        canvas.save();
        boolean noNeedUnreach = false;
        canvas.translate(getPaddingLeft(), height / 2);
        float progressX = (progress * 1.0f / maxProgress) * mRealWidth;
        float endx = progressX;
        String text = "";
        if (showTextProgress) {
            text = progress + "%";
        }
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textWidth = mTextPaint.measureText(text);

        if (progressX + textWidth + progress_text_offset * 2 >= mRealWidth) {
            endx = mRealWidth - textWidth - progress_text_offset * 2;
            noNeedUnreach = true;
        }

        float textStartx = endx + progress_text_offset;
        canvas.drawLine(0, 0, endx, 0, reachPaint);
        canvas.drawText(text, textStartx, (-fontMetrics.descent - fontMetrics.ascent) / 2, mTextPaint);
        if (!noNeedUnreach) {
            canvas.drawLine(endx + progress_text_offset * 2 + textWidth, 0, mRealWidth, 0, unReachPaint);
        }
        canvas.restore();
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
