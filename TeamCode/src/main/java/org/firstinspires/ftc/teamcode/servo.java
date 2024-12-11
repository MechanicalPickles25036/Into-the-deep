package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Control", group="TeleOp")
public class servo extends OpMode {

    // הצהרת משתנה לסרוו
    private Servo servoMotor;

    @Override
    public void init() {
        // הגדרת הסרוו (יש להחליף את השם לשם הסרוו בממשק הקונטרול)
        servoMotor = hardwareMap.get(Servo.class, "leftServo");  // שנה את "servoName" לשם האמיתי
    }

    @Override
    public void loop() {
        // קבלת ערך ה-Y מהסטיק הימני של Gamepad 2
        double rightStickY = -gamepad2.right_stick_y;  // הפיכת הערך לשליטה נכונה

        // מיפוי ערכי הסטיק (בין -1 ל-1) לערכי הסרוו (בין 0 ל-1)
        double servoPosition = (rightStickY + 1) / 2;  // ממיר את הטווח של -1 עד 1 ל-0 עד 1

        // שליחת הערך לסרוו
        servoMotor.setPosition(servoPosition);

        // הדפסת ערכים למסך (אופציונלי לעזרה בדיבוג)
        telemetry.addData("Servo Position", servoPosition);
        telemetry.addData("Right Stick Y", rightStickY);
        telemetry.update();
    }
}
