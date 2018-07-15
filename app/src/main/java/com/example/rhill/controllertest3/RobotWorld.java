package com.example.rhill.controllertest3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

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
    public MovableObject box1;
    public ArrayList<MovableObject> boxes;

    // The Handler class will give us periodic callbacks
    Handler handler;

    // Attach the RobotWorldView (android View) object so we can force it to redraw
    // through an invalidate() call.
    RobotWorldView robotWorldView;

    // Attach the OpMode subclass - we'll call it's loop() method periodically
    ArrayList<FakeOpMode> opModes;
    FakeOpMode opMode;
    int opModeSelect;

    // The physics world and its components
    Vec2 gravity = new Vec2(0, 0.0f);
    World physicsWorld = new World(gravity);
    float timeStep = 1.0f/30.0f;
    int velocityIterations = 6;
    int positionIterations = 2;

    public RobotWorld(RobotWorldView robotWorldView, TextView telemetryTextView) {
        this.robotWorldView = robotWorldView;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap robotBitmap = BitmapFactory.decodeResource(robotWorldView.getResources(),R.drawable.robot_image,options);
        robot = new Robot(physicsWorld, null ,robotBitmap, 0.5f, 0.2f);
        Bitmap boxBitmap = BitmapFactory.decodeResource(robotWorldView.getResources(),R.drawable.box,options);
        boxes = new ArrayList<MovableObject>();
        for(int y=0;y<3;y++) {
            for (int x = 0; x < 3; x++) {
                Body boxBody = CreateBox(1.5f + 0.25f * x, 1.5f + 0.25f * y, 0.20f, 0.20f, 0.0001f, 0.3f);
                box1 = new MovableObject(boxBody, boxBitmap);
                boxes.add(box1);
            }
        }

        telemetry = new Telemetry(telemetryTextView);
        gamepad1 = new Gamepad();
        gamepad2 = new Gamepad();
        opModeSelect = -1;
        opModes = new ArrayList<FakeOpMode>();
        float s = 3.56f;  // World size
        float t = 0.10f;   // Wall thickness
        CreateWall(-t,-t,0,s+t);  // Left wall
        CreateWall(-t,-t,s+t,0);  // Bottom wall
        CreateWall(s,-t, s+t,s+t); // Right wall
        CreateWall(-t,s,s+t, s+t); // Top wall
    }

    private void CreateWall(float l, float b, float r, float t) {
        BodyDef wallDef = new BodyDef();
        wallDef.position.set((r+l)/2,(t+b)/2);
        Body wallBody = physicsWorld.createBody(wallDef);
        PolygonShape wallBox = new PolygonShape();
        wallBox.setAsBox((r-l)/2, (t-b)/2);
        wallBody.createFixture(wallBox, 0);
    }

    private Body CreateBox(float x, float y, float w, float h, float m, float fric) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(x,y);
        //bodyDef.setAngle(35 * 3.14f / 180f);
        bodyDef.linearDamping = 1e6f;
        bodyDef.angularDamping = 1e6f;
        Body body = physicsWorld.createBody(bodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(w/2, h/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.setDensity(m);
        fixtureDef.friction = fric;
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);
        return body;
    }

    public void StartPhysics() {
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
        // Run the physics loop
        physicsWorld.step(timeStep, velocityIterations, positionIterations);

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
