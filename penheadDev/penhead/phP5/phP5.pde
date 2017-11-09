import processing.serial.*;

Serial myPort;
boolean firstContact = false;
StringBuilder sb;

void setup() {
  size(256, 256);  // Stage size

  printArray(Serial.list());
  String portName = Serial.list()[1];
  myPort = new Serial(this, portName, 115200);
  sb = new StringBuilder();
}

void draw() {
  if (firstContact) {
    if (mousePressed) {
      sb.append(map(mouseX, 0, width, 0, 180));
      sb.append(",");
      sb.append(map(mouseY, 0, height, 180, 0));
      sb.append("\n");
      myPort.write(sb.toString());
      sb.setLength(0);
    }
  }
  background(firstContact? 0 : 127);
  fill(255);
  ellipse(mouseX, mouseY, 20, 20);
}

void mousePressed() {
}
void mouseDragged() {
}
void mouseReleased() {
}

void serialEvent(Serial myPort) {
  String myString = myPort.readStringUntil('\n');
  myString = trim(myString);
  if (myString != null) {
    if (!firstContact) {
      if (myString.equalsIgnoreCase("arduino")) {
        myPort.clear();
        firstContact = true;
        myPort.write("processing\n");
      }
    } else {
      println(myString);
    }
  }
}