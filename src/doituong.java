package helu;

public class doituong {
	
	public double x,y,vx,vy;
	public int w,h;
	public Sprite sprite;
	public doituong(double x,double y,Sprite s,double vx,double vy)
	{
		this.x=x;
		this.y=y;
		
		this.w=s.w;
		this.h=s.h;
		this.sprite=s;
		this.vx=vx;
		this.vy=vy;
	}
	public void cn()
	{
		x+=vx;
		y+=vy;
		if(x<0||x>=duck.W-w) vx*=-1;
		if(y<0||y>=duck.H-h) vy*=-1;
		
	}
	public void quet()
	{
		nguoiquet.quetSprite(sprite, (int)x, (int)y);
	}
}

