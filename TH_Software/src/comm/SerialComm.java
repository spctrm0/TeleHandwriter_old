package comm;

import java.util.concurrent.TimeUnit;

import main.Settings;
import processing.core.PApplet;
import processing.serial.Serial;

public class SerialComm
{

	private final int		baudRate			= 115200;
	private final char		parity				= 'n';
	private final int		dataBits			= 8;
	private final float		stopBits			= 1.0f;
	private final char		delimeter			= '\r';
	private final int		waitingTimeSec		= 3;
	private final String	connectionChecker	= Settings.connectionChecker;

	private long			connectTime			= 0;
	private int				portIdx				= -1;
	private boolean			isConnected			= false;

	private StringBuilder	charToStrBuffer;

	private PApplet			p5;
	private Serial			srlPort;
	private GrblComm		grblBuffer;

	public SerialComm(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("dispose", this);
	}

	boolean isConnected()
	{
		return isConnected;
	}

	void setConnected(boolean _isConnected)
	{
		isConnected = _isConnected;
	}

	public void updateConnection()
	{
		disconnect();
	}

	void connect(int _portIdx)
	{
		String portName_ = Serial.list()[_portIdx];
		disconnect();
		srlPort = new Serial(p5, portName_, baudRate, parity, dataBits, stopBits);
		connectTime = System.nanoTime();
	}

	void disconnect()
	{
		if (srlPort != null)
			srlPort.stop();
		srlPort = null;
		setConnected(false);
	}

	void send(String _msg)
	{
		srlPort.write(_msg);
	}

	public void receive(char _char)
	{
		if (_char != delimeter)
			charToStrBuffer.append(_char);
		else
		{
			String msg_ = charToStrBuffer.toString().replaceAll("\\R", "");
			if (!msg_.isEmpty())
			{
				if (!isConnected)
					if (msg_.equals(connectionChecker))
						setConnected(true);
				grblBuffer.receive(msg_);
			}
			charToStrBuffer.setLength(0);
		}
	}

	float getWaitingTimeElapsedSec()
	{
		return TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - connectTime);
	}

	public void threadLoop()
	{
		if (!isConnected)
			if (getWaitingTimeElapsedSec() > waitingTimeSec)
			{
				portIdx++;
				if (portIdx >= Serial.list().length)
					portIdx = 0;
				connect(portIdx);
			}
	}

	public void dispose()
	{
		disconnect();
	}
}