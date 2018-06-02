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

public class RobotWorldView extends View {
    Bitmap robotBitmap;
    Bitmap fieldBitmap;
    Matrix robotMatrix;
    Matrix fieldMatrix;
    float robotX, robotY, robotRot;
    float robotSpeed;
    RobotWorld robotWorld;

    public RobotWorldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fieldBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.field_diagram);
        robotBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.robot_image);
        robotMatrix = new Matrix();
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
        canvas.drawBitmap(fieldBitmap,fieldMatrix,null);
        robotMatrix.reset();
        robotMatrix.postTranslate(-46,-50);
        robotMatrix.postRotate(robotWorld.robot.rot);
        robotMatrix.postTranslate(robotWorld.robot.x,robotWorld.robot.y);
        canvas.drawBitmap(robotBitmap,robotMatrix,null);
     }

}
