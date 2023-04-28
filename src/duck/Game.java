package duck;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;

import Connect.SQL.database;
import sound.SoundManager;

public class Game extends JComponent{
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	public static double best,lastTime,scr;
	private List<cnvat> dts;
	private List<cnvat> b;
	private nguoichoi player;
	public static final int heart=1;
	public  int mang;
	Block playerSprite=new Block(50, 50, 0xffff0000);
	private SoundManager soundManager = new SoundManager();
	public int n;
	public static boolean gameOver=true,gameStarted=false;
	public double startTime=0;
	config cf=new config();
	
	public int insertScore(double score)
	{
		try {
			Class.forName(DRIVER_CLASS);
			Connection cn=database.getConnection();
			double bestScore = getBestScore(cn);
			if (best > bestScore) {
				 PreparedStatement ps = cn.prepareStatement("DELETE FROM highscore");
				 ps.executeUpdate();
	            PreparedStatement prt = cn.prepareStatement("INSERT INTO highscore (score) VALUES (?)");
	            prt.setDouble(1, score);
	            prt.executeUpdate();
	            System.out.println("Success");
	        }

	        database.closeConnection(cn);
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	private double getBestScore(Connection cn) throws SQLException {
	    double bestScore = 0.0;
	    Statement stmt = cn.createStatement();
	    ResultSet rs = stmt.executeQuery("SELECT MAX(score) AS best_score FROM highscore");
	    if (rs.next()) {
	        bestScore = rs.getDouble("best_score");
	    }
	    return bestScore;
	}
	public Game() {
		khoitao();
		cf.loadConfig("config.xml");
		
		
	}
	public void khoitao()
	{
		mang=2;
		dts=new ArrayList<cnvat>();//
		b=new ArrayList<cnvat>();//0xff0f
		b.add(new cnvat(300,150,new Block(70,70,0xff0f),7.0,7.3));
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
		        double speedx = Double.parseDouble(tokens[4]);
		        double speedy = Double.parseDouble(tokens[5]);
		        //(double)Math.random()*(600-100)
		        dts.add(new cnvat(x, y, new Block(sizex, sizey,0xff0000ff ), speedx, speedy));
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
		player.capnhat();
		for(int i=0;i<dts.size();i++)
		{
			dts.get(i).cn();
		}
		for(int i=0;i<b.size();i++)
		{
			b.get(i).cn();
		}
		vacham();
	}
	private void death()
	{
		mang--;
		if(mang<=0)
		{
			gameOver=true;
		}
	}
	private void vacham() {
		for(int i=0;i<dts.size();i++)
		{
			cnvat a=dts.get(i);
			if(player.x<a.x+a.w && player.x+player.w>a.x && player.y<a.y+a.h &&player.y+player.h>a.y)
			{
				gameOver=true;
			}
		}
		for(int i=0;i<b.size();i++)
		{
			cnvat c=b.get(i);
			if(player.x<c.x+c.w && player.x+player.w>c.x && player.y<c.y+c.h &&player.y+player.h>c.y)
			{
				death();
				c.setVx();
				c.setVy();
			}
		}
		if(player.x<=60||player.x+player.w>=Duck.W-60||player.y<=60||player.y+player.h>=Duck.H-60)
		{
			gameOver=true;
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
		for(int i=0;i<b.size();i++)
		{
			b.get(i).quet();
		}
		for(int i=0;i<Duck.pixel.length;i++)
		{
			Duck.pixel[i]=nguoiquet.pixels[i];
		}
		
	}
	public void renderText(Graphics2D g2)
	{
		g2.setFont(new Font("Arial",0,20));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		String s="Heart:"+mang;
		int w=g2.getFontMetrics().stringWidth(s)/2;
		g2.setColor(Color.white);
		g2.drawString(s, 250, 30);
	}
	public void ketthuc(Graphics2D g2)
	{
		if(gameOver)
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.black);
			g2.setFont(new Font("Monospaced",1,50));
			String g="GameOver";
			String st="Time:"+lastTime+"s";
			String sbs="Best:"+best+"s";
			int ig1=g2.getFontMetrics().stringWidth(g)/2;
			int ig2=g2.getFontMetrics().stringWidth(st)/2;
			int ig3=g2.getFontMetrics().stringWidth(sbs)/2;
			g2.drawString(g, Duck.W/2-ig1, 150);
			g2.drawString(st, Duck.W/2-ig2, 150*2);
			g2.drawString(sbs, Duck.W/2-ig3, 150*3);
		}
		if(!gameOver && !gameStarted) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(0,0,0));
			g2.setFont(new Font("Monospaced",1,40));
			String menu="DUCK!";
			String click="Click to start!";
			int ig4=g2.getFontMetrics().stringWidth(menu)/2;
			int ig5=g2.getFontMetrics().stringWidth(click)/2;
			g2.drawString(menu, Duck.W/2-ig4, 150);
			g2.drawString(click, Duck.W/2-ig5, 400);
		}
	}
}
