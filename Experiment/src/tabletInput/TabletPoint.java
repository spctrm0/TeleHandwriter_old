package tabletInput;

import codeanticode.tablet.Tablet;
import processing.event.MouseEvent;
import tabletInput.TabletInput.TabletAction;

public class TabletPoint
{
	private int		x, y;
	private float	pressure;
	private long	time_ms;

	public enum PointType
	{
		HEAD, BODY, TAIL;
	}

	private PointType type;

	private void setXY(int _x, int _y)
	{
		x = _x;
		y = _y;
	}

	private void setPressure(float _pressure)
	{
		pressure = _pressure;
	}

	private void setTime_ms(long _time_ms)
	{
		time_ms = _time_ms;
	}

	private void setTypeByTabletAction(TabletAction _mouseAction)
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

	public void setPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		setXY((int) _tablet.getPenX(), (int) _tablet.getPenY());
		setPressure(_tablet.getPressure());
		setTime_ms(_mouseEvt.getMillis());
		TabletAction tabletAction_ = TabletAction.convertP5MouseAction(_mouseEvt.getAction());
		setTypeByTabletAction(tabletAction_);
	}

	public TabletPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		setPoint(_tablet, _mouseEvt);
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

	public long getTime_ms()
	{
		return time_ms;
	}

	public PointType getType()
	{
		return type;
	}

	public boolean isTail()
	{
		return type == PointType.TAIL;
	}

	@Override
	public String toString()
	{
		return x + "," + y + "," + pressure + "," + time_ms + "," + type;
	}
}
