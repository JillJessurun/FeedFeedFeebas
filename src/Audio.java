import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    File musicPath;
    Clip clip;

    void startMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    void stopMusic(){
        clip.stop();
        clip.close();
    }

    void playMusic(String musicLocation){

        try{

            musicPath = new File(musicLocation);

            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                //JOptionPane.showMessageDialog(null,"Hit ok to pauze");
                //clip.stop();

            }else{
                System.out.println("Cannot find file");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
