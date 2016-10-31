package core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;

import interfaces.AudioUpdater;
import utils.PlayerMath;

public class Player implements LineListener{

	private JSlider timeSlider;
	private Clip clip;
	private long lastTimePos = 0;
	private AudioUpdater audioUpdater;

	Player(AudioUpdater audioUpdater) {
		this.audioUpdater=audioUpdater;
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
	}
	
	public void startPlaying(){
		
	}
	
	public void stopPlaying(){
		
	}
	
	public void pausePlaying(){
		
	}

	private class SliderUpdateThread extends Thread {

		private volatile boolean stop = false;
		
		@Override
		public void run() {
			long clipLength = clip.getMicrosecondLength();
			long currentTime = lastTimePos;
			while(clipLength > currentTime && !stop){
				currentTime = clip.getMicrosecondPosition();
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String timer;
				double min = PlayerMath.microToMin(currentTime);
				double sec = PlayerMath.microToSec(currentTime);
				timer = ""+min+":"+sec;
				audioUpdater.updateTimeLbl(timer);
				audioUpdater.updateSlider(PlayerMath.timeProgress(clipLength, currentTime));
			}
		}
		
		public void stopThread(){
			stop = true;
		}
		
		
	}

	@Override
	public void update(LineEvent arg0) {
		// TODO Auto-generated method stub
		LineEvent.Type type = arg0.getType();
		if(type.equals(LineEvent.Type.START)){
			
		}else if(type.equals(LineEvent.Type.STOP)){
			
		}else if(type.equals(LineEvent.Type.OPEN)){
			
		}else if(type.equals(LineEvent.Type.CLOSE)){
			
		}
		
	}

}
