package com.example.rhill.controllertest3;

/*
 Created by rhill on 7/8/18.
 */

public class OpModeRaina extends FakeOpMode {
    public void init() {

        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
        mode=0;
        count =0;
    }
    public void init_loop() {
    }
    boolean busywalking;
    boolean magneton;
    boolean busyturning;
    int mode;
    int count;

    public void loop() {
        robot.LeftMotorTorque(gamepad1.left_stick_y +gamepad1.right_stick_x);
        robot.RightMotorTorque(gamepad1.left_stick_y -gamepad1.right_stick_x);

        switch(mode){
            case 0:
                if(gamepad1.x) {
                    magneton = true;
                }
                if(gamepad1.b) {
                    magneton = false;
                }
                robot.EnableMagnet(magneton);
                break;
            case 1:
                if(gamepad1.y){busywalking = true;}
                if(busywalking) {
                    if (robot.IsFrontSensorTouching()) {
                        busywalking = false;
                        this.telemetry.AddData("test", "busywalking");
                    } else {
                        robot.LeftMotorTorque(-1);
                        robot.RightMotorTorque(-1);
                    }
                }
                break;
            case 2:
                if(gamepad1.y){busyturning = true;}
                if (busyturning) {
                    // get heading
                    int heading = robot.Compass();
                    // compare to target of 90
                    if (heading < 90) {
                        robot.LeftMotorTorque(-1);
                        robot.RightMotorTorque(+1);
                    }
                    if (heading > 90) {
                        robot.LeftMotorTorque(+1);
                        robot.RightMotorTorque(-1);
                    }
                    if (heading == 90) {
                        busyturning = false;
                    }

                }
                 break;
            case 3:

        }
        if(count==0&&gamepad1.a){
            count=15;
            mode=mode+1;
            if(mode==3) {
                mode=0;
            }

            this.telemetry.AddData("New Mode", String.valueOf(mode));
        } else {
            count=count-1;
            if(count<0)
                count=0;
        }
    }


    public void stop() {
    }
}