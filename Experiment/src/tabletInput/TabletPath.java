package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;

public class TabletPath
{
	private Deque<TabletPoint> tabletPoints;

	public TabletPath()
	{
		tabletPoints = new LinkedList<TabletPoint>();
	}

	public boolean isCoincideWithLastPoint(MouseEvent _mouseEvt)
	{
		boolean result_ = false;
		try
		{
			if (!tabletPoints.isEmpty())
				result_ = tabletPoints.getLast().getTime_ms() == _mouseEvt.getMillis();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result_;
	}

	public void addPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		try
		{
			if (isCoincideWithLastPoint(_mouseEvt))
				tabletPoints.getLast().setPoint(_tablet, _mouseEvt);
			else
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
	protected void finalize() throws Throwable
	{
		super.finalize();
		if (tabletPoints.isEmpty())
			tabletPoints = null;
		else
		{
			System.out.println(
					"WARNING: There're instance of " + TabletPath.class.getName() + "that are not yet removed.");
			while (!tabletPoints.isEmpty())
				tabletPoints.remove();
			tabletPoints = null;
		}
	}
}
