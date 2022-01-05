/*
author: Jill Jessurun
date: december 2021 - januari 2022

techno music site:
https://pixabay.com/music/search/genre/techno%20&%20trance/

images transparant maken:
Graphics2D g2d = (Graphics2D) g;
AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f);
g2d.setComposite(alphaComposite);

ideas:
- icoontje van snelkoppeling aanpassen
- powerups toevoegen (chansey bar refill, health refill, multiple food spawns, speed power up, enemy freeze, enemy slow)
- meer soundeffects
- enemies hebben verschillende grootte
- options; save, mute, help, naam van jouw feebas in kunnen voeren, new game starten
- render probleem oplossen dat objecten niet weggaan als ze bewegen maar blijven renderen (onder de achtergrond)
- scroll functie in level menu
- try again button wanneer game over
- text andere kleur bij hoveren over pauze menu knoppen
- bliep geluid bij hoveren over knoppen
- zorgen dat enemies niet in je kunnen spawnen
*/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    //classes
    private Thread thread;
    public JFrame jFrame;
    public JPanel jPanel;
    private Handler handler;
    private HUD hud;
    private KeyInput keyInput;
    private Paint paint;
    private BufferedImageLoader loader;
    private MakeTransparent makeTransparent;
    private Random random;
    private MakeMirror makeMirror;
    private Countdown countdown;
    private static Menu menu;
    private PausedMenu pausedMenu;
    private Popup popUpWarning;
    private GameOver gameOver;
    private LevelMenu levelMenu;
    private File fileLevel1;
    private File fileLevel2;
    private File fileLevel3;
    public Player player;
    public HighscoreCheck highscoreCheck;
    private Level1 level1;
    private Level2 level2;

    //variables
    public static int WIDTH = 150;
    public static int HEIGHT = 80;
    private boolean running = true;
    public boolean inGame = false;
    public boolean gameover = false;
    private boolean menuCreated;
    private boolean hudCreated;
    public boolean audioGameoverCreated = false;
    public boolean audioGameoverTimer = false;
    private int timer2 = 0;
    private boolean popUpWarningCreated = false;
    public boolean removedAllObjects = false;

    //audio
    public static Audio mainAudio;
    public static Audio level1Audio;
    public static Audio loadingAudio;
    public static Audio gameoverAudio;
    public static Audio foodAudio;

    //pages
    public enum STATE {
        Menu,
        Options,
        Pause,
        OptionsInGame,
        PopUp,
        GameOver,
        LevelMenu,
        Level1,
        Level2
    }

    //states
    public STATE gameState = STATE.Menu;
    public STATE levelState;

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
        Window window = new Window(WIDTH, HEIGHT, "FeedFeedFeebas!", this);
        jFrame = window.getFrame();

        handler = new Handler();
        jFrame.add(handler);

        //audio
        mainAudio = new Audio();
        level1Audio = new Audio();
        gameoverAudio = new Audio();
        foodAudio = new Audio();

        //create instances
        fileLevel1 = new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Highscores\\HighscoreW1L1.txt");
        fileLevel2 = new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Highscores\\HighscoreW1L2.txt");
        fileLevel3 = new File("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Highscores\\HighscoreW1L3.txt");
        loadingAudio = new Audio();
        countdown = new Countdown();
        makeTransparent = new MakeTransparent();
        random = new Random();
        makeMirror = new MakeMirror();
        paint = new Paint();
        menu = new Menu(feebasBG, feebasSprite, makeTransparent, handler, this, feebas, random, steak, countdown, paint);
        hud = new HUD(this, chansey2, makeTransparent, menu);
        this.menuCreated = true;
        this.hudCreated = true;
        pausedMenu = new PausedMenu(this);
        highscoreCheck = new HighscoreCheck(hud);

        //levels
        level1 = new Level1(countdown, handler, hud, koffing, makeTransparent, makeMirror, this, background, grass);
        level2 = new Level2(countdown, handler, hud, koffing, makeTransparent, makeMirror, this, background, grass);

        popUpWarning = new Popup(this, hud, countdown, menu, handler, level1);
        popUpWarningCreated = true;
        gameOver = new GameOver(this, hud, countdown, menu, handler, fileLevel1, level1, highscoreCheck, fileLevel2, fileLevel3);
        levelMenu = new LevelMenu(this, countdown, menu, fileLevel1, fileLevel2,fileLevel3);

        Player player1 = new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, hud, keyInput, feebas, makeTransparent, steak, makeMirror, this);
        this.player = player1;
        handler.addObject(player1);
        handler.addObject(new Food(random.nextInt(0, 1400), random.nextInt(0, 650), ID.Food, handler, makeTransparent, steak, player1, this));
        handler.addObject(new Chansey(0, 640, ID.Chansey, handler, chansey, makeTransparent, makeMirror, hud));

        //listeners for input
        this.addKeyListener(new KeyInput(handler, player1, this));
        this.addMouseListener(this.menu);
        this.addMouseListener(this.pausedMenu);
        this.addMouseListener(this.popUpWarning);
        this.addMouseListener(this.gameOver);
        this.addMouseListener(this.levelMenu);
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
        if (hudCreated && menuCreated) {
            if (gameState == STATE.Level1) {
                level1.tick();
            } else if (gameState == STATE.Level2) {
                level2.tick();
            } else if (gameState == STATE.Menu || gameState == STATE.Options) {
                menu.tick();
            } else if (gameState == STATE.Pause || gameState == STATE.OptionsInGame) {
                pausedMenu.tick();
            } else if (gameState == STATE.LevelMenu) {
                levelMenu.tick();
                menu.tick();
            } else if (hud.getHEALTH() > 10000) {
                gameState = STATE.GameOver;
                gameOver.tick();

                //audio
                if (audioGameoverCreated) {
                    try {
                        level1Audio.stopMusic();
                        gameoverAudio.startMusic();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    audioGameoverCreated = false;
                }

                if (audioGameoverTimer) {
                    timer2++;
                    if (timer2 == 69) {
                        timer2 = 0;
                        audioGameoverTimer = false;
                        try {
                            gameoverAudio.stopMusic();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
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

        Color color = new Color(0, 0, 0, 0);
        g.setColor(color);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (hudCreated && menuCreated) {
            if (gameState == STATE.Level1) {
                level1.render(g);
            } else if (gameState == STATE.Level2) {
                level2.render(g);
            } else if (gameState == STATE.Menu || gameState == STATE.Options) {
                menu.render(g);
            } else if (gameState == STATE.Pause || gameState == STATE.OptionsInGame || gameState == STATE.PopUp) {
                if (inGame) {
                    pausedMenu.render(g);
                }
                if (gameState == STATE.PopUp) {
                    if (popUpWarningCreated) {
                        popUpWarning.render(g);
                    }
                }
            } else if (gameState == STATE.LevelMenu) {
                menu.render(g);
                levelMenu.render(g);
            }
            if (hud.getHEALTH() > 10000) {
                gameState = STATE.GameOver;
                gameOver.render(g);
            }

            g.dispose();
            bs.show();
        }

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
        mainAudio.playMusic("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Audio\\fastbeat.wav");
        level1Audio.playMusic("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Audio\\level1.wav");
        loadingAudio.playMusic("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Audio\\synth.wav");
        gameoverAudio.playMusic("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Audio\\gameover.wav");
        foodAudio.playMusic("C:\\Users\\pc\\IdeaProjects\\FeedFeedFeebas!\\src\\Audio\\eating.wav");
        menu.stopIngameAudio();
        menu.stopLoadingAudio();
        menu.stopGameoverAudio();
        menu.stopFoodAudio();
    }
}
