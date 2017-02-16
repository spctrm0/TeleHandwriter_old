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

	enum TabletAction
	{
		PRESS, RELEASE, CLICK, DRAG, MOVE, ENTER, EXIT, WHEEL;

		static TabletAction convertP5MouseAction(int _p5MouseAction)
		{
			return TabletAction.values()[_p5MouseAction - 1];
		}
	}

	public TabletInput(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);

		tabletPathes = new TabletPathes();
	}

	public boolean isInputAction(TabletAction _mouseAction)
	{
		return ((_mouseAction == TabletAction.PRESS) || (_mouseAction == TabletAction.DRAG)
				|| (_mouseAction == TabletAction.RELEASE));
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		// if (tablet.getPenKind() == Tablet.STYLUS)
		// {
		TabletAction mouseAction_ = TabletAction.convertP5MouseAction(_mouseEvt.getAction());
		if (isInputAction(mouseAction_))
		{
			if (mouseAction_ == TabletAction.PRESS)
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
