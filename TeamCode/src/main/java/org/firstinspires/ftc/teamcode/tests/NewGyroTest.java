package org.firstinspires.ftc.teamcode.tests;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

//Created by Mikko on 4/2/2017

@Autonomous(name="NewGyroTest", group="Test")
public class NewGyroTest extends OpMode
{
    private final int NAVX_DIM_I2C_PORT = 0;
    private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;

    AHRS navx;

    public void init()
    {
        navx = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
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

        navx.zeroYaw();
    }

    float ax, ay, az;
    float rx, ry, rz;
    float head;
    float fusedHead;

    boolean isMoving;
    boolean isRotating;

    public void loop()
    {
        if(gamepad1.x)
        {
                navx.zeroYaw();
        }

        ax = navx.getWorldLinearAccelX();
        ay = navx.getWorldLinearAccelY();
        az = navx.getWorldLinearAccelZ();
        rx = navx.getRoll();
        ry = navx.getPitch();
        rz = navx.getYaw();
        head = navx.getCompassHeading();
        fusedHead = navx.getFusedHeading();
        isMoving = navx.isMoving();
        isRotating = navx.isRotating();

        telemetry.addData("Acceleration X", ax);
        telemetry.addData("Acceleration Y", ay);
        telemetry.addData("Acceleration Z", az);
        telemetry.addData("Rotation X", rx);
        telemetry.addData("Rotation Y", ry);
        telemetry.addData("Rotation Z", rz);
        telemetry.addData("Compass Heading", head);
        telemetry.addData("Fused Heading", fusedHead);
        telemetry.addData("Is Moving", isMoving);
        telemetry.addData("Is Rotating", isRotating);
        telemetry.update();
    }
}
