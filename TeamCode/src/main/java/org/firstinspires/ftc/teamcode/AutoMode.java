package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.mechanisms.HardwareControl;

@Autonomous(name = "Autonomous")
@Disabled

public class AutoMode extends OpMode {
    private BBMecanumDrive drive = new BBMecanumDrive();
    HardwareControl hc;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        drive.init(hardwareMap, telemetry);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        runtime.reset();
    }
    @Override
    // the robot moves at ~2fps
    public void loop() {
        if (runtime.seconds() <= 2.5) {
            drive.drive(1, 0, 0,1);
        } else if (runtime.seconds() <= 3.5) {
            drive.drive(0,1, 0,1);
        } else {
            drive.stopDrive();
        }
     //   hc.mechanumMoveRobotForwardInches(56);

        telemetry.addData("Time:", runtime.seconds());
        telemetry.update();
    }
}
