package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by mikko on 10/14/16.
 */

public class AutonomousDriveTrain
{
    final double metersInInch = 0.0254;
    final double wheelDiameter = 4 * metersInInch; //4 inch wheel diameter, to meters
    final double pi = 3.14159265; //3589793238462643383279502884197169399375105820974944592307816406;
    final double wheelCircumference = wheelDiameter * pi;

    Telemetry telemetry;

    enum Action
    {
        idle,
        positiveX
    }

    DcMotor frontRight, backRight, frontLeft, backLeft;
    Action currentAction = Action.idle;
    double target;

    public AutonomousDriveTrain(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.telemetry = telemetry;

        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
    }

    public void forwardsMeters(int meters)
    {
        forwards(meters * wheelCircumference);
    }

    public void forwards(double degrees)
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
        telemetry.addData("Forwards?", currentAction == Action.positiveX);

        if(currentAction == Action.positiveX)
        {
            if(Math.abs(backLeft.getCurrentPosition() - frontRight.getCurrentPosition()) / 2 >= target)
            {
                backLeft.setPower(0);
                frontRight.setPower(0);
            }
        }
    }
}