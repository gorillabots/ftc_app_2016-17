package org.firstinspires.ftc.teamcode.subclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

//Created by Jarred on 11/15/2016

public class ForkLift {
    private DcMotor lift;
    private TouchSensor limit;


    public ForkLift(HardwareMap hardwareMap)
    {
        lift = hardwareMap.dcMotor.get("lift");
        limit = hardwareMap.touchSensor.get("limit");
    }

    public void manipulateLift(double control)
    {
        if (limit.isPressed() && control > .1)
        {
            lift.setPower((Math.abs(control)));
        }
        else
        {
            lift.setPower(control);
        }
    }
}
