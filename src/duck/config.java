package duck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class config {
	Properties proper=new Properties();
	public void saveconfig(String key,double val)
	{
		String path="config.xml";
		try {
			File file =new File(path);
			boolean exists=file.exists();
			if(!exists)
			{
				file.createNewFile();
			}
			OutputStream write=new FileOutputStream(path);
			proper.setProperty(key, Double.toString(val));
			proper.storeToXML(write,"Ops");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void loadConfig(String path)
	{
		try {
			InputStream read=new FileInputStream(path);
			proper.loadFromXML(read);
			String best=proper.getProperty("best");
			setBest(Double.parseDouble(best));
			read.close();
		} catch (FileNotFoundException e) {
			saveconfig("best", 0.0);
			loadConfig(path);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBest(double bestTime) {
		Game.best=bestTime;
		
	}
}
