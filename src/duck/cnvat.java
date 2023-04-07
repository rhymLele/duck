package duck;

public class cnvat {
	
	public double x,y,vx,vy;
	public int w,h;
	public Block block;
	public cnvat(double x,double y,Block b,double vx,double vy)
	{
		this.x=x;
		this.y=y;
		
		this.w=b.w;
		this.h=b.h;
		this.block=b;
		this.vx=vx;
		this.vy=vy;
	}
	public void cn()
	{
		x+=vx;
		y+=vy;
		if(x<0||x>=Duck.W-w) vx*=-1;
		if(y<0||y>=Duck.H-h) vy*=-1;
		
	}
	public void quet()
	{
		nguoiquet.quetSprite(block, (int)x, (int)y);
	}
}

