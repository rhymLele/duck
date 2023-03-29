package helu;

public class Sprite {
	public int w,h;
	public int pixels[];
	
	public Sprite(int w,int h,int c)
	{
		this.w=w;
		this.h=h;
		this.pixels=new int[w*h];
		for(int i=0;i<pixels.length;i++)
		{
			pixels[i]=c;
		}
	}
}
