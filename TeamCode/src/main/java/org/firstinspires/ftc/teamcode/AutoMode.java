package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.mechanisms.BBMecanumDrive;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Autonomous")

public class AutoMode extends OpMode {
    private BBMecanumDrive drive = new BBMecanumDrive();
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
    public void loop() {
        if (runtime.seconds() <= 1.0) {
            drive.drive(1, 0, 0);
        } else {
            drive.drive(0,0,0);
        }

        telemetry.addData("Time:", runtime.seconds());
        telemetry.update();
    }
}
