/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */
package org.firstinspires.ftc.teamcode;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Sensor: MR Color", group = "Sensor")

public class SensorMRColor extends LinearOpMode {

    ColorSensor colorSensor;    // Hardware Device Object
    float hsvValues[] = {0F,0F,0F};

    @Override
    public void runOpMode() {


        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        ElapsedTime opmodeRunTime = new ElapsedTime();
        colorSensor = hardwareMap.colorSensor.get("BeaconSensor");

        waitForStart();
        while (opModeIsActive()) {


            String currentcolor = getBeaconColor();

            // send the info back to driver station using telemetry function.
            telemetry.addData("Running Time", opmodeRunTime.seconds());
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Saturation", hsvValues[1]);
            telemetry.addData("value", hsvValues[2]);
            telemetry.addData("current color", currentcolor);
            telemetry.update();
        }
    }

    public String getBeaconColor() {
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        String currentcolor = "none";

        if (hsvValues[0] < 15)    {
            currentcolor = "red";
        }
        if (hsvValues[0] > 220){
            currentcolor = "blue";
        }
        if (hsvValues[0] > 250){
            currentcolor = "non";
        }
        if (hsvValues[0] < 1){
            currentcolor = "non";
        }
        return currentcolor;
    }
}