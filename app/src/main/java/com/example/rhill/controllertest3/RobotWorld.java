package com.example.rhill.controllertest3;

import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by rhill on 5/6/18.
 */

    // There are two main types of events in the RobotWorld
    // 1. User events that affect the RobotWorld, and all are fielded by RobotWorldView and sent here
    //    a. KeyDown/KeyUp events on the game controller or keyboard (when running in the emulator)
    //    b. Motion events from the game controllers' joysticks
    //    c. Touch events (if we end up supporting any of those)
    // 2. Periodic calls to RobotWorld.run().  These allow us to simulate a physics environment
    //    with a nearly fixed timestamp.

// We need to use this Handler package
import android.os.Handler;

public class RobotWorld implements Runnable {
    // This is simply a container class for everything in the Robot's world
    public Robot robot;
    public Telemetry telemetry;
    public Gamepad gamepad1;
    public Gamepad gamepad2;

    // The Handler class will give us periodic callbacks
    Handler handler;

    // Attach the RobotWorldView (android View) object so we can force it to redraw
    // through an invalidate() call.
    RobotWorldView robotWorldView;

    // Attach the OpMode subclass - we'll call it's loop() method periodically
    FakeOpMode opMode;

    public RobotWorld(TextView telemetryTextView) {
        robot = new Robot();
        telemetry = new Telemetry(telemetryTextView);
        gamepad1 = new Gamepad();
        gamepad2 = new Gamepad();
    }

    public void AttachRobotWorldView(RobotWorldView robotWorldView) {
        this.robotWorldView = robotWorldView;
        handler = new Handler();
        handler.post(this);
    }

    public void AttachOpMode(FakeOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void run() {
        // Run the kids' opmode loop code
        opMode.loop();
        // Run the physics loop functions
        robot.y += robot.speed*Math.cos(3.14/180.0*robot.rot);
        robot.x -= robot.speed*Math.sin(3.14/180.0*robot.rot);
        // Let the screen know we want to update
        robotWorldView.invalidate();
        handler.postDelayed(this,30);
    }

    public void KeyDown(int keyCode, KeyEvent event) {
        // if(event.getDeviceId() == 0)  TODO: bind device IDs to gamepad1/gamepad2
        // TODO: if source == gamepad1:
        gamepad1.HandleKeyEvent(keyCode, true);
    }

    public void KeyUp(int keyCode, KeyEvent event) {
        // if(event.getDeviceId() == 0)  TODO: bind device IDs to gamepad1/gamepad2
        // TODO: if source == gamepad1:
        gamepad1.HandleKeyEvent(keyCode, false);
    }

    public void JoystickEvent(MotionEvent event) {
        // TODO: handle two controllers
        gamepad1.JoystickEvent(event);
    }
}
