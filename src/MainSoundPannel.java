import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class MainSoundPannel extends JPanel {
	
	JLabel lblTime, lblVolume;
	JSlider sliderTime, sliderVolume;
	private JList<String> fileList;

	/**
	 * Create the panel.
	 */
	public MainSoundPannel(String title) {

		lblTime = new JLabel("Time : ");
		lblTime.setFont(new Font("Consolas", Font.BOLD, 16));
		lblVolume = new JLabel("Volume : ");
		lblVolume.setFont(new Font("Consolas", Font.BOLD, 16));

		sliderTime = new JSlider();
		sliderVolume = new JSlider();
		sliderVolume.setOrientation(SwingConstants.VERTICAL);
		

		
		fileList = new JList<String>();
		
		
		//String[] data = {"1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3"};
		//fileList.setListData(data);
		
		TitledBorder titled = new TitledBorder(title);
	    setBorder(titled);

	    initLayout();
		

	}
	
	private void initLayout(){
		
		JScrollPane jScrollPane = new JScrollPane(fileList);
		JLabel lblFileList = new JLabel("Files :");
		lblFileList.setFont(new Font("Consolas", Font.BOLD, 16));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(sliderTime, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(sliderVolume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
					.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(72)
					.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(89)
					.addComponent(lblVolume, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
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
