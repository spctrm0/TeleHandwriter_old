package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;

public class TabletPathes
{
	Deque<TabletPath> tabletPathes;

	public TabletPathes()
	{
		tabletPathes = new LinkedList<TabletPath>();
	}

	public void addPath()
	{
		tabletPathes.add(new TabletPath());
	}

	public void addPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		try
		{
			tabletPathes.getLast().addPoint(_tablet, _mouseEvt);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Deque<TabletPath> getPathes()
	{
		return tabletPathes;
	}
}
