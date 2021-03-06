package GUI;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import core.Algorithm;
import utils.Configuration;
import utils.Consts;
import utils.WrongFileExeption;

import javax.swing.BoxLayout;

public class SoundMixerMainWindow extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainSoundPannel noisePanel,wordsPanel;
	private Configuration config;
	private MainControlPanel controlPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Set cross-platform Java L&F (also called "Metal")
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					SoundMixerMainWindow frame = new SoundMixerMainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SoundMixerMainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 700, 800);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		noisePanel = new MainSoundPannel("Noise");
		wordsPanel = new MainSoundPannel("Words");
		controlPanel = new MainControlPanel();
		controlPanel.setAudioConteler(noisePanel);
		controlPanel.setAudioConteler(wordsPanel);
		getContentPane().add(noisePanel);
		getContentPane().add(wordsPanel);
		getContentPane().add(controlPanel);
//		setContentPane(noisePanel);
				
		initMenu();

	}
	
	private void initMenu(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");

		JMenuItem menuItem = new JMenuItem(Consts.LOAD_CSV);
		menuFile.add(menuItem);
		menuItem.addActionListener(this);
		
		menuItem = new JMenuItem(Consts.CLEAR_RECORD);
		menuFile.add(menuItem);
		menuItem.addActionListener(this);
		
		menuItem = new JMenuItem(Consts.SAVE_RECORD);
		menuFile.add(menuItem);
		menuItem.addActionListener(this);
		
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
	}
	
	private File loadFile(){
		File loadedFile = null;
		FileDialog fd = new FileDialog(this, "Choose a config CSV file", FileDialog.LOAD);
		fd.setVisible(true);
		loadedFile = new File(fd.getFile());
		return loadedFile;
	}
	
	private File saveFile(){
		File loadedFile = null;
		FileDialog fd = new FileDialog(this, "Choose a config CSV file", FileDialog.SAVE);
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy-HHmmss");
		Date date = new Date();
		String filename = "SoundTestReport-"+dateFormat.format(date)+".csv";
		System.out.println(dateFormat.format(date));
		fd.setFile(filename);
		fd.setVisible(true);
		loadedFile = new File(fd.getFile());
		return loadedFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals(Consts.LOAD_CSV)){
			try{
				File f = loadFile();
				try {
					config = new Configuration(f);
					noisePanel.setAudioFiles(config.getNoiseFiles());
					wordsPanel.setAudioFiles(config.getWordsFiles());
					Algorithm al = new Algorithm(config.getSNR_levels());
					controlPanel.enableButtons();
					controlPanel.setAlgorithm(al);
				} catch (WrongFileExeption e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
			}catch (NullPointerException npe) {
//				npe.printStackTrace();
			}
		}else if(e.getActionCommand().equals(Consts.CLEAR_RECORD)){
			System.out.println(controlPanel.getRecords().size());
			controlPanel.clearRecord();
			System.out.println(controlPanel.getRecords().size());
		}else if(e.getActionCommand().equals(Consts.SAVE_RECORD)){
			try{
				File f = saveFile();
				config.saveRecord(f,controlPanel.getRecords());
			}catch (NullPointerException npe) {
				// TODO: handle exception
//				npe.printStackTrace();
			}
		}
	}

}
