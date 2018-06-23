package com.example.rhill.controllertest3;

import org.jbox2d.common.Vec2;

/**
 * Created by rhill on 6/3/18.
 */

// This class implements Coach Bacon's working opMode
public class OpModeCoachBacon extends FakeOpMode {
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    boolean drawingShape = false;
    public void loop() {
        float forward = gamepad1.left_stick_y;
        float steer = gamepad1.right_stick_x;
        robot.LeftMotorTorque(forward-0.5f*steer);
        robot.RightMotorTorque(forward+0.5f*steer);
    }
    public void stop() {
    }
}
