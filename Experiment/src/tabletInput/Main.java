package tabletInput;

import processing.core.PApplet;

public class Main extends PApplet
{
	TabletInput tabletInput;

	public void settings()
	{
		size(1280, 720);
	}

	public void setup()
	{
		tabletInput = new TabletInput(this);
		background(255);
	}

	public void draw()
	{
		background(255);
		noFill();
		stroke(255, 0, 0);
		for (TabletPath path_ : tabletInput.getPathes())
		{
			beginShape();
			for (TabletPoint point_ : path_.getPoints())
			{
				vertex(point_.getX(), point_.getY());
			}
			endShape();
		}
	}

	public void keyPressed()
	{
		if (key == 'a')
		{
			for (TabletPath path_ : tabletInput.getPathes())
			{
				for (TabletPoint point_ : path_.getPoints())
				{
					System.out.println(point_.toString());
				}
			}
		}
		else if (key == 's')
		{
			while (!tabletInput.getPathes().isEmpty())
			{
				TabletPath path_ = tabletInput.getPathes().poll();
				while (!path_.getPoints().isEmpty())
				{
					System.out.println(path_.getPoints().poll().toString());
				}
			}
		}
	}

	static public void main(String[] passedArgs)
	{
		String[] appletArgs = new String[] { tabletInput.Main.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
