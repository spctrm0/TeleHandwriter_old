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
