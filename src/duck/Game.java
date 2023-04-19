package duck;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Connect.SQL.database;
import sound.SoundManager;

public class Game {
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	public static double best,lastTime;
	private List<cnvat> dts;
	private nguoichoi player;
	Block playerSprite=new Block(50, 50, 0xffff0000);
	private SoundManager soundManager = new SoundManager();

//	public double sx[],sy[],lx[],ly[];
//	public int sizx[],sizy[],col[],n;
	public int n;
	public static boolean gameOver=true,gameStarted=false;
	public double startTime=0;
	config cf=new config();
	ArrayList<Double> 
			sx=new ArrayList<>()
			,sy=new ArrayList<>()
			,lx=new ArrayList<>()
			,ly=new ArrayList<>();
	ArrayList<Integer> sizx=new ArrayList<>()
			,sizy=new ArrayList<>()
			,col=new ArrayList<>();
	 public double[] lxList = new double[100];
	 public double[] lyList = new double[100];
	 public int[] sizxList = new int[100];
	 public int[] sizyList = new int[100];
	 public int[] sxList = new int[100];
	 public int[] syList = new int[100];
	public int insertScore(double score)
	{
		try {
			Class.forName(DRIVER_CLASS);
			Connection cn=database.getConnection();
			PreparedStatement prt=cn.prepareStatement("INSERT highscore(score) VALUES(?)");
			prt.setDouble(1, score);			
			prt.executeUpdate();
			database.closeConnection(cn);
			System.out.println("Sut");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public Game() {
		khoitao();
		cf.loadConfig("config.xml");
		
		
	}
	public void khoitao()
	{
		dts=new ArrayList<cnvat>();//0xff0000ff
//		dts.add(new cnvat(100,100,new Block(85,85,0xff0f),7.0,4.3));
//		dts.add(new cnvat(355,90,new Block(90,75,0xff0f),3.6,6.0));
//		dts.add(new cnvat(100,430,new Block(40,80,0xff0f),7.0,-6.0));
//		dts.add(new cnvat(415,450,new Block(125,30,0xff0f),-5.2,-8.3));
//		dts.add(new cnvat(50,50,new Block(125,30,0xff0f),-5.2,-8.3));
		try {
		    FileReader fr = new FileReader("config.txt");
		    BufferedReader br = new BufferedReader(fr);

		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] tokens = line.split(",");

		        double x = Double.parseDouble(tokens[0]);
		        double y = Double.parseDouble(tokens[1]);
		        int sizex = Integer.parseInt(tokens[2]);
		        int sizey =Integer.parseInt(tokens[3]);
		        double sx = Double.parseDouble(tokens[4]);
		        double sy = Double.parseDouble(tokens[5]);

		        dts.add(new cnvat(x, y, new Block(sizex, sizey, 0xff0f), sx, sy));
		    }
		    

		    br.close();
		    fr.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		player=new nguoichoi(Duck.W/2-playerSprite.w/2,Duck.H/2-playerSprite.h/2,playerSprite);
		gameOver=false;gameStarted=false;
		soundManager.playMusic("bg.wav");
	}
	public void capnhat()
	{
		if(gameOver)
		{
			if(chuot.buttonDown(MouseEvent.BUTTON1))
			{
				khoitao();
			}return;
		}
//		while(!gameOver) {
//			soundManager.playMusic("src/sound/bg.wav");
//		}
		if(!gameStarted)
		{
			if(chuot.buttonDown(MouseEvent.BUTTON1))
			{
				if(player.isMouseOnP())
				{
					gameStarted=true;
					startTime=System.currentTimeMillis();
				}else
				{
					return;
				}
			}else
			{
				return;
			}
		}
		player.cn();
		for(int i=0;i<dts.size();i++)
		{
			dts.get(i).cn();
		}
		vacham();
	}
	private void vacham() {
		for(int i=0;i<dts.size();i++)
		{
			cnvat a=dts.get(i);
			if(player.x<a.x+a.w && player.x+player.w>a.x && player.y<a.y+a.h &&player.y+player.h>a.y)
			{
				gameOver=true;
//				soundManager.playMusic("src/dodge/bg.wav");
			}
		}
		if(player.x<=60||player.x+player.w>=Duck.W-60||player.y<=60||player.y+player.h>=Duck.H-60)
		{
			gameOver=true;
//			soundManager.playMusic("src/dodge/bg.wav");
		}
		if(gameOver)
		{
			lastTime=(System.currentTimeMillis()-startTime)/1000.0;
			if(lastTime>best)
			{
				best=lastTime;
				cf.saveconfig("best", best);
				insertScore(best);
			}
			soundManager.stopMusic();
			
		}

	}
	public void vegame()
	{
		nguoiquet.quetnen();
		
		player.quet();
		for(int i=0;i<dts.size();i++)
		{
			dts.get(i).quet();
		}
		for(int i=0;i<Duck.pixel.length;i++)
		{
			Duck.pixel[i]=nguoiquet.pixels[i];
		}
		
	}
	public void ketthuc(Graphics2D g2)
	{
		if(gameOver)
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.black);
			g2.setFont(new Font("Monospaced",1,70));
			String g="GameOver";
			String st="Time:"+lastTime+"s";
			String sbs="Best:"+best+"s";
			int ig1=g2.getFontMetrics().stringWidth(g)/2;
			int ig2=g2.getFontMetrics().stringWidth(st)/2;
			int ig3=g2.getFontMetrics().stringWidth(sbs)/2;
			g2.drawString(g, Duck.W/2-ig1, 150);
			g2.drawString(st, Duck.W/2-ig2, 175*2);
			g2.drawString(sbs, Duck.W/2-ig3, 175*3);
		}
		if(!gameOver && !gameStarted) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(0,0,0));
			g2.setFont(new Font("Monospaced",1,60));
			String menu="DUCK!";
			String click="Click to start!";
			int ig4=g2.getFontMetrics().stringWidth(menu)/2;
			int ig5=g2.getFontMetrics().stringWidth(click)/2;
			g2.drawString(menu, Duck.W/2-ig4, 150);
			g2.drawString(click, Duck.W/2-ig5, 600);
		}
	}
}
