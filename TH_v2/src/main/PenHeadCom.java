package main;

import processing.core.PApplet;

public class PenHeadCom extends SrlCom {

	public PenHeadCom(PApplet _p5) {
		super(_p5);
		setExpectedResponseMsg("Pen Head");
		setResponseBackMsg("C" + '\r');
	}

	@Override
	protected void sendMsg(String _msg) {
		System.out.print("(O)PenHead: ");
		System.out.println(_msg);
		port.write(_msg);
	}

	@Override
	protected void receiveMsg(String _msg) {
		if (!isConnected) {
			if (_msg.equals(expectedResponseMsg)) {
				port.clear();
				sendMsg(responseBackMsg);
				isConnected = true;
				System.out.print("(I)PenHead: ");
				System.out.println(_msg);
			}
		} else {
			System.out.print("(I)PenHead: ");
			System.out.println(_msg);
		}
	}
}