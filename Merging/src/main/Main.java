package main;

import grblComm.GrblComm;
import processing.core.PApplet;
import processing.serial.Serial;
import srlComm.SrlComm;

public class Main extends PApplet {

	SrlComm		srlComm;
	GrblComm	grblComm;

	public void settings() {
		size(500, 500);
	}

	public void grblCommThreadLoop() {
		while (true)
			grblComm.threadLoop();
	}

	public void srlCommThreadLoop() {
		while (true)
			srlComm.threadLoop();
	}

	public void setup() {
		srlComm = new SrlComm(this);
		grblComm = new GrblComm();
		srlComm.addListner(grblComm);
		grblComm.addListner(srlComm);

		thread("grblCommThreadLoop");
		thread("srlCommThreadLoop");
	}

	public void draw() {
		background(255);
		fill(0);
		noStroke();
		ellipse(mouseX, mouseY, 10, 10);
		// text(srlComm.portIdx, width / 2.0f, height / 2.0f);
		text(srlComm.isConnected() ? "T" : "F", width / 2.0f, height / 2.0f);
	}

	public void serialEvent(Serial _serialEvt) {
		char replyChar_ = _serialEvt.readChar();
		srlComm.receiveCharAndAssembleMsg(replyChar_);
	}

	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { main.Main.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
