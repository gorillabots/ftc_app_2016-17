package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.UltrasonicSensor;


/**
 * Created by Jarred  on 1/17/17.
 */

public class rangeHelp{

    public double range(UltrasonicSensor range){
        return range.getUltrasonicLevel();

    }



}
