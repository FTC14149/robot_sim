package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */

public class OpModeGarhet extends FakeOpMode {
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        robot.LeftMotorTorque(gamepad1.left_stick_y);
        robot.RightMotorTorque(gamepad1.right_stick_y);
    }
    public void stop() {
    }
}
