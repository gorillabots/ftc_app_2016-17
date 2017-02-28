package org.firstinspires.ftc.teamcode.submodules;

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

public class BallControl
{
    HardwareMap hardwareMap;
    Telemetry telemetry;

    DcMotor fly;
    DcMotor vac;
    DcMotor elevator;
    ElapsedTime rampTime;

    public BallControl(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        vac = hardwareMap.dcMotor.get("vac");
        fly = hardwareMap.dcMotor.get("fly");
        elevator = hardwareMap.dcMotor.get("elevator");

        rampTime = new ElapsedTime();
        rampTime.startTime();
    }

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

    @Deprecated
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

    @Deprecated
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

    public String getRampState = "null";

    void rampFlyDown(){
        if(getRampState.equals("running") || getRampState.equals("ramping down") && fly.getPower() >= 0){
            if(fly.getPower()== -1){
                fly.setPower(-.9);
                getRampState = "ramping up";

            }
            if(fly.getPower()== -.9 && rampTime.milliseconds() >500){
                fly.setPower(-.8);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.8 && rampTime.milliseconds() >500){
                fly.setPower(-.7);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.7 && rampTime.milliseconds() >500){
                fly.setPower(-.6);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.6 && rampTime.milliseconds() >500){
                fly.setPower(-.5);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.5 && rampTime.milliseconds() >500){
                fly.setPower(-.4);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.4 && rampTime.milliseconds() >500){
                fly.setPower(-.3);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.3 && rampTime.milliseconds() >500){
                fly.setPower(-.2);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.2 && rampTime.milliseconds() >500){
                fly.setPower(-.1);
                getRampState = "ramping up";
                rampTime.reset();
                rampTime.startTime();
            }if(fly.getPower()== -.1 && rampTime.milliseconds() >500){
                fly.setPower(0);
                getRampState = "still";
                rampTime.reset();
                rampTime.startTime();
            }


        }


    }

    void rampFlyUp(){
        if(getRampState.equals("running") || getRampState.equals("ramping down") && fly.getPower() >= 0){
            if(fly.getPower()== 0){
                fly.setPower(-.1);
                getRampState = "ramping down";

            }
            if(fly.getPower()== -.1 && rampTime.milliseconds() >500){
                fly.setPower(-.2);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.2 && rampTime.milliseconds() >500){
                fly.setPower(-.3);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.3 && rampTime.milliseconds() >500){
                fly.setPower(-.4);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.4 && rampTime.milliseconds() >500){
                fly.setPower(-.5);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.5 && rampTime.milliseconds() >500){
                fly.setPower(-.6);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.6 && rampTime.milliseconds() >500){
                fly.setPower(-.7);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -.7 && rampTime.milliseconds() >500){
                fly.setPower(-.8);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }
            if(fly.getPower()== -8 && rampTime.milliseconds() >500){
                fly.setPower(-.9);
                getRampState = "ramping down";
                rampTime.reset();
                rampTime.startTime();
            }if(fly.getPower()== -.9 && rampTime.milliseconds() >500){
                fly.setPower(-1);
                getRampState = "running";
                rampTime.reset();
                rampTime.startTime();
            }


        }
    }

    }



