package main;

public class PathToGCode {
	int				convertedPathesNum	= 0;
	int				convertedPointsNum	= 0;

	StringBuilder	gCodes_;
	Point			prevPoint			= null;

	Pathes			pathes;
	GrblCom			grblCom;

	public PathToGCode(Pathes _pathes, GrblCom _grblCom) {
		gCodes_ = new StringBuilder();

		pathes = _pathes;
		grblCom = _grblCom;
	}

	public int getConvertedPathesNum() {
		return convertedPathesNum;
	}

	public void threadLoop() {
		for (int i = convertedPathesNum; i < pathes.getPathesNum(); i++) {
			Path path_ = pathes.getPath(i);
			for (int j = convertedPointsNum; j < path_.getPointsNum(); j++) {
				Point point_ = path_.getPoint(j);
				if (!point_.equals(prevPoint)) {
					if (j == 0) {
						// set mode to rapid linear motion
						gCodes_.append("G0");
						// XY plain move to beginning point with full speed
						gCodes_.append("X-");
						gCodes_.append(point_.getX() * 0.1f);
						gCodes_.append("Y");
						gCodes_.append(point_.getY() * 0.1f);
						gCodes_.append('\r');
						grblCom.sendGrblMsg(gCodes_.toString());
						gCodes_.setLength(0);
						// set mode to linear motion at feed rate (temporarily
						// set
						// to 0)
						gCodes_.append("G1");
						gCodes_.append("F1");
						gCodes_.append('\r');
						grblCom.sendGrblMsg(gCodes_.toString());
						gCodes_.setLength(0);
					} else {
						gCodes_.append("X-");
						gCodes_.append(point_.getX() * 0.1f);
						gCodes_.append("Y");
						gCodes_.append(point_.getY() * 0.1f);
						gCodes_.append("F");
						long feedRate_ = point_.getTimeMs() - prevPoint.getTimeMs();
						if (feedRate_ != 0) {
							feedRate_ = 60000 / feedRate_;
							gCodes_.append(feedRate_);
							gCodes_.append('\r');
							grblCom.sendGrblMsg(gCodes_.toString());
						}
						gCodes_.setLength(0);
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
