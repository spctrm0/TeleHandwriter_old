package main;

import penInput.PathToGCode;
import penInput.Pathes;
import penInput.PenInput;
import processing.core.PApplet;

public class Main extends PApplet
{
	Pathes		pathes;
	PenInput	penInput;
	PathToGCode	pathToGCode;

	public void settings()
	{
		size(1280, 720);
	}

	public void setup()
	{
		pathes = new Pathes();
		penInput = new PenInput(this, pathes);
		pathToGCode = new PathToGCode(pathes);
		thread("pathToGCodeThread");
	}

	public void draw()
	{
		background(255);
		fill(0);
		text((pathes.getPathesNum() + ", " + pathToGCode.getConvertedPathesNum()), width / 2.0f, height / 2.0f);
	}

	public void keyPressed()
	{
	}

	public void pathToGCodeThread()
	{
		while (true)
			pathToGCode.threadLoop();
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
