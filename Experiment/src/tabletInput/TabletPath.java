package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;

public class TabletPath
{
	Deque<TabletPoint> tabletPoints;

	TabletPath()
	{
		tabletPoints = new LinkedList<TabletPoint>();
	}

	void addPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		tabletPoints.add(new TabletPoint(_tablet, _mouseEvt));
	}

	boolean isComplete()
	{
		try
		{
			return tabletPoints.getLast().isTail();
		}
		catch (Exception e)
		{
			return false;
		}
	}

	@Override
	protected void finalize()
	{
		if (tabletPoints.isEmpty())
			tabletPoints = null;
	}
}
