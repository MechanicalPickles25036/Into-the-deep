package org.firstinspires.ftc.teamcode.newcode.SubSystem;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
public class intake {

    private DcMotor motor;

    private HardwareMap hardwareMap;


    public void init(HardwareMap hardwareMap){
        motor = hardwareMap.get(DcMotor.class, "intake_motor");

    }

    public void start(){
        motor.setPower(1);
    }

    public void stop(){
        motor.setPower(0);

    }
}



