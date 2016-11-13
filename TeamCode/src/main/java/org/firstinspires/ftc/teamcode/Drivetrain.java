package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Owner on 9/27/2016.
 */


/**
 * Related Links
 *		http://www.simbotics.org/resources/mobility/omnidirectional-drive
 *		http://www.vexforum.com/index.php/12370-holonomic-drives-2-0-a-video-tutorial-by-cody/0
 */
public class Drivetrain
{
    HardwareMap hardwareMap;
    Telemetry telemetry;

    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;


    public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
    }

    /**
     *
      * @param stickX direction to move relative to the field (X)
     * @param stickY direction to move relative to the field (Y)
     * @param stickRot how much to turn the robot
     * @param heading how much to robot has rotated from initial value
     */
    public void oneStickLoop(float stickX, float stickY, float stickRot, int heading)
    {
        int facingDeg = -45 - heading; //Robot's rotation
        double facingRad = Math.toRadians(facingDeg); // Convert to radians

        double cs = Math.cos(facingRad);
        double sn = Math.sin(facingRad);

        double headX = stickX * cs - stickY * sn; //Rotated vector (Relative heading)
        double headY = stickX * sn + stickY * cs; //Each is in range -root 2 to root 2

        headX /= Math.sqrt(2); //In range -1 to 1
        headY /= Math.sqrt(2);

        telemetry.addData("absHead", "(" + stickX + ", " + stickY + ")");
        telemetry.addData("head", heading);
        telemetry.addData("relHead", "(" + headX + ", " + headY + ")");

        double backLeftPower = limitToOne(headX - stickRot);
        double frontRightPower = limitToOne(-headX - stickRot);
        double backRightPower = limitToOne(-headY - stickRot);
        double frontLeftPower = limitToOne(headY - stickRot);

        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
        frontLeft.setPower(frontLeftPower);
    }

    double limitToOne(double in)
    {
        if(in < -1)
        {
            return -1;
        }
        if(in > 1)
        {
            return 1;
        }

        return in;
    }

    /*private void twoStickLoop()
    {


        float stickX = (int) opMode.gamepad1.left_stick_x; // Stick position (Absolute heading)
        float stickY = (int) opMode.gamepad1.left_stick_y; // Each is in range -100 to 100




        float stickRotation = (int) opMode.gamepad1.right_stick_x; // Stick position (Absolute heading)




        if(){

            backLeft.setPower();
            frontLeft.setPower();
            frontRight.setPower();
            backRight.setPower();

        }

        else{

        }










    }*/
}
