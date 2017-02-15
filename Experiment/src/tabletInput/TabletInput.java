package tabletInput;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;

public class TabletInput
{
	PApplet			p5;
	Tablet			tablet;

	TabletPathes	tabletPathes;

	TabletInput(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);

		tabletPathes = new TabletPathes();
	}

	boolean isInputAction(int _mouseAction)
	{
		return ((_mouseAction == MouseEvent.PRESS) || (_mouseAction == MouseEvent.DRAG)
				|| (_mouseAction == MouseEvent.RELEASE));
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		// if (tablet.getPenKind() == Tablet.STYLUS)
		// {
		if (isInputAction(_mouseEvt.getAction()))
		{
			tabletPathes.addTabletPath();
			tabletPathes.addPoint(tablet, _mouseEvt);
		}
		// }
	}
}
