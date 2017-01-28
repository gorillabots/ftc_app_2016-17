package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Owner on 1/15/2017.
 */
@Autonomous (name="BlueBeaconSlam", group="beta")
public class BlueAutoSlam extends LinearOpMode
{

    AutonomousDriveTrain driveTrain;
    ColorSensor floorColor;
    ColorSensor beaconColorL;
    ColorSensor beaconColorR;
    Servo sensorSwing;
    ModernRoboticsI2cRangeSensor range;

    public void runOpMode()
    {
        driveTrain = new AutonomousDriveTrain(); //Initialize hardware
        driveTrain.init(this);

        floorColor = hardwareMap.colorSensor.get("floorColor");
        beaconColorL = hardwareMap.colorSensor.get("beaconColor");
        beaconColorR = hardwareMap.colorSensor.get("beaconColor2");
        beaconColorL.setI2cAddress(I2cAddr.create8bit(58));
        beaconColorR.setI2cAddress(I2cAddr.create8bit(62));

        floorColor.enableLed(false);
        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        telemetry.addData("init" , "1");
        telemetry.update();
        sensorSwing = hardwareMap.servo.get("servoSwing");
        sensorSwing.setPosition(.56);
        telemetry.addData("init" , "2");
        telemetry.update();

        waitForStart(); //Initialization done!

        driveTrain.resetGyro();

        sensorSwing.setPosition(.0);
        telemetry.addData("init" , "3");
        telemetry.update();
        driveTrain.frontRightGyro(2.5, .8, 1, .1); //Go out
        driveTrain.rightGyroToTouch(.3, 1, .1); //Go to wall slowly
        sensorSwing.setPosition(.52); //Raise touch arm
        driveTrain.left(.15, .5); //Back away from wall

        floorColor.enableLed(true);
        driveTrain.backGyroToLine(floorColor, .22, 1, .05); //Go to first beacon line
        floorColor.enableLed(false);

        driveTrain.forwards(.06, .3);
        driveTrain.goToDistance(range, 10, 1, .2);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);


        //Finishing up

       driveTrain.right(.12,.25);
        driveTrain.forwards(.2, .8);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .23,1,.05);
        driveTrain.goToDistance(range,10,1,.2);


        beaconColorL.enableLed(false);
        beaconColorR.enableLed(false);
        driveTrain.beaconResponse(TeamColors.BLUE, beaconColorL, beaconColorR);





        /* OLD CODE FOLLOWS (For reference)

        //Go to first beacon
        sensorSwing.setPosition(.0);
        driveTrain.backRightGyro(1.76, .5); //Go out
        driveTrain.rightGyroToTouch(.3); //Go to wall slowly
        sensorSwing.setPosition(52
        );
        driveTrain.right(.0352, .5); //Go out
        driveTrain.turnToGyro(.3);
        //driveTrain.GyroRotation(0e, .2);
        floorColor.enableLed(true);
        driveTrain.forwardsGyroToLine(floorColor, .5);

        //beacon code //////
        driveTrain.forwardsGyro(.02, 5);
        floorColor.enableLed(false);
        sleep(200);
        driveTrain.turnToGyro(.3);
        sleep(200);
        driveTrain.left(.200, .5);
        sleep(200);
        driveTrain.right(.02, .5);
        driveTrain.turnToGyro(.3);
        driveTrain.left(.01, .5);
       // driveTrain.turnToGyro();

        sleep(500);

        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }
        driveTrain.forwards(.01, .5);
        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }
        driveTrain.back(.02, .5);
        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }
        if(ColorHelper.getBeaconColor(beaconColor).equals("red"))
        {
            driveTrain.left(.1, .5);
            driveTrain.right(.02, .5);
        }

        driveTrain.backwardsGyroToLine(floorColor, .5);

        /*driveTrain.right(.25);
        driveTrain.backwardsGyroToLine(floorColor);


        driveTrain.left(.1); //Changed


        if(ColorHelper.getBeaconColor(beaconColor) == "red"){
            driveTrain.right(.25);
            driveTrain.left(.1); //Changed
        }

        beaconColor.enableLed(false); //Go to second beacon
        floorColor.enableLed(true);

        driveTrain.back(.25);
        driveTrain.backwardsGyroToLine(floorColor);
        driveTrain.left(.02112);
        beaconColor.enableLed(true);
        driveTrain.left(.352);
        if(ColorHelper.getBeaconColor(beaconColor) == "red"){
            driveTrain.right(.095);
            driveTrain.left(.352);

        }
        driveTrain.right(.15);

        */
    }
}
