package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Mikko on 9/27/2016.
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
    ModernRoboticsI2cGyro gyro;

    public Drivetrain(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        //Initialize Motors
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");

        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
    }

    /**
     *
      * @param stickX direction to move relative to the field (X)
     * @param stickY direction to move relative to the field (Y)
     * @param stickRot how much to turn the robot
     * @param heading how much to robot has rotated from initial value
     */
    public void oneStickLoop(float stickX, float stickY, float stickRot, int heading, boolean dummy) //TODO: Fix multiple possible memory leaks
    {
        double holder = 1;

        if(dummy == true && holder == 1)
        {
            holder =.5;
        }
        else if(dummy == true && holder == .5)
        {
            holder = 1;
        }

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

        double backLeftPower = limitToOne(-headX + stickRot);
        double frontRightPower = limitToOne(headX + stickRot);
        double backRightPower = limitToOne(headY + stickRot);
        double frontLeftPower = limitToOne(-headY + stickRot);

        backLeft.setPower((backLeftPower)*holder);
        frontRight.setPower((frontRightPower)*holder);
        backRight.setPower((backRightPower)*holder);
        frontLeft.setPower((frontLeftPower)*holder);
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

    public void resetGyro(boolean action)
    {
        if(action == true)
        {
            gyro.resetZAxisIntegrator();
        }
    }
}

