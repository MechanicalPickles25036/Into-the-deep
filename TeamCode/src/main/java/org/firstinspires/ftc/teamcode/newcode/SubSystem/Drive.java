package org.firstinspires.ftc.teamcode.newcode.SubSystem;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Drive {
    private DcMotor right_front;
    private DcMotor left_front;
    private DcMotor right_back;
    private DcMotor left_back;
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
        right_front = hardwareMap.get(DcMotor.class, "frontRight");
        right_back = hardwareMap.get(DcMotor.class, "backRight");
        left_front = hardwareMap.get(DcMotor.class, "frontLeft");
        left_back = hardwareMap.get(DcMotor.class, "backLeft");

        left_back.setDirection(DcMotor.Direction.FORWARD);
        right_back.setDirection(DcMotor.Direction.REVERSE);
        left_front.setDirection(DcMotor.Direction.FORWARD);
        right_front.setDirection(DcMotor.Direction.REVERSE);

        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



    }






    public void all_drive(@NonNull double[] direction){
        right_front.setPower(direction[0] - direction[1] - direction[2]);
        right_back.setPower(direction[0]+ direction[1] - direction[2]);
        left_front.setPower (direction[0] + direction[1] + direction[2]);
        left_back.setPower (direction[0] - direction[1] + direction[2]);



    }


}
