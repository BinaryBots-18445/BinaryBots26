package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Shooter {
    public DcMotorEx topWheel;
    public DcMotorEx bottomWheel;
    double p = 10; //8
    double i = 0;

    double d = 0.3;
    double f = 12.2;//11.62 // Variable for PID overshooting. 32767/maxVelocity(2820) /12.65 normal 13.85
    private double nominalVoltage = 14;
    private double topTargetVelocity = 1200;
    private double bottomTargetVelocity = 1000;
    private double lastSentF = 0;
    private double lastTargetVelocity = 0;
    private final double VoltageTolerance = 0.05;
    private VoltageSensor batterySensor;
    public enum Direction {
        FORWARD,
        BACKWARD,
        STOPPED
    }

    public void init(HardwareMap hwMap){
        batterySensor = hwMap.get(VoltageSensor.class, "Control Hub");

        topWheel = hwMap.get(DcMotorEx.class, "topTurret");
        topWheel.setDirection(DcMotorEx.Direction.FORWARD);
        topWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topWheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        bottomWheel = hwMap.get(DcMotorEx.class, "bottomTurret");
        bottomWheel.setDirection(DcMotorEx.Direction.FORWARD);
        bottomWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomWheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

    }

  /*  public void setPIDFBasedOnVoltage () {
        double currentVoltage = batterySensor.getVoltage();
        if (currentVoltage < 1) {currentVoltage = nominalVoltage;}

        double compensatedF = f *(nominalVoltage/currentVoltage);

       // motor.setVelocityPIDFCoefficients(p,i,d,compensatedF);

        if (Math.abs(compensatedF - lastSentF) > VoltageTolerance || targetVelocity != lastTargetVelocity) {
            motor.setVelocityPIDFCoefficients(p,i,d, compensatedF);

            lastSentF = compensatedF;
            lastTargetVelocity = targetVelocity;
        }
    } */
    public void stopshooter(){
        topWheel.setVelocity(0);
        bottomWheel.setVelocity(0);
    }

    public void powershooter(DcMotorEx motor) {
        motor.setPower(1);
    }

    public void runshooter(double Velocity, DcMotorEx motor) {
        if (motor == topWheel) {
            topTargetVelocity = Velocity;
            topWheel.setVelocity(-Velocity);
        } else if (motor == bottomWheel) {
            bottomTargetVelocity = Velocity;
            bottomWheel.setVelocity(-Velocity);
        }
       // setPIDFBasedOnVoltage();
       // motor.setVelocity(Velocity);
    }

    // our turret should rotate
    // rotateTurret

}
