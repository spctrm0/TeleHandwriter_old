package tabletInput;

import java.util.Deque;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;

public class TabletInput
{
	private PApplet			p5;
	private Tablet			tablet;

	private TabletPathes	tabletPathes;

	public enum TabletAction
	{
		PRESS, RELEASE, CLICK, DRAG, MOVE, ENTER, EXIT, WHEEL;

		static TabletAction convertP5MouseAction(int _p5MouseAction)
		{
			return TabletAction.values()[_p5MouseAction - 1];
		}
	}

	public TabletInput(PApplet _p5, TabletPathes _tabletPathes)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);

		tabletPathes = _tabletPathes;
	}

	public boolean isInputAction(TabletAction _mouseAction)
	{
		return ((_mouseAction == TabletAction.PRESS) || (_mouseAction == TabletAction.DRAG)
				|| (_mouseAction == TabletAction.RELEASE));
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		if (tablet.getPenKind() == Tablet.STYLUS)
		{
			TabletAction tabletAction_ = TabletAction.convertP5MouseAction(_mouseEvt.getAction());
			if (isInputAction(tabletAction_))
			{
				if (tabletAction_ == TabletAction.PRESS)
					tabletPathes.addPath();
				tabletPathes.addPoint(tablet, _mouseEvt);
			}
		}
	}

	public Deque<TabletPath> getPathes()
	{
		return tabletPathes.getPathes();
	}
}
