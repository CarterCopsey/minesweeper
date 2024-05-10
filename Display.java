
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class Display extends Canvas implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	public static final int Width = 800;
	public static final int Height = 600;
	public static final String Title = "Sweeper of Mines";
	
	public static Display game = new Display();
	public static JFrame f = new JFrame();
	public static JButton startB, menuB[], bonusB[], backB, mainB, mainB2, againB, againB2;
	public static Tile tiles[][];
	public static JComboBox menuCB;
	public static JPanel p1, p2, p3, p4, p5, p6;
	public static JTextField score;
	public static int difficulty, bombs, flags, time1, time2, scoreInt;
	public static boolean firstClick;
	
	public static void main(String[] args) {
		initialize();
		p1.setVisible(true);
	}
	
	public static void startMenu() {
		startB = new JButton();
		startB.setIcon(new ImageIcon("images\\startmenu.jpg"));
		startB.setPreferredSize(new Dimension(Width, Height));
		startB.setBorder(null);
		startB.setContentAreaFilled(false);
		startB.addActionListener(game);
		p1 = new JPanel();
		p1.setSize(Width, Height);
		p1.setLayout(new GridLayout(1,1));
		p1.setBackground(new Color(50, 100, 50));
		p1.add(startB);
		p1.setVisible(false);
		f.add(p1);
	}

	public static void mainMenu() {
		p2 = new JPanel();
		p2.setSize(Width, Height);
		p2.setBackground(new Color(50, 100, 50));
		p2.setLayout(new GridBagLayout());
		
		menuB = new JButton[3];
		menuB[0] = new JButton("PLAY GAME");
		menuB[1] = new JButton("BONUS");
		menuB[2] = new JButton("QUIT GAME");
		for(int i=0; i<3; i++) {
			menuB[i].setUI(new ButtonStyle());
			menuB[i].setBorder(null);
			menuB[i].setOpaque(false);
			menuB[i].setFont(new Font("Courier New", Font.BOLD, 30));
			menuB[i].setPreferredSize(new Dimension(200, 80));
			menuB[i].addActionListener(game);
		}
		
		String[] difficultyStrings = {"Super Easy", "Easy", "Medium", "Hard", "Super Hard"};
		menuCB = new JComboBox(difficultyStrings);
		menuCB.setFont(new Font("Courier New", Font.BOLD, 30));
		menuCB.setSelectedIndex(2);
		menuCB.setBorder(null);
		menuCB.setOpaque(true);
		
		JTextField filler = new JTextField(); 
		filler.setForeground(null);
		filler.setBackground(null);
		filler.setEditable(false);
		filler.setBorder(null);
		filler.setPreferredSize(menuCB.getPreferredSize());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(15, 15, 15, 15);
		c.gridx = 2 ;
		c.gridy = 1 ;
		p2.add(menuB[0], c);
		c.gridx = 2;
		c.gridy = 2;
		p2.add(menuB[1], c);
		c.gridx = 2;
		c.gridy = 3;
		p2.add(menuB[2], c);
		c.gridx = 1;
		c.gridy = 1;
		p2.add(menuCB, c);
		c.gridx = 3;
		c.gridy = 1;
		p2.add(filler, c);
		p2.setVisible(false);
		f.add(p2);
	}
	
	public static void gameMenu(){
		updateGame();
		firstClick=true;
		p3 = new JPanel();
		p3.setSize(Width, Height);
		p3.setLayout(new GridLayout(difficulty, difficulty));
		p3.setBackground(new Color(50, 100, 50));
		tiles = new Tile[difficulty][difficulty];
		for(int i=0; i<difficulty; i++) {
			for(int j=0;j<difficulty;j++) {
				tiles[i][j] = new Tile();
				tiles[i][j].setBackground(new Color(130, 230, 50));
				if(i==j||i%2==0&&j%2==0||i%2!=0&&j%2!=0) {
					tiles[i][j].setBackground(new Color(120, 200, 50));
				}
				tiles[i][j].setBorder(null);
				tiles[i][j].setOpaque(true);
				tiles[i][j].addActionListener(game);
				tiles[i][j].addMouseListener(game);
				p3.add(tiles[i][j]);
			}
		}
		extraBombs();
		p3.setVisible(false);
		f.add(p3);
	}
	
	public static void bonusMenu() {
		p4 = new JPanel();
		p4.setSize(Width, Height);
		p4.setLayout(new GridBagLayout());
		p4.setBackground(new Color(50, 100, 50));
		bonusB = new JButton[4];
		bonusB[0] = new JButton("COMING SOON");
		bonusB[1] = new JButton("COMING SOON");
		bonusB[2] = new JButton("COMING SOON");
		bonusB[3] = new JButton("COMING SOON");
		for(int i=0; i<4; i++) {
			bonusB[i].setUI(new ButtonStyle());
			bonusB[i].setBorder(null);
			bonusB[i].setOpaque(false);
			bonusB[i].setFont(new Font("Courier New", Font.BOLD, 30));
			bonusB[i].setPreferredSize(new Dimension(210, 80));
			bonusB[i].addActionListener(game);
		}
		
		backB = new JButton("BACK");
		backB.setUI(new ButtonStyle());
		backB.setBorder(null);
		backB.setOpaque(false);
		backB.setFont(new Font("Courier New", Font.BOLD, 30));
		backB.addActionListener(game);
		backB.setPreferredSize(new Dimension(100, 40));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(15, 15, 15, 15);
		c.gridx = 2;
		c.gridy = 0;
		p4.add(bonusB[0], c);
		c.gridy = 1;
		p4.add(bonusB[1], c);
		c.gridy = 2;
		p4.add(bonusB[2], c);
		c.gridy = 3;
		p4.add(bonusB[3], c);
		c.gridy = 4;
		p4.add(backB, c);
		
		p4.setVisible(false);
		f.add(p4);
	}
	
	public static void winnerMenu() {
		p5 = new JPanel();
		p5.setSize(Width, Height);
		p5.setLayout(new GridBagLayout());
		p5.setBackground(new Color(50, 100, 50));
		
		mainB = new JButton("MAIN MENU");
		mainB.setUI(new ButtonStyle());
		mainB.setBorder(null);
		mainB.setOpaque(false);
		mainB.setFont(new Font("Courier New", Font.BOLD, 30));
		mainB.setPreferredSize(new Dimension(200, 90));
		mainB.addActionListener(game);
		
		againB = new JButton("PLAY AGAIN");
		againB.setUI(new ButtonStyle());
		againB.setBorder(null);
		againB.setOpaque(false);
		againB.setFont(new Font("Courier New", Font.BOLD, 30));
		againB.setPreferredSize(new Dimension(200, 90));
		againB.addActionListener(game);
	
		score = new JTextField("SCORE:"+ scoreInt);
		score.setFont(new Font("Courier New", Font.BOLD, 30));
		score.setForeground(Color.white);
		score.setBackground(null);
		score.setEditable(false);
		score.setBorder(null);
		score.setOpaque(true);
		
		JTextField filler = new JTextField(); 
		filler.setForeground(null);
		filler.setBackground(null);
		filler.setEditable(false);
		filler.setBorder(null);
		filler.setPreferredSize(menuCB.getPreferredSize());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(15, 15, 15, 15);
		c.gridx = 1;
		c.gridy = 3;
		p5.add(filler, c);
		c.gridx = 3;
		c.gridy = 3;
		p5.add(filler , c);
		c.gridx = 1;
		c.gridy = 4;
		p5.add(againB, c);
		c.gridx = 3;
		p5.add(mainB, c);
		c.gridx = 1;
		c.gridy = 5;
		p5.add(score, c);
		
		p5.setVisible(false);
		f.add(p5);
	}
	
	public static void loserMenu() {
		p6 = new JPanel();
		p6.setSize(Width, Height);
		p6.setLayout(new GridBagLayout());
		p6.setBackground(new Color(50, 100, 50));
		
		mainB2 = new JButton("MAIN MENU");
		mainB2.setUI(new ButtonStyle());
		mainB2.setBorder(null);
		mainB2.setOpaque(false);
		mainB2.setFont(new Font("Courier New", Font.BOLD, 30));
		mainB2.setPreferredSize(new Dimension(200, 90));
		mainB2.addActionListener(game);
		
		againB2 = new JButton("PLAY AGAIN");
		againB2.setUI(new ButtonStyle());
		againB2.setBorder(null);
		againB2.setOpaque(false);
		againB2.setFont(new Font("Courier New", Font.BOLD, 30));
		againB2.setPreferredSize(new Dimension(200, 90));
		againB2.addActionListener(game);
	
		score = new JTextField("SCORE:"+ scoreInt);
		score.setFont(new Font("Courier New", Font.BOLD, 30));
		score.setForeground(Color.white);
		score.setBackground(null);
		score.setEditable(false);
		score.setBorder(null);
		score.setOpaque(true);
		
		JTextField filler = new JTextField(); 
		filler.setForeground(null);
		filler.setBackground(null);
		filler.setEditable(false);
		filler.setBorder(null);
		filler.setPreferredSize(menuCB.getPreferredSize());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(15, 15, 15, 15);
		c.gridx = 1;
		c.gridy = 2;
		p6.add(filler, c);
		c.gridx = 1;
		p6.add(filler, c);
		c.gridx = 1;
		c.gridy = 4;
		p6.add(againB2, c);
		c.gridx = 2;
		p6.add(mainB2, c);
		
		p6.setVisible(false);
		f.add(p6);
	}
	
	public static void initialize() {
		startMenu();
		mainMenu();
		gameMenu();
		bonusMenu();
		winnerMenu();
		loserMenu();
		
		f.pack();
		f.setTitle(Title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(Width, Height);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource()==startB) {
				p1.setVisible(false);
				p2.setVisible(true);
			}
			if(e.getSource()==menuB[0]) {
				p2.setVisible(false);
				gameMenu();
				time1 = (int) System.currentTimeMillis();
				p3.setVisible(true);
			}
			if(e.getSource()==menuB[1]) {
				p2.setVisible(false);
				p4.setVisible(true);
			}
			if(e.getSource()==menuB[2]) {
				System.exit(0);
			}
			if(e.getSource()==backB) {
				p4.setVisible(false);
				p2.setVisible(true);
			}
			if(e.getSource()==mainB||e.getSource()==mainB2) {
				p6.setVisible(false);
				p5.setVisible(false);
				p2.setVisible(true);
			}
			if(e.getSource()==againB||e.getSource()==againB2) {
				p6.setVisible(false);
				p5.setVisible(false);
				gameMenu();
				time1 = (int) System.currentTimeMillis();
				p3.setVisible(true);
			}
			for(int i=0; i<difficulty; i++) {
				for(int j=0; j<difficulty;j++) {
					if(e.getSource()==tiles[i][j]) {
						if(firstClick==true) {
							firstClick(i, j);
						}
						if(tiles[i][j].bomb==true&&tiles[i][j].flag==false) {
						    p3.setVisible(false);
							p6.setVisible(true);
						}
						else if(tiles[i][j].flag==false){
							tiles[i][j].setBackground(new Color(0, 120, 200));
							tiles[i][j].safe=true;
							tiles[i][j].setFocusPainted(false);
							if(tiles[i][j].nearbyBombs!=0) {
								tiles[i][j].setText(Integer.toString(tiles[i][j].nearbyBombs));
							}
							tiles[i][j].setForeground(Color.white);
							if(tiles[i][j].nearbyBombs==0) {
								breakBoard(i, j);
							}
						}
					}
				}
			}
		}
		catch(NullPointerException e1) {}
		catch(ArrayIndexOutOfBoundsException e2) {
			System.out.println("getsource error");
		}
	}
	
	public void mouseClicked(MouseEvent e) {
        for(int i=0; i<difficulty; i++) {
    		for(int j=0;j<difficulty;j++) {
    			if (e.getSource()==tiles[i][j] && SwingUtilities.isRightMouseButton(e)){
    				if(tiles[i][j].safe==false) {
    					if(tiles[i][j].flag==false) {
    						tiles[i][j].setIcon(new ImageIcon("images\\flag.png"));
    						tiles[i][j].flag=true;
    					}
    					else if(tiles[i][j].flag==true){
    						tiles[i][j].setIcon(null);
    						tiles[i][j].flag=false;
    					}
    				}
    				winCheck();
    			}
        	}
        }
    }
	
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	
	public static void updateGame() {
		flags=0;
		bombs=0;
		if(menuCB.getSelectedIndex()==0) {
			difficulty = 9;
			bombs = 15;
		}
		if(menuCB.getSelectedIndex()==1){
			difficulty = 13;
			bombs = 35;
		}
		if(menuCB.getSelectedIndex()==2) {
			difficulty = 17;
			bombs = 60;
		}
		if(menuCB.getSelectedIndex()==3) {
			difficulty = 21;
			bombs = 90;
		}
		if(menuCB.getSelectedIndex()==4) {
			difficulty = 25;
			bombs = 125;
		}
	}
	
	public static void extraBombs() {
		try {
			while(bombs!=0) {
				double tileXd = (Math.random()*(difficulty-1));
				int tileXi = (int)Math.round(tileXd);
				double tileYd = (Math.random()*(difficulty-1));
				int tileYi = (int)Math.round(tileYd);
				if(tiles[tileXi][tileYi].bomb==false) {
					tiles[tileXi][tileYi].bomb=true;
					bombs--;
					flags++;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e3) {
			System.out.println("extrabombs error");
		}
	}
	
	public static void locateBombs() {
		try {
			for(int i=0;i<difficulty; i++) {
				for(int j=0;j<difficulty;j++) {
					if(!tiles[i][j].bomb) {
		                if(i>0&&j>0&&tiles[i-1][j-1].bomb){
		                    tiles[i][j].nearbyBombs++;
		                }
		                if(i>0&&tiles[i-1][j].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
		                if(i>0&&j<difficulty-1&&tiles[i-1][j+1].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
		                if(j>0&&tiles[i][j-1].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
		                if(j<difficulty-1&&tiles[i][j+1].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
		                if(i<difficulty-1&&j>0&&tiles[i+1][j-1].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
		                if(i<difficulty-1&&tiles[i+1][j].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
		                if(i<difficulty-1&&j<difficulty-1&&tiles[i+1][j+1].bomb){
		                	tiles[i][j].nearbyBombs++;
		                }
					}
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e4) {
			System.out.println("locatebombs error");
		}
	}
	
	public void winCheck() {
		int trueFlags = 0;
		int falseFlags = 0;
		for(int i=0;i<difficulty; i++) {
			for(int j=0;j<difficulty;j++) {
				if(tiles[i][j].bomb==true&&tiles[i][j].flag==true) 
					trueFlags++;
				else if(tiles[i][j].bomb==false&&tiles[i][j].flag==true) 
					falseFlags++;
			}
		}
		if(trueFlags==flags&&falseFlags==0) {
			time2 = (int)System.currentTimeMillis();
			scoreInt=((time2-time1)/1000);
			winnerMenu();
			loserMenu();
			p3.setVisible(false);
			p5.setVisible(true);
		}
	}
	
	public void breakBoard(int i, int j) {
		if(i>0&&j>0&&tiles[i-1][j-1].safe==false){
			if(tiles[i-1][j-1].flag==false&&tiles[i-1][j-1].bomb==false){
				tiles[i-1][j-1].setBackground(new Color(0, 120, 200));
				tiles[i-1][j-1].safe=true;
				tiles[i-1][j-1].setFocusPainted(false);
				if(tiles[i-1][j-1].nearbyBombs!=0) {
					tiles[i-1][j-1].setText(Integer.toString(tiles[i-1][j-1].nearbyBombs));
				}
				tiles[i-1][j-1].setForeground(Color.white);
				if(tiles[i-1][j-1].nearbyBombs==0) {
					breakBoard(i-1, j-1);
				}
			}
        }
        if(i>0&&tiles[i-1][j].safe==false){
        	if(tiles[i-1][j].flag==false&&tiles[i-1][j].bomb==false){
				tiles[i-1][j].setBackground(new Color(0, 120, 200));
				tiles[i-1][j].safe=true;
				tiles[i-1][j].setFocusPainted(false);
				if(tiles[i-1][j].nearbyBombs!=0) {
					tiles[i-1][j].setText(Integer.toString(tiles[i-1][j].nearbyBombs));
				}
				tiles[i-1][j].setForeground(Color.white);
				if(tiles[i-1][j].nearbyBombs==0) {
					breakBoard(i-1, j);
				}
			}
        }
        if(i>0&&j<difficulty-1&&tiles[i-1][j+1].safe==false){
        	if(tiles[i-1][j+1].flag==false&&tiles[i-1][j+1].bomb==false){
				tiles[i-1][j+1].setBackground(new Color(0, 120, 200));
				tiles[i-1][j+1].safe=true;
				tiles[i-1][j+1].setFocusPainted(false);
				if(tiles[i-1][j+1].nearbyBombs!=0) {
					tiles[i-1][j+1].setText(Integer.toString(tiles[i-1][j+1].nearbyBombs));
				}
				tiles[i-1][j+1].setForeground(Color.white);
				if(tiles[i-1][j+1].nearbyBombs==0) {
					breakBoard(i-1, j+1);
				}
			}
        }
        if(j>0&&tiles[i][j-1].safe==false){
        	if(tiles[i][j-1].flag==false&&tiles[i][j-1].bomb==false){
				tiles[i][j-1].setBackground(new Color(0, 120, 200));
				tiles[i][j-1].safe=true;
				tiles[i][j-1].setFocusPainted(false);
				if(tiles[i][j-1].nearbyBombs!=0) {
					tiles[i][j-1].setText(Integer.toString(tiles[i][j-1].nearbyBombs));
				}
				tiles[i][j-1].setForeground(Color.white);
				if(tiles[i][j-1].nearbyBombs==0) {
					breakBoard(i, j-1);
				}
			}
        }
        if(j<difficulty-1&&tiles[i][j+1].safe==false){
        	if(tiles[i][j+1].flag==false&&tiles[i][j+1].bomb==false){
				tiles[i][j+1].setBackground(new Color(0, 120, 200));
				tiles[i][j+1].safe=true;
				tiles[i][j+1].setFocusPainted(false);
				if(tiles[i][j+1].nearbyBombs!=0) {
					tiles[i][j+1].setText(Integer.toString(tiles[i][j+1].nearbyBombs));
				}
				tiles[i][j+1].setForeground(Color.white);
				if(tiles[i][j+1].nearbyBombs==0) {
					breakBoard(i, j+1);
				}
			}
        }
        if(i<difficulty-1&&j>0&&tiles[i+1][j-1].safe==false){
        	if(tiles[i+1][j-1].flag==false&&tiles[i+1][j-1].bomb==false){
				tiles[i+1][j-1].setBackground(new Color(0, 120, 200));
				tiles[i+1][j-1].safe=true;
				tiles[i+1][j-1].setFocusPainted(false);
				if(tiles[i+1][j-1].nearbyBombs!=0) {
					tiles[i+1][j-1].setText(Integer.toString(tiles[i+1][j-1].nearbyBombs));
				}
				tiles[i+1][j-1].setForeground(Color.white);
				if(tiles[i+1][j-1].nearbyBombs==0) {
					breakBoard(i+1, j-1);
				}
			}
        }
        if(i<difficulty-1&&tiles[i+1][j].safe==false){
        	if(tiles[i+1][j].flag==false&&tiles[i+1][j].bomb==false){
				tiles[i+1][j].setBackground(new Color(0, 120, 200));
				tiles[i+1][j].safe=true;
				tiles[i+1][j].setFocusPainted(false);
				if(tiles[i+1][j].nearbyBombs!=0) {
					tiles[i+1][j].setText(Integer.toString(tiles[i+1][j].nearbyBombs));
				}
				tiles[i+1][j].setForeground(Color.white);
				if(tiles[i+1][j].nearbyBombs==0) {
					breakBoard(i+1, j);
				}
			}
        }
        if(i<difficulty-1&&j<difficulty-1&&tiles[i+1][j+1].safe==false){
        	if(tiles[i+1][j+1].flag==false&&tiles[i+1][j+1].bomb==false){
				tiles[i+1][j+1].setBackground(new Color(0, 120, 200));
				tiles[i+1][j+1].safe=true;
				tiles[i+1][j+1].setFocusPainted(false);
				if(tiles[i+1][j+1].nearbyBombs!=0) {
					tiles[i+1][j+1].setText(Integer.toString(tiles[i+1][j+1].nearbyBombs));
				}
				tiles[i+1][j+1].setForeground(Color.white);
				if(tiles[i+1][j+1].nearbyBombs==0) {
					breakBoard(i+1, j+1);
				}
			}
        }
	}
	
	public void firstClick(int i, int j) {
		if(firstClick==true) {
            if(i>0&&j>0){
                if(tiles[i-1][j-1].bomb) {
                	tiles[i-1][j-1].bomb=false;
                	Display.flags--;
                }
            }
            if(i>0){
            	if(tiles[i-1][j].bomb) {
            		tiles[i-1][j].bomb=false;
                	Display.flags--;
            	}
            }
            if(i>0&&j<difficulty-1){
            	if(tiles[i-1][j+1].bomb) {
            		tiles[i-1][j+1].bomb=false;
                	Display.flags--;
            	}
            }
            if(j>0){
            	if(tiles[i][j-1].bomb) {
            		tiles[i][j-1].bomb=false;
                	Display.flags--;
            	}
            }     
            if(j<difficulty-1){
            	if(tiles[i][j+1].bomb) {
            		tiles[i][j+1].bomb=false;
                	Display.flags--;
            	}
            }
            if(i<difficulty-1&&j>0){
            	if(tiles[i+1][j-1].bomb) {
            		tiles[i+1][j-1].bomb=false;
                	Display.flags--;
            	}
            }
            if(i<difficulty-1){
            	if(tiles[i+1][j].bomb) {
            		tiles[i+1][j].bomb=false;
                	Display.flags--;
            	}
            }
            if(i<difficulty-1&&j<difficulty-1){
            	if(tiles[i+1][j+1].bomb) {
            		tiles[i+1][j+1].bomb=false;
                	Display.flags--;
            	}
            }
            if(tiles[i][j].bomb) {
        		tiles[i][j].bomb=false;
            	Display.flags--;
        	}
            locateBombs();
			firstClick=false;
		}
	}
}

class Tile extends JButton{
	private static final long serialVersionUID = 1L;
	
	public boolean bomb;
	public boolean safe = false;
	public boolean flag = false;
	public int nearbyBombs;
	
	public Tile() {
		super("");
		if(Math.random()<=.2) {
			if(Display.bombs>0) {
				bomb = true;
				Display.bombs--;
				Display.flags++;
			}
		}
		else {
			bomb = false;
		}
	}
}

class ButtonStyle extends BasicButtonUI {
	
    @Override
    public void paint (Graphics g, JComponent c) {
    	AbstractButton b = (AbstractButton)c;
    	paintBackground(g, b, b.getModel().isPressed() ? 3 : 0);
    	super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int shift) {
        Dimension d = c.getSize();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, shift, d.width, d.height - shift, 15, 15);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, shift, d.width, d.height + shift - 6, 15, 15);
    }
}