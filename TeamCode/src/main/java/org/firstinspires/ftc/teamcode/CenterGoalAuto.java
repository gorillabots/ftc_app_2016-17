package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import static org.firstinspires.ftc.teamcode.Constants.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Jarred on 12/10/2016.
 */
@Autonomous(name="center goal Auto", group="final")
public class CenterGoalAuto extends LinearOpMode {

    BallControl shooter;
    ElapsedTime timer1 = new ElapsedTime();
    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColor;
    ButtonPresserClass beacon;
    Servo button_presser_1;
    Servo button_presser_2;
    public void runOpMode(){
        driveTrain = new AutonomousDriveTrain();
        shooter = new BallControl(hardwareMap, telemetry);
        driveTrain.init(this);driveTrain = new AutonomousDriveTrain();
        beacon = new ButtonPresserClass();
        driveTrain.init(this);

        button_presser_1 = hardwareMap.servo.get("butt1");
        button_presser_2 = hardwareMap.servo.get("butt2");
        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColor = hardwareMap.colorSensor.get("beaconColor");
        floorColor.setI2cAddress(I2cAddr.create8bit(58));
        beaconColor.enableLed(false);
        floorColor.enableLed(false);
        //float mark =C1.1;
        waitForStart();

        driveTrain.forwards(.61);
        timer1.startTime();
        while(timer1.milliseconds() < 15000) {
            shooter.runFlywheel(true);
          //  shooter.runElevator(false, );
        }
        shooter.runFlywheel(false);
        //shooter.runElevator(false, false);

        driveTrain.forwards(1.0);

    }

}
