package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "all28", group = "TeleOp")
public class horim extends OpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotorEx motor1, motor2, motor3,motor4;
    private Servo servoMotor;
    private ColorSensor colorSensor;
    private TouchSensor touch1, touch2;
    int hit1,hit2;
    public static PIDCoefficients armCoefficients = new PIDCoefficients(0.7, 0, 0);
    private double kP = 0.0005;
    private double kG = 4;
    double armVert = 0;
    double armRad = 1100;
    int count = 1;
    boolean servo_pos;
    private String color = "";
    private int currentServoPositionIndex = 0;
    private final double[] Positions = {-100, 0, 200, 350, 640,800}; // פוזיציות למנוע הסרוו
    private boolean lastLeftBumper = false, lastRightBumper = false, lastDpad = false;
    boolean wasButtonAPressed = false;
    private boolean lock = false;
    private static final int MAX_ENCODER_TICKS = 3500; // מקסימום ערכי אינקודר (לדוגמה)
    private static final int MIN_ENCODER_TICKS = -5000; // מינימום ערכים

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        motor1 = hardwareMap.get(DcMotorEx.class, "armMotor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "armMotor2");
        motor3 = hardwareMap.get(DcMotorEx.class, "armMotor3");
        motor4 = hardwareMap.get(DcMotorEx.class, "armMotor4");
        servoMotor = hardwareMap.get(Servo.class, "leftServo"); // ודא שהשם תואם ל-Config
        servoMotor.setPosition(1);
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        touch1 = hardwareMap.get(TouchSensor.class,"button1");
        touch2 = hardwareMap.get(TouchSensor.class,"button2");
        resetArm();

        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
    }


    private void resetArm() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    } //איפוס מנועים

    public void setPowerV(double target) {
        motor2.setPower(kP * 5 * (target - motor2.getCurrentPosition()));
    }

    public void setPowerR(double target) {
        motor1.setPower((10 * kP + kG * 1.9) * (target - (1000 + motor1.getCurrentPosition())));
        // motor3.setPower(motor1.getPower());
    }


    @Override
    public void loop() {
        kG = Math.cos((3 * Math.PI * (2000 + motor1.getCurrentPosition())) / 3895.9) * (((Math.abs(motor2.getCurrentPosition())) * 29.5) / (484.5 * 3 * Math.PI * 3500));
        double y = -gamepad1.left_stick_y * Math.abs(gamepad1.left_stick_y); // Inverted because y is negative when pushed forward
        double x = gamepad1.left_stick_x * Math.abs(gamepad1.left_stick_x);
        double rx = gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x);

        double frontLeftPower = y + x + rx;
        double frontRightPower = y - x - rx;
        double backLeftPower = y - x + rx;
        double backRightPower = y + x - rx;


        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        double hend = Math.pow(gamepad2.left_stick_y,3);


        if (gamepad2.b){
            hit1 = motor3.getCurrentPosition();
            hit2 = motor4.getCurrentPosition();
            if(!touch1.isPressed()){
                motor3.setPower(-0.5);
            }
            else{
                motor3.setPower(0);
                motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            }
            if(!touch2.isPressed()){
                motor4.setPower(-0.5);
            }
            else{
                motor4.setPower(0);
                motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            }

        }
        else {
            if (gamepad2.left_stick_y < 0.1 & gamepad2.left_stick_y > -0.1) {
                motor3.setPower(kP * 10* (hit1-motor3.getCurrentPosition()));
                motor4.setPower(kP * 10* (hit1-motor4.getCurrentPosition()));
            }
            else{
                motor3.setPower(hend);
                motor4.setPower(hend);
                hit1 = motor3.getCurrentPosition();
                //hit2 = motor4.getCurrentPosition();
            }
        }
        boolean leftBumper = gamepad2.left_bumper;
        boolean rightBumper = gamepad2.right_bumper;

        // שליטה על הזרוע באמצעות כפתורים
        if (gamepad2.left_bumper & !lastLeftBumper) {
            lastLeftBumper = true;
        } else if (!gamepad2.left_bumper & lastLeftBumper) {
            lastLeftBumper = false;
            count = count + 1;
            if (count > 5) {
                count = 5;
            }
            armRad = 1000 - Positions[count];
        }
        setPowerR(armRad);

        if (gamepad2.right_bumper & !lastRightBumper) {
            lastRightBumper = true;
        } else if (!gamepad2.right_bumper & lastRightBumper) {
            lastRightBumper = false;
            count = count - 1;
            if (count < 0) {
                count = 0;
            }
            armRad = 1000 - Positions[count];
        }
        setPowerR(armRad);

        if (gamepad2.y) {
            servoMotor.setPosition(0.9);
        } // servo open/close
        if (gamepad2.x) {
            servoMotor.setPosition(0.19);
        }
        if (gamepad2.a){
            motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


        if (gamepad2.dpad_up & !lastDpad) {
            lastDpad = true;
        } else if (!gamepad2.dpad_up & lastDpad) {
            lastDpad = false;
            lock = !lock; // שינוי מצב נעילה של המנועים
        }

        if (!lock) {

            if (gamepad2.right_stick_y < 0.1 & gamepad2.right_stick_y > -0.1) {
                setPowerV(armVert);
                telemetry.addData("on", "");
            } else {
                if ((Math.cos((2 * Math.PI * (1000 + motor1.getCurrentPosition())) / 3895.9) * (((Math.abs(motor2.getCurrentPosition())) * 19.5) / (384.5 * 2 * Math.PI ))) > 18){
                    motor2.setPower(gamepad2.right_stick_y* Math.abs(gamepad2.right_stick_y)+1);
                    armVert = motor2.getCurrentPosition();
                }
                else {
                    motor2.setPower(gamepad2.right_stick_y * Math.abs(gamepad2.right_stick_y));
                    armVert = motor2.getCurrentPosition();
                }
            }
        } else if (lock) { //נעילת מנוע בלתייה
            motor2.setPower(0.9);
        }
/*
        // אם המסילה נעולה, המנוע לא יפעל
        if (lock) {
            motor2.setPower(0); // עצירת המנוע כשנעול
        } else {
            motor2.setPower(gamepad2.right_stick_y); // הפעלת המנוע כאשר לא נעול
            armVert = motor2.getCurrentPosition();
        }*/

        // זיהוי צבע
        if (colorSensor.blue() > 100 && colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
            color = "BLUE";
        } else if (colorSensor.red() > 100 && colorSensor.red() > colorSensor.blue() && colorSensor.red() > colorSensor.green()) {
            color = "RED";
        } else if (colorSensor.green() > 100 && colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
            color = "GREEN";
        } else {
            color = "UNKNOWN";
        } //color sensor

        // telemetry.addData("Detected Color", color);
        telemetry.addData("",Math.cos((2 * Math.PI * (1000 + motor1.getCurrentPosition())) / 3895.9) * (((Math.abs(motor2.getCurrentPosition())) * 19.5) / (384.5 * 2 * Math.PI )));
        telemetry.addData("h1",hit1);
        // telemetry.addData("h2",hit2);
        telemetry.addData("Arm power", motor2.getPower());
        //  telemetry.addData("Arm Motor 1 Position", motor1.getCurrentPosition());
        // telemetry.addData("Arm Motor 2 Position", motor2.getCurrentPosition());
        //telemetry.addData("press a", count);
        telemetry.addData("m3", motor3.getPower());
        // telemetry.addData("m4", kP * 5 * (hit2-motor4.getCurrentPosition()));
        telemetry.addData("armRad", armRad);
        telemetry.addData("armVert", armVert);
        telemetry.addData("last", lastDpad);
        telemetry.addData("motor 3", motor3.getCurrentPosition());
        // telemetry.addData("motor 4", motor4.getCurrentPosition());
        telemetry.update();
    }
}
