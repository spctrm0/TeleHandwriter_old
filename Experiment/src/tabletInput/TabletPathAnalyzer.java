package tabletInput;

public class TabletPathAnalyzer
{
	final static float	monitorDiagonal_mm	= 584.2f;
	final static int	monitorWidth_px		= 1920;
	final static int	monitorHeight_px	= 1080;
	final static double	monitorDiagonal_px	= Math
			.sqrt(monitorWidth_px * monitorWidth_px + monitorHeight_px * monitorHeight_px);
	final static double	pxToMm				= monitorDiagonal_mm / monitorDiagonal_px;
	final static double	mmToPx				= monitorDiagonal_px / monitorDiagonal_mm;

	public static void analyze(TabletPathes _tabletPathes)
	{
		int pathIdx_ = 0;
		for (TabletPath path_ : _tabletPathes.getPathes())
		{
			int pointIdx_ = 0;
			for (TabletPoint point_ : path_.getPoints())
			{
				System.out.print(pathIdx_ + "," + pointIdx_ + ",");
				System.out.print(TabletPathAnalyzer.getAnalysis(point_));
				System.out.println();
				pointIdx_++;
			}
			pathIdx_++;
		}
	}

	public static String getAnalysis(TabletPoint _tabletPoint)
	{
		String result_ = "";

		double x_mm_ = _tabletPoint.getX() * pxToMm;
		double y_mm_ = _tabletPoint.getY() * pxToMm;
		double pressure_ = _tabletPoint.getPressure();

		result_ += x_mm_;
		result_ += "," + y_mm_;
		result_ += "," + pressure_;

		TabletPoint prevTabletPoint_ = _tabletPoint.getPrevPoint();

		if (prevTabletPoint_ != null)
		{
			double p_x_mm_;
			double p_y_mm;
			double distX_mm_;
			double distY_mm_;
			double dist_mm_;
			double duration_ms_;
			double velX_mmPerMin_;
			double velY_mmPerMin_;
			double vel_mmPerMin_;

			p_x_mm_ = prevTabletPoint_.getX() * pxToMm;
			p_y_mm = prevTabletPoint_.getY() * pxToMm;
			distX_mm_ = x_mm_ - p_x_mm_;
			distY_mm_ = y_mm_ - p_y_mm;
			dist_mm_ = Math.sqrt(distX_mm_ * distX_mm_ + distY_mm_ * distY_mm_);

			duration_ms_ = _tabletPoint.getTime_ms() - prevTabletPoint_.getTime_ms();
			velX_mmPerMin_ = 60 * 1000 * distX_mm_ / duration_ms_;
			velY_mmPerMin_ = 60 * 1000 * distY_mm_ / duration_ms_;
			vel_mmPerMin_ = 60 * 1000 * dist_mm_ / duration_ms_;

			result_ += "," + distX_mm_;
			result_ += "," + distY_mm_;
			result_ += "," + dist_mm_;
			result_ += "," + duration_ms_;
			result_ += "," + velX_mmPerMin_;
			result_ += "," + velY_mmPerMin_;
			result_ += "," + vel_mmPerMin_;
		}
		return result_;
	}
}
