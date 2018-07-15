package com.example.rhill.controllertest3;

/*
 Created by rhill on 7/8/18.
 */

public class OpModeRaina extends FakeOpMode {
    public void init() {
        speed = 1;
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    float speed;
    public void loop() {
        robot.LeftMotorTorque(gamepad1.left_stick_y +gamepad1.right_stick_x*speed);
        robot.RightMotorTorque(gamepad1.left_stick_y -gamepad1.right_stick_x*speed);
        if (gamepad1.a){speed = speed*2;
        }
        if(gamepad1.y){speed = speed/2;
        }
    }
    public void stop() {
    }
}
