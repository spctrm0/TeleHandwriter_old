package tabletInput;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;

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

	public void setTypeByMouseAction(int _mouseAction)
	{
		switch (_mouseAction)
		{
			// PRESS
			case 1:
				type = PointType.HEAD;
				break;
			// DRAG
			case 4:
				type = PointType.BODY;
				break;
			// RELEASE
			case 2:
				type = PointType.TAIL;
				break;
		}
	}

	public TabletPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		setXY((int) _tablet.getPenX(), (int) _tablet.getPenY());
		setPressure(_tablet.getPressure());
		setTimeInMillis(_mouseEvt.getMillis());
		setTypeByMouseAction(_mouseEvt.getAction());
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
