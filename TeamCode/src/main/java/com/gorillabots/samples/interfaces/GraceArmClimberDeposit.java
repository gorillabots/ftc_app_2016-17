package com.gorillabots.samples.interfaces;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Chris on 10/1/2016.
 *
 */

public class GraceArmClimberDeposit implements ClimberDepositInterface {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    public GraceArmClimberDeposit(OpMode opMode) {
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;
    }

    @Override
    public void prepareToDepositClimbers() {

    }

    @Override
    public void resetPositionToStart() {

    }

    @Override
    public void depositClimbers() {

    }

    @Override
    public void stopDepossitingClimbers() {

    }

    @Override
    public boolean isReadyToDepositClimbers() {
        return false;
    }

    @Override
    public boolean haveClimbersBeenDepositted() {
        return false;
    }
}
