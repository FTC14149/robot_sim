package com.example.rhill.controllertest3;

/*
 Created by rhill on 7/8/18.
*/

import android.os.MessageQueue;

public class OpModeRaina extends FakeOpMode {
    public enum state{
        IDLE,
        FWD1,
        TURN1,
        FWD2,
        TURN2,
        FWD3,
        TURN3,
        FWD4,
        TURN4
    }
    public void init() {

        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
        mode = 0;
        count = 0;
        mystate = state.IDLE;
    }
    public void init_loop() {
    }
   // boolean busywalking;
   // boolean magneton;
   // boolean fiftyfive;
   // boolean busyturning;
    int mode;
    int count;
    state mystate;

    public void loop() {
        switch (mystate){
            case IDLE:
                robot.LeftMotorTorque(gamepad1.left_stick_y + gamepad1.right_stick_x);
                robot.RightMotorTorque(gamepad1.left_stick_y - gamepad1.right_stick_x);
                if (gamepad1.x){
                    count = 0;
                    mystate = state.FWD1;
                }
                break;
            case FWD1:
                robot.LeftMotorTorque(-1);
                robot.RightMotorTorque(-1);
                count = count + 1;
                if (count >= 45){
                    mystate = state.TURN1;
                    count = 0;

                }

                break;
            case TURN1:
                robot.RightMotorTorque(-1);
                robot.LeftMotorTorque(0);
                count = count + 1;
                if(count >=44){
                    mystate = state.FWD2;
                    count = 0;
                }
                break;
            case FWD2:
                robot.LeftMotorTorque(-1);
                robot.RightMotorTorque(-1);
                count = count + 1;
                if (count >= 45){
                    mystate = state.TURN2;
                    count = 0;

                }

                break;
            case TURN2:
                robot.RightMotorTorque(-1);
                robot.LeftMotorTorque(0);
                count = count + 1;
                if(count >=44){
                    mystate = state.FWD3;
                    count = 0;
                }
                break;
            case FWD3:
                robot.LeftMotorTorque(-1);
                robot.RightMotorTorque(-1);
                count = count + 1;
                if (count >= 45){
                    mystate = state.TURN3;
                    count = 0;

                }

                break;
            case TURN3:
                robot.RightMotorTorque(-1);
                robot.LeftMotorTorque(0);
                count = count + 1;
                if(count >=44){
                    mystate = state.FWD4;
                    count = 0;
                }
                break;
            case FWD4:
                robot.LeftMotorTorque(-1);
                robot.RightMotorTorque(-1);
                count = count + 1;
                if (count >= 45){
                    mystate = state.TURN4;
                    count = 0;

                }

                break;
            case TURN4:
                robot.RightMotorTorque(-1);
                robot.LeftMotorTorque(0);
                count = count + 1;
                if(count >=44){
                    mystate = state.IDLE;
                    count = 0;
                }
                break;
        }
//        switch (mode) {
//            case 0:
//                if (gamepad1.x) {
//                    magneton = true;
//                }
//                if (gamepad1.b) {
//                    magneton = false;
//                }
//                robot.EnableMagnet(magneton);
//                break;
//            case 1:
//                if (gamepad1.y) {
//                    busywalking = true;
//                }
//                if (busywalking) {
//                    if (robot.IsFrontSensorTouching()) {
//                        busywalking = false;
//                        this.telemetry.AddData("test", "busywalking");
//                    } else {
//                        robot.LeftMotorTorque(-1);
//                        robot.RightMotorTorque(-1);
//                    }
//                }
//                break;
//            case 2:
//                if (gamepad1.y) {
//                    busyturning = true;
//                }
//                if (busyturning) {
//                    // get heading
//                    int heading = robot.Compass();
//                    // compare to target of 90
//                    if (heading < 90) {
//                        robot.LeftMotorTorque(-1);
//                        robot.RightMotorTorque(+1);
//                    }
//                    if (heading > 90) {
//                        robot.LeftMotorTorque(+1);
//                        robot.RightMotorTorque(-1);
//                    }
//                    if (heading == 90) {
//                        busyturning = false;
//                    }
//
//                }
//                break;
//            case 3:
//                if (gamepad1.y) {
//                    fiftyfive = true;
//                }
//                if (fiftyfive) {
//                    int heading = robot.Compass();
//                    if (heading < 305) {
//                        robot.LeftMotorTorque(-1);
//                        robot.RightMotorTorque(+1);
//                    }
//                    if (heading > 305) {
//                        robot.LeftMotorTorque(+1);
//                        robot.RightMotorTorque(-1);
//                    }
//                    if (heading == 305) {
//                        fiftyfive = false;
//                        busywalking = true;
//                        mode = 1;
//                    }
//
//                }
//        }
//        if (count == 0 && gamepad1.a) {
//            count = 15;
//            mode = mode + 1;
//            if (mode == 4) {
//                mode = 0;
//            }
//
//            this.telemetry.AddData("New Mode", String.valueOf(mode));
//        } else {
//            count = count - 1;
//            if (count < 0)
//                count = 0;
//        }

    }

        public void stop () {
        }

}
/*
Mode 0 is Magnet On(X)/Off(B)
Mode 1 is Busy Walking
Mode 2 is Busy Turning
Mode 3 is Compass

*/
