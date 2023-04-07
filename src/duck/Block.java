package duck;

public class Block {
	public int w,h;
	public int pixels[];
	
	public Block(int w,int h,int c)
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
