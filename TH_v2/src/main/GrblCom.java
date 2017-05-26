package main;

import processing.core.PApplet;

public class GrblCom extends SrlCom {
	public GrblCom(PApplet _p5) {
		super(_p5);
		setExpectedResponseMsg("Grbl 1.1e ['$' for help]");
	}
}