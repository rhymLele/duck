package helu;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private final URL touch;
	
	public Sound()
	{
		this.touch=this.getClass().getClassLoader().getResource("/jsound/dwk.wav");
	}
	public void touch()
	{
		play(touch);
	}
	private void play(URL url)
	{
		try {
			AudioInputStream audioIn=AudioSystem.getAudioInputStream(url);
			Clip clip=AudioSystem.getClip();
			clip.open(audioIn);
			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					// TODO Auto-generated method stub
//					throw new UnsupportedOperationException("No sp.");
					if(event.getType()==LineEvent.Type.STOP)
					{
						clip.close();
					}
				}
			});
			audioIn.close();
			clip.start();
		} catch (IOException |LineUnavailableException| UnsupportedAudioFileException e) {
			System.err.println(e);
		}
	}
}
