package com.example.rhill.controllertest3;
import java.lang.Math;

/**
 * Created by rhill on 5/4/18.
 */

public class OpModeTest1 extends FakeOpMode {
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        //robot.LeftMotorTorque(gamepad1.left_stick_y);
        //robot.RightMotorTorque(gamepad1.right_stick_y);
        robot.LeftMotorImpulse(gamepad1.left_stick_y);
        robot.RightMotorImpulse(gamepad1.right_stick_y);
    }
    public void stop() {
    }

}
