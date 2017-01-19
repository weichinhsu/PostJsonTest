package com.example.winnie.postjsontest;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;


public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    //ViewPager.OnPageChangeListener滑動事件
    private ViewPager mViewPager;
    private CardAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;

    public ShadowTransformer(ViewPager viewPager, CardAdapter adapter) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
        
    }

    public void enableScaling() {
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
        // shrink main card
//                currentCard.animate().scaleY(1);
//                currentCard.animate().scaleX(1);

            // grow main card
//                currentCard.animate().scaleY(1.1f);//scale 2D的縮放
//                currentCard.animate().scaleX(1.1f);
    }

    //頁面切換
    @Override
    public void transformPage(View page, float position) {

    }
    /*onPageScrolled(int arg0,float arg1,int arg2) ，当页面在滑动的时候会调用此方法，
    在滑动被停止之前，此方法回一直得到调用。
    其中三个参数的含义分别为：arg0 :当前页面，及你点击滑动的页面。arg1:当前页面偏移的百分比。arg2:当前页面偏移的像素位置。*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;
    }
    /*onPageSelected(int arg0) ，此方法是页面跳转完后得到调用，arg0是你当前选中的页面的position。*/
    @Override
    public void onPageSelected(int position) {

    }
    /*onPageScrollStateChanged(int arg0) ，此方法是在状态改变的时候调用，
    其中arg0这个参数有三种状态（0，1，2）。arg0 ==1表示正在滑动，arg0==2表示滑动完毕了，arg0==0表示什么都没做。
    当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）*/
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
