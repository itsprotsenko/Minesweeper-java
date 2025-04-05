package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Level implements ActionListener{
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	double scrnW = screensize.getWidth();
	double scrnH = screensize.getHeight();
	
	Sound bgMusic;
	JFrame frame = new JFrame();
	
	JButton musicPS = new JButton();
	JLabel musicVol = new JLabel("VOLUME");
	boolean musicPlaying = true;
	JSlider volume = new JSlider(-80, 6 ,-3);
	
	JPanel panelTitle = new JPanel();
	JLabel mineIcon1 = new JLabel();
	JLabel mineIcon2 = new JLabel();
	JLabel title = new JLabel("MINESWEEPER", SwingConstants.CENTER);
	JLabel falgCount = new JLabel("10");
	int flags = 10;
	JButton reset = new JButton("NEW GAME");
	JLabel gameEnd = new JLabel("");
	
	JPanel panelGame = new JPanel();
	
	JPanel panelMusic = new JPanel();
	JPanel Test = new JPanel();
	
	Image image1 = new ImageIcon("game/images/bomb.png").getImage();
	ImageIcon img1 = new ImageIcon(image1.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
	ImageIcon imgbomb = new ImageIcon(image1.getScaledInstance(36, 36, java.awt.Image.SCALE_SMOOTH));
	Image image2 = new ImageIcon("game/images/playpause.png").getImage();
	ImageIcon img2 = new ImageIcon(image2.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH));
	Image image3 = new ImageIcon("game/images/flag.png").getImage();
	ImageIcon imgflag = new ImageIcon(image3.getScaledInstance(36, 36, java.awt.Image.SCALE_SMOOTH));
	
	Border labelBorder = BorderFactory.createLineBorder(Color.black);
	
	private final static int boxesW = 9;
	private final static int boxesH = 9;
	
	int[][] field = new int[boxesW][boxesH];
	JLabel[][] boxes = new JLabel[boxesW][boxesH];
	boolean[][] boxOpen = new boolean[boxesW][boxesH];
	boolean[][] boxFlag = new boolean[boxesW][boxesH];
	boolean setup = true;
	boolean alive = true;
	boolean[][] checked = new boolean[boxesW][boxesH];
	
	public Level(int frameW, int frameH, int bgMusicFrame, URL bgMusicURL) throws Exception{
		bgMusic = new Sound(bgMusicURL);
		bgMusic.loop(bgMusicFrame);
		
		bgMusic.setVolume(-2.6f);
		
		frame.add(panelTitle);
		panelTitle.setBounds(0, 0, frameW, 95);
		panelTitle.setVisible(true);
		panelTitle.setBorder(labelBorder);
		panelTitle.setLayout(null);
		
		panelTitle.add(title);
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		title.setHorizontalAlignment(2);
		title.setBounds(65, 30, 220, 25);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		title.setVisible(true);
		
		panelTitle.add(falgCount);
		falgCount.setBounds(65, 70, 50, 25);
		falgCount.setBackground(Color.black);
		falgCount.setForeground(Color.red);
		falgCount.setOpaque(true);
		falgCount.setFont(new Font("serif", Font.PLAIN, 20));
		
		panelTitle.add(reset);
		reset.setBounds(130, 70, 150, 25);
		reset.setOpaque(true);
		reset.setFont(new Font("serif", Font.PLAIN, 20));
		reset.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				flags = 10;
				falgCount.setText("10");
				setup = true;
				Test.setVisible(false);
				alive = true;
				field = new int[boxesW][boxesH];
				for(int i = 0; i < boxesW; i++) {
					for(int j = 0; j<boxesH; j++) {
						boxes[i][j].setBackground(Color.cyan);
						boxes[i][j].setText("");
						boxes[i][j].setIcon(null);
						boxOpen[i][j] = false;
						boxFlag[i][j] = false;
						checked[i][j] = false;
					}
				}
			}
		});
		
		
		panelTitle.add(mineIcon1);
		mineIcon1.setBounds(5, 20, 50, 50);
		mineIcon1.setIcon(img1);
		mineIcon1.setVisible(true);
		panelTitle.add(mineIcon2);
		mineIcon2.setIcon(img1);
		mineIcon2.setBounds(295, 20, 50, 50);
		mineIcon2.setVisible(true);
		
		Test.setBounds(75, 225, 200, 40);
		Test.setOpaque(true);
		Test.setVisible(false);
		frame.add(Test);
		
		Test.add(gameEnd);
		gameEnd.setBounds(0, 0, 200 ,40);
		gameEnd.setOpaque(true);
		gameEnd.setVisible(true);
		gameEnd.setFont(new Font("serif", Font.BOLD, 20));
		gameEnd.setHorizontalAlignment(SwingConstants.CENTER);
		gameEnd.setVerticalAlignment(SwingConstants.CENTER);
		
		frame.add(panelMusic);
		panelMusic.setBounds(0, 450, frameW, 50);
		panelMusic.setVisible(true);
		panelMusic.setLayout(null);
		
		panelMusic.add(musicPS);
		musicPS.setBounds(15, 10, 25, 25);
		musicPS.setIcon(img2);
		musicPS.setVisible(true);
		musicPS.addActionListener(this);
		
		panelMusic.add(volume);
		volume.setBounds(55, 10, 200, 25);
		volume.setVisible(true);
		volume.addChangeListener(new ChangeListener( ) {
			@Override
			public void stateChanged(ChangeEvent e) {
				bgMusic.setVolume(volume.getValue());
					
			}
		});
		
		
		panelGame.setBounds(4, 100, 342, 342);
		panelGame.setLayout(new GridLayout(boxesW, boxesH));
		panelGame.setVisible(true);
		
		for(int i = 0; i<boxesW; i++) {
			for(int j = 0; j<boxesH; j++) {
				int x = i;
				int y = j;
				
				boxOpen[x][y] = false;
				boxFlag[x][y] = false;
				boxes[x][y] = new JLabel();
				boxes[x][y].setText("");
				boxes[x][y].setBorder(labelBorder);
				boxes[x][y].setBackground(Color.cyan);
				boxes[x][y].setOpaque(true);
				
				boxes[x][y].addMouseListener(new MouseAdapter() {
						
					public void mouseClicked(MouseEvent e) {
						if(setup && alive) {
							field = generateBombs(field, boxesW, boxesH, x, y);
							setup = false;
						}
						else {
							if(e.getButton()==MouseEvent.BUTTON1 && !boxFlag[x][y] && alive) {
								int sum=0;
								if(!boxOpen[x][y]) {
									boxOpen[x][y] = true;
									boxes[x][y].setBackground(Color.lightGray);
									boxes[x][y].setText(Integer.toString(field[x][y]));
									clearSpace(x, y);
									
									for(int i = 0; i<9;i++) {
										for(int j = 0;j<9;j++) {
											if(boxOpen[i][j]) {
												sum++;
											}
										}
									}
									if(sum == 71) {
										alive = false;
										Test.setVisible(true);
										gameEnd.setText("I am Surpirsed");
									}
								}
								else {
									clearSurounding(x, y);
									for(int i = 0; i<9;i++) {
										for(int j = 0;j<9;j++) {
											if(boxOpen[i][j]) {
												sum++;
											}
										}
									}
									if(sum == 71) {
										alive = false;
										Test.setVisible(true);
										gameEnd.setText("I am Surpirsed");
									}
								}
							}
							if(e.getButton()==MouseEvent.BUTTON3 && !boxOpen[x][y] && alive) {
								if(!boxFlag[x][y] && flags>0) {
									boxes[x][y].setIcon(imgflag);
									boxFlag[x][y] = true;
									flags--;
									falgCount.setText(Integer.toString(flags));
								}
								else if(boxFlag[x][y]) {
									boxes[x][y].setIcon(null);
									boxFlag[x][y] = false;
									flags++;
									falgCount.setText(Integer.toString(flags));
								}
							
							}
						}
						
						
					}
				});
				panelGame.add(boxes[x][y]);
			}
		}
		frame.add(panelGame);
		
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
	
	private void clearSpace(int x, int y) {
		if(field[x][y] == 0) {
			clearBlank(x, y);
		}
		if(field[x][y] >= 10) {
			boxes[x][y].setIcon(imgbomb);
			alive = false;
			Test.setVisible(true);
			gameEnd.setText("You Lose, Get Good");
		}
		
	}
	private void clearSurounding(int cx, int cy) {
		int flags =0;
		if(!boxFlag[cx][cy]) {
			for(int x=cx-1;x<=cx+1;x++) {
				if(x>-1 && x<9) {
					for(int y=cy-1;y<=cy+1;y++) {
					if(y>-1 && y<9) {
						if(boxFlag[x][y]) {
						flags++;
					}
					}
					
				}
				}
				
			}
			if(flags == field[cx][cy]) {
				for(int x=cx-1;x<=cx+1;x++) {
					for(int y=cy-1;y<=cy+1;y++) {
						if(y>-1 && y<9 && x>-1 && x<9) {
							if(!boxFlag[x][y]) {
								boxOpen[x][y] = true;
								
								boxes[x][y].setBackground(Color.lightGray);
								boxes[x][y].setText(Integer.toString(field[x][y]));
								clearSpace(x, y);
							}
						}
						
					}
				}
			}
		}
		
	}
	
	private void clearBlank(int xpos, int ypos) {
		switch(xpos) {
		case 0:
			switch(ypos) {
			case 0:
				for(int x=xpos;x<=xpos+1;x++) {
					for(int y=ypos;y<=ypos+1;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
			case 8:
				for(int x=xpos;x<=xpos+1;x++) {
					for(int y=ypos-1;y<=ypos;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
			default:
				for(int x=xpos;x<=xpos+1;x++) {
					for(int y=ypos-1;y<=ypos+1;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
		}
			break;
		case 8:
			switch(ypos) {
			case 0:
				for(int x=xpos-1;x<=xpos;x++) {
					for(int y=ypos;y<=ypos+1;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
			case 8:
				for(int x=xpos-1;x<=xpos;x++) {
					for(int y=ypos-1;y<=ypos;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
			default:
				for(int x=xpos-1;x<=xpos;x++) {
					for(int y=ypos-1;y<=ypos+1;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
		}
			break;
		default:
			switch(ypos) {
			case 0:
				for(int x=xpos-1;x<=xpos+1;x++) {
					for(int y=ypos;y<=ypos+1;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
			case 8:
				for(int x=xpos-1;x<=xpos+1;x++) {
					for(int y=ypos-1;y<=ypos;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
			default:
				for(int x=xpos-1;x<=xpos+1;x++) {
					for(int y=ypos-1;y<=ypos+1;y++) {
						boxes[x][y].setBackground(Color.lightGray);
						boxes[x][y].setText(Integer.toString(field[x][y]));
						boxOpen[x][y]=true;
						
						if(field[x][y] == 0 && !checked[x][y]) {
							checked[x][y]=true;
							clearBlank(x, y);
						}
					}
				}
				break;
		}
			break;
	}
	}
	
	private int[][] generateBombs(int[][] arr, int w, int h, int strtx, int strty) {
		arr[strtx][strty] = 0;
		Random rand = new Random();
		int ranx, rany = -1;
		for(int i = 0;i<10; i++) {
			ranx = rand.nextInt(9);
			rany = rand.nextInt(9);
			if(arr[ranx][rany] >= 10 || ((strtx-1<=ranx)&&(strtx+1>=ranx)) || ((strty-1<=rany)&&(strty+1>=rany))) {
				i--;
			}
			else {
				arr[ranx][rany] = 10;
				switch(ranx) {
					case 0:
						switch(rany) {
						case 0:
							for(int x = ranx; x <= ranx+1;x++) {
								for(int y=rany;y<=rany+1;y++) {
									arr[x][y]++;
								}
							}
							break;
						case 8:
							for(int x = ranx; x <= ranx+1;x++) {
								for(int y=rany-1;y<=rany;y++) {
									arr[x][y]++;
								}
							}
							break;
						default:
							for(int x = ranx; x <= ranx+1;x++) {
								for(int y=rany-1;y<=rany+1;y++) {
									arr[x][y]++;
								}
							}
							break;
					}
						break;
					case 8:
						switch(rany) {
						case 0:
							for(int x = ranx-1; x <= ranx;x++) {
								for(int y=rany;y<=rany+1;y++) {
									arr[x][y]++;
								}
							}
							break;
						case 8:
							for(int x = ranx-1; x <= ranx;x++) {
								for(int y=rany-1;y<=rany;y++) {
									arr[x][y]++;
								}
							}
							break;
						default:
							for(int x = ranx-1; x <= ranx;x++) {
								for(int y=rany-1;y<=rany+1;y++) {
									arr[x][y]++;
								}
							}
							break;
					}
						break;
					default:
						switch(rany) {
						case 0:
							for(int x = ranx-1; x <= ranx+1;x++) {
								for(int y=rany;y<=rany+1;y++) {
									arr[x][y]++;
								}
							}
							break;
						case 8:
							for(int x = ranx-1; x <= ranx+1;x++) {
								for(int y=rany-1;y<=rany;y++) {
									arr[x][y]++;
								}
							}
							break;
						default:
							for(int x = ranx-1; x <= ranx+1;x++) {
								for(int y=rany-1;y<=rany+1;y++) {
									arr[x][y]++;
								}
							}
							break;
					}
						break;
				}
			}
		}
		clearSpace(strtx, strty);
		
		return arr;
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
		
	}
}
