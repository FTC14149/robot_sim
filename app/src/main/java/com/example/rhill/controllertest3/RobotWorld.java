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

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<FakeOpMode> opModes;
    FakeOpMode opMode;
    int opModeSelect;

    public RobotWorld(TextView telemetryTextView) {
        robot = new Robot();
        telemetry = new Telemetry(telemetryTextView);
        gamepad1 = new Gamepad();
        gamepad2 = new Gamepad();
        opModeSelect = -1;
        opModes = new ArrayList<FakeOpMode>();
    }

    public void AttachRobotWorldView(RobotWorldView robotWorldView) {
        this.robotWorldView = robotWorldView;
        handler = new Handler();
        handler.post(this);
    }
    // You can attach as many OpModes here as you like.  They will
    // get added to the opModes list, and can be selected as the
    // active opMode with the
    // left and right shoulder buttons on the controller below.
    public void AttachOpMode(FakeOpMode opMode) {
        this.opModes.add(opMode);
        this.opModeSelect += 1;
        this.opMode = opModes.get(this.opModeSelect);
    }
    // This method is called 30 times a second by the handler.  This periodic event
    // allows us to move the robot, call the active opMode's loop() method, and
    // redraw the screen.
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
        // If the right shoulder button is pressed, advance to the next opmode
        // that was registered with AttachOpMode()
        if(keyCode == KeyEvent.KEYCODE_BUTTON_R1) {
            this.opModeSelect += 1;
            this.opModeSelect = this.opModeSelect % opModes.size();
            this.opMode = opModes.get(this.opModeSelect);
            this.opMode.init();
        }
        // If the left shoulder button is pressed, advance to the previous opmode
        // that was registered with AttachOpMode()
        if(keyCode == KeyEvent.KEYCODE_BUTTON_L1) {
            this.opModeSelect -= 1;
            if(this.opModeSelect < 0) this.opModeSelect = opModes.size() - 1;
            this.opMode = opModes.get(this.opModeSelect);
            this.opMode.init();
        }
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
