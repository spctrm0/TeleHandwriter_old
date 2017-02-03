package main;

import java.util.ArrayList;

import codeanticode.tablet.Tablet;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class PenInput
{
	PApplet			p5;
	Tablet			tablet;
	boolean			isWritable	= true;
	ArrayList<Path>	pathes;

	PenInput(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
		tablet = new Tablet(p5);
		pathes = new ArrayList<Path>();
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		if (isWritable)
			// if (tablet.getPenKind() == Tablet.STYLUS)
			if (_mouseEvt.getAction() == MouseEvent.PRESS)
			{
			addPath((int) tablet.getPenX(), (int) tablet.getPenY(), tablet.getPressure(), System.nanoTime());
			// System.out.println("mouse:" + (int) tablet.getPenX() + "," +
			// (int) tablet.getPenY());
			}
			else if (_mouseEvt.getAction() == MouseEvent.DRAG)
			{
			addPoint((int) tablet.getPenX(), (int) tablet.getPenY(), tablet.getPressure(), System.nanoTime(), false);
			// System.out.println("mouse:" + (int) tablet.getPenX() + "," +
			// (int) tablet.getPenY());
			}
			else if (_mouseEvt.getAction() == MouseEvent.RELEASE)
			{
			addPoint((int) tablet.getPenX(), (int) tablet.getPenY(), tablet.getPressure(), System.nanoTime(), true);
			// System.out.println("mouse:" + (int) tablet.getPenX() + "," +
			// (int) tablet.getPenY());
			}
	}

	void addPath(int _x, int _y, float _z, long _timeNano)
	{
		pathes.add(new Path(_x, _y, _z, _timeNano));
	}

	void addPoint(int _x, int _y, float _z, long _timeNano, boolean _isLastPoint)
	{
		getLastPath().addPoint(_x, _y, _z, _timeNano, _isLastPoint);
	}

	ArrayList<Path> getPathes()
	{
		return pathes;
	}

	Path getLastPath()
	{
		return pathes.get(pathes.size() - 1);
	}

	Point getLastPoint()
	{
		return getLastPath().getLastPoint();
	}
}