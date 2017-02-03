package test2;

import processing.core.PApplet;

public class ThreadTest extends PApplet
{
	ThreadClass	thread_data;
	int			cnt	= -1;

	public void settings()
	{
		size(100, 100);
	}

	public void setup()
	{
		thread_data = new ThreadClass();
		thread("myThread");
	}

	public void draw()
	{
		println("Thread data: " + thread_data.some_data);
		println("cnt: " + cnt);
		cnt++;
	}

	public void myThread()
	{
		while (true)
		{
			// thread_data.some_data += 1;
			thread_data.loop();
		}
	}

	static public void main(String[] passedArgs)
	{
		String[] appletArgs = new String[] { test2.ThreadTest.class.getName() };
		if (passedArgs != null)
			PApplet.main(concat(appletArgs, passedArgs));
		else
			PApplet.main(appletArgs);
	}
}
