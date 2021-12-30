//stap 63

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 150;
    public static int HEIGHT = 80;
    private Thread thread;
    private boolean running = true;
    private Handler handler;
    private HUD hud;

    //constructor
    public Game(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screenSize.getWidth();
        HEIGHT = (int)screenSize.getHeight();

        System.out.println("WIDTH = " + WIDTH);
        System.out.println("HEIGHT = " + HEIGHT);

        //create the window
        new Window(WIDTH, HEIGHT, "FeedFeedFeebas!", this);

        //create the handler
        handler = new Handler();
        hud = new HUD();
        handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
        handler.addObject(new Voltorb(WIDTH/2-20, HEIGHT/2-20, ID.Voltorb, handler));

        //listeners for input
        this.addKeyListener(new KeyInput(handler));
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
        hud.tick();
    }

    //render method
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g, g2d);

        g.dispose();
        bs.show();
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

    //clamp method: if the var is at the max, it stays at the max (same with the min)
    public static float clamp(float var, float min, float max){
        if(var >= max){
            return var = max;
        }else if(var <= min){
            return var = min;
        }else{
            return var;
        }
    }

    //main
    public static void main(String[] args) {
        new Game();
    }
}
