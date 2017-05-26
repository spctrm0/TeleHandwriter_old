package main;

import processing.core.PApplet;

public class PenHeadCom extends SrlCom {

	protected final String responseBackMsg = "C" + '\r';

	public PenHeadCom(PApplet _p5) {
		super(_p5);
		setExpectedResponseMsg("Pen Head");
	}

	@Override
	protected void receiveAndAssembleChar(char _inChar) {
		if (_inChar != delimeter)
			charAssembleBuffer.append(_inChar);
		else {
			String assembledMsg_ = charAssembleBuffer.toString().replaceAll("\\R", "");
			if (!assembledMsg_.isEmpty()) {
				if (!isConnected)
					if (assembledMsg_.equals(expectedResponseMsg)) {
						port.clear();
						sendMsg(responseBackMsg);
						isConnected = true;
					}
			}
			speak(assembledMsg_);
			charAssembleBuffer.setLength(0);
		}
	}
}