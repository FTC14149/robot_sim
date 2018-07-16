package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */

import org.jbox2d.common.Vec2;

public class OpModeCoachHam extends FakeOpMode {
    float speed = 1;
    int count;
    boolean magnetEnabled = false;
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        int heading = robot.Compass();
        Vec2 location = robot.Location();
        this.telemetry.AddData("conpass:" + String.valueOf(heading), "location (" + String.valueOf(location.x) + "," + String.valueOf(location.y) + ")");
        if(heading != 90)
            robot.LeftMotorTorque(-1);
        if(gamepad1.x & gamepad1.b) {
            count = 30;
        }
        if (count > 0) {
            robot.LeftMotorTorque(-1);
            robot.RightMotorTorque(-1);
            count = count -1;
        } else {
            robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
            robot.RightMotorTorque(gamepad1.right_stick_y * speed);
        }
        if (gamepad1.a) {
            speed = 1.2f * speed;
        }
        if (gamepad1.b & gamepad1.y) {
            speed = speed / 1.2f;
        }
        if(gamepad1.x) {
            magnetEnabled = true;
        }
        if(gamepad1.y) {
            magnetEnabled = false;
        }
        robot.EnableMagnet(magnetEnabled);

    }


    public void stop() {
    }
}
