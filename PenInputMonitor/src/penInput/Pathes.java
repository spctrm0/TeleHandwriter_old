package penInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pathes
{
	private List<Path> pathes;

	public Pathes()
	{
		pathes = Collections.synchronizedList(new ArrayList<Path>());
	}

	Path getPath(int _idx)
	{
		return pathes.get(_idx);
	}

	Path getLastPath()
	{
		return pathes.get(pathes.size() - 1);
	}

	void addPath(int _x, int _y, float _z, long _timeNano, boolean _isInternal)
	{
		pathes.add(new Path(_x, _y, _z, _timeNano, _isInternal));
	}

	public int getPathesNum()
	{
		return pathes.size();
	}

	Point getLastPoint()
	{
		return getLastPath().getLastPoint();
	}

	void addPoint(int _x, int _y, float _z, long _timeNano, boolean _isTail)
	{
		getLastPath().addPoint(_x, _y, _z, _timeNano, _isTail);
	}
}
