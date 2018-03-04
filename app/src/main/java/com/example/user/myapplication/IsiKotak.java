package com.example.user.myapplication;

import android.graphics.Rect;

/**
 * Created by WIN 10 on 3/4/2018.
 */

public class IsiKotak extends KotakExtension{
    String text;
    public IsiKotak(Rect rect,String text) {
        super(rect);
        this.text=text;
    }

    public String getText(){
        return this.text;
    }


}
