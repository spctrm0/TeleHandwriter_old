package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;
import tabletInput.TabletPoint.PointType;

public class TabletPath
{
	Queue<TabletPoint> tabletPoints;

	TabletPath()
	{
		tabletPoints = new LinkedList<TabletPoint>();
	}

	void addTabletPoint(Tablet _tablet, MouseEvent _mouseEvt, PointType _type)
	{
		System.out
				.println(tabletPoints.size() + " , " + tabletPoints.offer(new TabletPoint(_tablet, _mouseEvt, _type)));
	}
	
	void 
}
