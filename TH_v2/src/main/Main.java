package main;

import processing.core.PApplet;
import processing.serial.Serial;

public class Main extends PApplet {
	SrlCom		grblCom;
	SrlCom		penHeadCom;
	TabletInput	tableInput;

	public void settings() {
		fullScreen();
	}

	public void setup() {
		background(0);

		grblCom = new GrblCom(this);
		penHeadCom = new PenHeadCom(this);

		tableInput = new TabletInput(this, penHeadCom);

		grblCom.connectionLoop(-1);
		if (grblCom.isConnected())
			penHeadCom.connectionLoop(grblCom.getPortIdx());
	}

	public void draw() {
		background(255);
	}

	public void serialEvent(Serial _port) {
		char inChar_ = _port.readChar();
		if (grblCom.isMyPort(_port)) {
			grblCom.receiveAndAssembleChar(inChar_);
		} else if (penHeadCom.isMyPort(_port)) {
			penHeadCom.receiveAndAssembleChar(inChar_);
		}
	}

	static public void main(String[] _passedArgs) {
		String[] appletArgs_ = new String[] { main.Main.class.getName() };
		if (_passedArgs != null)
			PApplet.main(concat(appletArgs_, _passedArgs));
		else
			PApplet.main(appletArgs_);
	}
}
