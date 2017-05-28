package main;

public class Point {

	private int		x, y;
	private float	z;
	private long	timeMs;
	private boolean	isTail	= false;

	Point(int _x, int _y, float _z, long _timeMs, boolean _isTail) {
		x = _x;
		y = _y;
		z = _z;
		timeMs = _timeMs;
		isTail = _isTail;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public long getTimeMs() {
		return timeMs;
	}

	public boolean isTail() {
		return isTail;
	}
}
