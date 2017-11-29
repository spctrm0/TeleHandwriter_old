package main;

import processing.core.PApplet;
import processing.serial.Serial;

public class SerialComm {
	// references
	public PApplet			p5				= null;

	// settings
	public final int		baudRate		= 250000;
	public final char		parity			= 'n';
	public final int		dataBits		= 8;
	public final float		stopBits		= 1.0f;
	public final char		delimeter		= '\r';

	// buffers
	public StringBuilder	charToStrBfr	= null;
	public StringBuilder	prtTxtBfr		= null;

	public SerialComm(PApplet _p5) {
		p5 = _p5;

		prtTxtBfr = new StringBuilder();
		
		printSerialList();
	}

	public void print(StringBuilder _stringBuilder) {
		System.out.print(_stringBuilder);
		_stringBuilder.setLength(0);
	}

	public void printSerialList() {
		for (int i = 0; i < Serial.list().length; i++) {
			String portName_ = Serial.list()[i];
			prtTxtBfr.append("(SRL) <portList> [").append(i).append("] ").append(portName_);
			prtTxtBfr.append('\n');
		}
		print(prtTxtBfr);
	}
}
