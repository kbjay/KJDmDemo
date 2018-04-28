package com.example.kb_jay.danmudemo;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;

import android.view.View;

import android.widget.RelativeLayout;

import java.util.concurrent.ArrayBlockingQueue;

import rx.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by kb_jay on 2018/4/26.
 */

public class KJDmView extends RelativeLayout {

    private int mSpanY=80;
    private boolean mHasSpanY=false;
    private ArrayBlockingQueue<View> mData = new ArrayBlockingQueue<View>(20);
    private int mCurrentInNumber;
    private int mWidth;
    private int mHeight;

    public KJDmView(Context context) {
        this(context, null);
    }

    public KJDmView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KJDmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mWidth = r - l;
    }
    public void addCustomView(final ArrayBlockingQueue<View> data) {
        mData = data;
        Observable.create(new Observable.OnSubscribe<View>() {
            @Override
            public void call(Subscriber<? super View> subscriber) {
                while (true) {
                    if (mWidth != 0) {
                        while (mData.size() > 0) {
                            try {
                                Thread.sleep(500);
                                subscriber.onNext(data.poll());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<View>() {
                    @Override
                    public void call(final View view) {
                        try {
                            mData.put(view);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        view.setX(0);
                        if(mHasSpanY){
                            view.setY(mSpanY);
                            mHasSpanY=false;
                        }else {
                            view.setY(0);
                            mHasSpanY=true;
                        }

                        addView(view);

                        view.animate().translationX(mWidth).setDuration(3000).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                removeView(view);
                            }
                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }
                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        }).start();
                    }
                });
    }

}
