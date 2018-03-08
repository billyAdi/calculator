package com.example.user.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener, GestureDetector.OnGestureListener{
    protected Button button1,button2,buttonReset,buttonCalculate;
    protected Spinner spinner;
    protected ImageView iv;
    protected ImageView hasilPerhitungan;
    protected EditText et;
    protected TextView tv_hasil;
    protected GestureDetector mGestureDetector;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Paint paint1,paint2;
    protected KotakExtension[] rectList;
    private IsiKotak kotakYangDiDrag;
    private ArrayList<IsiKotak> daftarKotakYangDibuat;
    private int x,y,indeksAktif;
    private boolean nextAngka;
    private IsiKotak[] yangAkanDihitung;
    private ArrayList<String> hitung;
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

        this.rectList=new KotakExtension[8];
        this.yangAkanDihitung=new IsiKotak[8];

        this.mGestureDetector=new GestureDetector(this,this);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);
        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);
        this.buttonReset.setOnClickListener(this);
        this.buttonCalculate.setOnClickListener(this);
        this.iv.setOnTouchListener(this);
        this.nextAngka=true;
        this.paint1 = new Paint();
        this.paint1.setColor(Color.BLACK);
        this.paint1.setStrokeWidth(5);
        this.paint1.setStyle(Paint.Style.STROKE);
        this.paint2=new Paint();
        this.paint2.setColor(Color.BLACK);
        this.paint2.setStyle(Paint.Style.FILL);
        this.paint2.setTextSize(40);
        this.paint2.setTextAlign(Paint.Align.CENTER);
        this.x=25;
        this.y=400;
        this.indeksAktif=-1;
        this.iv.post(new ThreadActivity(this));


    }


    public void resetCanvas(){
        this.mCanvas.drawColor(Color.WHITE);
        this.paint1.setColor(Color.BLACK);
        this.paint1.setStrokeWidth(5);
        this.paint1.setStyle(Paint.Style.STROKE);
        this.drawKotak();
        this.paint1.setStyle(Paint.Style.FILL);
        this.paint1.setColor(Color.BLUE);
        if(this.daftarKotakYangDibuat.size()>0){
            for (int i=0;i<this.daftarKotakYangDibuat.size();i++){

                if(i!=this.indeksAktif){
                    this.mCanvas.drawRect(this.daftarKotakYangDibuat.get(i).getRect(),this.paint1);
                    this.mCanvas.drawText(this.daftarKotakYangDibuat.get(i).getText(),this.daftarKotakYangDibuat.get(i).posisiTengahX(),this.daftarKotakYangDibuat.get(i).posisiTengahY(),this.paint2);
                }
            }
        }
        this.iv.invalidate();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==this.button1.getId()||view.getId()==this.button2.getId()){
            for (int i=0;i<8;i++){
                Rect temp=this.rectList[i].getRect();
                System.out.println(temp.left+" "+temp.right+" "+temp.top+" "+temp.bottom);
            }

            String teks="";
            if(view.getId()==this.button1.getId()){
                teks=(String)this.spinner.getSelectedItem();
            }
            else if(view.getId()==this.button2.getId()){
                teks=this.et.getText()+"";
            }

            if(!teks.equals("")){

                this.paint1.setColor(Color.BLUE);
                this.paint1.setStyle(Paint.Style.FILL);
                int size = (5*iv.getWidth()/(6*6));
                IsiKotak isi = new IsiKotak(new Rect(this.x,this.y ,this.x+size ,this.y+size ),teks,size,40);
                daftarKotakYangDibuat.add(isi);
                this.mCanvas.drawRect(isi.getRect(), this.paint1);
                this.mCanvas.drawText(isi.getText(), isi.posisiTengahX(), isi.posisiTengahY()  ,this.paint2);
                if(this.daftarKotakYangDibuat.size()%5!=0){
                    this.x=this.x+25+size;
                }
                else{
                    this.x=25;
                    this.y=this.y+25+size;
                }
            }
            else{
                Toast toast=Toast.makeText(this,"Angka belum diisi",Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else if(view.getId()==this.buttonCalculate.getId()){
            double hasil=Hitungan.hasilHitung(hitung);
            if(hasil%1==0){
                this.tv_hasil.setText((int)hasil+"");
            }
            else{
                String str=String.format("%.4f",hasil);
                this.tv_hasil.setText(str);
            }

        }
        else if(view.getId()==this.buttonReset.getId()){
            recreate();
            this.et.setText("");
            this.spinner.setSelection(0);
        }


    }

    public IsiKotak isIsi(float x,float y){
        int i = 0;
        while(i!=daftarKotakYangDibuat.size()) {
            Rect rectTemp = daftarKotakYangDibuat.get(i).getRect();
            if (rectTemp.left<=x && rectTemp.right>=x) {
                if(rectTemp.top<=y&&rectTemp.bottom>=y){
                    return daftarKotakYangDibuat.get(i);
                }
            }
            i++;

        }

        return null;
    }

    public int insideRect(IsiKotak isi){
        int posisiKotak=-1;
        int bedaTengahX=Integer.MAX_VALUE,bedaTengahY=Integer.MAX_VALUE;

        for(int i = 0;i<rectList.length;i++) {
            Rect rectTemp = rectList[i].getRect();
            if ((rectTemp.left<=isi.getRect().left && rectTemp.right>=isi.getRect().left)||(rectTemp.left<=isi.getRect().right && rectTemp.right>=isi.getRect().right)) {
                if((rectTemp.top<=isi.getRect().top && rectTemp.bottom>=isi.getRect().top) ||(rectTemp.top<=isi.getRect().bottom && rectTemp.bottom>=isi.getRect().bottom) ){
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

        return posisiKotak;

    public int insideBlueRect(float x,float y){
        for(int i = 0;i<this.daftarKotakYangDibuat.size();i++) {
            Rect rectTemp = this.daftarKotakYangDibuat.get(i).getRect();
            if (rectTemp.left<=x && rectTemp.right>=x) {
                if(rectTemp.top<=y&&rectTemp.bottom>=y){
                    return i;
                }
            }

        }

        return -1;
    }


    public void moveKotakTengah(int posisi){
        if(this.indeksAktif!=-1){
            Rect tempatKotak= rectList[posisi].getRect();
            //this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().set(tempatKotak.left,tempatKotak.right,tempatKotak.top,tempatKotak.bottom);

            int temp3=this.daftarKotakYangDibuat.get(this.indeksAktif).size;
            int diff = (rectList[posisi].getRect().right-rectList[posisi].getRect().left-temp3)/2;

            int temp1=tempatKotak.left+diff;
            int temp2=tempatKotak.top+diff;
            this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().set(temp1,temp2,temp1+temp3,temp2+temp3);
            this.indeksAktif=-1;
            this.resetCanvas();



        }
    }

    public void drawKotak(){
        int x = 25;
        int y = 25;
        int size = iv.getWidth()/6;
        for(int i  = 0;i<8;i++){
            Rect rectData = new Rect(x,y,x+size,y+size);
            rectList[i]=new KotakExtension(rectData);
            if(i!=3){
                x+=25+size;
            }
            else{
                x=25;
                y+=25+size;
            }
        }
        drawKeView();
    }

    public void drawKeView(){
        for (int i  = 0;i<rectList.length;i++){
            this.mCanvas.drawRect(rectList[i].getRect(),this.paint1);
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
                    int pos = insideRect(this.daftarKotakYangDibuat.get(this.indeksAktif));
                    if(pos!=-1){

                        yangAkanDihitung[pos]=this.daftarKotakYangDibuat.get(this.indeksAktif);
                        //Log.d("aw",yangAkanDihitung[pos].getText());
                        moveKotakTengah(pos);
                        hitung = new ArrayList<String>();
                        for(int i = 0;i<8;i++){
                            if(yangAkanDihitung[i]!=null){
                                Log.d("aw"+i," "+yangAkanDihitung[i].getText());
                                hitung.add(yangAkanDihitung[i].getText());
                            }
                        }

                        if(hitung.size()!=0){
                            Log.d("aw"," "+Hitungan.hasilHitung(hitung));
                        }

                    }
                    System.out.println("ada di slot "+pos);
                }

                this.indeksAktif=-1;
                break;


        }
        return this.mGestureDetector.onTouchEvent(motionEvent);

    }
    double startX,startY;
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        int temp=this.insideBlueRect(motionEvent.getX(),motionEvent.getY());
        if(temp!=-1) {
            this.indeksAktif=temp;
            startX=motionEvent.getX();
            startY=motionEvent.getY();
            System.out.println("inside "+this.indeksAktif);
            for(int i =0;i<yangAkanDihitung.length;i++){
                if(yangAkanDihitung[i]==this.daftarKotakYangDibuat.get(this.indeksAktif)){
                    yangAkanDihitung[i]=null;
                }
            }
        }

        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        //System.out.println("scrollllllllll");


        if(this.indeksAktif!=-1) {
            int selisihX = (int) (motionEvent1.getX() - startX);
            int selisihY = (int) (motionEvent1.getY() - startY);
            this.resetCanvas();
            this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().left += selisihX;
            this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().top += selisihY;
            this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().bottom = this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().top + this.daftarKotakYangDibuat.get(this.indeksAktif).size;
            this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().right = this.daftarKotakYangDibuat.get(this.indeksAktif).getRect().left + this.daftarKotakYangDibuat.get(this.indeksAktif).size;


            this.mCanvas.drawRect(this.daftarKotakYangDibuat.get(this.indeksAktif).getRect(), this.paint1);
            this.mCanvas.drawText(this.daftarKotakYangDibuat.get(this.indeksAktif).getText(), this.daftarKotakYangDibuat.get(this.indeksAktif).posisiTengahX(), this.daftarKotakYangDibuat.get(this.indeksAktif).posisiTengahY(), this.paint2);
            this.iv.invalidate();

            startX = motionEvent1.getX();
            startY = motionEvent1.getY();

        }




        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        IsiKotak temp =isIsi(motionEvent.getX(),motionEvent.getY());
        if(temp!=null){
            Log.d("test","true");
            kotakYangDiDrag=temp;
        }
        else{
            Log.d("test","false");
        }
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }
}
