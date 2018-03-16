package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener{
    protected Button button1,button2,buttonReset,buttonCalculate;
    protected Spinner spinner;
    protected ImageView iv;
    protected EditText et;
    protected TextView tv_hasil;
    protected GestureDetector mGestureDetector;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Paint paint1,paint2,paint3;
    protected KotakExtension[] rectList;
    protected ArrayList<IsiKotak> daftarKotakYangDibuat;
    protected int x,y,indeksAktif,yMin,sizeSlot;
    protected IsiKotak[] yangAkanDihitung;
    protected double startX,startY,pos1,pos2;
    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.daftarKotakYangDibuat=new ArrayList<IsiKotak>();
        this.button1=this.findViewById(R.id.btn_add1);
        this.button2=this.findViewById(R.id.btn_add2);
        this.buttonCalculate=this.findViewById(R.id.btn_calculate);
        this.buttonReset=this.findViewById(R.id.btn_reset);
        this.spinner=this.findViewById(R.id.spinner);
        this.et=this.findViewById(R.id.et_number);
        this.iv=this.findViewById(R.id.iv);
        this.tv_hasil=this.findViewById(R.id.tv_hasil);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operator, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.rectList=new KotakExtension[getResources().getInteger(R.integer.banyakKotak)];
        this.yangAkanDihitung=new IsiKotak[getResources().getInteger(R.integer.banyakKotak)];
        this.presenter=new Presenter(this);
        this.x=25;
        this.indeksAktif=-1;
        this.mGestureDetector=new GestureDetector(this,new MyCustomGestureListener());
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);
        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);
        this.buttonReset.setOnClickListener(this);
        this.buttonCalculate.setOnClickListener(this);
        this.iv.setOnTouchListener(this);

        this.iv.post(new ThreadActivity(this));
    }

    public void initializeCanvas(){
        this.paint1 = new Paint();
        this.paint1.setColor(Color.BLACK);
        this.paint1.setStrokeWidth(5);
        this.paint1.setStyle(Paint.Style.STROKE);
        this.paint2=new Paint();
        this.paint2.setColor(Color.BLACK);
        this.paint2.setStyle(Paint.Style.FILL);
        this.paint2.setTextSize(40);
        this.paint2.setTextAlign(Paint.Align.CENTER);
        this.paint3=new Paint();
        this.paint3.setStyle(Paint.Style.FILL);
        this.paint3.setColor(Color.BLUE);

        int x = 25;
        int y = 25;

        int banyakKotak = 1;

        double sizeX = iv.getWidth()/(getResources().getInteger(R.integer.ukuranKotak)+1);
        double sizeY = iv.getHeight()/(getResources().getInteger(R.integer.ukuranKotak)+1);

        int size = (int)(Math.min(sizeX,sizeY));
        int sizeMax = (int)(Math.max(sizeX,sizeY));
        if(sizeX>sizeY){
            banyakKotak=(iv.getWidth()/(sizeMax+25));
        }
        else{
            banyakKotak=(iv.getHeight()/(sizeMax+25));
        }
        if(banyakKotak%2!=0){
            banyakKotak--;
        }
        this.sizeSlot=size;
        this.y=25+banyakKotak*size;
        this.yMin=this.y;

        for(int i  = 0;i<getResources().getInteger(R.integer.banyakKotak);i++){
            Rect rectData = new Rect(x,y,x+size,y+size);
            rectList[i]=new KotakExtension(rectData);
            if((i+1)%banyakKotak!=0){
                x+=25+size;
            }
            else{
                x=25;
                y+=25+size;
            }
        }
        this.drawSlot();
    }


    public void resetCanvas(){
        this.mCanvas.drawColor(Color.WHITE);
        this.drawSlot();
        this.drawBlueRect();
        this.iv.invalidate();
    }

    public void drawSlot(){
        for (int i  = 0;i<rectList.length;i++){
            this.mCanvas.drawRect(rectList[i].getRect(),this.paint1);
        }
    }

    public void drawBlueRect(){

        if(this.daftarKotakYangDibuat.size()>0){
            for (int i=0;i<this.daftarKotakYangDibuat.size();i++){
                    int temp=(int)(this.paint2.descent() + this.paint2.ascent() )/ 2;
                    this.mCanvas.drawRect(this.daftarKotakYangDibuat.get(i).getRect(),this.paint3);
                    this.mCanvas.drawText(this.daftarKotakYangDibuat.get(i).getText(),this.daftarKotakYangDibuat.get(i).posisiTengahX(),this.daftarKotakYangDibuat.get(i).posisiTengahY()-temp,this.paint2);
            }
        }


    }


    @Override
    public void onClick(View view) {
        if(view.getId()==this.button1.getId()||view.getId()==this.button2.getId()){

            String teks="";
            if(view.getId()==this.button1.getId()){
                teks=(String)this.spinner.getSelectedItem();
            }
            else if(view.getId()==this.button2.getId()){
                teks=this.et.getText()+"";
                this.et.setText("");
            }

            if(this.daftarKotakYangDibuat.size()>=15){
                Toast toast=Toast.makeText(this,"Jumlah kotak maksimal 15",Toast.LENGTH_LONG);
                toast.show();

            }
            else if(!teks.equals("")){
                int size = (5*this.sizeSlot/6);
                IsiKotak isi = new IsiKotak(new Rect(this.x,this.y ,this.x+size ,this.y+size ),teks,size);
                this.presenter.addKotak(isi);

                 if(this.x==25+4*(40+size)){
                     if(this.y==this.yMin+2*(size+30)){
                         this.y=this.yMin;
                     }
                    else {

                         this.y = this.y + 30 + size;
                     }
                     this.x = 25;
                }
                else{
                    this.x=this.x+40+size;
                }

            }
            else{
                Toast toast=Toast.makeText(this,"Angka belum diisi",Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else if(view.getId()==this.buttonCalculate.getId()){

            if(this.presenter.isValid()) {
                String hasil = this.presenter.hitung();
                if(this.presenter.isDouble(hasil)) {
                    if (Double.parseDouble(hasil) % 1 == 0) {
                        this.tv_hasil.setText((int)Double.parseDouble(hasil) + "");
                    } else {
                        String str = String.format("%.4f", Double.parseDouble(hasil));
                        this.tv_hasil.setText(str);
                    }
                }
                else{
                    Toast toast=Toast.makeText(this,"Dibagi 0",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else{

                Toast toast=Toast.makeText(this,"Terjadi kesalahan input",Toast.LENGTH_LONG);
                toast.show();
            }


        }
        else if(view.getId()==this.buttonReset.getId()){
            recreate();
            this.et.setText("");
            this.spinner.setSelection(0);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_UP:
                if(this.indeksAktif!=-1){

                    int pos = presenter.insideRect(this.daftarKotakYangDibuat.get(this.indeksAktif));
                    if(pos!=-1){


                        if(this.rectList[pos].cekIsi()){
                            int posisiKosong = this.presenter.indexKotakKosong();
                            if( posisiKosong!=-1){

                                this.presenter.cobaGeser(pos,posisiKosong);
                            }
                            else{
                                this.resetCanvas();
                                this.presenter.geserKotak(this.indeksAktif,(int)startX,(int)startY);
                            }

                        }
                        else{
                            yangAkanDihitung[pos]=this.daftarKotakYangDibuat.get(this.indeksAktif);
                            this.presenter.moveKeTengah(pos);
                        }
                    }
                }
                this.indeksAktif=-1;
                break;
        }
        this.drawBlueRect();
        return this.mGestureDetector.onTouchEvent(motionEvent);

    }



    private class MyCustomGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            int temp=presenter.insideBlueRect(motionEvent.getX(),motionEvent.getY());
            int temp2=presenter.insideRect(motionEvent.getX(),motionEvent.getY());
            if(temp!=-1) {
                indeksAktif=temp;

                if(temp2!=-1){
                    rectList[temp2].buang();
                }
                startX=daftarKotakYangDibuat.get(temp).getRect().left;
                startY=daftarKotakYangDibuat.get(temp).getRect().top;
                pos1=motionEvent.getX();
                pos2=motionEvent.getY();
                for(int i =0;i<yangAkanDihitung.length;i++){
                    if(yangAkanDihitung[i]==daftarKotakYangDibuat.get(indeksAktif)){
                        yangAkanDihitung[i]=null;
                    }
                }
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
            if(indeksAktif!=-1) {
                int selisihX = (int) (motionEvent1.getX() - pos1);
                int selisihY = (int) (motionEvent1.getY() - pos2);
                resetCanvas();

                int temp1=daftarKotakYangDibuat.get(indeksAktif).getRect().left;
                int temp2=daftarKotakYangDibuat.get(indeksAktif).getRect().top;

                presenter.geserKotak(indeksAktif,temp1+selisihX,temp2+selisihY);

                pos1 = motionEvent1.getX();
                pos2 = motionEvent1.getY();

            }
            return true;

        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            int temp1=presenter.insideBlueRect(motionEvent.getX(),motionEvent.getY());
            int temp2=presenter.insideRect(motionEvent.getX(),motionEvent.getY());
            if(temp1!=-1){

                if(temp2!=-1){
                    rectList[temp2].buang();
                }
                presenter.removeKotak(temp1);
                indeksAktif=-1;
                resetCanvas();
            }
        }
    }
}
