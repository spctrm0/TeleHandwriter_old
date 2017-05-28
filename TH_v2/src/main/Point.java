package main;

public class Point {

	private int		x, y;
	private float	z;
	private long	timeMs;
	private boolean	isTail	= false;

	public Point(int _x, int _y, float _z, long _timeMs, boolean _isTail) {
		x = _x;
		y = _y;
		z = _z;
		timeMs = _timeMs;
		isTail = _isTail;
	}

	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}

	protected float getZ() {
		return z;
	}

	protected long getTimeMs() {
		return timeMs;
	}

	protected boolean isTail() {
		return isTail;
	}
}
