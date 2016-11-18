package org.firstinspires.ftc.teamcode;

/**
 * Created by Jarred on 11/15/2016.
 */
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ForkLift {

    HardwareMap lifthardwareMap;
    DcMotor lift;
    Servo release;
    public ForkLift() {


        lift= lifthardwareMap.dcMotor.get("lift");
        release = lifthardwareMap.servo.get("release");

    }

    public void liftInit(){
        lift.setTargetPosition(0);
    }

    public void manipulateLift(double liftUp, double liftDown){

        if(lift.getCurrentPosition() < -12278 && liftUp > .1){
            lift.setPower(liftDown);
        }
        else if(lift.getCurrentPosition() >= 0){
            lift.setPower(liftUp);
        }
        else{
            if(liftUp > liftDown){
                lift.setPower(liftUp);
            }
            else if(liftUp < liftDown){
                lift.setPower(liftDown);
            }
        }


    }


    public void releaseFork(){

        if (getReleaseState() == true){

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
