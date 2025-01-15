package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="auto", group="FTC")
public class auto extends OpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    String color = "";
    private Servo servoMotor;
    private DcMotorEx motor1;
    private DcMotorEx motor2;
    public static PIDCoefficients armCoefficients = new PIDCoefficients(0.7, 0, 0);
    double armRad = 0;
    double armVert = 0;
    ColorSensor colorSensor;
    double kP = 0.00003;
    double tik_to_diss = (48*537.7)/2*3.1415;


    @Override
    public void init(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        motor1 = hardwareMap.get(DcMotorEx.class, "armMotor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "armMotor2");

        motor1.getCurrent(CurrentUnit.MILLIAMPS);

        servoMotor = hardwareMap.get(Servo.class, "leftServo");
        resetBody();

        resetArm();

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);

        backRight.getCurrentPosition();
    }

    private void resetArm() {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    private void distans(double target){
        resetBody();
        while(target > backLeft.getCurrentPosition()*tik_to_diss){
            backLeft.setPower(kP * (target - backLeft.getCurrentPosition() * tik_to_diss));
            backRight.setPower(kP * (target - backRight.getCurrentPosition() * tik_to_diss));
            frontRight.setPower(kP * (target - backLeft.getCurrentPosition() * tik_to_diss));
            frontLeft.setPower(kP * (target - backRight.getCurrentPosition() * tik_to_diss));
            //motor2.setPower(motor1.getPower());
        }

    }

    void resetBody() {
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }



    @Override
    public void loop() {
      stop();
    }
}