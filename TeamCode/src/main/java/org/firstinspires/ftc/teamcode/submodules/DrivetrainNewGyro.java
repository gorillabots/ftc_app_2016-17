package org.firstinspires.ftc.teamcode.submodules;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Mikko on 04/04/2017
 */


/**
 * Related Links
 *		http://www.simbotics.org/resources/mobility/omnidirectional-drive
 *		http://www.vexforum.com/index.php/12370-holonomic-drives-2-0-a-video-tutorial-by-cody/0
 */

public class DrivetrainNewGyro
{
    HardwareMap hardwareMap;
    Telemetry telemetry;

    private final int NAVX_DIM_I2C_PORT = 0;
    private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;

    AHRS navx;

    DcMotor frontRight;
    DcMotor backRight;
    DcMotor frontLeft;
    DcMotor backLeft;

    public DrivetrainNewGyro(HardwareMap hardwareMap, Telemetry telemetry)
    {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        navx = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("gyrobox"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                NAVX_DEVICE_UPDATE_RATE_HZ);

        while(navx.isCalibrating())
        {
            try
            {
                Thread.sleep(5);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        //Initialize Motors
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
    }

    //TODO: Remove possible memory leaks
    public void oneStickLoop(float stickX, float stickY, float stickRot) //TODO: Fix multiple possible memory leaks
    {
        float heading = navx.getYaw();

        float facingDeg = -45 - heading; //Robot's rotation
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

    public void resetGyro()
    {
        navx.zeroYaw();
    }

    public void addTelemetry(){

    telemetry.addData("FR power",frontRight.getPower());
    telemetry.addData("FL power",frontLeft.getPower());
    telemetry.addData("BR power",backRight.getPower());
    telemetry.addData("BL power",backLeft.getPower());
    telemetry.addData("Yaw",navx.getYaw());


    }

    public void stop()
    {
        navx.close();
    }
}

