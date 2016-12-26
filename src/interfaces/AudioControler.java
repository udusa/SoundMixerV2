package interfaces;

public interface AudioControler {
	
	public void startPlaying();
	public void stopPlaying();
	public void pausePlaying();
	
	public void setVolume(int presentage);
	public void setVolume(double db);
	
	public double getMaxDb();
	public double getMinDb();
	public double getCurrentDb();
	
	public String getCurrentFileName();
	
	public String getTime();

}
