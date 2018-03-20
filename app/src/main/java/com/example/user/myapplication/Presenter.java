package com.example.user.myapplication;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by user on 3/16/2018.
 */

public class Presenter {
    private MainActivity ui;
    private Hitungan hitungan;
    protected ArrayList<IsiKotak> isiKotak;
    
    private ArrayList<String> hitung;
    protected IsiKotak[] yangAkanDihitung;

    protected KotakExtension[] rectList;
    //protected ArrayList<IsiKotak> daftarKotakYangDibuat;

    public Presenter(MainActivity ui) {
        //this.daftarKotakYangDibuat=new ArrayList<IsiKotak>();
        this.isiKotak=new ArrayList<IsiKotak>();
        this.rectList=new KotakExtension[ui.getResources().getInteger(R.integer.banyakKotak)];
        this.yangAkanDihitung=new IsiKotak[ui.getResources().getInteger(R.integer.banyakKotak)];
        this.ui = ui;
       
        
        this.hitungan=new Hitungan();
        this.hitung=new  ArrayList<String>();
    }

    public ArrayList<IsiKotak> getIsiKotak(){
        return this.isiKotak;
    }

    public IsiKotak[] getYangAkanDihitung(){
        return this.yangAkanDihitung;
    }

    public KotakExtension[] getRectList(){
        return this.rectList;
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
        for (int i=0;i<this.rectList.length;i++){
            if(this.rectList[i].cekIsi()){
                this.hitung.add(this.rectList[i].getIsi().getText());
            }
        }
        return hitungan.isValid(this.hitung);
    }

    public boolean isDouble(String string){
        return this.hitungan.isDouble(string);
    }
    public int insideBlueRect(float x,float y){
        for(int i = 0;i<this.isiKotak.size();i++) {

            Log.d("Cekcek","Posisi kotakX: "+this.isiKotak.get(i).getRect().left+" "+this.isiKotak.get(i).getRect().right);
            Log.d("Cekcek","Posisi kotakY: "+this.isiKotak.get(i).getRect().top+" "+this.isiKotak.get(i).getRect().bottom);

            if (this.isiKotak.get(i).getRect().left<=x && this.isiKotak.get(i).getRect().right>=x) {
                if(this.isiKotak.get(i).getRect().top<=y&&this.isiKotak.get(i).getRect().bottom>=y){
                    return i;
                }
            }

        }
        return -1;
    }

    public int insideRect(float x,float y){
        for(int i = 0;i<this.rectList.length;i++) {
            if (this.rectList[i].getRect().left<=x && this.rectList[i].getRect().right>=x) {
                if(this.rectList[i].getRect().top<=y&&this.rectList[i].getRect().bottom>=y){
                    return i;
                }
            }

        }
        return -1;
    }

    public int insideRect(IsiKotak isi){
        int posisiKotak=-1;
        int bedaTengahX=Integer.MAX_VALUE,bedaTengahY=Integer.MAX_VALUE;

        for(int i = 0;i<rectList.length;i++) {

            if ((rectList[i].getRect().left<=isi.getRect().left && rectList[i].getRect().right>=isi.getRect().left)||(rectList[i].getRect().left<=isi.getRect().right && rectList[i].getRect().right>=isi.getRect().right)) {
                if((rectList[i].getRect().top<=isi.getRect().top && rectList[i].getRect().bottom>=isi.getRect().top) ||(rectList[i].getRect().top<=isi.getRect().bottom && rectList[i].getRect().bottom>=isi.getRect().bottom) ){
                    int diffX = Math.abs(rectList[i].posisiTengahX()-isi.posisiTengahX());
                    int diffY = Math.abs(rectList[i].posisiTengahY()-isi.posisiTengahY());
                    if(diffX<bedaTengahX && diffY<bedaTengahY){
                        posisiKotak=i;
                        bedaTengahX=diffX;
                        bedaTengahY=diffY;
                    }
                    else if(diffX>bedaTengahX && diffY>bedaTengahY){

                    }
                    else {
                        double jarakYangDicek = Math.sqrt((diffX * diffX) + (diffY * diffY));
                        double jarakTersimpan = Math.sqrt((bedaTengahX *bedaTengahX) + (bedaTengahY * bedaTengahY));
                        if(jarakTersimpan>jarakYangDicek){
                            posisiKotak=i;
                            bedaTengahX=diffX;
                            bedaTengahY=diffY;
                        }
                    }
                }
            }

        }

        return posisiKotak;}
    
    public void cobaGeser(int pos,int posisiKosong){
        boolean kedepan=true;
        int i = pos;
        int j = posisiKosong;

        int posisiUntukPindah=-1;
        if(pos>posisiKosong){
            kedepan=false;

            i = posisiKosong;
            j = pos;
        }

        if(kedepan){
            for(;j>=i;j--){
                if(yangAkanDihitung[j]==null){

                    posisiUntukPindah=j;
                }
                else{
                    if(posisiUntukPindah!=-1){
                        int temp =this.ui.indeksAktif;
                        this.ui.indeksAktif=indexKotak(yangAkanDihitung[j]);
                        this.yangAkanDihitung[posisiUntukPindah]=this.yangAkanDihitung[j];
                        moveKeTengah(posisiUntukPindah);
                        yangAkanDihitung[j]=null;
                        posisiUntukPindah=j;
                        this.ui.indeksAktif=temp;
                    }
                }
            }
        }
        else {
            for (; i <= j; i++) {
                if (this.yangAkanDihitung[i] == null) {
                    posisiUntukPindah = i;
                } else {
                    if (posisiUntukPindah != -1) {
                        int temp =  this.ui.indeksAktif;
                        this.ui.indeksAktif = indexKotak(yangAkanDihitung[i]);
                        this.yangAkanDihitung[posisiUntukPindah] = this.yangAkanDihitung[i];
                        if (i != posisiUntukPindah) {
                            this.yangAkanDihitung[i] = null;
                        }
                        moveKeTengah(posisiUntukPindah);
                        posisiUntukPindah = i;
                        this.ui.indeksAktif = temp;
                    }
                }
            }
        }
        this.yangAkanDihitung[pos]=this.isiKotak.get(this.ui.indeksAktif);
        moveKeTengah(pos);
    }

    public void updateUkuranBackground(int size,int banyakKotak){
        int x = 25;
        int y = 25;

        for(int i =0;i<rectList.length;i++){

            rectList[i].updateUkuranRect(x,y,x+size,y+size);
            if((i+1)%banyakKotak!=0){
                x+=25+size;
            }
            else{
                x=25;
                y+=25+size;
            }
        }
    }

    public void moveKeTengah(int posisi){
        if(this.ui.indeksAktif!=-1){

            this.rectList[posisi].isi(this.isiKotak.get(this.ui.indeksAktif),this.ui.indeksAktif);
            int temp1=this.isiKotak.get(this.ui.indeksAktif).getSize();
            int diff = (this.rectList[posisi].getRect().right-rectList[posisi].getRect().left-temp1)/2;

            int temp2=this.rectList[posisi].getRect().left+diff;
            int temp3=this.rectList[posisi].getRect().top+diff;

            this.geserKotak(this.ui.indeksAktif,temp2,temp3);
            this.ui.indeksAktif=-1;
            this.ui.resetCanvas();

        }
    }

    public void updateKotakDitengah(int posisi){
        int index=-1;

        for(int i = 0;i<isiKotak.size();i++){
            if(isiKotak.get(i)==this.rectList[posisi].getIsi()){
                index=i;

                break;

            }
        }
        int temp1=this.isiKotak.get(index).getSize();
        int diff = (this.rectList[posisi].getRect().right-rectList[posisi].getRect().left-temp1)/2;

        int temp2=this.rectList[posisi].getRect().left+diff;
        int temp3=this.rectList[posisi].getRect().top+diff;

        this.geserKotak(index,temp2,temp3);
    }

    public int indexKotak(IsiKotak isi){
        int idx=-1;
        for(int i = 0;i<this.isiKotak.size();i++){
            if(isi==this.isiKotak.get(i)){
                idx=i;
            }
        }
        return idx;
    }

    public int indexKotakKosong(){
        int indexKosong=-1;
        for(int i = 0;i<this.yangAkanDihitung.length;i++){
            if(this.yangAkanDihitung[i]==null){
                return i;
            }
        }
        return indexKosong;
    }
}
