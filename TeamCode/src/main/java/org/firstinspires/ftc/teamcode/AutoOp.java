package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by emper on 10/18/2016.
 */

public class AutoOp extends OpMode {
    Autonomous_DriveTrain auto;
    @Override
    public void init() {
        auto = new Autonomous_DriveTrain(hardwareMap, telemetry);
    }
    @Override
    public void loop() {
        auto.goForward(100, 100, 1000);
        auto.goBackward(100, 100, 1000);
        auto.goLeft(100, 100, 1000);
        auto.goRight(100, 100, 1000);
        auto.clockwise(100, 1000);
        auto.counterclockwise(100, 1000);
    }
}
