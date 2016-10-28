package GUI;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

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
		setBounds(100, 100, 700, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		noisePanel = new MainSoundPannel("Noise");
		wordsPanel = new MainSoundPannel("Words");
		
		getContentPane().add(noisePanel);
		getContentPane().add(wordsPanel);
//		setContentPane(noisePanel);
		
		initMenu();

	}
	
	private void initMenu(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");

		JMenuItem menuItem = new JMenuItem("Load CSV");
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals(Consts.LOAD_CSV)){
			try{
				File f = loadFile();
				System.out.println(f);
				try {
					new Configuration(f);
				} catch (WrongFileExeption e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}catch (NullPointerException npe) {
				
			}
		}
	}

}
