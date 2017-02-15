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

	TabletPoint(Tablet _tablet, MouseEvent _mouseEvt, PointType _type)
	{
		setXY((int) _tablet.getPenX(), (int) _tablet.getPenY());
		setPressure(_tablet.getPressure());
		setTimeInMillis(_mouseEvt.getMillis());
		type = _type;
	}

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
}
