package com.example.rhill.controllertest3;

/**
 * Created by rhill on 7/8/18.
 */

public class OpModeVish extends FakeOpMode {

   private boolean tankmode = true;
   private boolean steeringmode = false;
   private float rot = 0;
    private float speedmod = 1;
    private int magEnabled = 0;
    private int moveTillTouching = 0;
    private int driveToCornerOne = 0;
    private boolean wasBNotPressed;
    private boolean wasANotPressed;
    private boolean wasXNotPressed;
    private boolean wasYNotPressed;
    private boolean wasDPadDownNotPressed;
    private boolean wasDPadUpNotPressed;
    private boolean wasDPadRightNotPressed;
    private boolean wasDPadLeftNotPressed;



    private void test() {

  }

  private void stateAdvance(int var, int stateNumber) {
      var++;
      if (var > stateNumber) var = 0;
  }

  private void turnToX (int compassHeading) {
      if (robot.Compass() != compassHeading) {
          robot.LeftMotorTorque(-1);
          robot.RightMotorTorque(1);
      }
      else {
          robot.LeftMotorTorque(0);
          robot.RightMotorTorque(0);
      }
  }

  private void moveTillTouching() {
      if (moveTillTouching == 1) {
          if (robot.IsFrontSensorTouching()) {
              robot.LeftMotorTorque(0);
              robot.RightMotorTorque(0);
          } else {
              robot.LeftMotorTorque(30 * speedmod);
              robot.RightMotorTorque(30 * speedmod);
          }
      }
  }

  private org.jbox2d.common.Vec2 getLocation() {
      return robot.Location();
  }

  private void driveToCornerOne() {
      if (driveToCornerOne == 1) {
          turnToX(315);
          robot.LeftMotorTorque(30 * speedmod);
          robot.RightMotorTorque(30 * speedmod);
      }
  }

  private void magnetToggle() {
      if (magEnabled == 0) {
         robot.EnableMagnet(false);
         }
         else if (magEnabled == 1) {
              robot.EnableMagnet(true);
          }
      }






    public void init() {
        this.telemetry.AddData(this.getClass().getSimpleName(), "Started");
    }


    public void init_loop() {
    }
    public void loop() {
        test();
        tankmode();
        steeringmode();
        magnetToggle();
        driveToCornerOne();
        moveTillTouching();

        if (gamepad1.b) {
            wasBNotPressed = false;
        }
        else {
            wasBNotPressed = true;
        }
        if (gamepad1.x) {
            wasXNotPressed = false;
        }
        else {
            wasXNotPressed = true;
        }
        if (gamepad1.a) {
            wasANotPressed = false;
        }
        else {
            wasANotPressed = true;
        }
        if (gamepad1.y) {
            wasYNotPressed = false;
        }
        else {
            wasYNotPressed = true;
        }
        if (gamepad1.dpad_down) {
            wasDPadDownNotPressed = false;
        }
        else {
            wasDPadDownNotPressed = true;
        }
        if (gamepad1.dpad_up) {
            wasDPadUpNotPressed = false;
        }
        else {
            wasDPadUpNotPressed = true;
        }
        if (gamepad1.dpad_left) {
            wasDPadLeftNotPressed = false;
        }
        else {
            wasDPadLeftNotPressed = true;
        }
        if (gamepad1.dpad_right) {
            wasDPadRightNotPressed = false;
        }
        else {
            wasDPadRightNotPressed = true;
        }




       if (gamepad1.b&&wasBNotPressed) {
         stateAdvance(magEnabled, 1);
       }
       if (gamepad1.a&&wasANotPressed) {
           stateAdvance(moveTillTouching, 1);
       }

       if (gamepad1.dpad_down&&wasDPadDownNotPressed) {
          stateAdvance(driveToCornerOne,1 );
       }

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
    private void tankmode() {
    if (tankmode) {
        robot.LeftMotorTorque(gamepad1.left_stick_y * speedmod);
        robot.RightMotorTorque(gamepad1.right_stick_y * speedmod);
    }
    }

    private void steeringmode() {
        if(steeringmode) {
            rot = gamepad1.left_stick_x;

            robot.LeftMotorTorque((gamepad1.right_stick_y  + rot) * speedmod);
            robot.RightMotorTorque((gamepad1.right_stick_y + (rot*-1)) * speedmod);

        }
    }



}
