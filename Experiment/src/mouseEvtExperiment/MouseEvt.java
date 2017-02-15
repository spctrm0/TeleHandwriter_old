package mouseEvtExperiment;

import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class MouseEvt
{
	PApplet	p5;
	int		cnt				= 0;
	long	evtTimeInMillis	= 0;
	long	evtTimeInNano	= 0;

	MouseEvt(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("mouseEvent", this);
	}

	public void mouseEvent(MouseEvent _mouseEvt)
	{
		if (_mouseEvt.getAction() == MouseEvent.DRAG)
		{
			p5.stroke(255, 0, 0);
			p5.line(_mouseEvt.getX() - 4, _mouseEvt.getY(), _mouseEvt.getX() + 4, _mouseEvt.getY());
			p5.line(_mouseEvt.getX(), _mouseEvt.getY() - 4, _mouseEvt.getX(), _mouseEvt.getY() + 4);
			cnt++;
			System.out.print("DragEvent no." + cnt + " with longTask...");
			evtTimeInMillis = _mouseEvt.getMillis();
			evtTimeInNano = System.nanoTime();
			System.out.print(" " + evtTimeInMillis + " " + evtTimeInNano);
			// longTask();
			System.out.println();
		}
	}

	void longTask()
	{
		long startTimeInNano_ = System.nanoTime();
		long timeElapesedInNano_;
		do
			timeElapesedInNano_ = System.nanoTime() - startTimeInNano_;
		while (TimeUnit.NANOSECONDS.toMillis(timeElapesedInNano_) < 1000);
		System.out.print(" is finished.");
	}
}
