import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private Handler handler;
    private boolean[] keyDown = new boolean[4];
    private Player player;
    public boolean glalieHit;
    private Game game;

    public KeyInput(Handler handler, Player player, Game game){
        this.handler = handler;
        this.player = player;
        this.game = game;
        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                // key events for player 1
                if (!glalieHit) {
                    if (key == KeyEvent.VK_W) {
                        tempObject.setVelY(player.getNegativeVelocity());
                        keyDown[0] = true;
                        if (player.left) {
                            player.left = false;
                            player.right = false;
                        }
                    }
                    if (key == KeyEvent.VK_S) {
                        tempObject.setVelY(player.getPositiveVelocity());
                        keyDown[1] = true;
                        if (player.left) {
                            player.left = false;
                            player.right = false;
                        }
                    }
                    if (key == KeyEvent.VK_A) {
                        tempObject.setVelX(player.getNegativeVelocity());
                        keyDown[2] = true;
                        player.left = true;
                        player.right = false;
                    }
                    if (key == KeyEvent.VK_D) {
                        tempObject.setVelX(player.getPositiveVelocity());
                        keyDown[3] = true;
                        player.left = false;
                        player.right = true;
                    }
                    if (key == KeyEvent.VK_P) {
                        if (game.gameState == Game.STATE.Pause) {
                            game.gameState = Game.STATE.Level1;

                        } else {
                            game.gameState = Game.STATE.Pause;
                        }
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    if (game.gameState == Game.STATE.Pause) {
                        game.gameState = Game.STATE.Level1;
                    } else if (game.gameState == Game.STATE.Level1 && game.inGame) {
                        game.gameState = Game.STATE.Pause;
                    } else if (game.gameState == Game.STATE.Level1) {

                    } else if (game.gameState == Game.STATE.Options) {
                        game.gameState = Game.STATE.Menu;
                    } else if (game.gameState == Game.STATE.PopUp && game.inGame) {
                        game.gameState = Game.STATE.Pause;
                    } else if (game.gameState == Game.STATE.PopUp) {
                        game.gameState = Game.STATE.Menu;
                    } else if (game.gameState == Game.STATE.GameOver) {
                        game.gameState = Game.STATE.Menu;
                    } else if (game.gameState == Game.STATE.LevelMenu) {
                        game.gameState = Game.STATE.Menu;
                    } else if (game.gameState == Game.STATE.Menu) {{
                            game.gameState = Game.STATE.PopUp;
                        }
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Player){
                // key events for player 1
                if (key == KeyEvent.VK_W) keyDown[0] = false;//tempObject.setVelY(0);
                if (key == KeyEvent.VK_S) keyDown[1] = false;//tempObject.setVelY(0);
                if (key == KeyEvent.VK_A) keyDown[2] = false;//tempObject.setVelX(0);
                if (key == KeyEvent.VK_D) keyDown[3] = false;//tempObject.setVelX(0);

                //vertical movement
                if (!keyDown[0] && !keyDown[1]){
                    tempObject.setVelY(0);
                }
                if (!keyDown[2] && !keyDown[3]){
                    tempObject.setVelX(0);
                }
            }
        }
    }

    public void stopPlayer(){
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Player){
                //vertical movement
                if (!keyDown[0] && !keyDown[1]){
                    tempObject.setVelY(0);
                }
                if (!keyDown[2] && !keyDown[3]){
                    tempObject.setVelX(0);
                }
            }
        }

        keyDown[0] = false;
        keyDown[1] = false;
        keyDown[2] = false;
        keyDown[3] = false;
    }
}
