package com.customdev.gameland.behaviors;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.customdev.gameland.R;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("unused")
public class UserProfileAvatarBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

    private final static float MIN_AVATAR_PERCENTAGE_SIZE   = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING     = 80;

    private final static String TAG = "behavior";
    private Context mContext;

    private float mCustomFinalYPosition;
    private float mCustomStartXPosition;
    private float mCustomStartToolbarPosition;
    private float mCustomStartHeight;
    private float mCustomFinalHeight;

    private float mAvatarMaxSize;
    private float mFinalLeftAvatarPadding;
    private float mStartPosition;
    private int mStartXPosition;
    private float mStartToolbarPosition;
    private int mStartYPosition;
    private int mFinalYPosition;
    private int mStartHeight;
    private int mFinalXPosition;
    private float mChangeBehaviorPoint;

    private float travelDimension;
    private float mCustomToolbarHeight;
    int toolbarImageHeight;
    int imageFinalHeight;
    int imageFinalPosition;
    int imageTravelDimension;
    int imageScaleDiff;

    public UserProfileAvatarBehavior() {

    }

    public UserProfileAvatarBehavior(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UserProfileAvatarBehavior);

            mCustomFinalYPosition = a.getDimension(R.styleable.UserProfileAvatarBehavior_finalYPosition, 0);
            mCustomStartXPosition = a.getDimension(R.styleable.UserProfileAvatarBehavior_startXPosition, 0);
            mCustomStartToolbarPosition = a.getDimension(R.styleable.UserProfileAvatarBehavior_startToolbarPosition, 0);
            mCustomStartHeight = a.getDimension(R.styleable.UserProfileAvatarBehavior_startHeight, 0);
            mCustomFinalHeight = a.getDimension(R.styleable.UserProfileAvatarBehavior_finalHeight, 0);

            a.recycle();

        }

        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(R.dimen.spacing_normal);
    }

    private void init() {
        bindDimensions();

        toolbarImageHeight = (int) mContext.getResources().getDimension(R.dimen.toolbar_image_height);
        imageFinalHeight = (int) mContext.getResources().getDimension(R.dimen.image_final_width);
        mStartHeight = (int) mContext.getResources().getDimension(R.dimen.image_width);

        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            mCustomToolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }


        imageFinalPosition = (int) (mCustomToolbarHeight - imageFinalHeight) / 2;

        travelDimension = toolbarImageHeight - mCustomToolbarHeight;

    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
//        maybeInitProperties(child, dependency);

        if (mStartYPosition == 0) {
            mStartYPosition = toolbarImageHeight - child.getHeight() / 2;
            imageTravelDimension = mStartYPosition - imageFinalPosition;
        }

        if (imageScaleDiff == 0) {
            imageScaleDiff = child.getHeight() - imageFinalHeight;
        }

        float percentageTravel = 0;
        int positionY = 0;
        float d = getHeaderImageYTranslation(dependency);
        if (d != 0) {
            percentageTravel = d / travelDimension;

        }
        int position = (int) (mStartYPosition - imageTravelDimension * percentageTravel);
        child.setY(position);
        child.setX(mFinalLeftAvatarPadding);

//        child.getMe

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.width = (int) (imageFinalHeight + imageScaleDiff * (1 - percentageTravel));
        lp.height = (int) (imageFinalHeight + imageScaleDiff * (1 - percentageTravel));
//        lp.width = (int) (mStartHeight * (1 - percentageTravel));
//        lp.height = (int) (mStartHeight * (1 - percentageTravel));
        child.setLayoutParams(lp);


/*        final int maxScrollDistance = (int) (mStartToolbarPosition);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        if (expandedPercentageFactor < mChangeBehaviorPoint) {
            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;

            float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                    * heightFactor) + (child.getHeight()/2);
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (child.getHeight()/2);

            child.setX(mStartXPosition - distanceXToSubtract);
            child.setY(mStartYPosition - distanceYToSubtract);

            float heightToSubtract = ((mStartHeight - mCustomFinalHeight) * heightFactor);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight - heightToSubtract);
            lp.height = (int) (mStartHeight - heightToSubtract);
            child.setLayoutParams(lp);
        } else {
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (mStartHeight/2);

            child.setX(mStartXPosition - child.getWidth()/2);
//            child.setX(mStartXPosition);
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight);
            lp.height = (int) (mStartHeight);
            child.setLayoutParams(lp);
        }*/
        return true;
    }

//    private void maybeInitProperties(UserProfileImage child, View dependency) {
//        if (mStartYPosition == 0)
//            mStartYPosition = (int) (dependency.getY());
//
//        if (mFinalYPosition == 0)
//            mFinalYPosition = (dependency.getHeight() /2);
//
//        if (mStartHeight == 0)
//            mStartHeight = child.getHeight();
//
//        if (mStartXPosition == 0)
//            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));
//
//        if (mFinalXPosition == 0)
//            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + ((int) mCustomFinalHeight / 2);
//
//        if (mStartToolbarPosition == 0)
//            mStartToolbarPosition = dependency.getY();
//
//        if (mChangeBehaviorPoint == 0) {
//            mChangeBehaviorPoint = (child.getHeight() - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition));
//        }
//    }
//
//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
//
//        if (resourceId > 0) {
//            result = mContext.getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

    private float getHeaderImageYTranslation(View dependency) {
        return toolbarImageHeight - dependency.getY();
    }
}
