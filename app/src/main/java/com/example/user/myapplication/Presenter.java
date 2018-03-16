package com.example.user.myapplication;

import java.util.ArrayList;

/**
 * Created by user on 3/16/2018.
 */

public class Presenter {
    private MainActivity ui;
    private Hitungan hitungan;
    private ArrayList<IsiKotak> isiKotak;
    private KotakExtension[] slot;
    private ArrayList<String> hitung;

    public Presenter(MainActivity ui) {
        this.ui = ui;
        this.isiKotak=this.ui.daftarKotakYangDibuat;
        this.slot=this.ui.rectList;
        this.hitungan=new Hitungan();
        this.hitung=new  ArrayList<String>();
    }

    public void geserKotak(int position,int x,int y){
        this.isiKotak.get(position).geser(x,y);
        this.ui.resetCanvas();
    }

    public void addKotak(IsiKotak isiKotak){
        this.isiKotak.add(isiKotak);
        this.ui.resetCanvas();
    }

    public void removeKotak(int index){
        this.isiKotak.remove(index);
    }

    public String hitung(){
        return hitungan.hasilHitung(this.hitung);
    }

    public boolean isValid(){
        this.hitung=new  ArrayList<String>();
        for (int i=0;i<this.slot.length;i++){
            if(this.slot[i].cekIsi()){
                this.hitung.add(this.slot[i].getIsi().getText());
            }
        }
        return hitungan.isValid(this.hitung);
    }

    public boolean isDouble(String string){
        return this.hitungan.isDouble(string);
    }
}
