package helu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class chuot implements MouseListener,MouseMotionListener{

	private static int x=-1,y=-1;
	private static boolean[] Buttons=new boolean[5];
	private static boolean[] lastButtons=new boolean[5];
	private static boolean dragging=false;
	public static boolean isDragging()
	{
		return dragging;
	}
	public static int getX() {
		return x;
	}
	public static int getY() {
		return y;
	}
	public void cn()
	{
		for(int i=0;i<Buttons.length;i++)
		{
			 lastButtons[i]=Buttons[i];
		}
	}
	public static boolean button(int button)
	{
		return Buttons[button];
	}
	public static boolean buttonDown(int button)
	{
		return Buttons[button]&&!lastButtons[button];
	}
	public static boolean buttonUp(int button)
	{
		return !Buttons[button]&&lastButtons[button];
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x=(int)(e.getX()/duck.scale);
		y=(int)(e.getY()/duck.scale);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x=(int)(e.getX()/duck.scale);
		y=(int)(e.getY()/duck.scale);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
			Buttons[e.getButton()]=true;
			dragging=true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Buttons[e.getButton()]=false;
		dragging=false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
