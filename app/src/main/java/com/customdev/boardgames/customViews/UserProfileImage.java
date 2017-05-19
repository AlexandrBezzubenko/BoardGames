package com.customdev.boardgames.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

public class UserProfileImage extends android.support.v7.widget.AppCompatImageView {

    private Path mPath = new Path();
    private RectF mRect = new RectF();

    public UserProfileImage(Context context) {
        super(context);
    }

    public UserProfileImage(Context context, AttributeSet attrs) {
        super(context, attrs);
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
