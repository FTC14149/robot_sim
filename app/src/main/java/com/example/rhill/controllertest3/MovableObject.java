package com.example.rhill.controllertest3;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Created by rhill on 6/6/18.
 */

public class MovableObject {
    public Body body;
    public Bitmap bitmap;
    public Matrix drawMatrix;
    public float rot,x,y;
    public MovableObject(Body body, Bitmap bitmap) {
        this.body = body;
        this.bitmap = bitmap;
        this.drawMatrix = new Matrix();
    }
    public Matrix DrawMatrix(float scale) {
        if(body != null) {
            Vec2 position = body.getPosition();
            x = position.x * 100;
            y = position.y * 100;
            rot = body.getAngle() * 180f/3.14f;
        } else {
            x = 0;
            y = 0;
            rot = 0;
        }
        drawMatrix.reset();
        drawMatrix.postTranslate(-bitmap.getWidth()/2.0f, -bitmap.getHeight()/2.0f);
        drawMatrix.postRotate(rot);
        drawMatrix.postTranslate(x,y);
        drawMatrix.postScale(scale,scale);
        return drawMatrix;
    }
}
