package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareControl;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

@Autonomous (name="HelpingAuto", group="Robot")
public class HelpingAuto extends LinearOpMode {
    HardwareControl hc;
    BBMecanumDrive drive = new BBMecanumDrive();
    Intake intake = new Intake();
    AprilTagWebcam aTag = new AprilTagWebcam();
    Hopper hopper = new Hopper();
    Shooter shooter = new Shooter();

    public void runOpMode() {
        hc = new HardwareControl(this);

        shooter.init(hardwareMap);
        hopper.init(hardwareMap);
        intake.init(hardwareMap);
        drive.init(hardwareMap, telemetry);

        waitForStart();

        boolean run_once = true;
        while(opModeIsActive()) {

            if(run_once)
            {
                shooter.runshooter(1200);
                sleep(10000);
                  for (int i = 0; i < 4; i++) {
                      for (int x = 0; x < 20; x++) {

                          // Check if speed is in the target range
                          while (shooter.motor.getVelocity() != 1200) {
                              sleep(10); // Wait
                          }

                          sleep(1);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.update();
                      }
                      intake.runIntakeForwards();
                      hopper.runhopperBackwards();
                      for (int x = 0; x < 30; x++) { // sleep 0.3 sec and measure
                          sleep(10);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.update();
                      }
                      hopper.stophopper();
                    }

                run_once = false;
           }
            intake.stopIntake();
            hopper.stophopper();
            shooter.stopshooter();


            hc.mechanumMoveRobotBackwardsInches(20);
            hc.mechanumStrafeLeftInches(60);
            break;
        }
   }
}
