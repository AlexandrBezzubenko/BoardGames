package com.customdev.boardgames.behaviors;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.customdev.boardgames.R;
import com.customdev.boardgames.customViews.UserProfileImage;

public class UserProfileAvatarBehavior extends CoordinatorLayout.Behavior<UserProfileImage> {

    private final static int X = 0;
    private final static int Y = 1;
    private final static int WIDTH = 2;
    private final static int HEIGHT = 3;

    private int mTargetId;

    private int[] mView;

    private int[] mTarget;

    public UserProfileAvatarBehavior() {

    }

    public UserProfileAvatarBehavior(Context context, AttributeSet attrs) {
        if (attrs != null) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CollapsingImageBehavior);
//            mTargetId = a.getResourceId(R.styleable.CollapsingImageBehavior_collapsedTarget, 0);
//            a.recycle();
        }

        if (mTargetId == 0) {
            throw new IllegalStateException("collapsedTarget attribute not specified on view for behavior");
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, UserProfileImage child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, UserProfileImage child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
