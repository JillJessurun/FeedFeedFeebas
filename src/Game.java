//stap 63
/*
images transparant maken:
Graphics2D g2d = (Graphics2D) g;
AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f);
g2d.setComposite(alphaComposite);

ideas:
- als je de enemies raakt komt er een knipperend icoontje in beeld gebaseerd op de soort enemy om je te informeren
- icoontje van snelkoppeling aanpassen
- powerups toevoegen (chansey bar refill, health refill, multiple food spawns, speed power up, enemy freeze, enemy slow)
- target toevoegen (bepaald aantal food eten) of timer toevoegen (als ie 0 is heb je het level gehaald)
- level systeem
- geluid! theme song en soundeffects
- aftellen zijn cijfers die van klein naar groot gaan
- enemies hebben verschillende grootte
- options; save, mute, help
- bij hoveren over knoppen worden ze iets groter en vaag grijs (en klik handje als dat lukt)
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
    //public boolean gamePaused = false;
    private Countdown startCountdown;
    private Menu menu;
    private int timer = 0;
    private PausedMenu pausedMenu;
    private Popup popUpWarning;
    public boolean inGame = false;

    //pages menu screen
    public enum STATE {
        Menu,
        Options,
        Pause,
        OptionsInGame,
        PopUp,
        Level1
    }

    public STATE gameState = STATE.Menu;

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
    private BufferedImage chansey2;
    public static BufferedImage image5;

    private BufferedImage feebas;
    public static BufferedImage image6;

    public BufferedImage steak;
    public static BufferedImage image7;

    public BufferedImage grass;
    public static BufferedImage image8;

    public BufferedImage feebasBG;
    public static BufferedImage image9;

    public BufferedImage feebasSprite;
    public static BufferedImage image10;

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
        image8 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\grass.jpg");
        image9 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\cliff.jpg");
        image10 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Images\\feebasSprite.png");
        Image image = new Image(Game.image);
        Image image2 = new Image(Game.image2);
        Image image3 = new Image(Game.image3);
        Image image4 = new Image(Game.image4);
        Image image5 = new Image(Game.image5);
        Image image6 = new Image(Game.image6);
        Image image7 = new Image(Game.image7);
        Image image8 = new Image(Game.image8);
        Image image9 = new Image(Game.image9);
        Image image10 = new Image(Game.image10);
        background = image.grabImage();
        koffing = image2.grabImage();
        voltorb = image3.grabImage();
        glalie = image4.grabImage();
        chansey = image5.grabImage();
        chansey2 = image5.grabImage();
        feebas = image6.grabImage();
        steak = image7.grabImage();
        grass = image8.grabImage();
        feebasBG = image9.grabImage();
        feebasSprite = image10.grabImage();
        //resize images
        grass = image.resizeImage(grass, 200, 200);
        background = image.resizeImage(background, 1920, 1080);
        koffing = image.resizeImage(koffing, 120, 120);
        voltorb = image.resizeImage(voltorb, 150, 150);
        glalie = image.resizeImage(glalie, 100, 100);
        chansey = image.resizeImage(chansey, 240, 240);
        chansey2 = image.resizeImage(chansey2, 80, 80);
        feebas = image.resizeImage(feebas, 140, 140);
        steak = image.resizeImage(steak, 60, 60);
        feebasBG = image.resizeImage(feebasBG, 1920, 1080);
        feebasSprite = image.resizeImage(feebasSprite, 450, 450);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();

        System.out.println("WIDTH = " + WIDTH);
        System.out.println("HEIGHT = " + HEIGHT);

        //create the window
        new Window(WIDTH, HEIGHT, "FeedFeedFeebas!", this);

        //create instances
        handler = new Handler();
        makeTransparent = new MakeTransparent();
        hud = new HUD(this, chansey2, makeTransparent);
        random = new Random();
        makeMirror = new MakeMirror();
        startCountdown = new Countdown();
        menu = new Menu(feebasBG, feebasSprite, makeTransparent, handler, this, feebas, random, steak);
        pausedMenu = new PausedMenu(this);
        popUpWarning = new Popup(this, hud, startCountdown);

        Player player1 = new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, hud, keyInput, feebas, makeTransparent, steak, makeMirror, this);
        handler.addObject(player1);
        handler.addObject(new Voltorb(0, 0, ID.Voltorb, handler, voltorb, makeTransparent, makeMirror));
        handler.addObject(new Koffing(0, 0, ID.Koffing, handler, koffing, makeTransparent, makeMirror));
        handler.addObject(new Glalie(0, 0, ID.Glalie, handler, glalie, makeTransparent, makeMirror));
        handler.addObject(new Chansey(0, 640, ID.Chansey, handler, chansey, makeTransparent, makeMirror, hud));//y = 785 is on the ground
        handler.addObject(new Food(random.nextInt(0, Game.WIDTH - 20), random.nextInt(0, Game.HEIGHT - 20), ID.Food, handler, makeTransparent, steak));

        //listeners for input
        this.addKeyListener(new KeyInput(handler, player1, this));
        this.addMouseListener(this.menu);
        this.addMouseListener(this.pausedMenu);
        this.addMouseListener(this.popUpWarning);
    }

    //start thread
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    //stop thread
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            System.out.println("An error has occured, dunno what");
        }
    }

    public void tick() {
        if (gameState == STATE.Level1) {
            if (startCountdown.timer >= 260) {
                handler.tick();
                hud.tick();
                //later spawns
                timer++;
                if (timer > 500) {
                    timer = 0;
                    Random random = new Random();
                    handler.addObject(new Koffing(random.nextInt(0, 1500), random.nextInt(0, 750), ID.Koffing, handler, koffing, makeTransparent, makeMirror));
                }
            } else {
                startCountdown.tick();
            }

        } else if (gameState == STATE.Menu || gameState == STATE.Options) {
            menu.tick();
        } else if (gameState == STATE.Pause || gameState == STATE.OptionsInGame) {
            pausedMenu.tick();
        }
    }

    //render method
    public void render() throws IOException, FontFormatException {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        Color color = new Color(0, 0, 0, 0);
        g.setColor(color);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (gameState == STATE.Level1) {
            g.clearRect(0,0,1920,1080);
            g.drawImage(background, 0, 0, null);

            if (startCountdown.timer >= 260) {
                inGame = true;
                handler.render(g);
                hud.render(g, g2d);

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
            } else {
                startCountdown.render(g);
            }
        } else if (gameState == STATE.Menu || gameState == STATE.Options) {
            menu.render(g);
        } else if (gameState == STATE.Pause || gameState == STATE.OptionsInGame || gameState == STATE.PopUp) {
            if (inGame) {
                pausedMenu.render(g);
            }
            if (gameState == STATE.PopUp){
                popUpWarning.render(g);
            }
        }

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
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    //clamp method: if the var is at the max, it stays at the max (same with the min)
    public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

    //main
    public static void main(String[] args) throws IOException {
        new Game();
    }
}
