package penInput;

public class Point
{
	private int		x, y;
	private float	z;
	private long	timeNano;
	private boolean	isLastPoint	= false;

	Point(int _x, int _y, float _z, long _timeNano, boolean _isLastPoint)
	{
		x = _x;
		y = _y;
		z = _z;
		timeNano = _timeNano;
		isLastPoint = _isLastPoint;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public float getZ()
	{
		return z;
	}

	public long getTimeNano()
	{
		return timeNano;
	}

	public boolean isLastPoint()
	{
		return isLastPoint;
	}
}