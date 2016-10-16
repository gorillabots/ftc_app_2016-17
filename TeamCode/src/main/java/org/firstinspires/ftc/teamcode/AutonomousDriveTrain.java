package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by mikko on 10/14/16.
 */

public class AutonomousDriveTrain
{
    enum Action
    {
        idle,
        positiveX
    }

    DcMotor frontRight, backRight, frontLeft, backLeft;
    Action currentAction = Action.idle;
    int target;

    public AutonomousDriveTrain(HardwareMap hardwareMap)
    {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
    }

    public void forwards(int degrees)
    {
        if(currentAction == Action.idle)
        {
            target = degrees;

            currentAction = Action.positiveX;

            backLeft.setPower(1);
            frontRight.setPower(-1);
        }
    }

    public void loop()
    {
        if(currentAction == Action.positiveX)
        {
            if((backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2 >= target)
            {
                backLeft.setPower(0);
                frontRight.setPower(0);
            }
        }
    }
}