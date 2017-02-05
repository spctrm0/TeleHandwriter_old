package main;

import comm.GrblComm;
import comm.OscComm;
import comm.SerialComm;
import oscP5.OscMessage;
import penInput.PathToGCode;
import penInput.Pathes;
import penInput.PenInput;
import processing.core.PApplet;
import processing.serial.Serial;

public class Main extends PApplet
{
	Pathes		pathes;
	PenInput	penInput;
	PathToGCode	pathToGCode;
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
		oscComm = new OscComm(this, grblComm);
		pathToGCode = new PathToGCode(pathes, oscComm);
		thread("serialCommThread");
		thread("pathToGCodeThread");
		thread("grblCommThread");
	}

	public void draw()
	{
		background(255);
		fill(0);
		text((pathes.getPathesNum() + ", " + pathToGCode.getConvertedPathesNum()), width / 2.0f, height / 2.0f);
		text((grblComm.getBufferRemain() + ", " + grblComm.getQueueCmdNum() + ", " + grblComm.getBufferCmdNum()),
				width / 2.0f, height / 2.0f + 64);
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

	public void oscEvent(OscMessage _oscEvt)
	{
		oscComm.receive(_oscEvt);
	}

	public void serialCommThread()
	{
		while (true)
			serialComm.threadLoop();
	}

	public void pathToGCodeThread()
	{
		while (true)
			pathToGCode.threadLoop();
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
