package com.example.rhill.controllertest3;

import android.widget.TextView;

/**
 * Created by rhill on 5/3/18.
 */

public class Telemetry {
    TextView myTextView;
    public Telemetry(TextView myTextView) {
        this.myTextView = myTextView;
    }
    public void AddData(String caption, String text) {
        myTextView.setText(caption + ":" + text);
    }
}
