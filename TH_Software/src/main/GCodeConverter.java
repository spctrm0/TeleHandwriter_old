package main;

import java.util.ArrayList;

public class GCodeConverter
{
	ArrayList<Path>		pathes;
	int					conversionCnt	= 0;
	ArrayList<String>	gCodes;

	GCodeConverter(ArrayList<Path> _pathes)
	{
		pathes = _pathes;
		gCodes = new ArrayList<String>();
	}

	public void loop()
	{
	
			// Path path_ = pathes.get(i);
			// if (!path_.isConverted())
			// if (path_.isFinished())
			// {
			// // String[] gCodes_ = convertToGCode(path_).split("/");
			// // for (String gCode_ : gCodes_)
			// // gCodes.add(gCode_);
			// path_.setConverted(true);
			// conversionCnt++;
			// }
			// }
			conversionCnt++;
	}

	String convertToGCode(Path _path)
	{
		StringBuilder gCodes_ = new StringBuilder();
		Point prevPoint_ = null;
		for (Point point_ : _path.getPoints())
		{
			if (point_.getIdx() == 0)
			{
				// set mode to rapid linear motion
				gCodes_.append("G0");
				// XY plain move to beginning point with full speed
				gCodes_.append("X");
				gCodes_.append(point_.getX());
				gCodes_.append("Y");
				gCodes_.append(point_.getY());
				gCodes_.append('\r');
				gCodes_.append('/');
				// Z axis move to beginning point with full speed
				gCodes_.append("Z");
				gCodes_.append(0);
				gCodes_.append('\r');
				gCodes_.append('/');
				// set mode to linear motion at feed rate (temporarily set to 0)
				gCodes_.append("G1");
				gCodes_.append("F1");
				gCodes_.append('\r');
				gCodes_.append('/');
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
				// BigDecimal durationNano_ = new
				// BigDecimal(point_.getTimeNano() - prevPoint_.getTimeNano());
				// BigDecimal oneMinNano_ = new BigDecimal(1000000000);
				// BigDecimal feedRate_ = oneMinNano_.divide(durationNano_);
				// gCodes_.append(String.format("%.3f",
				// feedRate_.doubleValue()));
				double feedRate_ = 1000000000 / (point_.getTimeNano() - prevPoint_.getTimeNano());
				System.out.println(feedRate_);
				gCodes_.append(String.format("%.3f", feedRate_));
				gCodes_.append('\r');
				if (!point_.isLastPoint())
					gCodes_.append('/');
			}
			prevPoint_ = point_;
		}
		System.out.println(gCodes_.toString());
		_path.setConverted(true);
		return gCodes_.toString();
	}
}
