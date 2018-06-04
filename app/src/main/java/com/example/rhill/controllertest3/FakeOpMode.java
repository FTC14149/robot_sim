package com.example.rhill.controllertest3;

/**
 * Created by rhill on 5/4/18.
 */

public class FakeOpMode {
    Telemetry telemetry;
    Gamepad gamepad1;
    Gamepad gamepad2;
    Robot robot;
    public void Attach(RobotWorld robotWorld) {
        this.telemetry = robotWorld.telemetry;
        this.gamepad1 = robotWorld.gamepad1;
        this.gamepad2 = robotWorld.gamepad2;
        this.robot = robotWorld.robot;
        robotWorld.AttachOpMode(this);
        this.telemetry.AddData(this.getClass().getSimpleName(), "Attached");
    }
    public void init() {
    }
    public void init_loop() {
    }
    public void loop() {
    }
    public void stop() {
    }
}
