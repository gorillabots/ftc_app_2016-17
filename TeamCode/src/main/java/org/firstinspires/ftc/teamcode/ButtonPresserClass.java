package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

/**
 * Created by emper on 9/30/2016.
 */
@TeleOp(name = "ButtonPresserZ", group = "Concept")
public class ButtonPresserClass extends LinearOpMode implements ButtonPresserInterface {
    Servo button_presser_1;
    Servo button_presser_2;
    //ColorSensor color;
    //TouchSensor touch;
    HardwareMap hardware;
    //Telemetry telemetry;
    public double position1 = 0.0;
    public void Press_Button(Servo servo, double position){

    }
    public void Was_Button_Pressed(){

    }
    public boolean Is_Left_Side_Teamcolor(){
        return true;
    }
    public boolean Is_Right_Side_Teamcolor(){
        return true;
    }
    public void Get_Left_Color(){

    }
    public void Get_Right_Color(){

    }
    public boolean Light_Sensor(){
        return true;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        button_presser_1 = hardwareMap.servo.get("actuator1");
        button_presser_2 = hardwareMap.servo.get("actuator2");

        //color = hardwareMap.colorSensor.get("color");
//        touch = hardwareMap.touchSensor.get("touch");
        waitForStart();
        while(opModeIsActive()){
            button_presser_1.setDirection(Servo.Direction.FORWARD);
            double position = 0.1;
            for(int i = 2; i < 10; i++){
                button_presser_1.setPosition(position);
                position = position + 0.1;
                Thread.sleep(5000);
            }

      /*  telemetry.addData("is pressed", position1);
        if(button_presser_1.getDirection() != null){
            telemetry.addData("direction is", button_presser_1.getDirection().toString());
        }
        else{
            telemetry.addData("direction is", "this is null");
        }
        telemetry.update();
*/

        }
    }
}
