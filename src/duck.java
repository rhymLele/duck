package helu;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
public class duck extends Canvas implements Runnable{
	public static final int W=600,H=600;
	public static final float scale=1f;
	
	private game G;
	private JFrame jf;
	private Thread thread;
	private boolean running=false;
	chuot mouse;
	
	public static BufferedImage image =new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
	public static int[] pixel =((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public duck()
	{
		setPreferredSize(new Dimension((int)(W*scale),(int)(H*scale)));
		jf= new JFrame();
		G=new game();
		mouse =new chuot();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	public void start()
	{
		running=true;
		thread=new Thread(this,"loop");
		thread.start();
	}
	public void stop()
	{
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void run()
	{
		long TIME=System.nanoTime();
		long timer=System.currentTimeMillis();
		final double nSecondpu=1000000000.0/60.0;
		double updateTo=0;
		int frs=0;
		int updt=0;
		requestFocus();
		
		while(running)
		{
			long crtTime=System.nanoTime();
			updateTo+=(crtTime-TIME)/nSecondpu;
			TIME=crtTime;
			while(updateTo>=1)
			{
				update();
				updt++;
				updateTo--;
			}
			render();
			frs++;
			if(System.currentTimeMillis()-timer>1000)
			{
				timer+=1000;
				updt=0;
				frs=0;
			}
		}
		stop();
	}
	public void update()
	{
		G.cn();
		mouse.cn();
	}
	public void render()
	{
		BufferStrategy bs=getBufferStrategy();
		if(bs==null)
		{
			createBufferStrategy(3);
			return;
		}
		G.q();
		
		Graphics2D g2=(Graphics2D) bs.getDrawGraphics();
		
		g2.drawImage(image,0,0,(int)(W*scale),(int)(H*scale),null);
	
		G.qc(g2);
		g2.dispose();
		bs.show();
	}
	public static void main(String args[])
	{
		duck d=new duck();
		d.jf.setResizable(false);
		d.jf.setTitle("duck");
		d.jf.add(d);
		d.jf.pack();
		d.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.jf.setVisible(true);
		d.jf.setLocationRelativeTo(null);
		d.jf.setAlwaysOnTop(true);
		d.start();
	}
}
