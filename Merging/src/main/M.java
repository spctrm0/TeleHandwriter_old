package main;

import codeanticode.tablet.Tablet;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class M {
	private Tablet	tablet;

	private PApplet	p5;

	float[]			aziAndAlt	= { 0, 0 };

	public M(PApplet _p5) {
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
	}

	public void mouseEvent(MouseEvent _mouseEvt) {
		if (tablet.getPenKind() == Tablet.STYLUS) {
			// System.out.println(tablet.getAltitude());
			// System.out.println(tablet.getRotation());
//			tablet.getAzimuthXAndAltitude(aziAndAlt);
//			System.out.println(aziAndAlt[0] + "," + aziAndAlt[1]);
//			p5.background(255);
//			p5.pushMatrix();
//			p5.translate(p5.width/2.0f, p5.height/2.0f);
//			p5.ellipse(0,0, 200, 200);
//			p5.rotate(aziAndAlt[0]);
//			p5.line(0, 0, 100, 0);
//			p5.popMatrix();
			p5.background(255);
			p5.pushMatrix();
			p5.translate(p5.width/2.0f, p5.height/2.0f);
			p5.rotate(tablet.getTiltX());
			p5.line(0, 0, 100, 0);
			p5.popMatrix();
		}
	}
}
