package oscComm;

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import setting.Settings;

public class OscComm {
	final String	addrPtrnAsk			= "ask";
	final String	addrPtrnRpl			= "reply";
	final String	addrPtrnRplBack		= "replyBack";
	final String	addrPtrnDisconnect	= "disconnect";
	final String	addrPtrnInter		= "in";
	final String	addrPtrnExter		= "ex";

	boolean			isConnected			= false;
	int				myPort				= Settings.myPort;
	String			targetIp			= Settings.targetIp;
	int				targetPort			= Settings.targetPort;

	OscP5			port;
	NetAddress		myAddr;
	NetAddress		targetAddr;
	OscMessage		msg;

	PApplet			p5;



	
	boolean isConnected() {
		return isConnected;
	}

	void setConnected(boolean _isConnected) {
		isConnected = _isConnected;
	}
	
	void closePort() {
		if (port != null){
			port.stop();
			port.dispose();
		}
		port = null;
		setConnected(false);
	}
	
	void openPort() {
		closePort();
		port = new OscP5(p5, myPort);
	}

	void setMyAddr() {
		myAddr = new NetAddress("127.0.0.1", myPort);
	}

	void setTargetAddr() {
		targetAddr = new NetAddress(targetIp, targetPort);
	}

	public OscComm(PApplet _p5) {
		p5 = _p5;
		p5.registerMethod("dispose", this);
		msg = new OscMessage("");
		openPort();
		setMyAddr();
		setTargetAddr();
	}

	

	public void updateConnection() {
		connect();
	}

	void connect() {
		disconnect();
		sendConnectionMsg(addrPtrnAsk);
	}

	void disconnect() {
		sendConnectionMsg(addrPtrnDisconnect);
		setConnected(false);
	}

	void sendConnectionMsg(String _addrPattern) {
		msg.clear();
		msg.setAddrPattern(_addrPattern);
		msg.add(port.ip()).add(myPort);
		port.send(msg, targetAddr);
	}

	void send(OscMessage _oscMsg, boolean _isInternal) {
		port.send(_oscMsg, _isInternal ? myAddr : targetAddr);
	}

	public void sendGCode(String _gCode, boolean _isInternal) {
		msg.clear();
		msg.setAddrPattern(_isInternal ? addrPtrnInter : addrPtrnExter);
		msg.add(_gCode);
		send(msg, _isInternal);
	}

	public void receive(OscMessage _oscMsg) {
		if (_oscMsg.addrPattern().equals(addrPtrnInter) || _oscMsg.addrPattern().equals(addrPtrnExter))
			grblComm.send(_oscMsg.get(0).stringValue());
		else if (_oscMsg.addrPattern().equals(addrPtrnAsk)) {
			setConnected(false);
			sendConnectionMsg(addrPtrnRpl);
		} else if (_oscMsg.addrPattern().equals(addrPtrnRpl)) {
			if (_oscMsg.get(0).stringValue().equals(targetIp) && _oscMsg.get(1).intValue() == targetPort) {
				setConnected(true);
				sendConnectionMsg(addrPtrnRplBack);
			}
		} else if (_oscMsg.addrPattern().equals(addrPtrnRplBack)) {
			if (_oscMsg.get(0).stringValue().equals(targetIp) && _oscMsg.get(1).intValue() == targetPort)
				setConnected(true);
		} else if (_oscMsg.addrPattern().equals(addrPtrnDisconnect)) {
			if (_oscMsg.get(0).stringValue().equals(targetIp) && _oscMsg.get(1).intValue() == targetPort)
				setConnected(false);
		}
	}

	public void dispose() {
		if (port != null) {
			disconnect();
			closePort();
		}
	}
}
