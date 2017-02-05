package penInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path
{
	private boolean		isInternal;

	private List<Point>	points;

	Path(int _x, int _y, float _z, long _timeNano, boolean _isInternal)
	{
		isInternal = _isInternal;
		points = Collections.synchronizedList(new ArrayList<Point>());
		addPoint(_x, _y, _z, _timeNano, false);
	}

	boolean isInternal()
	{
		return isInternal;
	}

	Point getPoint(int _idx)
	{
		return points.get(_idx);
	}

	Point getLastPoint()
	{
		return points.get(points.size() - 1);
	}

	void addPoint(int _x, int _y, float _z, long _timeNano, boolean _isTail)
	{
		points.add(new Point(_x, _y, _z, _timeNano, _isTail));
	}

	int getPointsNum()
	{
		return points.size();
	}

	boolean isCompleted()
	{
		return getLastPoint().isTail();
	}
}