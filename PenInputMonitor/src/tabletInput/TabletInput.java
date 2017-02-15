package tabletInput;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;
import tabletInput.TabletPoint.PointType;

public class TabletInput
{
	PApplet		p5;
	Tablet		tablet;

	TabletPath	tabletPath;

	TabletInput(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);

		tabletPath = new TabletPath();
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		// if (tablet.getPenKind() == Tablet.STYLUS)
		// {
		if (_mouseEvt.getAction() == MouseEvent.PRESS)
		{
			tabletPath.addTabletPoint(tablet, _mouseEvt, PointType.HEAD);
		}
		else if (_mouseEvt.getAction() == MouseEvent.DRAG)
		{
			tabletPath.addTabletPoint(tablet, _mouseEvt, PointType.BODY);
		}
		else if (_mouseEvt.getAction() == MouseEvent.RELEASE)
		{
			tabletPath.addTabletPoint(tablet, _mouseEvt, PointType.TAIL);
		}
		// }
	}

}
