package com.example.rhill.controllertest3;


import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by rhill on 5/5/18.
 */

public class Gamepad {
    int lastKeyCode = 0;
    public float left_stick_x;
    public float left_stick_y;
    public float right_stick_x;
    public float right_stick_y;

    public boolean a,b,x,y;
    public boolean dpad_up, dpad_down, dpad_right, dpad_left;

    public Gamepad() {
        left_stick_x = 0;
        left_stick_y = 0;
        right_stick_x = 0;
        right_stick_y = 0;
        a = b = x = y = false;
        dpad_up = dpad_down = dpad_right = dpad_left = false;
    }
    public void HandleKeyEvent(int keyCode, boolean state) {
        lastKeyCode = keyCode;
        // Gamepad events
        switch(keyCode) {
            case(KeyEvent.KEYCODE_DPAD_LEFT):
                dpad_left = state;
                break;
            case(KeyEvent.KEYCODE_DPAD_RIGHT):
                dpad_right = state;
                break;
            case(KeyEvent.KEYCODE_DPAD_UP):
                dpad_up = state;
                break;
            case(KeyEvent.KEYCODE_DPAD_DOWN):
                dpad_down = state;
                break;
            case(KeyEvent.KEYCODE_BUTTON_X):
                x = state;
                break;
            case(KeyEvent.KEYCODE_BUTTON_Y):
                y = state;
                break;
            case(KeyEvent.KEYCODE_BUTTON_B):
                b = state;
                break;
            case(KeyEvent.KEYCODE_BUTTON_A):
                a = state;
                break;
        }

    }
    public void JoystickEvent(MotionEvent event) {
        // TODO: handle two controllers
        if (event.isFromSource(InputDevice.SOURCE_CLASS_JOYSTICK)) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // process the joystick movement...
                // Process all historical movement samples in the batch
                final int historySize = event.getHistorySize();

                // Process the movements starting from the
                // earliest historical position in the batch
                for (int i = 0; i < historySize; i++) {
                    // Process the event at historical position i
                    processJoystickInput(event, i);
                }

                // Process the current movement sample in the batch (position -1)
                processJoystickInput(event, -1);
            }
        }
    }

    private static float getCenteredAxis(MotionEvent event,
                                         InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range =
                device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis):
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }

    private void processJoystickInput(MotionEvent event,
                                      int historyPos) {

        InputDevice mInputDevice = event.getDevice();

        // Set gamepad positions from the joystick values
        float x = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_X, historyPos);
        left_stick_x = x;

        float y = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Y, historyPos);
        left_stick_y = y;

        float z = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Z, historyPos);
        right_stick_x = z;

        float rz = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_RZ, historyPos);
        right_stick_y = rz;

        float dx = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_HAT_X, historyPos);
        dpad_left = (dx < 0);
        dpad_right = (dx > 0);

        float dy = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_HAT_Y, historyPos);
        dpad_up = (dy < 0);
        dpad_down = (dy > 0);
        // Update the ship object based on the new x and y values
    }

}
