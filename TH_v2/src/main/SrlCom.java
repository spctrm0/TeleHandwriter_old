package main;

import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.serial.Serial;

public abstract class SrlCom {

	// SerialComm port
	public Serial		port;

	// SerialComm port property
	public final int		baudRate				= 115200;
	public final char	parity					= 'n';
	public final int		dataBits				= 8;
	public final float	stopBits				= 1.0f;
	public final char	delimeter				= '\r';

	// SerialComm connection setting
	public final int		responseWaitingTimeMs	= 2000;
	public String		expectedResponseMsg		= null;
	public String		responseBackMsg			= null;

	// SerialComm status
	public int			portIdx					= -1;
	public long			connectionAttemptTimeNs	= 0;
	public boolean		isConnected				= false;

	// SerialComm variables
	public StringBuilder	charAssemblingBuffer;

	// Reference
	public PApplet		p5;

	public SrlCom(PApplet _p5) {
		charAssemblingBuffer = new StringBuilder();
		p5 = _p5;
		p5.registerMethod("dispose", this);
	}

	public void dispose() {
		disconnect();
	}

	public int getPortIdx() {
		return portIdx;
	}

	public boolean isMyPort(Serial _port) {
		return _port == port;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setExpectedResponseMsg(String _expectedResponseMsg) {
		expectedResponseMsg = _expectedResponseMsg;
	}

	public void setResponseBackMsg(String _responseBackMsg) {
		responseBackMsg = _responseBackMsg;
	}

	public void disconnect() {
		if (port != null) {
			port.clear();
			port.stop();
			port.dispose();
		}
		port = null;
		isConnected = false;
	}

	public void attemptConnection(int _portIdx) {
		String portName_ = Serial.list()[_portIdx];
		disconnect();
		port = new Serial(p5, portName_, baudRate, parity, dataBits, stopBits);
		connectionAttemptTimeNs = System.nanoTime();
	}

	public abstract void sendMsg(String _msg);

	public abstract void receiveMsg(String _msg);

	public void assembleCharAndDeliverMsg(char _inChar) {
		if (_inChar != delimeter)
			charAssemblingBuffer.append(_inChar);
		else {
			String assembledMsg_ = charAssemblingBuffer.toString().replaceAll("\\R", "");
			charAssemblingBuffer.setLength(0);
			if (!assembledMsg_.isEmpty())
				receiveMsg(assembledMsg_);
		}
	}

	public long getWaitingTimeElapsedMs() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - connectionAttemptTimeNs);
	}

	public void attemptConnectionLoop(int _occupiedPortIdx) {
		while (!isConnected)
			if (getWaitingTimeElapsedMs() > responseWaitingTimeMs) {
				portIdx++;
				if (portIdx == _occupiedPortIdx)
					portIdx++;
				if (portIdx >= Serial.list().length)
					portIdx = 0;
				attemptConnection(portIdx);
			}
	}
}