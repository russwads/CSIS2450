#include <NewPing.h>
#include <Servo.h>

#define trig_pin A1 //analog input 1 for ultrasonic trigger
#define echo_pin A2 //analog input 2 for ultrasonic echo
#define maximum_distance 200

NewPing pupils(trig_pin, echo_pin, maximum_distance);
Servo eyes;

const int enA = 11;
const int enB = 10;
const int in1 = 5; // motor A forward
const int in2 = 6; // motor A reverse
const int in3 = 7; // motor B forward
const int in4 = 8; // motor B reverse

int distance = 0; // forward distance
boolean isForward = false;

void setup() {
  // Attaches power to hardware through Arduino pins
  eyes.attach(9);
  eyes.write(90);
  pinMode(enA, OUTPUT);
  pinMode(enB, OUTPUT);
  pinMode(in1, OUTPUT);
  pinMode(in2, OUTPUT);
  pinMode(in3, OUTPUT);
  pinMode(in4, OUTPUT);

  // At setup, all motors are stopped
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);
  digitalWrite(in3, LOW);
  digitalWrite(in4, LOW);
}

void loop() {
  int d1 = 0; // distance to left
  int d2 = 0; // distance to right
  if (distance <= 25 && distance != 0) {
    // move ROAM away from the obstacle
    motorStop();
    delay(1000);
    motorBackward();
    delay(500);
    motorStop();
    delay(300);
    // begin looking left and right sequence
    d1 = lookLeft();
    delay(300);
    d2 = lookRight();
    delay(300);
    // left distance is greater than or equal to right
    if (d2 >= d1) {
      motorRight();
      motorStop();
    }
    else {
      motorLeft();
      motorStop();
    }
  }
  // no delay due to indefinite forward
  else {
    motorForward();
  }
  // read distance
  distance = int(eyesSee());
}

int lookLeft() {
  eyes.write(180);
  delay(500);
  int d = int(eyesSee());
  delay(100);
  eyes.write(90);
  return d;
}

int lookRight() {
  eyes.write(0);
  delay(500);
  int d = int(eyesSee());
  delay(100);
  eyes.write(90);
  return d;
}

/**
 * Stops all motors
 */
void motorStop() {
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);
  digitalWrite(in3, LOW);
  digitalWrite(in4, LOW);
}

/**
 * Turns the wheels to move ROAM forward
 */
void motorForward() {
  // Set motors to maximum speed
  // PWM value ranges from 0 to 255
  analogWrite(enA, 255);
  analogWrite(enB, 255);

  if (!isForward) {
    // Turn on motor A & B forward
    isForward = true;
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);
    digitalWrite(in3, HIGH);
    digitalWrite(in4, LOW);
  }
}

/**
 * Turns the wheels to move ROAM backward
 */
void motorBackward() {
  // Set motors to maximum speed
  // PWM value ranges from 0 to 255
  analogWrite(enA, 255);
  analogWrite(enB, 255);

  isForward = false;
  // Turn on motor A & B reverse
  digitalWrite(in1, LOW);
  digitalWrite(in2, HIGH);
  digitalWrite(in3, LOW);
  digitalWrite(in4, HIGH);
}

/**
 * Turns the wheels to move ROAM so he faces left
 */
void motorRight() {
  // Set motors to maximum speed
  // PWM value ranges from 0 to 255
  analogWrite(enA, 255);
  analogWrite(enB, 255);

  // Turn on motor A & B to go left
  digitalWrite(in1, HIGH);
  digitalWrite(in2, LOW);
  digitalWrite(in3, LOW);
  digitalWrite(in4, HIGH);
  delay(500);
  digitalWrite(in1, HIGH);
  digitalWrite(in2, LOW);
  digitalWrite(in3, HIGH);
  digitalWrite(in4, LOW);
}

/**
 * Turns the wheels to move ROAM so he faces right
 */
void motorLeft() {
  // Set motors to maximum speed
  // PWM value ranges from 0 to 255
  analogWrite(enA, 255);
  analogWrite(enB, 255);

  // Turn on motor A & B to go right
  digitalWrite(in1, LOW);
  digitalWrite(in2, HIGH);
  digitalWrite(in3, HIGH);
  digitalWrite(in4, LOW);
  delay(500);
  digitalWrite(in1, HIGH);
  digitalWrite(in2, LOW);
  digitalWrite(in3, HIGH);
  digitalWrite(in4, LOW);
}

/**
 * Controls the ultrasonic sensor ping mechanism
 */
unsigned long eyesSee() {
 // check distance in centimeters
 int cm = pupils.ping_cm();
 // ping_cm() returns 0 if there is no ping echo within the max distance
 if (cm == 0) {
  cm = maximum_distance;
 }
 return cm;
}
