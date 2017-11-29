package main;

import java.util.Calendar;

import processing.core.PApplet;

public class TH_RAMPS extends PApplet {

	SerialComm serialComm;

	public void settings() {
		size(500, 500);
	}

	public void setup() {
		serialComm = new SerialComm(this);
	}

	public void draw() {
		line(pmouseX, pmouseY, mouseX, mouseY);
	}

	String timestamp() {
		Calendar now = Calendar.getInstance();
		return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
	}

	static public void main(String[] _passedArgs) {
		String[] appletArgs_ = new String[] { main.TH_RAMPS.class.getName() };
		if (_passedArgs != null)
			PApplet.main(concat(appletArgs_, _passedArgs));
		else
			PApplet.main(appletArgs_);
	}
}