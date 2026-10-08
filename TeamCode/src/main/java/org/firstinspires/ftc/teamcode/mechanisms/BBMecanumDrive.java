package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class BBMecanumDrive {
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontRight;
    private Telemetry telemetry;

    // IMU for navigation
    private IMU imu;

    public void init(HardwareMap hwMap, Telemetry tele) {
        // Declare motors
        frontLeft = hwMap.get(DcMotor.class, "frontLeft");
        backLeft = hwMap.get(DcMotor.class, "backLeft");
        backRight = hwMap.get(DcMotor.class, "backRight");
        frontRight = hwMap.get(DcMotor.class, "frontRight");

        // Establishing the direction and mode for the motors
        // Left motors were flipped, so we made the FL and BL motors from Reverse to Forward.
        // CHECK: Wheels are rotating correctly when testing and not fighting each other
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        // Mode - set this to use encoders to prevent drift
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Zero Power Mode - Brake so robot does not move when not receiving power
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

    public void drive(double forward, double strafe, double rotate) {
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
    }

    // May have to change some of this math to get the robot driving correctly
    // CHECK: If robot is not driving correctly for field relative mode but driving
    //        correctly for driver mode, is the math here correct?
    public void driveFieldRelative(double forward, double strafe, double rotate){
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(strafe, forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newStrafe =  r * Math.cos(theta);

        telemetry.addData("Driving", "Fwd: %.2f, Stf: %.2f, Rot: %.2f",
                newForward, newStrafe, rotate);
        this.drive(newForward, newStrafe, rotate);
    }
}