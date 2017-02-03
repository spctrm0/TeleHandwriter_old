package main;

import processing.core.PApplet;

public class Main extends PApplet
{
	PenInput		penInput;
	GCodeConverter	gCodeConverter;

	public void settings()
	{
		size(1280, 720);
	}

	public void setup()
	{
		penInput = new PenInput(this);
		gCodeConverter = new GCodeConverter(penInput.pathes);
		thread("gCodeConverter");
	}

	public void draw()
	{
		println("conversionCnt:" + gCodeConverter.conversionCnt);
		println("pathes.size():" + gCodeConverter.pathes.size());
	}

	public void gCodeConverter()
	{
		while (true)
		{
			gCodeConverter.loop();
		}
	}

	public void keyPressed()
	{
	}

	static public void main(String[] passedArgs)
	{
		String[] appletArgs = new String[] { main.Main.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
