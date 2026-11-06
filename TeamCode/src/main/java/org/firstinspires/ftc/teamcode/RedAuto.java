package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;
import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareControl;
import org.firstinspires.ftc.teamcode.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

@Autonomous (name="RedAuto", group="Robot")
public class RedAuto extends LinearOpMode {
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

                  for (int i = 0; i < 4; i++) {
                      for (int x = 0; x < 50; x++) {

                          // Check if speed is in the target range
                          while (shooter.motor.getVelocity() < 1180 || shooter.motor.getVelocity() > 1200) {
                              sleep(10); // Wait
                          }

                          sleep(1);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.update();
                      }
                      intake.runIntakeForwards();
                      hopper.runhopperBackwards();
                      for (int x = 0; x < 300; x++) { // sleep 0.3 sec and measure
                          sleep(1);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.update();
                      }
                      hopper.stophopper();
                     /* for (int x = 0; x < 50; x++) { // sleep 0.25 sec and measure
                          sleep(5);
                          telemetry.addData("Shooter Velocity", shooter.motor.getVelocity());
                          telemetry.update();
                      }*/
                    }

                run_once = false;
           }
            intake.stopIntake();
            hopper.stophopper();
            shooter.stopshooter();


            hc.mechanumMoveRobotBackwardsInches(100);
            hc.mechanumStrafeLeftInches(-61);
            break;
        }
   }
}
