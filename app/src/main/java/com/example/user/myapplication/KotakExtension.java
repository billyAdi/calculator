package com.example.user.myapplication;

import android.graphics.Rect;

/**
 * Created by WIN 10 on 3/4/2018.
 */

public class KotakExtension{
    boolean terisi = false;
    Rect rect;
    public KotakExtension(Rect rect){
        this.rect=rect;
    }

    public void isi(){
        this.terisi=true;
    }

    public void buang(){
        this.terisi=false;
    }

    public boolean cekIsi(){
        return this.terisi;
    }

    public Rect getRect(){
        return this.rect;
    }
}
