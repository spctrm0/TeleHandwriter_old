package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;

public class GrblCom extends SrlCom {

	private final int		bufferLimit	= 128;

	private List<String>	bufferQueue	= null;
	private List<String>	buffer		= null;

	private int				bufferSize	= 0;

	public GrblCom(PApplet _p5) {
		super(_p5);
		setExpectedResponseMsg("Grbl 1.1f ['$' for help]");
		setResponseBackMsg("" + '\r');

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
		bufferQueue.add(_msg);
	}

	public void sendMsgThread() {
		if (!bufferQueue.isEmpty()) {
			String msg_ = bufferQueue.get(0);
			if (getBufferRemain() >= msg_.length()) {
				bufferSize += msg_.length();
				buffer.add(msg_);
				bufferQueue.remove(0);
				sendMsg(msg_);
			}
		}
	}

	@Override
	protected void sendMsg(String _msg) {
		System.out.print("(O)GRBL: ");
		System.out.println(_msg);
		port.write(_msg);
	}

	@Override
	protected void receiveMsg(String _msg) {
		if (!isConnected) {
			if (_msg.equals(expectedResponseMsg)) {
				port.clear();
				bufferQueue.clear();
				buffer.clear();
				bufferSize = 0;
				isConnected = true;
				System.out.print("(I)GRBL: ");
				System.out.println(_msg);
			}
		} else {
			if (_msg.contains("ok") || _msg.contains("error:")) {
				bufferSize -= buffer.get(0).length();
				buffer.remove(0);
				System.out.print("(I)GRBL: ");
				System.out.println(_msg);
			}
		}
	}
}