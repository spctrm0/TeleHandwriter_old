package main;

import java.util.ArrayList;

public class Path
{
	ArrayList<Point>	points;
	boolean				isConverted	= false;

	Path(int _x, int _y, float _z, long _timeNano)
	{
		points = new ArrayList<Point>();
		addPoint(_x, _y, _z, _timeNano, false);
	}

	void addPoint(int _x, int _y, float _z, long _timeNano, boolean _isLastPoint)
	{
		int idx_ = points.size();
		points.add(new Point(_x, _y, _z, _timeNano, idx_, _isLastPoint));
	}

	Point getLastPoint()
	{
		return points.get(points.size() - 1);
	}

	boolean isFinished()
	{
		return getLastPoint().isLastPoint;
	}

	int getPointsNum()
	{
		return points.size();
	}

	ArrayList<Point> getPoints()
	{
		return points;
	}

	boolean isConverted()
	{
		return isConverted;
	}

	void setConverted(boolean _isConverted)
	{
		isConverted = _isConverted;
	}
}