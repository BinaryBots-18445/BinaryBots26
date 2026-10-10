package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Turret;

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
    Turret turret = new Turret();

    // Drivetrain variables
    double forward;
    double strafe;
    double rotate;

    // Intake variables
    boolean intakeForward = false;
    boolean intakeBackward = false;
    Intake.Direction intakeDirection = Intake.Direction.STOPPED;

    @Override
    public void init() {
        drive.init(hardwareMap, this.telemetry);
        // Re-enable once we have a motor for the intake
        intake.init(hardwareMap);

        telemetry.addData("Initialization" , "Init complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        // CHECK: If robot is not moving correctly, check the stick inputs and scalar values
        forward = gamepad1.left_stick_y * -1; //inverted y input because fwd and bwd were reversed
        strafe = gamepad1.left_stick_x; // reset x input because left and right were being reversed
        rotate = gamepad1.right_stick_x;

        fire = gamepad1.left_trigger_pressed; // Turret code for shooting
        aimup  = gamepad1.dpad_up; // angles turret up
        aimdown = gamepad1.dpad_down; // angles turret down
        aimleft - gamepad1.dpad_left; // rotates turret left
        aimright = gamepad1.dpad_right; // rotates turret right


        intakeForward = gamepad1.right_trigger_pressed;
        intakeBackward = gamepad1.right_bumper;

        drive.drive(forward, strafe, rotate);

        // Re-enable once we have a motor for the intake
        // Will need debouncing for intake functionality:
        // If we press the Y button
                if(intakeForward){
                    // And intake is not already running forwards
                    if (intakeDirection != Intake.Direction.FORWARD) {
                        // Start intake because we are stopped or are running backwards
                        intake.runIntakeForwards();
                        intakeDirection = Intake.Direction.FORWARD;
                    }
                    else { // We are already running forwards. A second press should stop the intake
                        intake.stopIntake();
                        intakeDirection = Intake.Direction.STOPPED;
                    }
                }
        // If we press the A button
                if(intakeBackward){
                    // And intake is not already running backwards
                    if (intakeDirection != Intake.Direction.BACKWARD) {
                        // Start running intake backward
                        intake.runIntakeBackwards();
                        intakeDirection = Intake.Direction.BACKWARD;
                    }
                    else { // We are already running backwards. A second press should stop the intake
                        intake.stopIntake();
                        intakeDirection = Intake.Direction.STOPPED;
                    }
                }


         //Do turret stuff here
        turret.turretAngleUp(5.5);

        boolean updated = telemetry.update();
        if (!updated){
            telemetry.addLine().addData("Last telemetry update failed", "");
        }
    }
}
