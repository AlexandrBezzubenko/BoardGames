package com.customdev.boardgames.customViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

public class UserProfileImage extends android.support.v7.widget.AppCompatImageView {

    private Path path;

    public UserProfileImage(Context context) {
        super(context);
        float sRadius = 18.0f;
        path = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, sRadius, sRadius, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
