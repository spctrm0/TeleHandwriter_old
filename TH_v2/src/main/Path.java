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

	int getPointsNum() {
		return points.size();
	}

	Point getPoint(int _idx) {
		return points.get(_idx);
	}

	Point getLastPoint() {
		return points.get(points.size() - 1);
	}

	boolean isCompleted() {
		return getLastPoint().isTail();
	}

	public void addPoint(int _x, int _y, float _z, long _millis, boolean _isTail) {
		points.add(new Point(_x, _y, _z, _millis, _isTail));
	}

}
