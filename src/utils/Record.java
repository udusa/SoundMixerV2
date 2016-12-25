package utils;

public class Record {
	
	private String sign,timestampSignal,timestampNoise; 
	private double snr,noiseDb,signalDb;

	public Record(String sign, double snr, String timestampNoise, String timestampSignal, double noiseDb,
			double signalDb) {
		this.sign = sign;
		this.timestampSignal = timestampSignal;
		this.timestampNoise = timestampNoise;
		this.snr = snr;
		this.noiseDb = noiseDb;
		this.signalDb = signalDb;
	}

	@Override
	public String toString() {
		return "" + sign + "," + snr + "," + timestampNoise
				+ "," + timestampSignal + "," + noiseDb + "," + signalDb;
	}

	

}
