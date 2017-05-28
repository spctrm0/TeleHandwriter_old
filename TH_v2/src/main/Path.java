package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {

	private List<Point> points;

	public Path(int _x, int _y, float _z, long _timeMs) {
		points = Collections.synchronizedList(new ArrayList<Point>());
		addPoint(_x, _y, _z, _timeMs, false);
	}

	protected int getPointsNum() {
		return points.size();
	}

	protected Point getPoint(int _idx) {
		return points.get(_idx);
	}

	protected Point getLastPoint() {
		return points.get(points.size() - 1);
	}

	protected boolean isCompleted() {
		return getLastPoint().isTail();
	}

	protected void addPoint(int _x, int _y, float _z, long _timeMs, boolean _isTail) {
		if (getPointsNum() > 0) {
			float prevTimeMs_ = getLastPoint().getTimeMs();
			if (prevTimeMs_ != _timeMs) {
				points.add(new Point(_x, _y, _z, _timeMs, _isTail));
			} else {
				if (_isTail) {
					points.add(new Point(_x, _y, _z, (_timeMs + 1), _isTail));
				}
			}
		} else {
			points.add(new Point(_x, _y, _z, _timeMs, _isTail));
		}
	}
}
