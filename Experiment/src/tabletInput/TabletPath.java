package tabletInput;

import java.util.*;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;
import tabletInput.TabletInput.TabletAction;
import tabletInput.TabletPoint.PointType;

public class TabletPath
{
	private Deque<TabletPoint> tabletPoints;

	public TabletPath()
	{
		tabletPoints = new LinkedList<TabletPoint>();
	}

	public boolean isSameTypeWithLastPoint(MouseEvent _mouseEvt)
	{
		boolean result_ = false;
		try
		{
			if (!tabletPoints.isEmpty())
			{
				TabletAction tabletAction_ = TabletAction.convertP5MouseAction(_mouseEvt.getAction());
				PointType thisPointType_ = PointType.getPointTypeFromTabletAction(tabletAction_);
				PointType lastPointType_ = tabletPoints.getLast().getType();
				result_ = (thisPointType_ == lastPointType_);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result_;
	}

	public boolean isCoincideWithLastPoint(MouseEvent _mouseEvt)
	{
		boolean result_ = false;
		try
		{
			if (isSameTypeWithLastPoint(_mouseEvt))
				result_ = tabletPoints.getLast().getTime_ms() == _mouseEvt.getMillis();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result_;
	}

	public boolean isSameXYWithLastPoint(MouseEvent _mouseEvt)
	{
		boolean result_ = false;
		try
		{
			if (isSameTypeWithLastPoint(_mouseEvt))
				result_ = ((tabletPoints.getLast().getX() == (int) _mouseEvt.getX())
						&& (tabletPoints.getLast().getY() == (int) _mouseEvt.getY()));
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
			if (isCoincideWithLastPoint(_mouseEvt) || isSameXYWithLastPoint(_mouseEvt))
			{
				System.out.println(isSameXYWithLastPoint(_mouseEvt));
				tabletPoints.getLast().setPoint(_tablet, _mouseEvt);
			}
			else
				tabletPoints.add(
						new TabletPoint(_tablet, _mouseEvt, tabletPoints.isEmpty() ? null : tabletPoints.getLast()));
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
