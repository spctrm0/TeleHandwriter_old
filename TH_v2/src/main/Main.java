package main;

import processing.core.PApplet;
import processing.serial.Serial;

public class Main extends PApplet {
	SrlCom[]				srlComm;				// 0: grblCom, 1:
													// penHeadCom;
	GrblCom					grblCom;
	PenHeadCom				penHeadCom;

	Pathes					pathes;

	TabletInput				tabletInput;

	PathToGCodeConverter	pathToGCodeConverter;

	public void settings() {
		size(1280, 720);
	}

	public void setup() {
		background(0);

		srlComm = new SrlCom[2];
		srlComm[0] = new GrblCom(this);
		srlComm[1] = new PenHeadCom(this);
		grblCom = (GrblCom) srlComm[0];
		penHeadCom = (PenHeadCom) srlComm[1];

		pathes = new Pathes();

		tabletInput = new TabletInput(this, pathes, grblCom, penHeadCom);

		pathToGCodeConverter = new PathToGCodeConverter(pathes, grblCom);

		srlComm[0].attemptConnectionLoop(-1);
		if (srlComm[0].isConnected())
			srlComm[1].attemptConnectionLoop(srlComm[0].getPortIdx());

		thread("grblComThread");
		thread("pathToGCodeThread");
	}

	public void grblComThread() {
		while (true) {
			grblCom.sendMsgThread();
		}
	}

	public void pathToGCodeThread() {
		while (true) {
			pathToGCodeConverter.convertThread();
		}
	}

	public void draw() {
		background(255);
		fill(0);
		text(pathes.getPathesNum(), width / 2.0f, height / 2.0f);
		text(pathToGCodeConverter.getConvertedPathesNum() + ", " + pathToGCodeConverter.getConvertedPointsNum(),
				width / 2.0f, height / 2.0f + 20);
	}

	public void keyPressed() {
		if (key == 'a') {
			grblCom.sendGrblMsg("G94" + '\r');
			grblCom.sendGrblMsg("G92X0Y0Z0" + '\r');
		}
	}

	public void serialEvent(Serial _port) {
		char inChar_ = _port.readChar();
		for (SrlCom srlcom_ : srlComm)
			if (srlcom_.isMyPort(_port))
				srlcom_.assembleCharAndDeliverMsg(inChar_);
	}

	static public void main(String[] _passedArgs) {
		String[] appletArgs_ = new String[] { main.Main.class.getName() };
		if (_passedArgs != null)
			PApplet.main(concat(appletArgs_, _passedArgs));
		else
			PApplet.main(appletArgs_);
	}
}
