package tabletInput;

import java.util.Deque;

import codeanticode.tablet.Tablet;
import processing.core.*;
import processing.event.*;

public class TabletInput
{
	private PApplet			p5;
	private Tablet			tablet;
	public TabletInput(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		if (tablet.getPenKind() == Tablet.STYLUS)
		{

		}
	}
}
