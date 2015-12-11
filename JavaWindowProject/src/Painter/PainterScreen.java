package Painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PainterScreen extends JFrame {

	private int[] screen = { 1024, 768 }; // X, Y
	private int[] btn_Size = { 100, 25 }; // X, Y
	private int btn_index = 0;
	private int btn_Spacing = 10;
	private String[] btn_title = { "Line", "Rectangle", "Square", "Round", "Net", "Color", "Clean", "Exit" };
	private int btn_len = btn_title.length;
	private JButton[] btn_group = new JButton[btn_len];
	private JPanel jpn_Canvas;
	private Color color = Color.BLACK;
	private int[] MouseStart = new int[2];
	private int[] MouseEnd = new int[2];
	private int[] tmpStart = new int[2];
	private int[] tmpEnd = new int[2];
	private int[] tmpLen = new int[2];
	
	public PainterScreen() {
		initcomp();
	}

	public void initcomp() {

		this.setTitle("小畫家 by Kovich Ver. 1.0");
		this.getContentPane().setLayout(null); // 拿掉panel 座標才能使用
		this.setSize(screen[0], screen[1]); // 設定主畫面Size
		this.setLocationRelativeTo(null); // 設定顯示視窗置中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 設定完全關閉
		this.getContentPane().setBackground(Color.darkGray);

		for (int i = 0; i < btn_len; i++) {
			btn_group[i] = new JButton();
			btn_group[i].setText(btn_title[i]);
			btn_group[i].setBounds(btn_Spacing, ((btn_Size[1] + btn_Spacing) * i) + btn_Spacing, btn_Size[0], btn_Size[1]);
			PainterScreen.this.add(btn_group[i]);
		}
		
		//Line
		btn_group[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(0);
				btn_index = 0;
			}
		});
		
		//Rectangle
		btn_group[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(1);
				btn_index = 1;
			}
		});
		
		//Square
		btn_group[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(2);
				btn_index = 2;
			}
		});
		
		//Round
		btn_group[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(3);
				btn_index = 3;
			}
		});
		
		//ColorChoose
		btn_group[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(4);
				btn_index = 4;
			}
		});
				
		//ColorChoose
		btn_group[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(5);
				color = JColorChooser.showDialog(PainterScreen.this, "Choose Color", color);
			}
		});
		
		//Clean
		btn_group[6].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(6);
				jpn_Canvas.setBackground(color.WHITE);
				jpn_Canvas.repaint();
				btn_index = 0;
			}
		});
				
		//Exit
		btn_group[7].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(7);
				System.exit(0);
			}
		});
		
		jpn_Canvas = new JPanel();
		jpn_Canvas.setBounds(2 * btn_Spacing + btn_Size[0], btn_Spacing, screen[0] - (2 * btn_Spacing + btn_Size[0]), screen[1] - (2 * btn_Spacing));
		jpn_Canvas.setBackground(Color.WHITE);
		jpn_Canvas.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				MouseStart[0] = e.getX();
				MouseStart[1] = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				MouseEnd[0] = e.getX();
				MouseEnd[1] = e.getY();
				
				for(int i=0;i<2;i++){
					tmpStart[i] = Math.min(MouseStart[i], MouseEnd[i]);
					tmpEnd[i] = Math.max(MouseStart[i], MouseEnd[i]);
					tmpLen[i] = Math.abs(MouseEnd[i] - MouseStart[i]); //[0]X 軸距離  [1]Y 軸距離
				}
				
				Graphics g = jpn_Canvas.getGraphics();
				g.setColor(color);
				switch (btn_index) {
				case 0: //Line
					g.drawLine(MouseStart[0], MouseStart[1], MouseEnd[0], MouseEnd[1]);
					break;
				case 1: //Rectangle
					g.drawRect(tmpStart[0], tmpStart[1], tmpLen[0], tmpLen[1]);
					break;
				case 2: //Square
					int s_smX = (MouseStart[0] < MouseEnd[0])?MouseStart[0]:(MouseStart[0]-(Math.min(tmpLen[0], tmpLen[1])));
					int s_smY = (MouseStart[1] < MouseEnd[1])?MouseStart[1]:(MouseStart[1]-(Math.min(tmpLen[0], tmpLen[1])));
					g.drawRect( s_smX, s_smY, Math.min(tmpLen[0], tmpLen[1]), Math.min(tmpLen[0], tmpLen[1]));
					break;
				case 3: //Round
					s_smX = (MouseStart[0] < MouseEnd[0])?MouseStart[0]:(MouseStart[0]-(Math.min(tmpLen[0], tmpLen[1])));
					s_smY = (MouseStart[1] < MouseEnd[1])?MouseStart[1]:(MouseStart[1]-(Math.min(tmpLen[0], tmpLen[1])));
					g.drawOval( s_smX, s_smY, Math.min(tmpLen[0], tmpLen[1]), Math.min(tmpLen[0], tmpLen[1]));
					break;
				case 4: //Net
					int speceX = tmpLen[0] / 15;
					int speceY = tmpLen[1] / 15;
					for(int i=0;i<15;i++){
						g.drawLine(tmpStart[0], tmpStart[1], tmpStart[0]+(i*speceX), tmpEnd[1]);  //直線
						g.drawLine(tmpStart[0], tmpEnd[1]-(i*speceY), tmpEnd[0], tmpEnd[1]);  //橫線
					}
					break;
				case 5: //ColorChoose
					break;
				case 6: //Clean
					break;
				case 7: //Exit
					break;
				}
			}
		});
		PainterScreen.this.add(jpn_Canvas);
	}
}
