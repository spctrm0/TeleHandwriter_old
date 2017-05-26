package penHead;

import processing.core.PApplet;
import processing.serial.Serial;

public class Main extends PApplet {
	Serial		serial;
	int			portIdx	= 1;
	TabletInput	tabletInput;

	public void settings() {
		// size(1920 / 2, 1080 / 2);
		fullScreen();
	}

	public void setup() {
		for (String portName_ : Serial.list())
			println(portName_);
		serial = new Serial(this, Serial.list()[portIdx], 115200);
		serial.bufferUntil('\r');

		tabletInput = new TabletInput(this, serial);

		background(255);
	}

	public void draw() {
		background(255);

		pushMatrix();
		translate(width / 4.0f, height / 2.0f);
		rotate(tabletInput.tiltX);
		line(0, 0, 0, -100);
		popMatrix();

		pushMatrix();
		translate(3.0f * width / 4.0f, height / 2.0f);
		rotate(tabletInput.tiltY);
		line(0, 0, 100, 0);
		popMatrix();
	}

	public void serialEvent(Serial _serial) {
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { penHead.Main.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
