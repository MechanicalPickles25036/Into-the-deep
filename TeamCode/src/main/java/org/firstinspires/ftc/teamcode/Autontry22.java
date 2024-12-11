package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.io.ObjectInputStream;

@Autonomous(name="Autontry22", group="FTC")

public class Autontry22 extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // נתונים לגודל הגלגלים
    private static final double WHEEL_DIAMETER_METERS = 0.1; // קוטר גלגל במטרים
    private static final double TARGET_DISTANCE_METERS = 3.6; // מרחק יעד במטרים
    private static final double TIME_SECONDS = 3.0; // זמן יעד בשניות
    private static final double TARGET_SPEED_MPS = TARGET_DISTANCE_METERS / TIME_SECONDS; // מהירות מטר/שניה

    @Override
    public void runOpMode() {
        // אתחול המנועים
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        // הגדרות ראשוניות
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        // חישוב היעד באנקודרים
        double wheelCircumference = WHEEL_DIAMETER_METERS * Math.PI; // היקף גלגל
        int targetPosition = (int) ((TARGET_DISTANCE_METERS / wheelCircumference) * frontLeft.getMotorType().getTicksPerRev());

        // הגדרת מיקום יעד לכל המנועים
        frontLeft.setTargetPosition(targetPosition);
        frontRight.setTargetPosition(targetPosition);
        backLeft.setTargetPosition(targetPosition);
        backRight.setTargetPosition(targetPosition);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // הגדרת מהירות לכל המנועים
        frontLeft.setPower(TARGET_SPEED_MPS);
        frontRight.setPower(TARGET_SPEED_MPS);
        backLeft.setPower(TARGET_SPEED_MPS);
        backRight.setPower(TARGET_SPEED_MPS);

        // המתנה להשלמת התנועה
        while (opModeIsActive() && frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy()) {
            telemetry.addData("Target Position", targetPosition);
            telemetry.addData("Current Position", frontLeft.getCurrentPosition());
            telemetry.update();
            // עצירת המנועים לאחר הגעה ליעד
            frontLeft.setPower(0.1);
            frontRight.setPower(0.1);
            backLeft.setPower(0.1);
            backRight.setPower(0.1);
        }
    }
}