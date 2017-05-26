package main;

public class Point
{
	private int		x, y;
	private float	z;
	private long	time;
	private boolean	isTail	= false;

	Point(int _x, int _y, float _z, long _time, boolean _isTail)
	{
		x = _x;
		y = _y;
		z = _z;
		time = _time;
		isTail = _isTail;
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
		return time;
	}

	public boolean isTail()
	{
		return isTail;
	}
}