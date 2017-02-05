package penInput;

import java.math.BigDecimal;

import comm.OscComm;

public class PathToGCode
{
	int		convertedPathesNum	= 0;

	Pathes	pathes;
	OscComm	oscComm;

	public PathToGCode(Pathes _pathes, OscComm _oscComm)
	{
		pathes = _pathes;
		oscComm = _oscComm;
	}

	public int getConvertedPathesNum()
	{
		return convertedPathesNum;
	}

	String convert(Path _path)
	{
		StringBuilder gCodes_ = new StringBuilder();
		Point prevPoint_ = null;
		for (int i = 0; i < _path.getPointsNum(); i++)
		{
			Point point_ = _path.getPoint(i);
			if (i == 0)
			{
				// set mode to rapid linear motion
				gCodes_.append("G0");
				// XY plain move to beginning point with full speed
				gCodes_.append("X");
				gCodes_.append(point_.getX());
				gCodes_.append("Y");
				gCodes_.append(point_.getY());
				gCodes_.append('\r');
				gCodes_.append('\n');
				// Z axis move to beginning point with full speed
				gCodes_.append("Z");
				gCodes_.append(0);
				gCodes_.append('\r');
				gCodes_.append('\n');
				// set mode to linear motion at feed rate (temporarily set to 0)
				gCodes_.append("G1");
				gCodes_.append("F1");
				gCodes_.append('\r');
				gCodes_.append('\n');
			}
			else
			{
				gCodes_.append("X");
				gCodes_.append(point_.getX());
				gCodes_.append("Y");
				gCodes_.append(point_.getY());
				gCodes_.append("Z");
				gCodes_.append(point_.getZ());
				gCodes_.append("F");
				BigDecimal durationNano_ = new BigDecimal(
						String.valueOf(point_.getTimeNano() - prevPoint_.getTimeNano()));
				BigDecimal oneMinNano_ = new BigDecimal("1000000000");
				BigDecimal feedRate_ = oneMinNano_.divide(durationNano_, 3, BigDecimal.ROUND_HALF_UP);
				gCodes_.append(String.format("%.3f", feedRate_.doubleValue()));
				gCodes_.append('\r');
				if (!point_.isTail())
					gCodes_.append('\n');
			}
			prevPoint_ = point_;
		}
		return gCodes_.toString();
	}

	public void threadLoop()
	{
		for (int i = convertedPathesNum; i < pathes.getPathesNum(); i++)
			if (pathes.getPath(i).isCompleted())
			{
				String[] split_ = convert(pathes.getPath(i)).split("\n");
				for (String gCode_ : split_)
					oscComm.sendGCode(gCode_, pathes.getPath(i).isInternal());
				convertedPathesNum++;
			}
	}
}
