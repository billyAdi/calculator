package com.example.user.myapplication;

import android.graphics.Rect;

/**
 * Created by WIN 10 on 3/4/2018.
 */

public class IsiKotak {
    private String text;
    private int size;
    private Rect rect;
    public IsiKotak(Rect rect,String text,int size) {
        this.rect=rect;
        this.text=text;
        this.size=size;
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

    public int getSize() {
        return this.size;
    }

    public void geser(int x,int y){
        this.getRect().left=x;
        this.getRect().right=x+this.size;
        this.getRect().top=y;
        this.getRect().bottom=y+this.size;
    }

    public void updateUkuranRect(int left,int top,int right,int bottom){
        this.rect.set(left,top,right,bottom);
        this.size=(right-left);
    }
}
