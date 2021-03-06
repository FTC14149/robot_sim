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
    int distance_to_go;
    public void loop() {
        if(gamepad1.a) {
            distance_to_go = 50;
        }
        if(distance_to_go > 0) {
            robot.LeftMotorTorque(-1);
            robot.RightMotorTorque(-1);
            distance_to_go -= 1;
        } else {
            robot.LeftMotorTorque(gamepad1.left_stick_y);
            robot.RightMotorTorque(gamepad1.right_stick_y);
        }
        if(robot.IsFrontSensorTouching()) {
            this.telemetry.AddData(this.getClass().getSimpleName(), "Touching");
        } else {
            this.telemetry.AddData(this.getClass().getSimpleName(), "Not Touching");
        }
        if(gamepad1.x) {
            robot.EnableMagnet(true);
        } else {
            robot.EnableMagnet(false);
        }
        //this.telemetry.AddData("keycode", String.valueOf(gamepad1.lastKeyCode));
        //this.telemetry.AddData(this.getClass().getSimpleName(),String.valueOf(robot.Compass()));
        //Vec2 location = robot.Location();
        //this.telemetry.AddData(this.getClass().getSimpleName(),String.valueOf(location.x)+","+String.valueOf(location.y));
    }
    public void stop() {
    }
}
