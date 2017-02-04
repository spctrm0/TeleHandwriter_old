package main;

import comm.GrblComm;
import comm.OscComm;
import comm.SerialComm;
import penInput.PathToGCode;
import penInput.Pathes;
import penInput.PenInput;
import processing.core.PApplet;
import processing.serial.Serial;

public class Main extends PApplet
{
	Pathes		pathes;
	PenInput	penInput;
	PathToGCode	gCodeConverter;
	SerialComm	serialComm;
	GrblComm	grblComm;
	OscComm		oscComm;

	public void settings()
	{
		size(1280, 720);
	}

	public void setup()
	{
		pathes = new Pathes();
		penInput = new PenInput(this, pathes);
		serialComm = new SerialComm(this);
		grblComm = new GrblComm(serialComm);
		oscComm = new OscComm(this);
		gCodeConverter = new PathToGCode(pathes, oscComm);
		thread("serialCommThread");
		thread("gCodeConverterThread");
		thread("grblCommThread");
	}

	public void draw()
	{
	}

	public void keyPressed()
	{
		if (key == 'a')
			serialComm.updateConnection();
	}

	public void SerialEvent(Serial _serialEvt)
	{
		char replyChar_ = _serialEvt.readChar();
		serialComm.receive(replyChar_);
	}

	public void serialCommThread()
	{
		while (true)
			serialComm.threadLoop();
	}

	public void gCodeConverterThread()
	{
		while (true)
			gCodeConverter.threadLoop();
	}

	public void grblCommThread()
	{
		while (true)
			grblComm.threadLoop();
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
