package interfaces;

public interface AudioUpdater {
	
	public enum PlayerStatus
	{ START,PAUSE,STOP}
	
	public void updateSlider(int percentage);
	
	public void updateTimeLbl(String time);
	
	public void updatePlayerStatus(PlayerStatus playerStatus);
	
}
