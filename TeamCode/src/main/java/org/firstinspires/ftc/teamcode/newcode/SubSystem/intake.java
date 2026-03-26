package org.firstinspires.ftc.teamcode.newcode.SubSystem;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
public class intake {
    private DcMotor motor2;
    private DcMotor motor;

    private HardwareMap hardwareMap;


    public void init(HardwareMap hardwareMap){
        motor = hardwareMap.get(DcMotor.class, "intake_motor1");
        motor.setDirection(DcMotor.Direction.REVERSE);
//        motor2 = hardwareMap.get(DcMotor.class, "intake_motor2");
//        motor2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void start_in(){
        motor.setPower(-1);
//        motor2.setPower(1);
    }
    public void start_out(){
        motor.setPower(1);
//        motor2.setPower(-0.65);
    }
    public void stop(){
        motor.setPower(0);
//        motor2.setPower(0);
    }




}
