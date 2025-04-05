package game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class StartWin implements ActionListener{
	
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	double scrnW = screensize.getWidth();
	double scrnH = screensize.getHeight();
	JLabel mineIcon1 = new JLabel();
	JLabel mineIcon2 = new JLabel();
	JButton lvlBeginner = new JButton("BEGINNER");
	
	JLabel title = new JLabel("MINESWEEPER", SwingConstants.CENTER);
	JButton musicPS = new JButton();
	JLabel musicVol = new JLabel("VOLUME");
	boolean musicPlaying = true;
	JSlider volume = new JSlider(-80, 6 ,-3);
	int frameW = 350;
	int frameH = 500;
	//ImageIcon img = new ImageIcon("C:\\Users\\alex\\Documents\\JAVAProjects\\MineSweeper\\bomb.png");
	Image image1 = new ImageIcon("game/images/bomb.png").getImage();
	ImageIcon img1 = new ImageIcon(image1.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
	
	Image image2 = new ImageIcon("game/images/playpause.png").getImage();
	ImageIcon img2 = new ImageIcon(image2.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
	Sound bgMusic = new Sound(new URL("file:game/images/bgMusic.wav"));
	public StartWin() throws Exception {
		bgMusic.reset();
		bgMusic.loop();
		bgMusic.setVolume(-2.6f);
		
		frame.add(lvlBeginner);
		lvlBeginner.setFont(new Font("Serif", Font.BOLD, 20));
		lvlBeginner.setHorizontalTextPosition(2);
		lvlBeginner.setBounds(100,80,150, 25);
		lvlBeginner.addActionListener(this);
        
		frame.add(title);
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		title.setHorizontalAlignment(2);
		title.setBounds(65, 30, 220, 25);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setVisible(true);
		
		frame.add(musicPS);
		musicPS.setBounds(15, 460, 25, 25);
		musicPS.setIcon(img2);
		musicPS.setVisible(true);
		musicPS.addActionListener(this);
		
		frame.add(mineIcon1);
		mineIcon1.setBounds(5, 20, 50, 50);
		mineIcon1.setIcon(img1);
		mineIcon1.setVisible(true);
		frame.add(mineIcon2);
		mineIcon2.setIcon(img1);
		mineIcon2.setBounds(295, 20, 50, 50);
		mineIcon2.setVisible(true);
		
		frame.add(volume);
		volume.setBounds(55, 460, 200, 25);
		volume.setVisible(true);
		volume.addChangeListener(new ChangeListener( ) {
			@Override
			public void stateChanged(ChangeEvent e) {
				bgMusic.setVolume(volume.getValue());
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Minesweeper");
		frame.setLocation((int)(scrnW/2 - frameW/2), (int)(scrnH/2 - frameH/2));
		frame.setIconImage(image1);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.getContentPane().setPreferredSize(new Dimension(frameW, frameH));
		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == musicPS) {
			if(musicPlaying == true) {
				bgMusic.stop();
				musicPlaying = false;
			}
			else {
				bgMusic.loop();
				musicPlaying = true;
			}
		}
		if(e.getSource() == lvlBeginner) {
			frame.dispose();
			try {
				Level lvl = new Level(350, 500, bgMusic.getFrame(),new URL("file:game/images/bgMusic.wav"));
				bgMusic.stop();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
