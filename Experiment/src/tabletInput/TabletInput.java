package tabletInput;

import java.util.Deque;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;

public class TabletInput
{
	PApplet			p5;
	Tablet			tablet;

	TabletPathes	tabletPathes;

	public TabletInput(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);

		tabletPathes = new TabletPathes();
	}

	public boolean isInputAction(int _mouseAction)
	{
		return ((_mouseAction == MouseEvent.PRESS) || (_mouseAction == MouseEvent.DRAG)
				|| (_mouseAction == MouseEvent.RELEASE));
	}

	public boolean isPressAction(int _mouseAction)
	{
		return (_mouseAction == MouseEvent.PRESS);
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		// if (tablet.getPenKind() == Tablet.STYLUS)
		// {
		if (isInputAction(_mouseEvt.getAction()))
		{
			if (isPressAction(_mouseEvt.getAction()))
				tabletPathes.addPath();
			tabletPathes.addPoint(tablet, _mouseEvt);
		}
		// }
	}

	public Deque<TabletPath> getPathes()
	{
		return tabletPathes.getPathes();
	}
}
