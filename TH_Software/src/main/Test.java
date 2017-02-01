package main;

import processing.core.PApplet;

public class Test extends PApplet
{
	public void settings()
	{
		size(100, 100);
	}

	public void setup()
	{

	}

	public void draw()
	{

	}

	static public void main(String[] passedArgs)
	{
		String[] appletArgs = new String[] { main.Test.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
