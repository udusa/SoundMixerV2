package GUI;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.SliderUI;

import core.Player;
import interfaces.AudioControler;
import interfaces.AudioUpdater;
import utils.Consts;
import utils.PlayerMath;

public class MainSoundPannel extends JPanel implements AudioUpdater,AudioControler,ListSelectionListener,ChangeListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lblTime, lblVolume;
	JSlider sliderTime, sliderVolume;
	private JList<File> fileList;
	private Player player;
	private int fileIndex;
	private boolean isPaused=false,isStop=false;

	/**
	 * Create the panel.
	 */
	public MainSoundPannel(String title) {
		
		player = new Player(this);

		lblTime = new JLabel(Consts.LBL_TIME);
		lblTime.setFont(new Font("Consolas", Font.BOLD, 16));
		lblVolume = new JLabel(Consts.LBL_VOLUME);
		lblVolume.setFont(new Font("Consolas", Font.BOLD, 16));

		sliderTime = new JSlider();
		sliderTime.setValue(0);
		sliderVolume = new JSlider();
		sliderVolume.addChangeListener(this);
		
		lblVolume.setText(Consts.LBL_VOLUME+"/50%");
		
		sliderVolume.setOrientation(SwingConstants.VERTICAL);
		
		fileList = new JList<File>();
		fileList.addListSelectionListener(this);
		
		//String[] data = {"1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3"};
		//fileList.setListData(data);
		
		TitledBorder titled = new TitledBorder(title);
	    setBorder(titled);

	    initLayout();
		

	}
	public void setAudioFiles(ArrayList<File> list){
		fileList.setListData(list.toArray(new File[0]));
		fileList.setSelectedIndex(fileIndex);
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void startPlaying() {
		// TODO Auto-generated method stub
		isStop = false;
		isPaused=false;
		File soundFile = fileList.getSelectedValue();
		try {
			player.setAudioFile(soundFile);
			setVolume(sliderVolume.getValue());
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		player.startPlaying();
	}
	@Override
	public void stopPlaying() {
		// TODO Auto-generated method stub
		isPaused=false;
		isStop = true;
		player.stopPlaying();
		
	}
	@Override
	public void pausePlaying() {
		// TODO Auto-generated method stub
		isPaused=true;
		isStop = false;
		player.pausePlaying();
	}
	
	@Override
	public void updateTimeLbl(String time) {
		// TODO Auto-generated method stub
		lblTime.setText(Consts.LBL_TIME+time);
	}
	
	@Override
	public void updateSlider(int percentage) {
		// TODO Auto-generated method stub
		sliderTime.setValue(percentage);
	}

	@Override
	public void updatePlayerStatus(PlayerStatus playerStatus) {
		// TODO Auto-generated method stub
		switch(playerStatus){
		case START:
			break;
		case PAUSE:
			break;
		case STOP:
			if(!isPaused){
				fileIndex = (fileIndex+1)%fileList.getModel().getSize();
			}
			fileList.setSelectedIndex(fileIndex);
			System.out.println(fileIndex);
			if(!isStop && !isPaused){
				startPlaying();
			}
			break;
		}
	}
	
	@Override
	public void setVolume(int presentage) {
		// TODO Auto-generated method stub
		player.setVolume(presentage);
	}
	@Override
	public void setVolume(double db) {
		// TODO Auto-generated method stub
		player.setVolume(db);
	}
	@Override
	public double getMaxDb() {
		// TODO Auto-generated method stub
		return player.getMaxDb();
	}
	@Override
	public double getMinDb() {
		// TODO Auto-generated method stub
		return player.getMinDb();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
			if(e.getSource().equals(sliderVolume)){
				int presentage = sliderVolume.getValue();
				setVolume(presentage);
				int db = (int)PlayerMath.persenteageToDb(presentage, getMaxDb(), getMinDb());
				lblVolume.setText(Consts.LBL_VOLUME+db+"/"+presentage+"%");
			}
	}
	
	private void initLayout(){
		
		JScrollPane jScrollPane = new JScrollPane(fileList);
		JLabel lblFileList = new JLabel(Consts.LBL_FILE_LIST);
		lblFileList.setFont(new Font("Consolas", Font.BOLD, 16));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(sliderTime, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
					.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(72)
					.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(lblVolume, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
					.addComponent(lblFileList, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(85))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTime)
								.addComponent(lblFileList, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblVolume))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(77)
									.addComponent(sliderTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(40)
							.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(60))
		);
		setLayout(groupLayout);
	}







}
