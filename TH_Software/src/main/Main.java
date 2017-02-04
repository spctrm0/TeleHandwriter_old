package main;

import processing.core.PApplet;

public class Main extends PApplet
{
	Pathes			pathes;
	PenInput		penInput;
	PathToGCode	gCodeConverter;

	public void settings()
	{
		size(1280, 720);
	}

	public void setup()
	{
		pathes = new Pathes();
		penInput = new PenInput(this, pathes);
		gCodeConverter = new PathToGCode(pathes);
		thread("gCodeConverter");
	}

	public void draw()
	{
	}

	public void gCodeConverter()
	{
		while (true)
		{
			gCodeConverter.threadLoop();
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
