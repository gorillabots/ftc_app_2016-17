package org.firstinspires.ftc.teamcode.subclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

//Created by Jarred on 10/11/2016
//TODO: Remove old functions when unused

public class BallControl
{
    private DcMotor fly;
    private DcMotor vac;
    private DcMotor elevator;

    public BallControl(HardwareMap hardwareMap)
    {
        vac = hardwareMap.dcMotor.get("vac");
        fly = hardwareMap.dcMotor.get("fly");
        elevator = hardwareMap.dcMotor.get("elevator");
    }

    @Deprecated
    public void runFlywheel(boolean on)
    {

        if(on)
        {
            fly.setPower(-1);
        }

        else
        {
            fly.setPower(0);
        }

    }

    /*
    if the flywheels receive an on signal, run the flywheels in a manner that ejects the balls
    when they pass through the lower portion of the flywheel
    */
    @Deprecated
    public void runCollector(boolean on, boolean switcher)
    {

        if(on && !switcher)
        {
            vac.setPower(1);
        }
        else if(!on && switcher)
        {
            vac.setPower(-1);
        }
        else
        {
            vac.setPower(0);
        }

        //If a button is pressed run the collector in a manner that balls are sucked in
        //If another button is pressed and the other is not
    }

    @Deprecated
    public void runElevator(boolean on, float switcher)
    {
        if(on && switcher<.6)
        {
            elevator.setPower(-1);
        }
        else if(!on && switcher>.6)
        {
            elevator.setPower(1);
        }
        else
        {
            elevator.setPower(0);
        }
    }

    public void newRunFlywheel(boolean on)
    {
        if(on)
        {
            fly.setPower(-1);
        }
        else
        {
            fly.setPower(0);
        }

    }

    public void newRunElevator(boolean direction)
    {
        if(direction)
        {
            elevator.setPower(1);
        }
        else
        {
            elevator.setPower(-1);
        }
    }

    public void newStopElevator()
    {
        elevator.setPower(0);
    }

    public void newRunCollector(boolean direction)
    {

        if(direction)
        {
            vac.setPower(1);
        }
        else
        {
            vac.setPower(-1);
        }
    }

    public void newStopCollector()
    {
        vac.setPower(0);
    }
}
