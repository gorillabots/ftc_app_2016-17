package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Jarred on 10/11/2016.
 */

public class BallControl {


    DcMotor fly;
    DcMotor vac;
    DcMotor elevator;
    HardwareMap hardwareMap;
    Telemetry telemetry;

    public BallControl(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        vac = hardwareMap.dcMotor.get("vac");
        fly = hardwareMap.dcMotor.get("fly");
        elevator = hardwareMap.dcMotor.get("elevator");
    }




    public void runFlywheel(boolean on){

        if(on == true){
            fly.setPower(-1);
        }

        else{
            fly.setPower(0);
        }

    }

    /*
    if the flywheels receive an on signal, run the flywheels in a manner that ejects the balls
    when they pass through the lower portion of the flywheel
    */
    public void runCollector(boolean on, boolean switcher){

        if(on == true && switcher == false){
            vac.setPower(1);
        }
        else if(on== false && switcher ==true){
            vac.setPower(-1);
        }
        else{
            vac.setPower(0);
        }
    /*
    if a button is pressed run the collector in a manner that balls are sucked in
    if another button is pressed and the other is not


    */
    }

    public void runElevator(boolean on, float switcher){
        if(on == true && switcher<.6){
            elevator.setPower(-1);
        }
        else if(on == false && switcher>.6){
            elevator.setPower(1);
        }
        else{
            elevator.setPower(0);
        }
    }

    /**
     * if on is true, run wheels; false stop
     * @param on
     */
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

    /**
     * if direction is false, raise elevator; true lower
     * @param direction
     */
    public void newRunElevator(boolean direction)
    {
        if(direction)
        {
            elevator.setPower(-1);
        }
        else
        {
            elevator.setPower(1);
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

