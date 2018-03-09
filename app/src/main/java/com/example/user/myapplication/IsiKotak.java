package com.example.user.myapplication;

import android.graphics.Rect;

/**
 * Created by WIN 10 on 3/4/2018.
 */

public class IsiKotak {
    String text;
    int size,ukuranText;
    Rect rect;
    public IsiKotak(Rect rect,String text,int size,int ukuran) {
        this.rect=rect;
        this.text=text;
        this.size=size;
        this.ukuranText=ukuran;
    }

    public String getText(){
        return this.text;
    }

    public Rect getRect(){
        return this.rect;
    }

    public int posisiTengahX(){
        return (this.rect.right+this.rect.left)/2;
    }

    public int posisiTengahY(){
        return (this.rect.top+this.rect.bottom)/2;
    }

}
