package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path
{
	private List<Point> points;

	Path(int _x, int _y, float _z, long _timeNano)
	{
		points = Collections.synchronizedList(new ArrayList<Point>());
		addPoint(_x, _y, _z, _timeNano, false);
	}

	Point getPoint(int _idx)
	{
		return points.get(_idx);
	}

	Point getLastPoint()
	{
		return points.get(points.size() - 1);
	}

	int getPointsNum()
	{
		return points.size();
	}

	void addPoint(int _x, int _y, float _z, long _timeNano, boolean _isLastPoint)
	{
		points.add(new Point(_x, _y, _z, _timeNano, _isLastPoint));
	}

	boolean isFinished()
	{
		return getLastPoint().isLastPoint();
	}
}