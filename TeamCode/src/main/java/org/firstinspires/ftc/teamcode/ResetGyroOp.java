package org.firstinspires.ftc.teamcode;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by mikko on 4/14/17.
 */

@Autonomous(name="GyroResetOp", group="Test")
public class ResetGyroOp extends LinearOpMode
{
    private final int NAVX_DIM_I2C_PORT = 5;
    private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;

    AHRS navx;

    public void runOpMode()
    {
        navx = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                NAVX_DIM_I2C_PORT,
                AHRS.DeviceDataType.kProcessedData,
                NAVX_DEVICE_UPDATE_RATE_HZ);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        navx.zeroYaw();

        telemetry.addData("Status", "Calibrating...");
        telemetry.update();

        while(navx.isCalibrating())
        {
            sleep(50);
        }

        telemetry.addData("Status", "Calibrated!!!");
        telemetry.update();

        navx.close();
    }
}
