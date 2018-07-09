package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */

public class OpModeCoachHam extends FakeOpMode {
    float speed = 1;
    int count;
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        if (gamepad1.x) {
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
            speed = 2 * speed;
        }
        if (gamepad1.y) {
            speed = speed / 2;
        }

    }


    public void stop() {
    }
}
