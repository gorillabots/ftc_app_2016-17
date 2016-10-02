package com.gorillabots.samples.interfaces;

/**
 * Created by Chris on 10/1/2016.
 *
 */

public interface DrivetrainInterface {
      //public DrivetrainInterface(HardwareMap hardwareMap, Telemetry telemetry);

      void goForward(Double speed);
      void goBackward(Double speed);
      void rotate(Double angle);
      void stop();
      boolean isFrontTouchingWall();
      boolean isBeaconDetected();
      boolean isOverWhiteLine();
      boolean isOverCenterLine();
      double getRearDistanceFromWall();
      double getFrontDistanceFromWall();
      double getGyroHeader();
}
