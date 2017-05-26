package main;

import processing.core.PApplet;
import processing.serial.Serial;
import srlComm.SrlComm;

public class Main2 extends PApplet {

	M m;

	public void settings() {
		size(800, 800);
	}

	public void setup() {
		m = new M(this);
	}

	public void draw() {
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { main.Main2.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
