import java.awt.*;
import java.io.*;

public class HighscoreCheck {

    //classes
    private HUD hud;

    //variables
    private boolean appended = false;
    private boolean newHighscore = true;

    public HighscoreCheck(HUD hud){
        this.hud = hud;
    }

    public void tick(){

    }

    public void render(Graphics g, File file) throws IOException, FontFormatException {
        Color color4 = new Color(10, 203, 5);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font pokemonFont3 = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")).deriveFont(35f);
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Fonts\\pokemon.ttf")));

        int highScore = hud.getEATSCORE();
        int oldHighscore = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            int score = Integer.parseInt(line.trim());   // parse each line as an int
            if (score > highScore) {
                highScore = score;
                newHighscore = false;
                oldHighscore = score;
            }

            line = reader.readLine();
        }
        reader.close();
        if (!appended && newHighscore) {
            //append highscore to the file
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            output.newLine();
            output.append("" + highScore);
            output.close();
            appended = true;
        }

        //display highscore
        if (hud.getEATSCORE() == highScore) {
            g.setColor(color4);
            g.setFont(pokemonFont3);
            g.drawString("New high score!", 655, 530);
        } else {
            g.setFont(pokemonFont3);
            g.drawString("Your highscore: " + oldHighscore, 640, 550);
        }
    }

}
