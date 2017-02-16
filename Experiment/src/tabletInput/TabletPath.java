package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;

public class TabletPath
{
	Deque<TabletPoint> tabletPoints;

	public TabletPath()
	{
		tabletPoints = new LinkedList<TabletPoint>();
	}

	public void addPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		tabletPoints.add(new TabletPoint(_tablet, _mouseEvt));
	}

	public boolean isComplete()
	{
		try
		{
			return tabletPoints.getLast().isTail();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void finalize()
	{
		if (tabletPoints.isEmpty())
			tabletPoints = null;
		else
		{
			while (!tabletPoints.isEmpty())
				tabletPoints.remove();
			tabletPoints = null;
		}
	}
}
