package duck;

public class nguoiquet {
	private static int w=Duck.W,h=Duck.H;
	public static int[] pixels=new int[w*h];
	public static void quetnen()
	{
		for(int y=0;y<h;y++)
		{
			for(int x=0;x<w;x++)
			{
				if(x<=60||x>=w-60||y<=60||y>=h-60)
				{
					pixels[x+y*w]=0xff000000;
				}
				else
				{
					pixels[x+y*w]=0xffffffff;
				}
			}
		}
	}
	public static void quetSprite(Block s,int xp,int yp)
	{
		if(xp<-s.w||yp<-s.h||xp>=w||yp>=h)
		{
			return;
		}
		for(int y=0;y<s.h;y++)
		{
			int yy=y+yp;
			if(yy>=h||yy<0) continue;
			for(int x=0;x<s.w;x++)
			{
				int xx=x+xp;
				if(xx>=w||xx<0) continue;
				int col=s.pixels[x+y*s.w];
				if(col==0xffff00ff) continue;
				pixels[xx+yy*w]=col;
			}
		}
	}
}
