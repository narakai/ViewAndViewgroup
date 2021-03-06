package com.example.leon.viewandviewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: Leon Lai
 * Date: 2016-06-19
 */
public class CustomViewgroup extends ViewGroup {
    private int mHorizontalSpacing;
    private int mVerticalSpacing;

    public CustomViewgroup(Context context) {
        this(context, null);
    }

    public CustomViewgroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewgroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CascadeLayout);
        int n = a.getIndexCount();
        try {
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.CascadeLayout_horizontal_spacing:
                        //第一个参数是属性的索引，第二个是默认值(10dp)
                        mHorizontalSpacing = a.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(
                                R.dimen.cascade_horizontal_spacing));
                        break;
                    case R.styleable.CascadeLayout_vertical_spacing:
                        mVerticalSpacing = a.getDimensionPixelSize(attr, getResources().getDimensionPixelSize(
                                R.dimen.cascade_vertical_spacing));
                        break;
                }
            }
        }
        finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft();
        int height = getPaddingTop();
        int verticalSpacing;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            verticalSpacing = mVerticalSpacing;

            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            width = getPaddingLeft() + mHorizontalSpacing * i;

            lp.x = width;
            lp.y = height;

//            if (lp.verticalSpacing >= 0) {
//                verticalSpacing = lp.verticalSpacing;
//            }

            width += child.getMeasuredWidth();
            height += verticalSpacing;
        }

        width += getPaddingRight();
        height += getChildAt(getChildCount() - 1).getMeasuredHeight()
                + getPaddingBottom();

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y
                    + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        int x;
        int y;
//        public int verticalSpacing;

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);

//            TypedArray a = context.obtainStyledAttributes(attrs,
//                    R.styleable.CascadeLayout_LayoutParams);
//            try {
//                verticalSpacing = a.getDimensionPixelSize(R.styleable.CascadeLayout_LayoutParams_layout_vertical_spacing, -1);
//            } finally {
//                a.recycle();
//            }
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

    }


}
