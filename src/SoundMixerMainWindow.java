import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class SoundMixerMainWindow extends JFrame {

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

	}

}
