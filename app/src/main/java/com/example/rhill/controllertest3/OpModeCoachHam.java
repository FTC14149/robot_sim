package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */

import org.jbox2d.common.Vec2;

import static java.lang.StrictMath.abs;

public class OpModeCoachHam extends FakeOpMode {
    public enum states {
        IDLE,
        ENTERLENGTH,
        FWD,
        TURN

    }
    float speed = 1;
    int count;
    boolean magnetEnabled = false;
    states mystate;
    int newheading;
    int turns = 0;
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
        mystate = states.IDLE;
    }
    public void init_loop() {
    }
    public void loop() {
        boolean forward = false;
        int heading = robot.Compass();
        Vec2 location = robot.Location();
        //this.telemetry.AddData("conpass:" + String.valueOf(heading), "location (" + String.valueOf(location.x) + "," + String.valueOf(location.y) + ")");



        switch(mystate) {
            case ENTERLENGTH:
                count = 10;
                mystate = states.FWD;
                break;
            case FWD:
                if (count > 0) {
                    robot.LeftMotorTorque(-1);
                    robot.RightMotorTorque(-1);
                    count = count -1;
                }
                else {
                    newheading = (heading + 90) % 360;
                    turns = turns+1;
                    this.telemetry.AddData("conpass:" + String.valueOf(newheading), "location (" + String.valueOf(location.x) + "," + String.valueOf(location.y) + ")");
                    mystate = states.TURN;
                }
                break;
            case TURN:
                if (turns == 5) {
                    mystate = states.IDLE;}
                if (abs(heading-newheading) < 2) {
                    mystate = states.ENTERLENGTH;
                } else {
                    robot.LeftMotorTorque(-1);
                }
                break;
            case IDLE:
                robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
                robot.RightMotorTorque(gamepad1.right_stick_y * speed);
                if (gamepad1.a) {
                    mystate = states.ENTERLENGTH;
                    turns = 0;
                }
                break;
        }

        //if (heading != 270) {
        //   robot.LeftMotorTorque(-1);
        //}
        //if (location.x != 5) {
         //   robot.LeftMotorTorque(-1);
         //   robot.RightMotorTorque(-1);
        //}
//        if(gamepad1.x & gamepad1.b) {
//            count = 30;
//        }
//        if (count > 0) {
//            robot.LeftMotorTorque(-1);
//            robot.RightMotorTorque(-1);
//            count = count -1;
//        } else {
//            robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
//            robot.RightMotorTorque(gamepad1.right_stick_y * speed);
//        }
//        if (gamepad1.a) {
//            speed = 1.2f * speed;
//        }
//        if (gamepad1.b & gamepad1.y) {
//            speed = speed / 1.2f;
//        }
//        if(gamepad1.x) {
//            magnetEnabled = true;
//        }
//        if(gamepad1.y) {
//            magnetEnabled = false;
//        }
//        robot.EnableMagnet(magnetEnabled);

    }


    public void stop() {
    }
}
