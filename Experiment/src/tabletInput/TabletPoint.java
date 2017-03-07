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

		public static PointType getPointTypeFromTabletAction(TabletAction _tabletAction)
		{
			switch (_tabletAction)
			{
				case PRESS:
					return PointType.HEAD;
				case DRAG:
					return PointType.BODY;
				case RELEASE:
					return PointType.TAIL;
				default:
					break;
			}
			return null;
		}
	}

	private PointType	type;

	private TabletPoint	prevPoint;

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

	private void setType(PointType _type)
	{
		type = _type;
	}

	public void setPoint(Tablet _tablet, MouseEvent _mouseEvt)
	{
		setXY((int) _tablet.getPenX(), (int) _tablet.getPenY());
		setPressure(_tablet.getPressure());
		setTime_ms(_mouseEvt.getMillis());
		TabletAction tabletAction_ = TabletAction.convertP5MouseAction(_mouseEvt.getAction());
		PointType type_ = PointType.getPointTypeFromTabletAction(tabletAction_);
		setType(type_);
	}

	public void setPrevPoint(TabletPoint _prevPoint)
	{
		prevPoint = _prevPoint;
	}

	public TabletPoint(Tablet _tablet, MouseEvent _mouseEvt, TabletPoint _prevPoint)
	{
		setPoint(_tablet, _mouseEvt);
		setPrevPoint(_prevPoint);
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

	public boolean isHead()
	{
		return type == PointType.HEAD;
	}

	public boolean isTail()
	{
		return type == PointType.TAIL;
	}

	public PointType getType()
	{
		return type;
	}

	public TabletPoint getPrevPoint()
	{
		return prevPoint;
	}

	@Override
	public String toString()
	{
		return x + "," + y + "," + pressure + "," + time_ms + "," + type;
	}
}
