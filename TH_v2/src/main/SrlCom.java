package main;

import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.serial.Serial;

public abstract class SrlCom {

	// SerialComm port
	protected Serial		port;

	// SerialComm port property
	protected final int		baudRate				= 115200;
	protected final char	parity					= 'n';
	protected final int		dataBits				= 8;
	protected final float	stopBits				= 1.0f;
	protected final char	delimeter				= '\r';

	// SerialComm connection setting
	protected final int		responseWaitingTimeMs	= 2000;
	protected String		expectedResponseMsg		= null;
	protected String		responseBackMsg			= null;

	// SerialComm status
	protected int			portIdx					= -1;
	protected long			connectionAttemptTimeNs	= 0;
	protected boolean		isConnected				= false;

	// SerialComm variables
	protected StringBuilder	charAssemblingBuffer;

	// Reference
	protected PApplet		p5;

	protected SrlCom(PApplet _p5) {
		charAssemblingBuffer = new StringBuilder();
		p5 = _p5;
		p5.registerMethod("dispose", this);
	}

	public void dispose() {
		disconnect();
	}

	protected int getPortIdx() {
		return portIdx;
	}

	protected boolean isMyPort(Serial _port) {
		return _port == port;
	}

	protected boolean isConnected() {
		return isConnected;
	}

	protected void setExpectedResponseMsg(String _expectedResponseMsg) {
		expectedResponseMsg = _expectedResponseMsg;
	}

	protected void setResponseBackMsg(String _responseBackMsg) {
		responseBackMsg = _responseBackMsg;
	}

	protected void disconnect() {
		if (port != null) {
			port.clear();
			port.stop();
			port.dispose();
		}
		port = null;
		isConnected = false;
	}

	protected void attemptConnection(int _portIdx) {
		String portName_ = Serial.list()[_portIdx];
		disconnect();
		port = new Serial(p5, portName_, baudRate, parity, dataBits, stopBits);
		connectionAttemptTimeNs = System.nanoTime();
	}

	protected abstract void sendMsg(String _msg);

	protected abstract void receiveMsg(String _msg);

	protected void assembleCharAndDeliverMsg(char _inChar) {
		if (_inChar != delimeter)
			charAssemblingBuffer.append(_inChar);
		else {
			String assembledMsg_ = charAssemblingBuffer.toString().replaceAll("\\R", "");
			charAssemblingBuffer.setLength(0);
			if (!assembledMsg_.isEmpty())
				receiveMsg(assembledMsg_);
		}
	}

	protected long getWaitingTimeElapsedMs() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - connectionAttemptTimeNs);
	}

	protected void attemptConnectionLoop(int _occupiedPortIdx) {
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