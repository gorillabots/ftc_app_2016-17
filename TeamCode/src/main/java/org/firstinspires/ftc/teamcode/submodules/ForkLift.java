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

    public ForkLift(HardwareMap hardwareMap)
    {
        this.hardwareMap = hardwareMap;
        lift = hardwareMap.dcMotor.get("lift");
    }

    public void lift(double power)
    {
        lift.setPower(power);
    }

    public void stop()
    {
        lift.setPower(0);
    }
}





