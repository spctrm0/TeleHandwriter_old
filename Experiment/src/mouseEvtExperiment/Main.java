package mouseEvtExperiment;

import processing.core.PApplet;

public class Main extends PApplet
{
	MouseEvt	mouseEvt;
	int			dragEventCnt	= 0;

	public void settings()
	{
		size(1280, 720);
	}

	public void setup()
	{
		mouseEvt = new MouseEvt(this);

		background(255);
	}

	public void draw()
	{
	}

	public void mouseDragged()
	{
//		stroke(0, 0, 255);
//		line(mouseX - 4, mouseY - 4, mouseX + 4, mouseY + 4);
//		line(mouseX + 4, mouseY - 4, mouseX - 4, mouseY + 4);
//		dragEventCnt++;
//		System.out.println("DragEvent no." + dragEventCnt + " without longTask is finished.");
	}

	static public void main(String[] passedArgs)
	{
		String[] appletArgs = new String[] { mouseEvtExperiment.Main.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
