package com.example.rhill.controllertest3;

/*
 Created by rhill on 7/8/18.
 */

public class OpModeRaina extends FakeOpMode {
    public void init() {

        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }
    public void init_loop() {
    }
    boolean busywalking;
    boolean magneton;
    boolean busyturning;
    public void loop() {
        robot.LeftMotorTorque(gamepad1.left_stick_y +gamepad1.right_stick_x);
        robot.RightMotorTorque(gamepad1.left_stick_y -gamepad1.right_stick_x);

        if(gamepad1.x) {
            magneton = true;
        }
        if(gamepad1.b) {
            magneton = false;
        }
        robot.EnableMagnet(magneton);
        //this.telemetry.AddData(this.getClass().getSimpleName() + ",Compass",String.valueOf(robot.Compass()));
        if(gamepad1.a){busyturning = true;}
        if (busyturning){
            // get heading
            int heading =robot.Compass();
            // compare to target of 90
            if(heading < 90){
                robot.LeftMotorTorque(-1);
                robot.RightMotorTorque(+1);
            }
            if(heading > 90){
                robot.LeftMotorTorque(+1);
                robot.RightMotorTorque(-1);
            }
            if(heading == 90){
                busyturning =false;
            }
        }
        if(gamepad1.y){busywalking = true;}
        if(busywalking){
            if(robot.IsFrontSensorTouching()) {
                busywalking = false;
                this.telemetry.AddData("test", "busywalking");
            }else{
                robot.LeftMotorTorque(-1);
                robot.RightMotorTorque(-1);
            }


        }
    }
    public void stop() {
    }
}