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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener, GestureDetector.OnGestureListener{
    protected Button button1,button2;
    protected Spinner spinner;
    protected ImageView iv;
    protected GestureDetector mGestureDetector;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Paint paint;
    protected Rect[] rectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button1=this.findViewById(R.id.btn_add1);
        this.button2=this.findViewById(R.id.btn_add2);
        this.spinner=this.findViewById(R.id.spinner);
        this.iv=this.findViewById(R.id.iv);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operator, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.rectList=new Rect[8];
        this.mGestureDetector=new GestureDetector(this,this);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this);
        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);

        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        this.paint.setStrokeWidth(5);
        this.paint.setStyle(Paint.Style.STROKE);
        this.iv.setOnTouchListener(this);
        this.iv.post(new ThreadActivity(this));
    }



    @Override
    public void onClick(View view) {
        Paint textPaint = new Paint();
        textPaint.setARGB(200, 254, 0, 0);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPaint.setTextSize(30);
        mCanvas.drawText("Hello", mCanvas.getWidth()/2, mCanvas.getHeight()/2  , textPaint);

    }

    public void drawKotak(){
        int x = 25;
        int y = 25;
        int size = 150;
        for(int i  = 0;i<8;i++){
            Rect rectData = new Rect(x,y,x+size,y+size);
            rectList[i]=rectData;
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
            this.mCanvas.drawRect(rectList[i],this.paint);
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
        if(insideRect(motionEvent.getX(),motionEvent.getY())){
            Log.d("test","true");
            System.out.println("TRUE");
            Paint textPaint = new Paint();
            textPaint.setARGB(200, 254, 0, 0);
            textPaint.setTextAlign(Paint.Align.CENTER);

            textPaint.setTextSize(30);
            mCanvas.drawText("Hello", motionEvent.getX(),motionEvent.getY()  , textPaint);
            iv.invalidate();
        }
        else{
            Log.d("test","false");
            System.out.println("FALSE");
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
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
        return true;
    }

    public boolean insideRect(float x,float y){
        for(int i = 0;i<rectList.length;i++) {
            if (rectList[i].left<=x && rectList[i].right>=x) {
                if(rectList[i].top<=y&&rectList[i].bottom>=y){
                    return true;
                }
            }

            }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        if(insideRect(motionEvent.getX(),motionEvent.getY())){
            Log.d("test","true");
            System.out.println("TRUE");
        }
        else{
            Log.d("test","false");
            System.out.println("FALSE");
        }

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }
}