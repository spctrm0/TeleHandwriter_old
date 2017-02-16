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
		try
		{
			tabletPoints.add(new TabletPoint(_tablet, _mouseEvt));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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

	public Deque<TabletPoint> getPoints()
	{
		return tabletPoints;
	}

	@Override
	protected void finalize()
	{
		if (tabletPoints.isEmpty())
		{
			System.out.println("Succesfully removed.");
			tabletPoints = null;
		}
		else
		{
			System.out.println("WARNING: There're instance of " + TabletPath.class.getName() + " not yet removed.");
			while (!tabletPoints.isEmpty())
				tabletPoints.remove();
			tabletPoints = null;
		}
	}
}
