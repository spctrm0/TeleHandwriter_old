package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.serial.Serial;

public class SrlCom {

	protected interface SrlListener {
		void srlReceiveMsg(String _msg);
	}

	protected List<SrlListener> listeners = new ArrayList<SrlListener>();

	protected void addListner(SrlListener _listener) {
		listeners.add(_listener);
	}

	protected void speak(String _msg) {
		System.out.println(_msg);
		for (SrlListener listener : listeners)
			listener.srlReceiveMsg(_msg);
	}

	// SerialComm port
	protected Serial		port;

	// SerialComm port property
	protected final int		baudRate				= 115200;
	protected final char	parity					= 'n';
	protected final int		dataBits				= 8;
	protected final float	stopBits				= 1.0f;
	protected final char	delimeter				= '\r';

	// SerialComm connection setting
	protected final int		responseWaitingTimeMs	= 3000;
	protected String		expectedResponseMsg		= null;

	// SerialComm status
	protected int			portIdx					= -1;
	protected long			connectionAttemptTimeNs	= 0;
	protected boolean		isConnected				= false;

	// SerialComm variables
	protected StringBuilder	charAssembleBuffer;

	// Reference
	protected PApplet		p5;

	protected SrlCom(PApplet _p5) {
		charAssembleBuffer = new StringBuilder();
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

	protected void sendMsg(String _msg) {
		port.write(_msg);
	}

	protected void receiveAndAssembleChar(char _inChar) {
		if (_inChar != delimeter)
			charAssembleBuffer.append(_inChar);
		else {
			String assembledMsg_ = charAssembleBuffer.toString().replaceAll("\\R", "");
			if (!assembledMsg_.isEmpty()) {
				if (!isConnected)
					if (assembledMsg_.equals(expectedResponseMsg)) {
						port.clear();
						isConnected = true;
					}
			}
			speak(assembledMsg_);
			charAssembleBuffer.setLength(0);
		}
	}

	protected long getWaitingTimeElapsedMs() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - connectionAttemptTimeNs);
	}

	protected void connectionLoop(int _occupiedPortIdx) {
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