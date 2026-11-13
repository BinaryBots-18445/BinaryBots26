package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class Shooter {
    public DcMotorEx motor;
    double p = 5; //8
    double i = 0;

    double d = 0.1;
    double f = 12.2;//11.62 // Variable for PID overshooting. 32767/maxVelocity(2820) /12.65 normal 13.85
    private double nominalVoltage = 14;
    private double targetVelocity = 1200;
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
        motor = hwMap.get(DcMotorEx.class, "shooterMotor");
        motor.setDirection(DcMotorEx.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }

    public void setPIDFBasedOnVoltage () {
        double currentVoltage = batterySensor.getVoltage();
        if (currentVoltage < 1) {currentVoltage = nominalVoltage;}

        double compensatedF = f *(nominalVoltage/currentVoltage);

       // motor.setVelocityPIDFCoefficients(p,i,d,compensatedF);

        if (Math.abs(compensatedF - lastSentF) > VoltageTolerance || targetVelocity != lastTargetVelocity) {
            motor.setVelocityPIDFCoefficients(p,i,d, compensatedF);

            lastSentF = compensatedF;
            lastTargetVelocity = targetVelocity;
        }
    }
    public void stopshooter(){
        motor.setVelocity(0);
    }

    public void powershooter() {motor.setPower(1);}

    public void runshooter(double Velocity) {
        targetVelocity = Velocity;
        setPIDFBasedOnVoltage();
        motor.setVelocity(Velocity);
    }

    // our turret should rotate
    // rotateTurret

}
