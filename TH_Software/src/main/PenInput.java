package main;

import codeanticode.tablet.Tablet;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class PenInput
{
	private PApplet	p5;
	private Tablet	tablet;
	private boolean	isWritable	= true;
	private Pathes	pathes;

	PenInput(PApplet _p5, Pathes _pathes)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
		pathes = _pathes;
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		if (isWritable)
			if (_mouseEvt.getAction() == MouseEvent.PRESS)
				pathes.addPath((int) tablet.getPenX(), (int) tablet.getPenY(), tablet.getPressure(), System.nanoTime());
			else if (_mouseEvt.getAction() == MouseEvent.DRAG)
				pathes.addPoint((int) tablet.getPenX(), (int) tablet.getPenY(), tablet.getPressure(), System.nanoTime(),
						false);
			else if (_mouseEvt.getAction() == MouseEvent.RELEASE)
			{
				pathes.addPoint((int) tablet.getPenX(), (int) tablet.getPenY(), tablet.getPressure(), System.nanoTime(),
						true);
				System.out.println("getPathesNum(): " + pathes.getPathesNum());
			}
	}
}