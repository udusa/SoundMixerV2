package utils;

public class PlayerMath {

	public static double microToMin(long micros){
		return (micros/Consts.MICRO_TO_MIN_RATIO);
	}
	public static double microToSec(long micros){
		return (micros/Consts.MICRO_TO_SEC_RATIO)%60;
	}
	public static int timeProgress(long microsLength,long microsCurrent){
		return (int)((microsCurrent/(double)microsLength) * 100);
	}
	public static double persenteageToDb(int presentage, double maxDb, double minDb) {
		double ratio = presentage/Consts.SLIDER_RANGE;
		double range = maxDb-minDb;
		return ratio*range + minDb;
	}
	public static int dbToPresentage(double db,double maxDb,double minDb){
		double ratio = 100.0/(maxDb-minDb);
		return (int)((db-minDb)*ratio);
	}
	
}
