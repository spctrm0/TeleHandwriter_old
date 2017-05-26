package penHead;

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
	Serial			serial;

	public TabletInput(PApplet _p5, Serial _serial) {
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
		serial = _serial;
	}

	public void mouseEvent(MouseEvent _mouseEvt) {
		if (tablet.getPenKind() == Tablet.STYLUS) {
			if (_mouseEvt.getAction() == MouseEvent.PRESS || _mouseEvt.getAction() == MouseEvent.DRAG) {
				serial.write(serialX + "," + serialY + "," + 90 + '\r');
			} else {
				tiltX = tablet.getTiltX();
				tiltY = tablet.getTiltY();
				serialX = 180 - (int) (PApplet.degrees(tiltX) + 90);
				serialY = 180 - (int) (PApplet.degrees(tiltY) + 90);
				serial.write(serialX + "," + serialY + "," + 180 + '\r');
			}
		}
	}
}
