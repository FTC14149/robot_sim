package com.example.rhill.controllertest3;
import java.lang.Math;

/**
 * Created by rhill on 5/4/18.
 */

public class OpModeTest1 extends FakeOpMode {
    public void init() {
        telemetry.AddData("OpModeTest1", "Started");
    }
    public void init_loop() {
    }
    public void loop() {
        robot.speed = gamepad1.left_stick_y*6;
        //robot.rot += gamepad1.left_stick_x*4;
        if(gamepad1.a) {
            robot.x = 400;
            robot.y = 600;
            robot.rot = 0;
        }
        if(gamepad1.dpad_left) {
            robot.rot -= 4;
        }
        if(gamepad1.dpad_right) {
            robot.rot += 4;
        }
    }
    public void stop() {
    }

}
