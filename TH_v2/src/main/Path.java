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

	public int getPointsNum() {
		return points.size();
	}

	public Point getPoint(int _idx) {
		return points.get(_idx);
	}

	public Point getLastPoint() {
		return points.get(points.size() - 1);
	}

	public boolean isCompleted() {
		return getLastPoint().isTail();
	}

	public void addPoint(int _x, int _y, float _z, long _timeMs, boolean _isTail) {
		// if (getPointsNum() > 0) {
		// float prevTimeMs_ = getLastPoint().getTimeMs();
		// if (prevTimeMs_ != _timeMs) {
		// points.add(new Point(_x, _y, _z, _timeMs, _isTail));
		// } else {
		// if (_isTail) {
		// points.add(new Point(_x, _y, _z, (_timeMs + 1), _isTail));
		// }
		// }
		// } else {
		// points.add(new Point(_x, _y, _z, _timeMs, _isTail));
		// }
		points.add(new Point(_x, _y, _z, _timeMs, _isTail));
	}
}
