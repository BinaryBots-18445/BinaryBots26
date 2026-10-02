package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;

/*
* This is the field oriented op mode for BB 2026 season using mecanum drive
* This file should be the same as DriverOrientedOp except for the fact that
* we use
*   drive.driveFieldRelative(forward, strafe, rotate);
* instead of
*   drive.drive(forward, strafe, rotate);
 * */
@TeleOp
public class FieldOrientedOp extends OpMode {
    BBMecanumDrive drive = new BBMecanumDrive();
    //Intake intake = new Intake();

    // Drivetrain variables
    double forward;
    double strafe;
    double rotate;

    // Intake variables
    boolean intakeForward = false;
    boolean intakeBackward = false;
    //Intake.Direction intakeDirection = Intake.Direction.STOPPED;

    @Override
    public void init() {
        drive.init(hardwareMap, this.telemetry);
        // Re-enable once we have a motor for the intake
        //intake.init(hardwareMap);

        telemetry.addData("Initialization" , "Init complete");
        telemetry.update();
    }

    @Override
    public void loop() {
        // CHECK: If robot is not moving correctly, check the stick inputs and scalar values
        forward = gamepad1.left_stick_x * -1;
        strafe = gamepad1.left_stick_y; // Inverted x input because left and right were being reversed
        rotate = gamepad1.right_stick_x;

        intakeForward = gamepad1.y;
        intakeBackward = gamepad1.a;

        drive.driveFieldRelative(forward, strafe, rotate);

        // Re-enable once we have a motor for the intake
        // Will need debouncing for intake functionality:
        // If we press the Y button
        //        if(intakeForward){
        //            // And intake is not already running forwards
        //            if (intakeDirection != Intake.Direction.FORWARD) {
        //                // Start intake because we are stopped or are running backwards
        //                intake.runIntakeForwards();
        //                intakeDirection = Intake.Direction.FORWARD;
        //            }
        //            else { // We are already running forwards. A second press should stop the intake
        //                intake.stopIntake();
        //                intakeDirection = Intake.Direction.STOPPED;
        //            }
        //        }
        // If we press the A button
        //        if(intakeBackward){
        //            // And intake is not already running backwards
        //            if (intakeDirection != Intake.Direction.BACKWARD) {
        //                // Start running intake backward
        //                intake.runIntakeBackwards();
        //                intakeDirection = Intake.Direction.BACKWARD;
        //            }
        //            else { // We are already running backwards. A second press should stop the intake
        //                intake.stopIntake();
        //                intakeDirection = Intake.Direction.STOPPED;
        //            }
        //        }
        boolean updated = telemetry.update();
        if (!updated){
            telemetry.addLine().addData("Last telemetry update failed", "");
        }
    }
}
