package com.customdev.gameland.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.customdev.gameland.R;

public class UserProfileImage extends android.support.v7.widget.AppCompatImageView {

    private Path mPath = new Path();
    private RectF mRect = new RectF();

    public UserProfileImage(Context context) {
        super(context);
    }

    public UserProfileImage(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserProfileImage);
        int width = typedArray.getDimensionPixelSize(R.styleable.UserProfileImage_android_width, 0);
    }

    public UserProfileImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = 18.0f;
        mRect.set(0, 0, this.getWidth(), this.getHeight());
        mPath.addRoundRect(mRect, radius, radius, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
