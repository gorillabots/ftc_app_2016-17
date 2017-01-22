package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by emper on 1/13/2017.
 */

@Autonomous(name="RangeTest", group="test")
public class RangeTest extends LinearOpMode{
    AutonomousDriveTrain drive;
    UltrasonicSensor range;
    public void runOpMode() throws InterruptedException{
        range = hardwareMap.ultrasonicSensor.get("range");
        drive = new AutonomousDriveTrain();
        drive.init(this);
        while(range.getUltrasonicLevel() > 25){
            drive.right_continuous(0.5);
        }
        if(range.getUltrasonicLevel() < 25){
          //  drive.stop(5000);
        }
    }

    }