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
	SrlCom			srlCom_;

	public TabletInput(PApplet _p5, SrlCom _srlCom) {
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
		srlCom_ = _srlCom;
	}

	public void mouseEvent(MouseEvent _mouseEvt) {
		if (srlCom_.isConnected()) {
			if (tablet.getPenKind() == Tablet.STYLUS) {
				if (_mouseEvt.getAction() == MouseEvent.PRESS || _mouseEvt.getAction() == MouseEvent.DRAG) {
					srlCom_.sendMsg(serialX + "," + serialY + "," + 90 + '\r');
				} else {
					tiltX = tablet.getTiltX();
					tiltY = tablet.getTiltY();
					serialX = 180 - (int) (PApplet.degrees(tiltX) + 90);
					serialY = 180 - (int) (PApplet.degrees(tiltY) + 90);
					srlCom_.sendMsg(serialX + "," + serialY + "," + 180 + '\r');
				}
			}
		}
		// if (srlCom_.isConnected()) {
		// if (tablet.getPenKind() == Tablet.STYLUS) {
		// tiltX = tablet.getTiltX();
		// tiltY = tablet.getTiltY();
		// serialX = 180 - (int) (PApplet.degrees(tiltX) + 90);
		// serialY = 180 - (int) (PApplet.degrees(tiltY) + 90);
		// srlCom_.sendMsg(serialX + "," + serialY + "," + 180 + '\r');
		// }
		// }
	}
}
