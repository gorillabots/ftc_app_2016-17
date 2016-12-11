package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.IOException;

/**
 * Created by emper on 10/14/2016.
 */
@TeleOp(name = "Autonomous_DriveTrain", group = "Concept")
public class Autonomous_DriveTrain implements Autonomous_Drive_Train_Interface{
    HardwareMap h;
    Telemetry telemetry;
    DcMotor R;
    DcMotor L;
    DcMotor F;
    DcMotor B;
    ColorSensor cSensor;
    public Autonomous_DriveTrain(HardwareMap h, Telemetry telemetry) {
        this.h = h;
        this.telemetry = telemetry;
        R = h.dcMotor.get("right");
        L = h.dcMotor.get("left");
        F = h.dcMotor.get("front");
        B = h.dcMotor.get("back");
    }
    public void Sleep(long time){
        //Throws exception
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void goForward(double right_side, double left_side, long time) {
        R.setPower(right_side);
        L.setPower(-left_side);
        Sleep(time);
    }

    @Override
    public void goBackward(double right_side, double left_side, long time) {
        R.setPower(right_side);
        L.setPower(-left_side);
        Sleep(time);
    }

    @Override
    public void goLeft(double front_side, double back_side, long time) {
        F.setPower(front_side);
        B.setPower(-back_side);
        Sleep(time);
    }

    @Override
    public void goRight(double front_side, double back_side, long time) {
        F.setPower(-front_side);
        B.setPower(back_side);
        Sleep(time);
    }

    @Override
    public void clockwise(double all_sides, long time) {
        F.setPower(-all_sides);
        B.setPower(-all_sides);
        R.setPower(-all_sides);
        L.setPower(-all_sides);
    }

    @Override
    public void counterclockwise(double all_sides, long time) {
        F.setPower(-all_sides);
        B.setPower(-all_sides);
        R.setPower(-all_sides);
        L.setPower(-all_sides);
    }
    public void encodeBackRight(){

    }
}
