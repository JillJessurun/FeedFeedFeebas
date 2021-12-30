import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    private Thread thread;
    private boolean running = true;
    private Handler handler;

    //constructor
    public Game(){
        handler = new Handler();

        new Window(WIDTH, HEIGHT, "FeedFeedFeebas!", this);
    }

    //start thread
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
    }

    //stop thread
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            System.out.println("An error has occured, dunno what");
        }
    }

    public void tick(){
        handler.tick();
    }

    //render method
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.dispose();
        bs.show();

        handler.render(g);
    }

    //game loop
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    //main
    public static void main(String[] args) {
        new Game();
    }
}
