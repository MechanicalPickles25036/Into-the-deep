package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "autonomi", group = "FTC")
public class autonomi extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private Servo servoMotor;
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    double kP = 0.15;
    double armTargetPos =0;
    double autodrivepos =0;
    double diss = 537.7/29.84;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        motor1 = hardwareMap.dcMotor.get("armMotor1");
        motor2 = hardwareMap.dcMotor.get("armMotor2");
        motor3 = hardwareMap.dcMotor.get("armMotor3");

        servoMotor = hardwareMap.get(Servo.class, "leftServo");

        resetArm();
        reserBody();

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        /*
        backRight.getCurrentPosition();
        backLeft.getCurrentPosition();
        frontRight.getCurrentPosition();
        frontLeft.getCurrentPosition();
        motor1.getCurrentPosition();
        motor2.getCurrentPosition();*/
    }
    public void setPower(double target) {
        motor1.setPower(kP * (target - motor1.getCurrentPosition()));
        motor2.setPower(kP * (target - motor2.getCurrentPosition()));
        //motor2.setPower(motor1.getPower());
    }
    public void distance(double target) {
        backLeft.setPower(kP * (target -backLeft.getCurrentPosition()));
        backRight.setPower(kP * (target - backRight.getCurrentPosition()));
        //motor2.setPower(motor1.getPower());
    }
    private void resetArm() {

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    private void reserBody(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    @Override
    public void loop() {
    if (count == 0){
        distance(100);
        

    }


        telemetry.addData("frontLeftPs", backRight.getCurrentPosition());
        telemetry.addData("frontRightPs", backLeft.getCurrentPosition());
        telemetry.addData("backLeftPs", frontRight.getCurrentPosition());
        telemetry.addData("backRightPs", frontLeft.getCurrentPosition());
        // telemetry.addData("Servo Position", servoPosition);
        // telemetry.addData("Right Stick Y", rightStickY);
        //  telemetry.addData("Motor Power", power);
        telemetry.addData("position1", motor1.getCurrentPosition());
        telemetry.addData("position2", motor2.getCurrentPosition());
        telemetry.update();
    }}