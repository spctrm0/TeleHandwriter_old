package main;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;
import processing.serial.Serial;

public class TabletInput {
	private PApplet	p5;
	private Tablet	tablet;

	public float	tiltX;
	public float	tiltY;
	int				serialX	= 90;
	int				serialY	= 90;

	Pathes			pathes;

	GrblCom			grblCom;
	PenHeadCom		penHeadCom;

	public TabletInput(PApplet _p5, GrblCom _grblCom, PenHeadCom _penHeadCom) {
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
		pathes = new Pathes();
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
					penHeadCom.sendMsg(serialX + "," + serialY + "," + 0 + '\r');
				} else {
					if (_mouseEvt.getAction() == MouseEvent.RELEASE) {
						pathes.completePath(_mouseEvt.getX(), _mouseEvt.getY(), tablet.getPressure(),
								_mouseEvt.getMillis());
					}
					tiltX = tablet.getTiltX();
					tiltY = tablet.getTiltY();
					serialX = 180 - (int) (PApplet.degrees(tiltX) + 90);
					serialY = 180 - (int) (PApplet.degrees(tiltY) + 90);
					penHeadCom.sendMsg(serialX + "," + serialY + "," + 180 + '\r');
				}
			}
		}
		// if (grblCom.isConnected() && penHeadCom.isConnected) {
		// if (tablet.getPenKind() == Tablet.STYLUS) {
		// if (_mouseEvt.getAction() == MouseEvent.PRESS ||
		// _mouseEvt.getAction() == MouseEvent.DRAG) {
		// if (_mouseEvt.getAction() == MouseEvent.PRESS) {
		// pathes.addPath(_mouseEvt.getX(), _mouseEvt.getY(),
		// tablet.getPressure(), _mouseEvt.getMillis());
		// } else {
		// pathes.addPoint(_mouseEvt.getX(), _mouseEvt.getY(),
		// tablet.getPressure(),
		// _mouseEvt.getMillis());
		// }
		// penHeadCom.sendMsg(45 + "," + 75 + "," + 0 + '\r');
		// } else {
		// if (_mouseEvt.getAction() == MouseEvent.RELEASE) {
		// pathes.finishPath(_mouseEvt.getX(), _mouseEvt.getY(),
		// tablet.getPressure(),
		// _mouseEvt.getMillis());
		// }
		// penHeadCom.sendMsg(45 + "," + 75 + "," + 180 + '\r');
		// }
		// }
		// }
	}
}
