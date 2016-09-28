package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.CompassSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.util.ElapsedTime;
/**
 * Created by Owner on 9/27/2016.
 */
        import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@Autonomous(name="Concept: Compass Calibration", group="Concept")


    public class Drivetrain extends LinearOpMode {

        DcMotor frontRight = null;
        DcMotor backRight = null;
        DcMotor frontLeft = null;
        DcMotor backLeft = null;


        @Override
        public void runOpMode () throws InterruptedException {
            float leftPower=gamepad1.left_stick_y;
            float rightPower=gamepad1.right_stick_y;

                

    }
}
