package helu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class game {
	
	public static double best,lastTime;
	private List<doituong> dts;
	private nguoichoi player;
	
	Sprite playerSprite=new Sprite(50, 50, 0xffff0000);
	
	public static boolean gameOver=true,gameStarted=false;
	public double startTime=0;
	public Sound sound;
	config cf=new config();
	
	public game() {
		kt();
		cf.loadConfig("config.xml");
	}
	public void kt()
	{
		sound=new Sound();
		dts=new ArrayList<doituong>();
		dts.add(new doituong(100,100,new Sprite(85,85,0xff0000ff),7.0,4.3));
		dts.add(new doituong(355,90,new Sprite(90,75,0xff0000ff),-4.6,5.0));
		dts.add(new doituong(100,430,new Sprite(40,80,0xff0000ff),7.0,-6.0));
		dts.add(new doituong(415,450,new Sprite(125,30,0xff0000ff),-5.2,-8.3));
		player=new nguoichoi(duck.W/2-playerSprite.w/2,duck.H/2-playerSprite.h/2,playerSprite);
		gameOver=false;gameStarted=false;
	}
	public void cn()
	{
		if(gameOver)
		{
			if(chuot.buttonDown(MouseEvent.BUTTON1))
			{
				kt();
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
			doituong a=dts.get(i);
			if(player.x<a.x+a.w && player.x+player.w>a.x && player.y<a.y+a.h &&player.y+player.h>a.y)
			{
				gameOver=true;
//				sound.touch();
			}
		}
		if(player.x<=60||player.x+player.w>=duck.W-60||player.y<=60||player.y+player.h>=duck.H-60)
		{
			gameOver=true;
//			sound.touch();
		}
		if(gameOver)
		{
			lastTime=(System.currentTimeMillis()-startTime)/1000.0;
			if(lastTime>best)
			{
				best=lastTime;
				cf.saveconfig("best", best);
			}
		}
	}
	public void q()
	{
		nguoiquet.quetnen();
		
		player.quet();
		for(int i=0;i<dts.size();i++)
		{
			dts.get(i).quet();
		}
		for(int i=0;i<duck.pixel.length;i++)
		{
			duck.pixel[i]=nguoiquet.pixels[i];
		}
	}
	public void qc(Graphics2D g2)
	{
		if(gameOver)
		{
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.black);
			g2.setFont(new Font("Arial",1,70));
			String g="GameOver";
			String st="Time:"+lastTime+"s";
			String sbs="Best:"+best+"s";
			int ig1=g2.getFontMetrics().stringWidth(g)/2;
			int ig2=g2.getFontMetrics().stringWidth(st)/2;
			int ig3=g2.getFontMetrics().stringWidth(sbs)/2;
			g2.drawString(g, duck.W/2-ig1, 150);
			g2.drawString(st, duck.W/2-ig2, 150*2);
			g2.drawString(sbs, duck.W/2-ig3, 150*3);
		}
	}
}
