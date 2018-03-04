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
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener, GestureDetector.OnGestureListener{
    protected Button button1,button2;
    protected Spinner spinner;
    protected ImageView iv;
    protected GestureDetector mGestureDetector;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Paint paint1,paint2;
    protected KotakExtension[] rectList;
    private IsiKotak kotakYangDiDrag;
    private ArrayList<IsiKotak> daftarKotakYangDibuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.daftarKotakYangDibuat=new ArrayList<IsiKotak>();
        this.button1=this.findViewById(R.id.btn_add1);
        this.button2=this.findViewById(R.id.btn_add2);
        this.spinner=this.findViewById(R.id.spinner);
        this.iv=this.findViewById(R.id.iv);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operator, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.rectList=new KotakExtension[8];
        this.mGestureDetector=new GestureDetector(this,this);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);
        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);
        this.iv.setOnTouchListener(this);
        this.paint1 = new Paint();
        this.paint1.setColor(Color.BLACK);
        this.paint1.setStrokeWidth(5);
        this.paint1.setStyle(Paint.Style.STROKE);
        this.paint2=new Paint();
        this.paint2.setColor(Color.BLACK);
        this.paint2.setStyle(Paint.Style.FILL);
        this.paint2.setTextSize(40);
        this.paint2.setTextAlign(Paint.Align.CENTER);
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
                this.mCanvas.drawRect(this.daftarKotakYangDibuat.get(i).getRect(),this.paint1);
                this.mCanvas.drawText(this.daftarKotakYangDibuat.get(i).getText(),this.daftarKotakYangDibuat.get(i).posisiTengahX(),this.daftarKotakYangDibuat.get(i).posisiTengahY(),this.paint2);
                //blm beres
                //klo misal kotak ke 1 lagi di drag, ga usah di gambar
            }
        }
        this.iv.invalidate();
    }

    @Override
    public void onClick(View view) {

        this.paint1.setColor(Color.BLUE);
        this.paint1.setStyle(Paint.Style.FILL);
        IsiKotak isi = new IsiKotak(new Rect(25,400 ,25+125 ,400+125 ),"123",125,40);
        daftarKotakYangDibuat.add(isi);
        this.mCanvas.drawRect(isi.getRect(), this.paint1);
        this.mCanvas.drawText(isi.getText(), isi.posisiTengahX(), isi.posisiTengahY()  ,this.paint2);
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

    public KotakExtension insideRect(float x,float y){
        for(int i = 0;i<rectList.length;i++) {
            Rect rectTemp = rectList[i].getRect();
            if (rectTemp.left<=x && rectTemp.right>=x) {
                if(rectTemp.top<=y&&rectTemp.bottom>=y){
                    return rectList[i];
                }
            }

        }

        return null;
    }

    public void moveKotakKeTengah(KotakExtension ext){
        if(kotakYangDiDrag!=null){
            Rect tempatKotak= ext.getRect();
            kotakYangDiDrag.getRect().set(tempatKotak.left,tempatKotak.right,tempatKotak.top,tempatKotak.bottom);
        }
    }
    
    public void drawKotak(){
        int x = 25;
        int y = 25;
        int size = 150;
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
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
               // if(kotakYangDiDrag!=null){
              //  kotakYangDiDrag.gerakinKotak((int)motionEvent.getX(),(int)motionEvent.getY());
              //  iv.invalidate();
              //  }

                //System.out.println("on movessssssssss");
                break;
            case MotionEvent.ACTION_UP:
             //   KotakExtension ext = insideRect(motionEvent.getX(),motionEvent.getY());
             //   if(ext!=null){
             //       moveKotakKeTengah(ext);
             //       iv.invalidate();
             //   }
              //  kotakYangDiDrag = null;
                break;


        }
        return this.mGestureDetector.onTouchEvent(motionEvent);

    }
    double startX,startY;
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if (this.daftarKotakYangDibuat.get(0).getRect().left<=motionEvent.getX() &&(this.daftarKotakYangDibuat.get(0).getRect().right>=motionEvent.getX())) {
            if((this.daftarKotakYangDibuat.get(0).getRect().top<=motionEvent.getY()&&(this.daftarKotakYangDibuat.get(0).getRect().bottom>=motionEvent.getY()))){
                // this.resetCanvas();
                //this.mCanvas.drawRect(new Rect((int)motionEvent1.getX()-75,(int)motionEvent1.getY()-75,(int)motionEvent1.getX()+150-75,(int)motionEvent1.getY()+150-75),this.paint1);
                // this.iv.invalidate();
                startX=motionEvent.getX();
                startY=motionEvent.getY();
            }}
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
        System.out.println("scrollllllllll");


        if (this.daftarKotakYangDibuat.get(0).getRect().left<=motionEvent1.getX() &&(this.daftarKotakYangDibuat.get(0).getRect().right>=motionEvent1.getX())) {
            if((this.daftarKotakYangDibuat.get(0).getRect().top<=motionEvent1.getY()&&(this.daftarKotakYangDibuat.get(0).getRect().bottom>=motionEvent1.getY()))){
                int selisihX=(int)(motionEvent1.getX()-startX);
                int selisihY=(int)(motionEvent1.getY()-startY);
                this.resetCanvas();
                this.daftarKotakYangDibuat.get(0).getRect().left+=selisihX;
                this.daftarKotakYangDibuat.get(0).getRect().top+=selisihY;
                this.daftarKotakYangDibuat.get(0).getRect().bottom= this.daftarKotakYangDibuat.get(0).getRect().top+this.daftarKotakYangDibuat.get(0).size;
                this.daftarKotakYangDibuat.get(0).getRect().right= this.daftarKotakYangDibuat.get(0).getRect().left+this.daftarKotakYangDibuat.get(0).size;


                this.mCanvas.drawRect( this.daftarKotakYangDibuat.get(0).getRect(),this.paint1);
                this.iv.invalidate();

                startX=motionEvent1.getX();
                startY=motionEvent1.getY();

            }
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
