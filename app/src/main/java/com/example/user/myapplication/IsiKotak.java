package com.example.user.myapplication;

import android.graphics.Rect;

/**
 * Created by WIN 10 on 3/4/2018.
 */

public class IsiKotak extends KotakExtension{
    String text;
    int size,ukuranText;

    public IsiKotak(Rect rect,String text,int size,int ukuran) {
        super(rect);
        this.text=text;
        this.size=size;
        this.ukuranText=ukuran;
    }

    public String getText(){
        return this.text;
    }



}
