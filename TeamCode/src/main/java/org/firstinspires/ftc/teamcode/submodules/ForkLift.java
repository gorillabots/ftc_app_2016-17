package org.firstinspires.ftc.teamcode.submodules;

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

    HardwareMap hardwareMap;
    DcMotor lift;
    TouchSensor magnetSense;
    double liftMaxAdder;
    double liftZero;
    public ForkLift(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;
        lift = hardwareMap.dcMotor.get("lift");
        magnetSense = hardwareMap.touchSensor.get("liftSafe");
        liftMaxAdder = 100000;
        liftZero = -1000;
    }


    public void lift(double power)
    {

        if(getBottomState()){
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

     void stop()
    {
        lift.setPower(0);
    }

    public boolean getBottomState(){

        return magnetSense.isPressed();
    }

}





