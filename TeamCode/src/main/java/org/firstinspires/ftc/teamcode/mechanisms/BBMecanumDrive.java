package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class BBMecanumDrive {
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private DcMotorEx frontRight;
    private Telemetry telemetry;

    // IMU for navigation
    private IMU imu;

    public void init(HardwareMap hwMap, Telemetry tele) {
        // Declare motors
        frontLeft = hwMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hwMap.get(DcMotorEx.class, "backLeft");
        backRight = hwMap.get(DcMotorEx.class, "backRight");
        frontRight = hwMap.get(DcMotorEx.class, "frontRight");

        // Establishing the direction and mode for the motors
        // Direction
        // Left motors were flipped, so the normal code also had to be flipped.
        // So we made the FL and BL motors from Reverse to Forward.
        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);

        // Mode - set this to use encoders to prevent drift
        frontLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Zero Power Mode - Brake so robot does not move when not receiving power
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class, "imu");
        // Change these orientations depending on how the Control Hub is positioned
        // on the robot.
        // CHECK: Is this set correctly when testing driving?
        RevHubOrientationOnRobot hubOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);

        imu.initialize(new IMU.Parameters(hubOrientation));

        telemetry = tele;
    }

    public void drive(double forward, double strafe, double rotate, double speedmultiplyer) {
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double backRightPower = forward + strafe - rotate;
        double frontRightPower = forward - strafe - rotate;

        // Normalizing power across motors to move evenly
        // CHECK: If robot is moving too slow, was this changed for an event?
        // Adjust speed for outreach/non-competition events
        double maxPower = 1.0;
        double maxSpeed = 1.0;

        // Get the max power from all motors and to set maxPower
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));

        // Scale power sent to motor by speed
        frontLeft.setPower(maxSpeed * (frontLeftPower / maxPower));
        backLeft.setPower(maxSpeed * (backLeftPower / maxPower));
        backRight.setPower(maxSpeed * (backRightPower / maxPower));
        frontRight.setPower(maxSpeed * (frontRightPower / maxPower));

        telemetry.addData("Left Motors",
                "FL: %.2f, BL: %.2f",
                frontLeft.getPower(), backLeft.getPower());
        telemetry.addData("Right Motors",
                "BR: %.2f, FR: %.2f",
                backRight.getPower(), frontRight.getPower());
        telemetry.addData("Left Motors Velocity",
                "FL: %.2f, BL: %.2f",
                frontLeft.getVelocity(), backLeft.getVelocity());
        telemetry.addData("Right Motors Velocity",
                "BR: %.2f, FR: %.2f",
                backRight.getVelocity(), frontRight.getVelocity());  // testing wheel velocity
    }

    public void stopDrive(){
        drive(0,0,0, 0);
    }

    // May have to change some of this math to get the robot driving correctly
    // CHECK: If robot is not driving correctly for field relative mode but driving
    //        correctly for driver mode, is the math here correct?
    public void driveFieldRelative(double forward, double strafe, double rotate, double speedmult){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe =  r * Math.cos(theta);

        telemetry.addData("Driving", "Fwd: %.2f, Stf: %.2f, Rot: %.2f",
                newForward, newStrafe, rotate);
        this.drive(newForward, newStrafe, rotate, speedmult);
    }
}