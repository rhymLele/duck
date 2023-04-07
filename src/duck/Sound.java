package duck;

import java.io.File;
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

//	private final URL touch;
	
//	public Sound()
//	{
//		this.touch=this.getClass().getClassLoader().getResource("/jsound/dwk.wav");
//	}
//	public void touch()
//	{
//		play(touch);
//	}
	public void play()
	{
		try 
	    {
	        String soundName = "dwk.wav";    
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch (Exception e) {}
	}
}
