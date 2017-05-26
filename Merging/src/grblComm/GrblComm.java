package grblComm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import srlComm.SrlComm.SrlListener;

public class GrblComm implements SrlListener {

	public interface GrblListener {
		void grblReceiveMsg(String _msg);
	}

	private List<GrblListener> listeners = new ArrayList<GrblListener>();

	public void addListner(GrblListener _listener) {
		listeners.add(_listener);
	}

	public void speak(String _msg) {
		for (GrblListener listener : listeners)
			listener.grblReceiveMsg(_msg);
	}

	private final String	expectedResponseMsg	= "Grbl 1.1e ['$' for help]";
	private final int		bufferLimit			= 128;

	private List<String>	bufferQueue			= null;
	private List<String>	buffer				= null;

	private int				bufferSize			= 0;

	public GrblComm() {
		bufferQueue = Collections.synchronizedList(new ArrayList<String>());
		buffer = Collections.synchronizedList(new ArrayList<String>());
	}

	public int getBufferRemain() {
		return bufferLimit - bufferSize;
	}

	public int getMsgInBufferQueueNum() {
		return bufferQueue.size();
	}

	public int getMsgInBufferNum() {
		return buffer.size();
	}

	public void sendGrblMsg(String _msg) {
		System.out.println("O: " + _msg);
		bufferQueue.add(_msg);
	}

	void receiveGrblMsg(String _msg) {
		System.out.println("I: " + _msg);
		if (_msg.contains("ok") || _msg.contains("error:")) {
			bufferSize -= buffer.get(0).length();
			buffer.remove(0);
		} else if (_msg.equals(expectedResponseMsg)) {
			bufferQueue.clear();
			buffer.clear();
			bufferSize = 0;
		}
	}

	@Override
	public void srlReceiveMsg(String _msg) {
		receiveGrblMsg(_msg);
	}

	public void threadLoop() {
		if (!bufferQueue.isEmpty()) {
			String msg_ = bufferQueue.get(0);
			if (getBufferRemain() >= msg_.length()) {
				bufferSize += msg_.length();
				buffer.add(msg_);
				bufferQueue.remove(0);
				// serialComm.send(msg_);
			}
		}
	}
}