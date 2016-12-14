package org.firstinspires.ftc.teamcode;

/**
 * Created by Jarred on 11/15/2016.
 */
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ForkLift {

    HardwareMap lifthardwareMap;
    DcMotor lift;
    Servo release;
    TouchSensor limit;

    HardwareMap hardwareMap;
    Telemetry telemetry;


    public ForkLift(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        lift= lifthardwareMap.dcMotor.get("lift");

        limit = lifthardwareMap.touchSensor.get("limit");

    }



    public void manipulateLift(double control){

        if(limit.isPressed() && control > .1  ){

            lift.setPower((Math.abs(control)));

        }

        else {
            lift.setPower(control);
            }
        }





    public void releaseFork(){

        if (getReleaseState() == true){
            release.setPosition(1);
        }
        else{
            release.setPosition(0);
        }

    }

    public boolean getReleaseState(){

        if(release.getPosition() == 0){
            return true;
        }
        else {
            return false;
        }

    }



}
