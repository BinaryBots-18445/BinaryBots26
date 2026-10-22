package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

/*
 * This is the driver oriented op mode for BB 2026 season using mecanum drive
 * This file should be the same as FieldOrientedOp except for the fact that
 * we use
 *   drive.drivee(forward, strafe, rotate);
 * instead of
 *   drive.driveFieldRelativ(forward, strafe, rotate);
 * */
@TeleOp
public class DriverOrientedOp extends OpMode {
    BBMecanumDrive drive = new BBMecanumDrive();
    Intake intake = new Intake();
    AprilTagWebcam aTag = new AprilTagWebcam();
    Hopper hopper = new Hopper();
    Shooter shooter = new Shooter();
    // Drivetrain variables
    double forward;
    double strafe;
    double rotate;

    // Intake variables
    boolean intakeForward = false;
    boolean intakeBackward = false;
    Intake.Direction intakeDirection = Intake.Direction.STOPPED;

    //Hopper variables
    boolean hopperForward = false;
    boolean hopperBackward = false;
    Hopper.Direction hopperDirection = Hopper.Direction.STOPPED;
    boolean shooterForward = false;
    boolean shooterBackward = false;
    Shooter.Direction shooterDirection = Shooter.Direction.STOPPED;

    // April Tag Variables


    @Override
    public void init() {
        drive.init(hardwareMap, this.telemetry);
        // Re-enable once we have a motor for the intake
        intake.init(hardwareMap);
        hopper.init(hardwareMap);
        aTag.init(hardwareMap, this.telemetry);

        telemetry.addData("Initialization", "Init complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        // CHECK: If robot is not moving correctly, check the stick inputs and scalar values
        forward = gamepad1.left_stick_y * -1; //inverted y input because fwd and bwd were reversed
        strafe = gamepad1.left_stick_x; // reset x input because left and right were being reversed
        rotate = gamepad1.right_stick_x;

        intakeForward = gamepad1.right_bumper;
        intakeBackward = gamepad1.right_trigger_pressed;

        hopperForward = gamepad1.left_trigger_pressed;
        //put this in if we need it to go backwards if we dont take it out
        hopperBackward = gamepad1.left_bumper;
        shooterForward = gamepad1.left_trigger_pressed;
        //put this in if we need it to go backwards if we dont take it out
        shooterBackward = gamepad1.left_bumper;

        drive.drive(forward, strafe, rotate);

        // Re-enable once we have a motor for the intake
        // Will need debouncing for intake functionality:
        // If we press the rb button
        if (intakeForward) {
            // And intake is not already running forwards
            if (intakeDirection != Intake.Direction.FORWARD) {
                // Start intake because we are stopped or are running backwards
                intake.runIntakeForwards();
                intakeDirection = Intake.Direction.FORWARD;
            } else { // We are already running forwards. A second press should stop the intake
                intake.stopIntake();
                intakeDirection = Intake.Direction.STOPPED;
            }
        }

        //    If we press the rt button
        if (intakeBackward) {
            // And intake is not already running backwards
            if (intakeDirection != Intake.Direction.BACKWARD) {
                // Start running intake backward
                intake.runIntakeBackwards();
                intakeDirection = Intake.Direction.BACKWARD;
            } else { // We are already running backwards. A second press should stop the intake
                intake.stopIntake();
                intakeDirection = Intake.Direction.STOPPED;
            }
        }
        // If we press the lt button
        if (shooterForward) {
            // And hopper is not already running forwards
            if (shooterDirection != shooterDirection.Direction.FORWARD) {
                // Start hopper because we are stopped or are running backwards
                shooterDirection.runshooterForwards();
                shooterDirection = shooterDirection.Direction.FORWARD;
            } else { // We are already running forwards. A second press should stop the hopper
                shooterDirection.stopshooter();
                shooterDirection = shooterDirection.Direction.STOPPED;
            }
        }
        //    If we press the lb button
        //we might not need this if we do comment everything bellow back in
        if (shooterBackward) {
            //And hopper is not already running backwards
            if (shooterDirection != Shooter.Direction.BACKWARD) {
                // Start running hopper Backward
                shooter.runshooterBackwards();
                shooterDirection = Shooter.Direction.BACKWARD;
            } else { // We are already running backwards. A second press should stop the hopper
                shooter.stopshooter();
                shooterDirection = Shooter.Direction.STOPPED;
                // If we press the lt button
                if (hopperForward) {
                    // And hopper is not already running forwards
                    if (hopperDirection != Hopper.Direction.FORWARD) {
                        // Start hopper because we are stopped or are running backwards
                        hopper.runhopperForwards();
                        hopperDirection = Hopper.Direction.FORWARD;
                    } else { // We are already running forwards. A second press should stop the hopper
                        hopper.stophopper();
                        hopperDirection = Hopper.Direction.STOPPED;
                    }
                }
                //    If we press the lb button
                //we might not need this if we do comment everything bellow back in
                if (hopperBackward) {
                    //And hopper is not already running backwards
                    if (hopperDirection != Hopper.Direction.BACKWARD) {
                        // Start running hopper Backward
                        hopper.runhopperBackwards();
                        hopperDirection = Hopper.Direction.BACKWARD;
                    } else { // We are already running backwards. A second press should stop the hopper
                        hopper.stophopper();
                        hopperDirection = Hopper.Direction.STOPPED;
                        boolean updated = telemetry.update();
                        if (!updated) {
                            telemetry.addLine().addData("Last telemetry update failed", "");
                        }

                    }
                }
            }
        }
    }
}