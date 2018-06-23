package com.example.rhill.controllertest3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import java.lang.Math;


/**
 * Created by rhill on 4/30/18.
 */

// This is the main View for the application.  It contains the area the robot can
// move around in, and the robot.
// TODO: upgrade the robot world to have a physics engine in it
public class RobotWorldView extends View {
    Bitmap fieldBitmap;
    Matrix robotMatrix;
    Matrix fieldMatrix;
    RobotWorld robotWorld;

    public RobotWorldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        fieldBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.field_diagram,options);
        fieldMatrix = new Matrix();
        requestFocus();
    }

    public void AttachRobotWorld(RobotWorld robotWorld) {
        this.robotWorld = robotWorld;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        robotWorld.KeyDown(keyCode,event);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        robotWorld.KeyUp(keyCode,event);
        return true;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        robotWorld.JoystickEvent(event);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return true;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawARGB(255,160,160,255);
        fieldMatrix.reset();
        float size = (float)canvas.getWidth()/(float)fieldBitmap.getWidth();
        fieldMatrix.postScale(size,size);
        canvas.drawBitmap(fieldBitmap,fieldMatrix,null);
        //robotMatrix.postTranslate(19,21);
        /*
        robotMatrix.postTranslate(365,365);
        robotMatrix.postScale(size,size,0,0);
        */
        canvas.drawBitmap(robotWorld.robot.bitmap,robotWorld.robot.DrawMatrix(size),null);
        for(int i=0;i<robotWorld.boxes.size();i++) {
            canvas.drawBitmap(robotWorld.boxes.get(i).bitmap, robotWorld.boxes.get(i).DrawMatrix(size), null);
        }
     }

}
