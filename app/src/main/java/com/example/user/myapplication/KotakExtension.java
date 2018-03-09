package com.example.user.myapplication;

import android.graphics.Rect;

/**
 * Created by WIN 10 on 3/4/2018.
 */

public class KotakExtension{
    private boolean terisi;
    private Rect rect;
    private IsiKotak isi;
    public KotakExtension(Rect rect){
        this.rect=rect;
        this.terisi=false;
    }

    public void isi(IsiKotak isi){
        this.terisi=true;
        this.isi=isi;
    }

    public void buang(){
        this.terisi=false;
        this.isi=null;
    }

    public boolean cekIsi(){
        return this.terisi;
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

    public IsiKotak getIsi(){
        return this.isi;
    }
}
