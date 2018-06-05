package com.example.rhill.controllertest3;

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
    int current_side = 0;
    int current_time = 0;
    private void draw_shape() {
        robot.speed = -4;
        current_time += 1;
        if(current_time == 30) {
            robot.rot += 90;
            current_time = 0;
            current_side += 1;
            if(current_side == 4) {
                drawingShape = false;
            }
        }
    }
    public void loop() {
        if(gamepad1.a) {
            drawingShape = true;
            current_side = 0;
        }
        if(drawingShape) {
            draw_shape();
        } else {
            robot.speed = gamepad1.left_stick_y*12;
            robot.rot += gamepad1.right_stick_x*8;
            if(gamepad1.a) {
                robot.x = 400;
                robot.y = 600;
                robot.rot = 0;
            }
        }
    }
    public void stop() {
    }
}
