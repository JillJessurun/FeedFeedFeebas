//stap 63
/*
images transparant maken:
Graphics2D g2d = (Graphics2D) g;
AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f);
g2d.setComposite(alphaComposite);

ideas:
- chansey heal timer with hud
- als je de enemies raakt komt er een knipperend icoontje in beeld gebaseerd op de soort enemy om je te waarschuwen
-
 */

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 150;
    public static int HEIGHT = 80;
    private Thread thread;
    private boolean running = true;
    private Handler handler;
    private HUD hud;
    private KeyInput keyInput;
    private BufferedImageLoader loader;
    private MakeTransparent makeTransparent;
    private Random random;
    private MakeMirror makeMirror;
    public boolean gamePaused = false;

    //images
    private BufferedImage background;
    public static BufferedImage image;

    private BufferedImage koffing;
    public static BufferedImage image2;

    private BufferedImage voltorb;
    public static BufferedImage image3;

    private BufferedImage glalie;
    public static BufferedImage image4;

    private BufferedImage chansey;
    public static BufferedImage image5;

    private BufferedImage feebas;
    public static BufferedImage image6;

    public BufferedImage steak;
    public static BufferedImage image7;

    public BufferedImage grass;
    public static BufferedImage image8;

    //constructor
    public Game() throws IOException {
        loader = new BufferedImageLoader();

        image = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\background.jpg");
        image2 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\koffing.png");
        image3 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\voltorb.png");
        image4 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\glalie.png");
        image5 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\chansey.png");
        image6 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\feebas.png");
        image7 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\steak.png");
        image8 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\grass.png");
        Image image = new Image(Game.image);
        Image image2 = new Image(Game.image2);
        Image image3 = new Image(Game.image3);
        Image image4 = new Image(Game.image4);
        Image image5 = new Image(Game.image5);
        Image image6 = new Image(Game.image6);
        Image image7 = new Image(Game.image7);
        Image image8 = new Image(Game.image8);
        background = image.grabImage();
        koffing = image2.grabImage();
        voltorb = image3.grabImage();
        glalie = image4.grabImage();
        chansey = image5.grabImage();
        feebas = image6.grabImage();
        steak = image7.grabImage();
        grass = image8.grabImage();
        //resize images
        grass = image.resizeImage(grass, 200, 200);
        background = image.resizeImage(background, 1920, 1080);
        koffing = image.resizeImage(koffing, 120, 120);
        voltorb = image.resizeImage(voltorb, 150, 150);
        glalie = image.resizeImage(glalie, 100, 100);
        chansey = image.resizeImage(chansey, 240, 240);
        feebas = image.resizeImage(feebas, 140, 140);
        steak = image.resizeImage(steak, 60, 60);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)screenSize.getWidth();
        HEIGHT = (int)screenSize.getHeight();

        System.out.println("WIDTH = " + WIDTH);
        System.out.println("HEIGHT = " + HEIGHT);

        //create the window
        new Window(WIDTH, HEIGHT, "FeedFeedFeebas!", this);

        //create the handler
        handler = new Handler();
        hud = new HUD(this);
        makeTransparent = new MakeTransparent();
        random = new Random();
        makeMirror = new MakeMirror();

        Player player1 = new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler, hud, keyInput, feebas, makeTransparent, steak, makeMirror, this);
        handler.addObject(player1);
        handler.addObject(new Voltorb(0, 0, ID.Voltorb, handler, voltorb, makeTransparent, makeMirror));
        handler.addObject(new Koffing(0, 0, ID.Koffing, handler, koffing, makeTransparent, makeMirror));
        handler.addObject(new Glalie(0, 0, ID.Glalie, handler, glalie, makeTransparent, makeMirror));
        handler.addObject(new Chansey(0, 640, ID.Chansey, handler, chansey, makeTransparent, makeMirror));//y = 785 is on the ground
        handler.addObject(new Food(random.nextInt(0,Game.WIDTH - 20), random.nextInt(0,Game.HEIGHT- 20), ID.Food, handler, makeTransparent, steak));

        //listeners for input
        this.addKeyListener(new KeyInput(handler, player1, this));
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
        if (!gamePaused) {
            handler.tick();
            hud.tick();
        }
    }

    //render method
    public void render() throws IOException, FontFormatException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        Color color = new Color(0,0,0,0);
        g.setColor(color);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(background, 0, 0, null);

        //render ground
        int colour = grass.getRGB(0, 0);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 0, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 200, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 230, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 400, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 500, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 550, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 750, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 865, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 953, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 1000, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 1200, 750, null);
        g.drawImage(makeTransparent.makeColorTransparent(grass, new Color(colour)), 1400, 750, null);


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
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }
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
    public static void main(String[] args) throws IOException {
        new Game();
    }
}
