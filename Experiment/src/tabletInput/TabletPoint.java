package tabletInput;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;
import tabletInput.TabletInput.TabletAction;

public class TabletPoint
{
	int		x, y;
	float	pressure;
	long	timeInMillis;

	enum PointType
	{
		HEAD, BODY, TAIL;
	}

	PointType type;

	public void setXY(int _x, int _y)
	{
		x = _x;
		y = _y;
	}

	public void setPressure(float _pressure)
	{
		pressure = _pressure;
	}

	public void setTimeInMillis(long _timeInMillis)
	{
		timeInMillis = _timeInMillis;
	}

	public void setTypeByTabletAction(TabletAction _mouseAction)
	{
		switch (_mouseAction)
		{
			case PRESS:
				type = PointType.HEAD;
				break;
			case DRAG:
				type = PointType.BODY;
				break;
			case RELEASE:
				type = PointType.TAIL;
				break;
			default:
				break;
		}
	}

	public TabletPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		setXY((int) _tablet.getPenX(), (int) _tablet.getPenY());
		setPressure(_tablet.getPressure());
		setTimeInMillis(_mouseEvt.getMillis());
		TabletAction tabletAction_ = TabletAction.convertP5MouseAction(_mouseEvt.getAction());
		setTypeByTabletAction(tabletAction_);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public float getPressure()
	{
		return pressure;
	}

	public long getTimeInMillis()
	{
		return timeInMillis;
	}

	public PointType getType()
	{
		return type;
	}

	public boolean isTail()
	{
		return type == PointType.TAIL;
	}

	public String toString()
	{
		return x + "," + y + "," + pressure + "," + timeInMillis + "," + type;
	}
}
