package com.example.rhill.controllertest3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RobotActivity extends AppCompatActivity {

    // These are the main parts of this program:
    // 1. This RobotActivity - it's the Android activity that starts up
    // 2. RobotWorldView - provides an Android View to receive events and draw the robot world
    // 3. RobotWorld - the abstraction of everything in the Robot's world:
    //    a. The world
    //    b. The robot
    //    c. Gamepad controllers
    //    d. A Telemetry object to report state to the user
    // 4. FakeOpMode - a superclass for everything a kid may write in their subclass
    //    a. constructor - connect to the RobotWorld
    //    b. init() - kid overrides with code to run when their OpMode starts
    //    c. init_loop() - kid overrids with ?
    //    d. loop() - kid overrides with code to run every cycle
    //    e. stop() - kid overrides with code to run when their OpMode stops
    OpModeTest1 opMode;
    OpModeCoachBacon opModeCoachBacon;
    OpModeCoachHam opModeCoachHam;
    OpModeCoachDonuts opModeCoachDonuts;
    OpModeCoachTamal opModeCoachTamal;
    OpModeMatthew opModeMatthew;
    OpModeVish opModeVish;
    OpModeTyler opModeTyler;
    OpModeRaina opModeRaina;
    OpModeGarhet opModeGabi;
    OpModeGus opModeGus;
    OpModeMartin opModeMartin;
    OpModeKrish opModeKrish;
    OpModeColin opModeColin;
    RobotWorld robotWorld;
    RobotWorldView robotWorldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        // Attach the robotWorld to RobotWorldView, which will field touch/gamepad events
        // and send them to the robotWorld:
        robotWorldView = this.findViewById(R.id.RobotWorldViewID);
        // Create basic robotics and simulation objects
        robotWorld = new RobotWorld(robotWorldView,(TextView)findViewById(R.id.TelemetryText));
        robotWorldView.AttachRobotWorld(robotWorld);
        // Instantiate the OpMode we'll use
        opMode = new OpModeTest1(); opMode.Attach(robotWorld);
        opModeCoachBacon = new OpModeCoachBacon(); opModeCoachBacon.Attach(robotWorld);
        opModeCoachHam = new OpModeCoachHam(); opModeCoachHam.Attach(robotWorld);
        opModeCoachDonuts = new OpModeCoachDonuts(); opModeCoachDonuts.Attach(robotWorld);
        opModeCoachTamal = new OpModeCoachTamal(); opModeCoachTamal.Attach(robotWorld);
        opModeMatthew = new OpModeMatthew(); opModeMatthew.Attach(robotWorld);
        opModeVish = new OpModeVish(); opModeVish.Attach(robotWorld);
        opModeTyler = new OpModeTyler(); opModeTyler.Attach(robotWorld);
        opModeRaina = new OpModeRaina(); opModeRaina.Attach(robotWorld);
        opModeGabi = new OpModeGarhet(); opModeGabi.Attach(robotWorld);
        opModeGus = new OpModeGus(); opModeGus.Attach(robotWorld);
        opModeMartin = new OpModeMartin(); opModeMartin.Attach(robotWorld);
        opModeKrish = new OpModeKrish(); opModeKrish.Attach(robotWorld);
        opModeColin = new OpModeColin(); opModeColin.Attach(robotWorld);
        // Start the physics loop in robotWorld
        robotWorld.StartPhysics();
        // Kick off the opmode
        opMode.init();
    }

}
