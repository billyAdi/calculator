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
    private IsiKotak[] yangAkanDihitung;

    public Presenter(MainActivity ui) {
        this.ui = ui;
        this.isiKotak=this.ui.daftarKotakYangDibuat;
        this.slot=this.ui.rectList;
        this.hitungan=new Hitungan();
        this.hitung=new  ArrayList<String>();
        this.yangAkanDihitung=this.ui.yangAkanDihitung;
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
    public int insideBlueRect(float x,float y){
        for(int i = 0;i<this.isiKotak.size();i++) {

            if (this.isiKotak.get(i).getRect().left<=x && this.isiKotak.get(i).getRect().right>=x) {
                if(this.isiKotak.get(i).getRect().top<=y&&this.isiKotak.get(i).getRect().bottom>=y){
                    return i;
                }
            }

        }
        return -1;
    }

    public int insideRect(float x,float y){
        for(int i = 0;i<this.slot.length;i++) {
            if (this.slot[i].getRect().left<=x && this.slot[i].getRect().right>=x) {
                if(this.slot[i].getRect().top<=y&&this.slot[i].getRect().bottom>=y){
                    return i;
                }
            }

        }
        return -1;
    }

    public int insideRect(IsiKotak isi){
        int posisiKotak=-1;
        int bedaTengahX=Integer.MAX_VALUE,bedaTengahY=Integer.MAX_VALUE;

        for(int i = 0;i<slot.length;i++) {

            if ((slot[i].getRect().left<=isi.getRect().left && slot[i].getRect().right>=isi.getRect().left)||(slot[i].getRect().left<=isi.getRect().right && slot[i].getRect().right>=isi.getRect().right)) {
                if((slot[i].getRect().top<=isi.getRect().top && slot[i].getRect().bottom>=isi.getRect().top) ||(slot[i].getRect().top<=isi.getRect().bottom && slot[i].getRect().bottom>=isi.getRect().bottom) ){
                    int diffX = Math.abs(slot[i].posisiTengahX()-isi.posisiTengahX());
                    int diffY = Math.abs(slot[i].posisiTengahY()-isi.posisiTengahY());
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
        this.yangAkanDihitung[pos]=ui.daftarKotakYangDibuat.get(this.ui.indeksAktif);
        moveKeTengah(pos);
    }

    public void moveKeTengah(int posisi){
        if(this.ui.indeksAktif!=-1){

            this.slot[posisi].isi(this.isiKotak.get(this.ui.indeksAktif));
            int temp1=this.isiKotak.get(this.ui.indeksAktif).getSize();
            int diff = (this.slot[posisi].getRect().right-slot[posisi].getRect().left-temp1)/2;

            int temp2=this.slot[posisi].getRect().left+diff;
            int temp3=this.slot[posisi].getRect().top+diff;

            this.geserKotak(this.ui.indeksAktif,temp2,temp3);
            this.ui.indeksAktif=-1;
            this.ui.resetCanvas();

        }
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
