package main;

public class Point
{
	int		x, y;
	float	z;
	long	timeNano;
	int		idx;
	boolean	isLastPoint	= false;

	Point(int _x, int _y, float _z, long _timeNano, int _idx, boolean _isLastPoint)
	{
		x = _x;
		y = _y;
		z = _z;
		timeNano = _timeNano;
		idx = _idx;
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

	public int getIdx()
	{
		return idx;
	}

	public boolean isLastPoint()
	{
		return isLastPoint;
	}
}