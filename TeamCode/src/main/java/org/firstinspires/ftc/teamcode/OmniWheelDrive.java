package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "all", group = "TeleOp")
public class OmniWheelDrive extends OpMode {
    String color = "";
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private Servo servoMotor;
    private DcMotorEx motor1;
    private DcMotorEx motor2;
    public static PIDCoefficients armCoefficients = new PIDCoefficients(0.1, 0, 0);
    PIDFController armController = new PIDFController(armCoefficients);
    double armTargetPos = 0;
    double autodrivepos = 0;
    ColorSensor colorSensor;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        colorSensor = hardwareMap.get(ColorSensor.class, "color");
        motor1 = hardwareMap.get(DcMotorEx.class, "armMotor1");
        motor2 = hardwareMap.get(DcMotorEx.class, "armMotor2");

        motor1.getCurrent(CurrentUnit.MILLIAMPS);

        servoMotor = hardwareMap.get(Servo.class, "leftServo");
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

    public void setPower(double target) {
        armController.setTargetPosition(armTargetPos);
        motor1.setPower(armController.getTargetVelocity());
//        motor2.setPower(kP * (target - motor1.getCurrentPosition()));
        motor2.setPower(motor1.getPower());
    }

    public void Autodrive(double target) {
//        backLeft.setPower(kP * (target - backLeft.getCurrentPosition()));
//        backRight.setPower(kP * (target - backRight.getCurrentPosition()));
//        motor2.setPower(motor1.getPower());
    }


    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Inverted because y is negative when pushed forward
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


        double rightStickY = gamepad2.right_stick_y;  // הפיכת הערך לשליטה נכונה
        double servoPosition = (rightStickY + 1) / 2;  // ממיר את הטווח של -1 עד 1 ל-0 עד 1
        servoMotor.setPosition(servoPosition);

        if (gamepad2.y) {
            armTargetPos = 100;
        }
        //double power = -gamepad2.left_stick_y;
        //motor1.setPower(power);
        //motor2.setPower(power);
        if (gamepad2.left_stick_y < 0.1 && gamepad2.left_stick_y > -0.1) {
            setPower(armTargetPos);
        } else {
            motor1.setPower(-gamepad2.left_stick_y);
            motor2.setPower(-gamepad2.left_stick_y);
            armTargetPos = motor1.getCurrentPosition();
        }

        if (colorSensor.blue() > 100 && colorSensor.blue() > colorSensor.red()) {
            color = "BLUE";
        } else if (colorSensor.red() > 100 && colorSensor.red() > colorSensor.blue()) {
            color = "red";
        } else if (colorSensor.green() > 100 && colorSensor.green() > colorSensor.red()) {
            color = "yellow";
        } else {
            color = "don't know?";

        }
        telemetry.addData("frontLeftPower", frontLeftPower);
        telemetry.addData("frontRightPower", frontRightPower);
        telemetry.addData("backRightPower", backRightPower);
        telemetry.addData("Servo Position", servoPosition);
        //  telemetry.addData("Right Stick Y", rightStickY);
        telemetry.addData("Arm power", motor1.getPower());
        telemetry.addData("COLOR: ", color);
        telemetry.addData("COLOR: R", colorSensor.red());
        telemetry.addData("COLOR: G", colorSensor.green());
        telemetry.addData("COLOR: B", colorSensor.blue());
        //   telemetry.addData("Motor Power", power);
        telemetry.addData("position1", motor1.getCurrentPosition());
        telemetry.addData("position2", motor2.getCurrentPosition());
        telemetry.update();
    }


}
