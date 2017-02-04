package comm;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscProperties;
import processing.core.PApplet;

public class OscComm
{
	final String	addrPtrnAsk			= "ask";
	final String	addrPtrnRpl			= "reply";
	final String	addrPtrnRplBack		= "replyBack";
	final String	addrPtrnDisconnect	= "disconnect";
	final String	addrPtrnInter		= "in";
	final String	addrPtrnExter		= "ex";
	final String	localhost			= "127.0.0.1";

	boolean			isConnected			= false;
	int				myPort;
	String			targetIp;
	int				targetPort;

	OscP5			oscPort;
	NetAddress		myAddr;
	NetAddress		targetAddr;
	OscMessage		msg;

	PApplet			p5;
	GrblComm		grblComm;

	public OscComm(PApplet _p5)
	{
		p5 = _p5;
		p5.registerMethod("dispose", this);

		msg = new OscMessage("");

		openPort();
		setMyAddr();
		setTargetAddr();
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
		connect();
	}

	void connect()
	{
		disconnect();
		sendConnectionMsg(addrPtrnAsk);
	}

	void disconnect()
	{
		sendConnectionMsg(addrPtrnDisconnect);
		setConnected(false);
	}

	void sendConnectionMsg(String _addrPattern)
	{
		msg.clear();
		msg.setAddrPattern(_addrPattern);
		msg.add(oscPort.ip()).add(myPort);
		oscPort.send(msg, targetAddr);
	}

	void openPort()
	{
		closePort();
		oscPort = new OscP5(p5, myPort);
	}

	void closePort()
	{
		if (oscPort != null)
			oscPort.stop();
		oscPort = null;
	}

	void setMyAddr()
	{
		myAddr = new NetAddress("127.0.0.1", myPort);
	}

	void setTargetAddr()
	{
		targetAddr = new NetAddress(targetIp, targetPort);
	}

	void send(OscMessage _oscMsg, boolean _isInternal)
	{
		oscPort.send(_oscMsg, _isInternal ? myAddr : targetAddr);
	}

	public void sendGCode(String _gCode, boolean _isInternal)
	{
		msg.clear();
		msg.setAddrPattern(_isInternal ? addrPtrnInter : addrPtrnExter);
		msg.add(_gCode);
		send(msg, _isInternal);
	}

	public void receive(OscMessage _oscMsg)
	{
		if (_oscMsg.addrPattern().equals(addrPtrnAsk))
		{
			setConnected(false);
			sendConnectionMsg(addrPtrnRpl);
		}
		else if (_oscMsg.addrPattern().equals(addrPtrnRpl))
		{
			if (_oscMsg.get(0).stringValue().equals(targetIp) && _oscMsg.get(1).intValue() == targetPort)
			{
				sendConnectionMsg(addrPtrnRplBack);
				setConnected(true);
			}
		}
		else if (_oscMsg.addrPattern().equals(addrPtrnRplBack))
		{
			if (_oscMsg.get(0).stringValue().equals(targetIp) && _oscMsg.get(1).intValue() == targetPort)
				setConnected(true);
		}
		else if (_oscMsg.addrPattern().equals(addrPtrnDisconnect))
		{
			if (_oscMsg.get(0).stringValue().equals(targetIp) && _oscMsg.get(1).intValue() == targetPort)
				setConnected(false);
		}
		else if (_oscMsg.addrPattern().equals(addrPtrnInter) || _oscMsg.addrPattern().equals(addrPtrnExter))
			grblComm.send(_oscMsg.get(0).stringValue());
	}

	public void dispose()
	{
		if (oscPort != null)
		{
			disconnect();
			closePort();
		}
	}
}
