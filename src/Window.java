import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferStrategy;

public class Window {

    public JFrame frame;

    public Window(int width, int height, String title, Game game){
        //create frame
        JFrame frame = new JFrame(title);
        this.frame = frame;
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\feebasicon.png");
        frame.setIconImage(icon);

        frame.setPreferredSize(new Dimension(width, height));//set the size of the dimension of the frame
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);//make the screen fullscreen
        frame.setUndecorated(true);//makes sure you dont see the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes the exit button int the top right corner work
        frame.setResizable(true);//makes sure you cannot resize the window which could generate a lot of problems
        frame.setLocationRelativeTo(null);//the window is locked to the middle of the screen now (null does that)
        frame.add(game);//add the Game class (the main class u just created) to the frame
        frame.setVisible(true);//makes the frame visible
        game.start();//runs the start method you created in your Game class
    }

    public JFrame getFrame() {
        return frame;
    }
}
