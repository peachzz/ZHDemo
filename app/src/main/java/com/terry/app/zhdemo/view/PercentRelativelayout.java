package com.terry.app.zhdemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.terry.app.zhdemo.R;


/**
 * Created by Taozi on 2016/7/17.
 */
public class PercentRelativelayout extends RelativeLayout {

    public PercentRelativelayout(Context context) {
        super(context);
    }

    public PercentRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);

        //测量子控件的高度进行改变
        int childCont = this.getChildCount();
        for (int i = 0; i < childCont; i++) {
            View child = this.getChildAt(i);//每一个子控件
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            //解析自定义进行替换
            float widthPercent = 0;
            float heightPercent = 0;
            if (layoutParams instanceof PercentRelativelayout.LayoutParams) {
                widthPercent = ((LayoutParams) layoutParams).getWidthPercent();
                heightPercent = ((LayoutParams) layoutParams).getHeightPercent();
            }
            if (widthPercent != 0) {
                layoutParams.width = (int) (width * widthPercent);
            }
            if (heightPercent != 0) {
                layoutParams.height = (int) (height * heightPercent);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        private float widthPercent;
        private float heightPercent;

        public float getWidthPercent() {
            return widthPercent;
        }

        public void setWidthPercent(float widthPercent) {
            this.widthPercent = widthPercent;
        }

        public float getHeightPercent() {
            return heightPercent;
        }

        public void setHeightPercent(float heightPercent) {
            this.heightPercent = heightPercent;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.PercentRelativelayout);
            widthPercent = typedArray.getFloat(R.styleable.PercentRelativelayout_layout_widthPercent, widthPercent);
            heightPercent = typedArray.getFloat(R.styleable.PercentRelativelayout_layout_heightPercent, heightPercent);
            typedArray.recycle();
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
        }
    }
}
