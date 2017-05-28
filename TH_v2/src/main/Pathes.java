package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathes {

	private List<Path> pathes;

	public Pathes() {
		pathes = Collections.synchronizedList(new ArrayList<Path>());
	}

	protected int getPathesNum() {
		return pathes.size();
	}

	protected Path getPath(int _idx) {
		return pathes.get(_idx);
	}

	protected Path getLastPath() {
		return pathes.get(pathes.size() - 1);
	}

	protected void addPath(int _x, int _y, float _z, long _timeMs) {
		pathes.add(new Path(_x, _y, _z, _timeMs));
	}

	protected void addPoint(int _x, int _y, float _z, long _timeMs) {
		getLastPath().addPoint(_x, _y, _z, _timeMs, false);
	}

	protected void completePath(int _x, int _y, float _z, long _timeMs) {
		getLastPath().addPoint(_x, _y, _z, _timeMs, true);
	}
}
