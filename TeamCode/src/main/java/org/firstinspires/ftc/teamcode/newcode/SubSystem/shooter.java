package org.firstinspires.ftc.teamcode.newcode.SubSystem;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

public class shooter {

    private DcMotor motor1;
    private DcMotor motor2;
    private HardwareMap hardwareMap;



    public void init(HardwareMap hardwareMap){
        motor1 = hardwareMap.get(DcMotor.class, "shooter_motor1");
        motor2 = hardwareMap.get(DcMotor.class, "shooter_motor2");
        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.REVERSE);

    }



    public void start(){
        motor1.setPower(1);
        motor2.setPower(1);

    }

    public void stop(){
        motor1.setPower(0);
        motor2.setPower(0);
    }

    public void reverse()
    {
        motor1.setPower(-0.5);
        motor2.setPower(-0.5);
    }
    }
