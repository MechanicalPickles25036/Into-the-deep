/*import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.objdetect.Board;{const { Board, Motors, Sensor, Servo } = abstract void require("johnny-five");
const board = new

autonmi23() {

}

{console.

abstract log("Board is ready!") {

}

// הגדרת מנועים
  const motors = new Motors([
{ pins: { pwm: 3, dir: 12 }, invertPWM: true },
        { pins: { pwm: 5, dir: 13 }, invertPWM: true },
        { pins: { pwm: 6, dir: 14 }, invertPWM: true },
        { pins: { pwm: 9, dir: 15 }, invertPWM: true },
        ]);

        // הגדרת חיישן צבע
        const colorSensor = new Sensor({ pin: "A0", freq: 250 });

        // הגדרת מנוע זרוע לאיסוף קובייה
        const servo = new Servo(10);

// פונקציות תנועה בסיסיות
function moveForward(speed = 255) {
    motors.forward(speed);
    console.log("Moving forward");
}

function stop() {
    motors.stop();
    console.log("Motors stopped");
}

function turnRight(speed = 200) {
    motors[0].reverse(speed);
    motors[1].forward(speed);
    motors[2].reverse(speed);
    motors[3].forward(speed);
    console.log("Turning right");
}

// פונקציית זיהוי צבע
function isCubeColor(value) {
    return value > 100 && value < 200; // התאמה לצבע הקובייה
}

// פונקציית איסוף קובייה
function pickUpCube() {
    console.log("Picking up cube...");
    servo.to(90); // הורדת הזרוע
    setTimeout(() => servo.to(0), 2000); // החזרת הזרוע אחרי 2 שניות
}

// פונקציית ניווט לסל
function navigateToBasket() {
    console.log("Navigating to basket...");
    moveForward(200);
    setTimeout(() => turnRight(200), 2000); // פנייה אחרי 2 שניות
    setTimeout(() => moveForward(200), 3000); // נסיעה נוספת
}

// פונקציית הנחת קובייה בסל
function placeCubeInBasket() {
    console.log("Placing cube in basket...");
    servo.to(90); // שחרור הזרוע
    setTimeout(() => servo.to(0), 2000); // חזרה למצב מנוחה
}

// תהליך אוטונומי מלא
function autonomousSequence() {
    console.log("Starting autonomous sequence...");
    moveForward(200);

    colorSensor.on("data", function () {
        if (isCubeColor(this.value)) {
            console.log("Cube detected!");
            stop();
            pickUpCube();
            setTimeout(() => {
                    navigateToBasket();
            setTimeout(() => placeCubeInBasket(), 5000);
        }, 5000);
        }
    });
}

// הפעלת התהליך האוטונומי
autonomousSequence();
});
     */