package org.firstinspires.ftc.teamcode.submodules;

/**
 * Created by Jarred on 11/15/2016.
 */
import org.firstinspires.ftc.robotcore.external.Telemetry;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ForkLift {

    HardwareMap hardwareMap;
    Telemetry telemetry;
    DcMotor lift;
    AnalogInput magnetSense;
    double liftMaxAdder;
    double liftZero;

    public ForkLift(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        lift = hardwareMap.dcMotor.get("raise");
        magnetSense = hardwareMap.analogInput.get("stop");
        liftMaxAdder = 12000;
        //19147
        liftZero = -1000;
    }


    public void lift(double power)
    {

        if(getBottomState()<.045){
            liftZero = lift.getCurrentPosition();
            if(power > 0){
                lift.setPower(power);
            }
            else{
                lift.setPower(0);
            }
        }

        else if(lift.getCurrentPosition() >= (liftMaxAdder + liftZero )){
        if(power < 0){
            lift.setPower(power);
        }
            else{
            lift.setPower(0);
        }
        }

        else{
            lift.setPower(power);
        }


    }

    public void liftOverride(double power)
    {
        lift.setPower(power);
    }

    public void stop()
    {
        lift.setPower(0);
    }

    public void addTelemetry(){
        telemetry.addData("Lift Power",lift.getPower());
        telemetry.addData("Lift Position", lift.getCurrentPosition());

    }

    public double getBottomState(){
        return magnetSense.getVoltage();
    }


}





