package comm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Settings;

public class GrblComm
{
	private final int		bufferLimit			= 128;
	private final String	connectionChecker	= Settings.connectionChecker;

	private int				bufferSize			= 0;

	private List<String>	queue			= null;
	private List<String>	buffer				= null;

	SerialComm				serialComm;

	public GrblComm(SerialComm _serialComm)
	{
		serialComm = _serialComm;
		queue = Collections.synchronizedList(new ArrayList<String>());
		buffer = Collections.synchronizedList(new ArrayList<String>());
	}

	public int getBufferRemain()
	{
		return bufferLimit - bufferSize;
	}

	public int getQueueCmdNum()
	{
		return queue.size();
	}

	public int getBufferCmdNum()
	{
		return buffer.size();
	}

	public void send(String _msg)
	{
		queue.add(_msg);
	}

	void receive(String _msg)
	{
		if (_msg.contains("ok") || _msg.contains("error:"))
		{
			bufferSize -= buffer.get(0).length();
			buffer.remove(0);
		}
		else if (_msg.equals(connectionChecker))
		{
			queue.clear();
			buffer.clear();
			bufferSize = 0;
		}
	}

	public void threadLoop()
	{
		if (!queue.isEmpty())
		{
			String msg_ = queue.get(0);
			if (getBufferRemain() >= msg_.length())
			{
				bufferSize += msg_.length();
				buffer.add(msg_);
				queue.remove(0);
				// serialComm.send(msg_);
			}
		}
	}
}
