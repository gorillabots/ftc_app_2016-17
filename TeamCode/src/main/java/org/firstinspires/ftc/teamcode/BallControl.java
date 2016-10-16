package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Jarred on 10/11/2016.
 */

public class BallControl {


    DcMotor fly;
    DcMotor collector;

    ElapsedTime flywheelPulse;


    int collectorIn;

    public void toggleCollect(int state){


        if(state == 1){
            collector.setPower(collectorIn);
        }
        else if(state == 2){
            collector.setPower(collectorIn*-1);

        }
        else if(state == 3){
            collector.setPower(0);
        }
    }

    public void fireBall(){



    }






}
