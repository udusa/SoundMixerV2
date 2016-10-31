package GUI;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

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
	private JButton playPauseBtn, stopBtn,viBtn,xBtn;
	private ArrayList<AudioControler> audioControlers;

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
			System.out.println("V");
		} else if (e.getActionCommand().equals(Consts.X_BTN)) {
			System.out.println("X");
		}
		
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

}
