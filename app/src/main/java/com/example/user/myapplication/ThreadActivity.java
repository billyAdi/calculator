package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by WIN 10 on 3/3/2018.
 */

public class ThreadActivity implements  Runnable{
    private MainActivity main;
    public ThreadActivity(MainActivity main){
        this.main=main;
    }
    @Override
    public void run() {
        main.mBitmap= Bitmap.createBitmap(main.iv.getWidth(),main.iv.getHeight(),Bitmap.Config.ARGB_8888);
        main.iv.setImageBitmap(main.mBitmap);
        main.mCanvas=new Canvas(main.mBitmap);
        main.drawKotak();
        main.iv.invalidate();
    }
}
