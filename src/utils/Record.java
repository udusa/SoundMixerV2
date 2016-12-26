package utils;

public class Record {
	
	private String sign,timestampSignal,timestampNoise,noiseFileName,soundFileName; 
	private double snr,noiseDb,signalDb;

	public Record(String sign, double snr, String timestampNoise, String timestampSignal, double noiseDb,
			double signalDb, String noiseFileName, String soundFileName) {
		this.sign = sign;
		this.timestampSignal = timestampSignal;
		this.timestampNoise = timestampNoise;
		this.snr = snr;
		this.noiseDb = noiseDb;
		this.signalDb = signalDb;
		this.noiseFileName = noiseFileName;
		this.soundFileName = soundFileName;
	}

	@Override
	public String toString() {
		return "" + sign + "," + snr + "," + timestampNoise
				+ "," + timestampSignal + "," + noiseDb + "," + signalDb+","+noiseFileName+","+soundFileName;
	}

	

}
