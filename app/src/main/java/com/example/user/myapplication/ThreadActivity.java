package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

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

            this.main.mBitmap= Bitmap.createBitmap(main.iv.getWidth(),main.iv.getHeight(),Bitmap.Config.ARGB_8888);
            this.main.iv.setImageBitmap(main.mBitmap);
            this.main.mCanvas=new Canvas(main.mBitmap);
            this.main.initializeCanvas();
            this.main.iv.invalidate();

            if(main.presenter.tempIsi!=null) {
                main.presenter.isiKotak = main.presenter.tempIsi;
                this.main.updatePosisiKotak();
            }





    }
}
