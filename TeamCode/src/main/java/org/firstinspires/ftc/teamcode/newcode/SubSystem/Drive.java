package org.firstinspires.ftc.teamcode.newcode.SubSystem;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drive {
    private DcMotor front_Right;
    private DcMotor front_Left;
    private DcMotor back_Right;
    private DcMotor back_Left;
    private HardwareMap hardwareMap;

//    public Drive(HardwareMap hardwareMap) {
//
//    }

    public Drive(){

//        this.right_front = right_front;
//        this.right_back = right_back;
//        this.left_front = left_front;
//        this.left_back = left_back;
//        this.hardwareMap = hardwareMap;
    }
    public void init(HardwareMap hardwareMap){
        front_Right = hardwareMap.get(DcMotor.class, "frontRight"); //חיבור0
        back_Right = hardwareMap.get(DcMotor.class, "backRight"); //חיבור1
        front_Left = hardwareMap.get(DcMotor.class, "frontLeft"); //חיבור2
        back_Left = hardwareMap.get(DcMotor.class, "backLeft"); //חיבור3

        back_Left.setDirection(DcMotor.Direction.FORWARD);
        back_Right.setDirection(DcMotor.Direction.REVERSE);
        front_Left.setDirection(DcMotor.Direction.FORWARD);
        front_Right.setDirection(DcMotor.Direction.REVERSE);

        back_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        back_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



    }






    public void all_drive(@NonNull double[] direction){
        front_Right.setPower(direction[0] - direction[1] - direction[2]);
        back_Right.setPower(direction[0]+ direction[1] - direction[2]);
        front_Left.setPower (direction[0] + direction[1] + direction[2]);
        back_Left.setPower (direction[0] - direction[1] + direction[2]);



    }


}
