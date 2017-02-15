package tabletInput;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;
import tabletInput.TabletPoint.PointType;

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

	void setXY(int _x, int _y)
	{
		x = _x;
		y = _y;
	}

	void setPressure(float _pressure)
	{
		pressure = _pressure;
	}

	void setTimeInMillis(long _timeInMillis)
	{
		timeInMillis = _timeInMillis;
	}

	void setTypeByMouseAction(int _mouseAction)
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

	TabletPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		setXY((int) _tablet.getPenX(), (int) _tablet.getPenY());
		setPressure(_tablet.getPressure());
		setTimeInMillis(_mouseEvt.getMillis());
		setTypeByMouseAction(_mouseEvt.getAction());
	}

	public boolean isTail()
	{
		return type == PointType.TAIL;
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
}
