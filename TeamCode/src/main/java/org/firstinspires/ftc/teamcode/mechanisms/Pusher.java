package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pusher {
private Servo Pusher;
boolean position = true;

public void init(HardwareMap hwMap){
    Pusher = hwMap.get(Servo.class, "pusher");
}

public void changePostion(){
    if (position) {
        Pusher.setPosition(0.5);
    } else {
        Pusher.setPosition(0);
    }
    position = !position;
}
}
