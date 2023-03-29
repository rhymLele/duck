package helu;

public class nguoichoi {
	public static double x,y;
	public static int w,h;
	public Sprite sprite;
	
	boolean moveN=false;
	
	public nguoichoi(double x,double y,Sprite s)
	{
		this.x=x;
		this.y=y;
		this.w=s.w;
		this.h=s.h;
		this.sprite=s;
	}
	public void cn()
	{
		if(!moveN)
		{
			if(chuot.isDragging() && isMouseOnP())
			{
				x=chuot.getX()-sprite.w/2;
				y=chuot.getY()-sprite.h/2;
//				moveN=true;
			}else
			{
				x=chuot.getX()-sprite.w/2;
				y=chuot.getY()-sprite.h/2;
				if(!chuot.isDragging())
				{
					moveN=false;
				}
			}
		}
	}
	public static boolean isMouseOnP()
	{
		if(chuot.getX()>=x && chuot.getX()< x+w &&chuot.getY()>=y&&chuot.getY()<y+h)
		{
			return true;
		}
		return false;
	}
	public void quet()
	{
		nguoiquet.quetSprite(sprite, (int)x, (int)y);
	}
}
