package main;

public class PathToGCodeConverter {
	private int				convertedPathesNum	= 0;
	private int				convertedPointsNum	= 0;

	private StringBuilder	gCodeConvertingBuffer;
	private Point			prevPoint			= null;

	private float			scaler				= 0.1f;

	private Pathes			pathes;
	private GrblCom			grblCom;

	public PathToGCodeConverter(Pathes _pathes, GrblCom _grblCom) {
		gCodeConvertingBuffer = new StringBuilder();

		pathes = _pathes;
		grblCom = _grblCom;
	}

	public int getConvertedPathesNum() {
		return convertedPathesNum;
	}

	public int getConvertedPointsNum() {
		return convertedPointsNum;
	}

	public void convertThread() {
		// if (convertedPathesNum < pathes.getPathesNum()) {
		// Path path_ = pathes.getPath(convertedPathesNum);
		// if (convertedPointsNum < path_.getPointsNum()) {
		// Point point_ = path_.getPoint(convertedPointsNum);
		// if (!point_.equals(prevPoint)) {
		// if (convertedPointsNum == 0) {
		// gCodeConvertingBuffer.append("G0");
		// gCodeConvertingBuffer.append("X-");
		// gCodeConvertingBuffer.append(point_.getX() * scaler);
		// gCodeConvertingBuffer.append("Y");
		// gCodeConvertingBuffer.append(point_.getY() * scaler);
		// gCodeConvertingBuffer.append('\r');
		// grblCom.sendGrblMsg(gCodeConvertingBuffer.toString());
		// gCodeConvertingBuffer.setLength(0);
		//
		// gCodeConvertingBuffer.append("G1");
		// gCodeConvertingBuffer.append("F1");
		// gCodeConvertingBuffer.append('\r');
		// grblCom.sendGrblMsg(gCodeConvertingBuffer.toString());
		// gCodeConvertingBuffer.setLength(0);
		// } else {
		// gCodeConvertingBuffer.append("X-");
		// gCodeConvertingBuffer.append(point_.getX() * scaler);
		// gCodeConvertingBuffer.append("Y");
		// gCodeConvertingBuffer.append(point_.getY() * scaler);
		// gCodeConvertingBuffer.append("F");
		// long feedRate_ = point_.getTimeMs() - prevPoint.getTimeMs();
		// feedRate_ = 60000 / feedRate_;
		// gCodeConvertingBuffer.append(feedRate_);
		// gCodeConvertingBuffer.append('\r');
		// grblCom.sendGrblMsg(gCodeConvertingBuffer.toString());
		// gCodeConvertingBuffer.setLength(0);
		// }
		// convertedPointsNum++;
		// prevPoint = point_;
		// if (point_.isTail()) {
		// convertedPathesNum++;
		// convertedPointsNum = 0;
		// point_ = null;
		// }
		// }
		// }
		// }

		for (int i = convertedPathesNum; i < pathes.getPathesNum(); i++) {
			Path path_ = pathes.getPath(i);
			for (int j = convertedPointsNum; j < path_.getPointsNum(); j++) {
				Point point_ = path_.getPoint(j);
				if (!point_.equals(prevPoint)) {
					if (j == 0) {
						gCodeConvertingBuffer.append("G0");
						gCodeConvertingBuffer.append("X-");
						gCodeConvertingBuffer.append(point_.getX() * scaler);
						gCodeConvertingBuffer.append("Y");
						gCodeConvertingBuffer.append(point_.getY() * scaler);
						gCodeConvertingBuffer.append('\r');
						grblCom.sendGrblMsg(gCodeConvertingBuffer.toString());
						gCodeConvertingBuffer.setLength(0);

						gCodeConvertingBuffer.append("G1");
						gCodeConvertingBuffer.append("F1");
						gCodeConvertingBuffer.append('\r');
						grblCom.sendGrblMsg(gCodeConvertingBuffer.toString());
						gCodeConvertingBuffer.setLength(0);
					} else {
						gCodeConvertingBuffer.append("X-");
						gCodeConvertingBuffer.append(point_.getX() * scaler);
						gCodeConvertingBuffer.append("Y");
						gCodeConvertingBuffer.append(point_.getY() * scaler);
						gCodeConvertingBuffer.append("F");
						long feedRate_ = point_.getTimeMs() - prevPoint.getTimeMs();
						if (feedRate_ != 0) {
							feedRate_ = 60000 / feedRate_;
							gCodeConvertingBuffer.append(feedRate_);
							gCodeConvertingBuffer.append('\r');
							grblCom.sendGrblMsg(gCodeConvertingBuffer.toString());
						}
						gCodeConvertingBuffer.setLength(0);
					}
					convertedPointsNum++;
					prevPoint = point_;
					if (point_.isTail()) {
						convertedPathesNum++;
						convertedPointsNum = 0;
						point_ = null;
					}
				}
			}
		}
	}
}
