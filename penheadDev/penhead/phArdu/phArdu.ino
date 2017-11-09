#include <PWM.h>

int32_t pulseFreq = 50; //frequency (in Hz)
float pulseCycle = 1000000 / pulseFreq;
int pulseWidthMin = 750;
int pulseWidthMax = 2250;
int angleMin = 0;
int angleMax = 180;

boolean flag_hiRes = true;
int pinAxisX = 9;
int pinAxisY = 10;


int convertAngleToPulseWidthRatio(float _angle) {
  return map(map(_angle, (float) angleMin, (float) angleMax, (float) pulseWidthMin, (float) pulseWidthMax), 0, pulseCycle, 0, flag_hiRes ? 65536 : 255);
}
float pulseWidthRatioX = convertAngleToPulseWidthRatio(90);
float pulseWidthRatioY = convertAngleToPulseWidthRatio(90);

boolean firstContact = false;
String inputString = "";
boolean stringComplete = false;

void setup() {
  InitTimersSafe();
  delay(100);

  bool successX = SetPinFrequencySafe(pinAxisX, pulseFreq);
  delay(100);

  bool successY = SetPinFrequencySafe(pinAxisY, pulseFreq);
  delay(100);

  if (successX && successY) {
    pinMode(13, OUTPUT);
    digitalWrite(13, HIGH);
  }

  inputString.reserve(200);
  Serial.begin(115200);
  while (!Serial) {
    ;
  }
  establishContact();
}

void loop() {
  if (stringComplete) {
    Serial.println(inputString);
    String angleX = inputString.substring(0, inputString.indexOf(","));
    String angleY = inputString.substring(inputString.indexOf(",") + 1);
    pulseWidthRatioX = convertAngleToPulseWidthRatio(constrain(angleX.toFloat(), angleMin, angleMax));
    pulseWidthRatioY = convertAngleToPulseWidthRatio(constrain(angleY.toFloat(), angleMin, angleMax));
    inputString = "";
    stringComplete = false;
  }
  if (flag_hiRes) {
    pwmWriteHR(pinAxisX, pulseWidthRatioX);
  } else {
    pwmWrite(pinAxisX, pulseWidthRatioX);
  }
  delay(30);

  if (flag_hiRes) {
    pwmWriteHR(pinAxisY, pulseWidthRatioY);
  } else {
    pwmWrite(pinAxisY, pulseWidthRatioY);
  }
  delay(30);
}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char) Serial.read();
    if (inChar != '\n') {
      inputString += inChar;
    } else {
      if (!firstContact) {
        if (inputString.equalsIgnoreCase("processing")) {
          inputString = "";
          firstContact = true;
        }
      } else {
        stringComplete = true;
      }
    }
  }
}

void establishContact() {
  while (Serial.available() <= 0) {
    Serial.println("arduino");
    delay(300);
  }
}
