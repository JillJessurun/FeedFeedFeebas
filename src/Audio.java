import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    File musicPath;
    Clip clip;
    public boolean hasStarted;

    public void startMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        hasStarted = true;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopMusic() {
        clip.stop();
        clip.close();
        hasStarted = false;
    }

    public void playMusic(String musicLocation) {

        try {

            musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            } else {
                System.out.println("Cannot find file");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
