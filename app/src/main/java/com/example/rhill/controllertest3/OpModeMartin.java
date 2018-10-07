package com.example.rhill.controllertest3;

public class OpModeMartin extends FakeOpMode {
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    int distance_to_go;
    public void loop() {
        robot.LeftMotorTorque(gamepad1.left_stick_y);
        robot.RightMotorTorque(gamepad1.right_stick_y);
        if(gamepad1.a) {
            distance_to_go = 50;
        }
        if(distance_to_go > 0) {
            robot.LeftMotorImpulse(-1);
            robot.RightMotorImpulse(-1);
            distance_to_go -= 1;
        } else {
            robot.LeftMotorImpulse(gamepad1.left_stick_y);
            robot.RightMotorImpulse(gamepad1.right_stick_y);
        }
    }
    public void stop() {
    }
}
