package LunarVerse;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
	
	Long currentFrame; 
    Clip clip; 
      
    // current status of clip 
    String status = "play"; 
      
    AudioInputStream audioInputStream; 
    static String filePath; 
  
    // constructor to initialize streams and clip 
    public Music(String s) 
        throws UnsupportedAudioFileException, 
        IOException, LineUnavailableException  
    { 
    	filePath = s;
        // create AudioInputStream object 
        audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
          
        // open audioInputStream to the clip 
        clip.open(audioInputStream); 
          
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

	public void play() {
		clip.start(); 
	} 
	
	public void stop() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException  
    { 
        currentFrame = 0L; 
        clip.stop(); 
        clip.close(); 
    } 
	
	public void pause()  
    { 
        if (status.equals("paused"))  
        { 
            return; 
        } 
        this.currentFrame =  
        this.clip.getMicrosecondPosition(); 
        clip.stop(); 
        status = "paused"; 
    }
	public void resumeAudio() throws UnsupportedAudioFileException, 
    IOException, LineUnavailableException  
{ 
if (status.equals("play"))  
{ 
System.out.println("Audio is already "+ 
"being played"); 
return; 
} 
clip.close(); 
resetAudioStream(); 
clip.setMicrosecondPosition(currentFrame); 
this.play(); 
}
	
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
    LineUnavailableException  
{ 
audioInputStream = AudioSystem.getAudioInputStream( 
new File(filePath).getAbsoluteFile()); 
clip.open(audioInputStream); 
clip.loop(Clip.LOOP_CONTINUOUSLY); 
} 
	

}
