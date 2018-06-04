package com.example.rhill.controllertest3;

/**
 * Created by rhill on 6/3/18.
 */

public class OpModeCoachBacon extends FakeOpMode {
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        robot.speed = gamepad1.left_stick_y*6;
        robot.rot += gamepad1.right_stick_x*4;
        if(gamepad1.a) {
            robot.x = 400;
            robot.y = 600;
            robot.rot = 0;
        }
        //if(gamepad1.dpad_left) {
        //    robot.rot -= 4;
        //}
        //if(gamepad1.dpad_right) {
        //    robot.rot += 4;
        //}
    }
    public void stop() {
    }
}
