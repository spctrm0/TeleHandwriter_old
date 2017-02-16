package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;

public class TabletPathes
{
	Deque<TabletPath> tabletPathes;

	TabletPathes()
	{
		tabletPathes = new LinkedList<TabletPath>();
	}

	void addTabletPath()
	{
		tabletPathes.add(new TabletPath());
	}

	void addPoint(Tablet _tablet, MouseEvent _mouseEvt)
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
}
