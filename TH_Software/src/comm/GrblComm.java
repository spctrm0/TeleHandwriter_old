package comm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrblComm
{
	private final int		bufferLimit	= 128;

	private int				bufferSize	= 0;

	private List<String>	buffer		= null;
	private List<String>	waitingList	= null;

	SerialComm				serialComm;

	public GrblComm(SerialComm _serialComm)
	{
		serialComm = _serialComm;
		buffer = Collections.synchronizedList(new ArrayList<String>());
		waitingList = Collections.synchronizedList(new ArrayList<String>());
	}

	public void threadLoop()
	{
		if (serialComm.isConnected())
			if (!waitingList.isEmpty())
			{
				String msg_ = waitingList.get(0);
				if (getBufferRemain() <= msg_.length())
				{
					bufferSize += msg_.length();
					buffer.add(msg_);
					waitingList.remove(0);
					serialComm.send(msg_);
				}
			}
	}

	int getBufferRemain()
	{
		return bufferLimit - bufferSize;
	}

	public void send(String _msg)
	{
		waitingList.add(_msg);
	}

	void receive(String _msg)
	{
		if (_msg.contains("ok") || _msg.contains("error:"))
		{
			bufferSize -= buffer.get(0).length();
			buffer.remove(0);
		}
	}
}
