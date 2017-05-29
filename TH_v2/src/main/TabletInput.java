package main;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;
import processing.serial.Serial;

public class TabletInput {
	private PApplet	p5;
	private Tablet	tablet;

	int				tiltX	= 90;
	int				tiltY	= 90;

	Pathes			pathes;

	GrblCom			grblCom;
	PenHeadCom		penHeadCom;

	public TabletInput(PApplet _p5, Pathes _pathes, GrblCom _grblCom, PenHeadCom _penHeadCom) {
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
		pathes = _pathes;
		grblCom = _grblCom;
		penHeadCom = _penHeadCom;
	}

	public void mouseEvent(MouseEvent _mouseEvt) {
		if (grblCom.isConnected() && penHeadCom.isConnected) {
			if (tablet.getPenKind() == Tablet.STYLUS) {
				if (_mouseEvt.getAction() == MouseEvent.PRESS || _mouseEvt.getAction() == MouseEvent.DRAG) {
					if (_mouseEvt.getAction() == MouseEvent.PRESS) {
						pathes.addPath(_mouseEvt.getX(), _mouseEvt.getY(), tablet.getPressure(), _mouseEvt.getMillis());
					} else {
						pathes.addPoint(_mouseEvt.getX(), _mouseEvt.getY(), tablet.getPressure(),
								_mouseEvt.getMillis());
					}
					penHeadCom.sendMsg(tiltX + "," + tiltY + "," + 0 + '\r');
				} else {
					if (_mouseEvt.getAction() == MouseEvent.RELEASE) {
						pathes.completePath(_mouseEvt.getX(), _mouseEvt.getY(), tablet.getPressure(),
								_mouseEvt.getMillis());
					}
					float inTiltX_ = tablet.getTiltX();
					float inTiltY_ = tablet.getTiltY();
					tiltX = 180 - (int) (PApplet.degrees(inTiltX_) + 90);
					tiltY = 180 - (int) (PApplet.degrees(inTiltY_) + 90);
					penHeadCom.sendMsg(tiltX + "," + tiltY + "," + 180 + '\r');
				}
			}
		}
	}
}
