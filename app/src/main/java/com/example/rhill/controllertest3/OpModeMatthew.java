package com.example.rhill.controllertest3;


/**
 * Created by rhill on 7/8/18.
 */

public class OpModeMatthew extends FakeOpMode {
    float speed;
    int count;
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
        speed = 1;
        count = 30;
    }
    public void init_loop() {
    }
    public void loop() {
        if (gamepad1.a) {
            speed = speed * 2;
        }
        if (gamepad1.y) {
            speed = speed / 2;
        }
        if (gamepad1.x) {
            count = 30;
        }
        if(count > 0) {
            // move robot forward
            robot.LeftMotorTorque(-1);
            robot.RightMotorTorque(-1);
            count = count - 1;
        } else {
            // move robot with controller
            robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
            robot.RightMotorTorque(gamepad1.right_stick_y * speed);
        }
    }
    public void stop() {
    }
}
