package game;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
	Clip clip;
	
	FloatControl fc;
	
	public Sound(URL url) throws Exception {
		AudioInputStream ais = AudioSystem.getAudioInputStream(url);
		clip = AudioSystem.getClip();
		clip.open(ais);
		fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	void setFrame(int frm) {
		clip.setFramePosition(frm);
	}
	int getFrame() {
		return clip.getFramePosition();
	}
	void setVolume(float vol) {
		fc.setValue(vol);
	}
	void reset() {
		clip.setFramePosition(0);
	}
	void play() {
		clip.start();
	}
	void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	void loop(int frame) {
		setFrame(frame);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	void stop() {
		clip.stop();
	}
}
