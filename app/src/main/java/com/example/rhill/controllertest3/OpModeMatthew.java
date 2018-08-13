package com.example.rhill.controllertest3;


/**
 * Created by rhill on 7/8/18.
 */

public class OpModeMatthew extends FakeOpMode {
    float speed;
    int count;
    boolean im_busy_turning;
    boolean run_away;
    int mode;
    boolean was_pressed;
    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
        speed = 1;
        count = 0;
        mode=0;
        im_busy_turning = false;
        run_away = false;
        was_pressed = false;
    }
    public void init_loop() {
    }
    public void loop() {
        robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
        robot.RightMotorTorque(gamepad1.right_stick_y * speed);
        if (gamepad1.dpad_up && ! was_pressed){
            mode = mode + 1;
            if (mode == 3) {
                mode = 0;
            }
            this.telemetry.AddData("New Mode", String.valueOf(mode));
        }
        was_pressed = gamepad1.dpad_up;
        switch (mode) {
            case 0:
                if (gamepad1.a) {
                    speed = speed * 2;
                }
                if (gamepad1.y) {
                    speed = speed / 2;
                }
                break;
            case 1:
                if (gamepad1.b) {
                    im_busy_turning = true;
                }
                if (im_busy_turning == true) {
                    if (robot.Compass() < 90) {
                        // turn robot right
                        robot.LeftMotorTorque(-1);
                        robot.RightMotorTorque(1);
                        count = count - 1;
                    } else {
                        if (robot.Compass() > 90) {
                            // turn robot left
                            robot.LeftMotorTorque(1);
                            robot.RightMotorTorque(-1);
                            count = count - 1;
                        } else {
                            im_busy_turning = false;
                        }
                    }
                } else {
                    robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
                    robot.RightMotorTorque(gamepad1.right_stick_y * speed);
                }
                break;
            case 2:
                if (gamepad1.x) {
                    run_away = true;
                }
                if (run_away == true) {
                    if (robot.frontSensorTouching == false) {
                        robot.LeftMotorTorque(-1);
                        robot.RightMotorTorque(-1);
                    } else {
                        run_away = false;
                    }
                } else {
                    robot.LeftMotorTorque(gamepad1.left_stick_y * speed);
                    robot.RightMotorTorque(gamepad1.right_stick_y * speed);
                }
                break;
        }
        //this.telemetry.AddData("compass", String.valueOf(robot.Compass()));
    }



    public void stop() {
    }
}
