package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */

public class OpModeTyler extends FakeOpMode {
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        float forward = gamepad1.left_stick_y;
        float steer = gamepad1.right_stick_x;
        robot.LeftMotorTorque(forward-0.5f*steer);
        robot.RightMotorTorque(forward+0.5f*steer);
    }
    public void stop() {
    }
}
