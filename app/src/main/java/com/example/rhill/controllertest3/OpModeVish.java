package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */
public class OpModeVish extends FakeOpMode {

   private boolean tankmode = true;
   private boolean steeringmode = false;
   private float rot = 0;
    private float speedmod = 1;

    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }

    public void init_loop() {
    }
    public void loop() {
        tankmode();
        steeringmode();
       if (gamepad1.x) {
           tankmode = true;
           steeringmode = false;
       }
       if(gamepad1.y) {
           tankmode = false;
           steeringmode = true;
       }

       if(gamepad1.dpad_left) {
           speedmod=1;
       }
       else if(gamepad1.dpad_up) {
           speedmod=2;
       }
       else if (gamepad1.dpad_right) {
           speedmod=3;
       }




    }
    public void stop() {
    }
    public void tankmode() {
    if (tankmode==true) {
        robot.LeftMotorTorque(gamepad1.left_stick_y * speedmod);
        robot.RightMotorTorque(gamepad1.right_stick_y * speedmod);
    }
    }

    public void steeringmode() {
        if(steeringmode==true) {
            rot = gamepad1.left_stick_x;

            robot.LeftMotorTorque((gamepad1.right_stick_y  + rot) * speedmod);
            robot.RightMotorTorque((gamepad1.right_stick_y + (rot*-1)) * speedmod);

        }
    }



}
