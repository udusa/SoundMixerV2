package core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import interfaces.AudioControler;
import interfaces.AudioUpdater;
import interfaces.AudioUpdater.PlayerStatus;
import utils.PlayerMath;

public class Player implements LineListener,AudioControler{

	private Clip clip;
	private long lastTimePos = 0;
	private AudioUpdater audioUpdater;
	private UpdaterThread updaterThread;
	private FloatControl gainControl;
	private String currentFileName;

	public Player(AudioUpdater audioUpdater) {
		this.audioUpdater=audioUpdater;
		currentFileName = "None";
	}

	public void setAudioFile(File soundFile)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (clip != null){
			clip.close();
			clip.removeLineListener(this);
		}
		AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
		DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		clip = (Clip) AudioSystem.getLine(info);
		clip.addLineListener(this);
		clip.open(sound);
		gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		currentFileName = soundFile.getName();
	}
	
	@Override
	public void startPlaying(){
		try{
			updaterThread.stopThread();
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		clip.setMicrosecondPosition(lastTimePos);
		updaterThread = new UpdaterThread();
		updaterThread.start();
		clip.start();
	}
	
	@Override
	public void stopPlaying(){
		try{
			updaterThread.stopThread();
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		clip.stop();
		lastTimePos = 0;
		audioUpdater.updateSlider(0);
	}
	
	@Override
	public void pausePlaying(){
		try{
			updaterThread.stopThread();
		}catch (NullPointerException e) {
			// TODO: handle exception
		}
		lastTimePos = clip.getMicrosecondPosition();
		clip.stop();
	}
	
	@Override
	public void setVolume(int presentage) {
		// TODO Auto-generated method stub
		setVolume(PlayerMath.persenteageToDb(presentage,getMaxDb(),getMinDb()));
	}

	@Override
	public void setVolume(double db) {
		// TODO Auto-generated method stub
		gainControl.setValue((float)db);
	}

	@Override
	public double getMaxDb() {
		// TODO Auto-generated method stub
		return gainControl.getMaximum();
	}

	@Override
	public double getMinDb() {
		// TODO Auto-generated method stub
		return gainControl.getMinimum();
	}
	
	public double getCurrentDb(){
		return gainControl.getValue();
	}
	
	@Override
	public void update(LineEvent arg0) {
		// TODO Auto-generated method stub
		LineEvent.Type type = arg0.getType();
		if(type.equals(LineEvent.Type.START)){
			
		}else if(type.equals(LineEvent.Type.STOP)){
			audioUpdater.updatePlayerStatus(PlayerStatus.STOP);
		}else if(type.equals(LineEvent.Type.OPEN)){
			
		}else if(type.equals(LineEvent.Type.CLOSE)){
			
		}
		
	}

	private class UpdaterThread extends Thread {

		private volatile boolean stop = false;
		
		@Override
		public void run() {
			long clipLength = clip.getMicrosecondLength();
			long currentTime = lastTimePos;
			String soundTime;
			double min = PlayerMath.microToMin(clipLength);
			double sec = PlayerMath.microToSec(clipLength);
			String sMin = ""+(int)min;
			if(min < 10)sMin = "0"+(int)min;
			String sSec = ""+(int)sec;
			if(sec < 10)sMin = "0"+(int)sec;
			soundTime = sMin+":"+sSec;
			while(clipLength > currentTime && !stop){
				currentTime = clip.getMicrosecondPosition();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String timer;
				min = PlayerMath.microToMin(currentTime);
				sec = PlayerMath.microToSec(currentTime);
				String secStr = ""+(int)sec;
				if(sec < 10)secStr = "0"+(int)sec;
				String minStr = ""+(int)min;
				if(min < 10)minStr = "0"+(int)min;
				timer = minStr+":"+secStr;
				audioUpdater.updateTimeLbl(timer+"/"+soundTime);
				audioUpdater.updateSlider(PlayerMath.timeProgress(clipLength, currentTime));
			}
		}
		public void stopThread(){
			stop = true;
		}
	}

	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentFileName() {
		// TODO Auto-generated method stub
		return currentFileName;
	}





}
