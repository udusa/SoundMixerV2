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

import javax.swing.JButton;

public class MainControlPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton playPauseBtn, stopBtn, viBtn, xBtn;
	private ArrayList<AudioControler> audioControlers;
	private Algorithm algorithm;

	/**
	 * Create the panel.
	 */
	public MainControlPanel() {
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
		} else if (e.getActionCommand().equals(Consts.PAUSE_BTN)) {
			playPauseBtn.setText(Consts.PLAY_BTN);
			performAction(PlayerStatus.PAUSE);
		} else if (e.getActionCommand().equals(Consts.STOP_BTN)) {
			playPauseBtn.setText(Consts.PLAY_BTN);
			performAction(PlayerStatus.STOP);
		} else if (e.getActionCommand().equals(Consts.VI_BTN)) {
			changeSNR(true);
		} else if (e.getActionCommand().equals(Consts.X_BTN)) {
			changeSNR(false);
		}
	}

	private void changeSNR(boolean toInc) {
		AudioControler noise = audioControlers.get(0);
		AudioControler sound = audioControlers.get(1);
		noise.setVolume(50);
		sound.setVolume(50);
		double avgDb = noise.getCurrentDb() + sound.getCurrentDb();
		avgDb /= 2.0;
		System.out.println(avgDb);
		double snr = 0;
		if (toInc)
			snr = algorithm.getHigherSNR() / 2;
		else
			snr = algorithm.getLowerSNR() / 2;
		System.out.println(snr);
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

}
