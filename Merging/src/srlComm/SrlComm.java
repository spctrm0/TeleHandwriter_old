package srlComm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import grblComm.GrblComm.GrblListener;
import processing.core.PApplet;
import processing.serial.Serial;

public class SrlComm implements GrblListener {

	public interface SrlListener {
		void srlReceiveMsg(String _msg);
	}

	private List<SrlListener> listeners = new ArrayList<SrlListener>();

	public void addListner(SrlListener _listener) {
		listeners.add(_listener);
	}

	public void speak(String _msg) {
		for (SrlListener listener : listeners)
			listener.srlReceiveMsg(_msg);
	}

	// SerialComm port
	private Serial			port;

	// SerialComm port property
	private final int		baudRate				= 115200;
	private final char		parity					= 'n';
	private final int		dataBits				= 8;
	private final float		stopBits				= 1.0f;
	private final char		delimeter				= '\r';

	// SerialComm connection setting
	private final int		responseWaitingTimeMs	= 3000;
	private final String	expectedResponseMsg		= "Grbl 1.1e ['$' for help]";

	// SerialComm status
	 public int				portIdx					= -1;
	private long			connectionAttemptTimeNs	= 0;
	private boolean			isConnected				= false;

	// SerialComm variables
	private StringBuilder	charToStrBuffer;

	// Reference
	private PApplet			p5;

	public SrlComm(PApplet _p5) {
		charToStrBuffer = new StringBuilder();
		p5 = _p5;
		p5.registerMethod("dispose", this);
	}

	public void dispose() {
		disconnect();
	}

	public boolean isConnected() {
		return isConnected;
	}

	void setConnected(boolean _isConnected) {
		isConnected = _isConnected;
	}

	void disconnect() {
		if (port != null) {
			port.clear();
			port.stop();
			port.dispose();
		}
		port = null;
		setConnected(false);
	}

	void attemptConnection(int _portIdx) {
		String portName_ = Serial.list()[_portIdx];
		disconnect();
		port = new Serial(p5, portName_, baudRate, parity, dataBits, stopBits);
		connectionAttemptTimeNs = System.nanoTime();
	}

	void sendMsg(String _msg) {
		port.write(_msg);
	}

	@Override
	public void grblReceiveMsg(String _msg) {
		sendMsg(_msg);
	}

	public void receiveCharAndAssembleMsg(char _char) {
		if (_char != delimeter)
			charToStrBuffer.append(_char);
		else {
			String msg_ = charToStrBuffer.toString().replaceAll("\\R", "");
			if (!msg_.isEmpty()) {
				if (!isConnected)
					if (msg_.equals(expectedResponseMsg))
						setConnected(true);
				speak(msg_);
			}
			charToStrBuffer.setLength(0);
		}
	}

	long getWaitingTimeElapsedMs() {
		return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - connectionAttemptTimeNs);
	}

	public void threadLoop() {
		if (!isConnected)
			if (getWaitingTimeElapsedMs() > responseWaitingTimeMs) {
				portIdx++;
				if (portIdx >= Serial.list().length)
					portIdx = 0;
				attemptConnection(portIdx);
			}
	}
}