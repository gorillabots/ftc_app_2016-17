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


    DcMotor flyOne;
    DcMotor flyTwo;
    DcMotor vac;
    CRServo elevator;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    public BallControl(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        vac = hardwareMap.dcMotor.get("vac");
        flyOne = hardwareMap.dcMotor.get("flyOne");
        flyTwo = hardwareMap.dcMotor.get("flyTwo");
        elevator = hardwareMap.crservo.get("elevator");
    }


    public void runFlywheel(boolean on){

        if(on == true){
            flyOne.setPower(-1);
            flyTwo.setPower(1);
        }

        else{
            flyOne.setPower(0);
            flyTwo.setPower(0);
        }

    }

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




    }
