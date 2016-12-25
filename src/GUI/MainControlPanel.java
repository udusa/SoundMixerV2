package GUI;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import core.Algorithm;
import interfaces.AudioControler;
import interfaces.AudioUpdater.PlayerStatus;
import utils.Consts;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import utils.Record;

public class MainControlPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton playPauseBtn, stopBtn, viBtn, xBtn;
	private ArrayList<AudioControler> audioControlers;
	private Algorithm algorithm;
	private JLabel lblSNR;
	private List<Record> records;

	/**
	 * Create the panel.
	 */
	public MainControlPanel() {
		records = new ArrayList<>();
		
		TitledBorder titled = new TitledBorder("Controls");
		setBorder(titled);
		audioControlers = new ArrayList<>();
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		playPauseBtn = new JButton(">");
		playPauseBtn.addActionListener(this);
		playPauseBtn.setEnabled(false);
		add(playPauseBtn);
		stopBtn = new JButton("[]");
		stopBtn.addActionListener(this);
		stopBtn.setEnabled(false);
		add(stopBtn);

		viBtn = new JButton(Consts.VI_BTN);
		viBtn.addActionListener(this);
		add(viBtn);

		xBtn = new JButton(Consts.X_BTN);
		xBtn.addActionListener(this);
		add(xBtn);

		lblSNR = new JLabel(Consts.LBL_SNR);
		add(lblSNR);
	}

	public void setAudioConteler(AudioControler audioControler) {
		audioControlers.add(audioControler);
	}

	public void enableButtons() {
		playPauseBtn.setEnabled(true);
		stopBtn.setEnabled(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals(Consts.PLAY_BTN)) {
			playPauseBtn.setText(Consts.PAUSE_BTN);
			performAction(PlayerStatus.START);
			changeSNR(true,true);
		} else if (e.getActionCommand().equals(Consts.PAUSE_BTN)) {
			playPauseBtn.setText(Consts.PLAY_BTN);
			performAction(PlayerStatus.PAUSE);
		} else if (e.getActionCommand().equals(Consts.STOP_BTN)) {
			playPauseBtn.setText(Consts.PLAY_BTN);
			performAction(PlayerStatus.STOP);
		} else if (e.getActionCommand().equals(Consts.VI_BTN)) {
			updateRecord(Consts.VI_BTN);
			changeSNR(false,false);
		} else if (e.getActionCommand().equals(Consts.X_BTN)) {
			updateRecord(Consts.X_BTN);
			changeSNR(true,false);
		}
	}

	private void changeSNR(boolean toInc,boolean start) {
		AudioControler noise = audioControlers.get(0);
		AudioControler sound = audioControlers.get(1);
		noise.setVolume(50);
		sound.setVolume(50);
		double avgDb = 0;
		try {
			avgDb = noise.getCurrentDb() + sound.getCurrentDb();
		} catch (NullPointerException e) {
			// TODO: handle exception
//			e.printStackTrace();
		}
		avgDb /= 2.0;
		double snr = 0;
		if(start)
			snr = algorithm.getCurrentSNR() / 2;
		else if (toInc)
			snr = algorithm.getHigherSNR() / 2;
		else
			snr = algorithm.getLowerSNR() / 2;
		lblSNR.setText(Consts.LBL_SNR + snr * 2);
		noise.setVolume(avgDb - snr);
		sound.setVolume(avgDb + snr);
	}

	public void performAction(PlayerStatus playerStatus) {
		for (AudioControler ac : audioControlers) {
			switch (playerStatus) {
			case START:
				ac.startPlaying();
				break;
			case PAUSE:
				ac.pausePlaying();
				break;
			case STOP:
				ac.stopPlaying();
				break;
			}
		}
	}

	public void setAlgorithm(Algorithm al) {
		// TODO Auto-generated method stub
		this.algorithm = al;
	}

	public void clearRecord(){
		records.clear();
	}
	public void updateRecord(String sign){
		double snr = algorithm.getCurrentSNR();
		AudioControler noise = audioControlers.get(0);
		AudioControler sound =  audioControlers.get(1);
		double noiseDb = noise.getCurrentDb();
		double signalDb = sound.getCurrentDb();
		String timestampNoise = noise.getTime();
		timestampNoise = timestampNoise.substring(Consts.LBL_TIME.length(), timestampNoise.length());
		String timestampSignal = sound.getTime();
		timestampSignal = timestampSignal.substring(Consts.LBL_TIME.length(), timestampSignal.length());
		Record r = new Record(sign,snr,timestampNoise,timestampSignal,noiseDb,signalDb); 
		records.add(r);
		System.out.println(r);
		
	}
	public List<Record> getRecords(){
		return records;
	}
}
