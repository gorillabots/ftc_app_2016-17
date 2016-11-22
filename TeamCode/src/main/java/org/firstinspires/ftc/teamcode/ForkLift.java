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

    public void manipulateLift(double control){

        if(lift.getCurrentPosition() < -12278 ){

            lift.setPower((Math.abs(control))*-1);

        }
        else if(lift.getCurrentPosition() >= 0){
            lift.setPower(Math.abs(control));
        }
        else if(getReleaseState()){
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
