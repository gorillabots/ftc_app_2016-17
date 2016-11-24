package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

/**
 * Created by emper on 9/30/2016.
 */
@TeleOp(name = "ButtonPresserZ", group = "Concept")
public class ButtonPresserClass implements ButtonPresserInterface {
    Servo button_presser_1;
    Servo button_presser_2;
    HardwareMap hardware;
    ColorSensor colorSensor;
    public void Press_Button(Servo servo, double position){
        servo.setDirection(Servo.Direction.FORWARD);
        servo.setPosition(position);
    }
    @Override
    public boolean atBeacon(ColorSensor color) {
        if(ColorHelper.getFloorColor(color) == "white"){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String getBeaconColor(ColorSensor color) {
        return ColorHelper.getBeaconColor(color);
    }

    @Override
    public String isTeamColor(String team) {
        //only use "red" and "blue"
        return team;
    }

    @Override
    public void Respond_If_In_Red_Alliance(ColorSensor color) throws InterruptedException{
        if(getBeaconColor(color) == isTeamColor("red")){
            Press_Button(button_presser_1, 0.75);
            Thread.sleep(2500);
            Press_Button(button_presser_1, 0.15);
            Thread.sleep(2500);
        }
        else if(getBeaconColor(color) == "blue"){
            Press_Button(button_presser_2, 0.75);
            Thread.sleep(2500);
            Press_Button(button_presser_2, 0.15);
            Thread.sleep(2500);
        }
        else{

        }
    }

    @Override
    public void Respond_If_In_Blue_Alliance(ColorSensor color) throws InterruptedException{
        if(getBeaconColor(color) == isTeamColor("blue")){
            Press_Button(button_presser_1, 0.75);
            Thread.sleep(2500);
            Press_Button(button_presser_1, 0.15);
            Thread.sleep(2500);
        }
        else if(getBeaconColor(color) == "red"){
            Press_Button(button_presser_2, 0.75);
            Thread.sleep(2500);
            Press_Button(button_presser_2, 0.15);
            Thread.sleep(2500);
        }
        else{

        }
    }

    public void extend_One( double position){

        Press_Button(button_presser_1, position);

    }

    public void extend_Two( double position){

        Press_Button(button_presser_2, position);

    }

    }

