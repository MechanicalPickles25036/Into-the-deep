package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="auto", group="FTC")
public class auto extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // הגדרות
    private static final double WHEEL_RADIUS_CM = 5; // רדיוס הגלגל בס"מ
    private static final double WHEEL_CIRCUMFERENCE = 30 * Math.PI * WHEEL_RADIUS_CM; // היקף הגלגל בס"מ

    // מהירויות סיבוב מנוע
    private static final double HIGH_RPM = 12000; // סיבובים לשנייה (המהירות הגבוהה)
    private static final double LOW_RPM = 9500;  // סיבובים לשנייה (המהירות הנמוכה)

    // הגדרת מרחקים בס"מ
    private static final double DISTANCE_300_CM = 300; // מרחק האטה
    private static final double DISTANCE_360_CM = 360; // מרחק עצירה

    @Override
    public void runOpMode() {
        // אתחול המנועים
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // הגדרת כיווני המנועים
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // המתנה לתחילת המשחק
        waitForStart();

        // חישוב הזמן הדרוש לנסיעה במהירות גבוהה עד למרחק 300 ס"מ (במילישניות)
        long timeTo300cm = (long) ((DISTANCE_300_CM / (HIGH_RPM * WHEEL_CIRCUMFERENCE)) * 30000);

        // חישוב הזמן הדרוש לנסיעה במהירות נמוכה עד 360 ס"מ
        long timeTo360cm = (long) (((DISTANCE_360_CM - DISTANCE_300_CM) / (LOW_RPM * WHEEL_CIRCUMFERENCE)) * 26000);

        if (opModeIsActive()) {
            // נסיעה במהירות גבוהה עד 300 ס"מ
            driveForward(HIGH_RPM);
            sleep(timeTo300cm);

            // האטה למהירות נמוכה עד 360 ס"מ
            driveForward(LOW_RPM);
            sleep(timeTo360cm);

            // עצירה
            stopMotors();
        }
    }

    // פונקציה לתנועה קדימה לפי מהירות סיבוב
    private void driveForward(double rpm) {
        double power = rpm /12000; // התאמת מהירות לפי כוח מנוע יחסי
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    // פונקציה לעצירת המנועים
    private void stopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
