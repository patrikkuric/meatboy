package meatboy.player;


import meatboy.Game;
import meatboy.LoadedImages;
import meatboy.platforms.IPlatform;
import meatboy.platforms.PlatformManager;
import meatboy.objects.IObject;
import meatboy.other.Direction;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda Player - hráč
 * @author Patrik Kuric
 */

public class Player implements ActionListener {

    private final Timer playerTimer;
    private int x;
    private int y;
    private boolean dead;
    private int score;
    private Direction direction;

    private PlayerThread playerThread;
    private PlatformManager platformManager;
    private BufferedImage playerImage;
    private final Timer runningAnimation;
    private int currentAnimationFrame;
    private boolean animatingRunningProcess;
    private PlayerHitbox hitbox;
    private final ArrayList<Integer> digits;
    private final ArrayList<BufferedImage> scoreboard;

    /**
     * Konštruktor triedy Player
     * @param platformManager - manažér platforiem
     */
    public Player(PlatformManager platformManager) {

        this.playerImage = null;
        this.animatingRunningProcess = false;
        this.scoreboard = new ArrayList<>();
        this.scoreboard.add(LoadedImages.getNumbers().get(0));
        this.digits = new ArrayList<>();

        this.platformManager = platformManager;
        this.score = 0;
        this.direction = Direction.RIGHT;
        this.x = 200;
        this.y = 600;
        this.hitbox = new PlayerHitbox(this.x, this.y);

        this.playerTimer = new Timer(75, this);
        this.playerTimer.start();

        this.playerThread = new PlayerThread(this);
        this.playerThread.start();

        //actionlistener nahradený lambdou
        this.runningAnimation = new Timer(25, e -> {
            if (Player.this.direction == Direction.RIGHT) {
                Player.this.playerImage = LoadedImages.getRunningImages().get(Player.this.currentAnimationFrame - 1);
            } else {
                Player.this.playerImage = LoadedImages.getInvertedRunningImages().get(Player.this.currentAnimationFrame - 1);
            }

            if (Player.this.currentAnimationFrame == 18 ) {
                Player.this.currentAnimationFrame = 1;
            } else {
                Player.this.currentAnimationFrame += 1;
            }
        });
    }

    /**
     * Getter pre obrázok
     * @return vráti obrázok hráča
     */
    public BufferedImage getImage() {
        return this.playerImage;
    }

    /**
     * Getter pre súradnicu X hráča
     * @return vráti hodnotu X-ovej súradnice
     */
    public int getX() {
        return this.x;
    }

    /**
     * Setter pre súradnicu X hráča
     * @param x - nastaví X-ovej súradnice hráča
     */
    public void setX(int x) {
        this.x = x;
        this.hitbox.setX(x);
    }

    /**
     * Getter pre súradnicu Y hráča
     * @return vráti hodnotu Y-ovej súradnice
     */
    public int getY() {
        return this.y;
    }

    /**
     * Setter pre súradnicu Y hráča
     * @param y - nastaví Y-ovej súradnice hráča
     */
    public void setY(int y) {
        this.y = y;
        this.hitbox.setY(y);
    }

    /**
     * Getter pre skóre hráča
     * @return vráti hodnotu celkového skóre hráča
     */
    public int getScore() {
        return this.score;
    }

    /**
     * nastaví skóre hráča na určitú hodnotu
     * @param score - hodnota, na ktorú bude skóre hráča nastavené
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * zistí, v akom stave je hráč. Hráč sa môže pohybovať nahor, nadol alebo spadnúť s platformy
     */
    public void playerStatus() {
        if ((this.y > 1000 || this.hitbox.getRightX() < 0 || this.hitbox.getLeftX() > 1800) && !this.dead) {
            this.die();
        } else if (!this.dead) {

            this.checkObjectHitboxes();
            for (IPlatform platform : this.platformManager.getPlatforms()) {

                if (((this.hitbox.getBottomY()) >= platform.getY()) &&
                    ((this.hitbox.getBottomY()) <= platform.getY() + 40) &&
                    ((this.hitbox.getRightX()) >= platform.getX() &&
                    ((this.hitbox.getLeftX()) <= platform.getXRightSide()))) {


                    this.playerThread.setImpact();
                    this.setY(platform.getY() - 70);

                    if (!platform.getInteractedWith()) {
                        platform.special();
                        platform.setInteractedWith();
                    }

                    this.startRunningAnimation();
                    this.animatingRunningProcess = true;
                    return;
                }
            }

            if (this.playerThread.isPlayerIsInJumpingProcess() && !this.playerThread.isFalling()) {
                //System.out.println("Player is moving up");
                this.stopRunningAnimation();
                this.setJumpingImage();
            } else if (this.playerThread.isPlayerIsInJumpingProcess() && this.playerThread.isFalling()) {
                //System.out.println("Player is falling down");
                this.stopRunningAnimation();
                this.setFallingImage();
            } else if (!this.playerThread.isPlayerIsInJumpingProcess()) {
                //System.out.println("Player rolled off the platform");
                this.playerThread.startFalling();
            }
        }
    }

    /**
     * nastaví obrázok hráča na obrázok výskoku
     */
    private void setJumpingImage() {
        if (!this.dead) {
            if (this.direction == Direction.RIGHT) {
                this.playerImage = LoadedImages.getJumpImage();
            } else {
                this.playerImage = LoadedImages.getInvertedJumpImage();
            }
        }
    }

    /**
     * nastaví obrázok hráča na obrázok padania
     */
    private void setFallingImage() {
        if (!this.dead) {
            if (this.direction == Direction.RIGHT) {
                this.playerImage = LoadedImages.getFallImage();
            } else {
                this.playerImage = LoadedImages.getInvertedFallImage();
            }
        }
    }

    /**
     * začne animáciu pohybu hráča
     */
    private void startRunningAnimation() {
        if (!this.animatingRunningProcess && !this.dead) {
            this.currentAnimationFrame = 1;
            this.runningAnimation.start();
        }
    }

    /**
     * preruší animáciu pohybu hráča
     */
    private void stopRunningAnimation() {
        this.animatingRunningProcess = false;
        this.runningAnimation.stop();
    }

    /**
     * skontroluje hitbox hráča s ostatnýmu hitboxmi
     */
    private void checkObjectHitboxes() {
        for (IObject object : this.platformManager.getObjects()) {

            if (((this.hitbox.getBottomY()) >= object.getY()) &&
                    ((this.hitbox.getUpperY()) <= (object.getY() + object.getHeight())) &&
                    ((this.hitbox.getRightX()) >= object.getX() &&
                            ((this.hitbox.getLeftX()) <= (object.getXRightSide())))) {

                if (!object.getInteractedWith()) {
                    object.interaction();
                    object.setInteractedWith();
                }
            }
        }
    }

    /**
     * inicalizuje výskok hráča
     */
    public void jump() {
        if (!this.playerThread.isPlayerIsInJumpingProcess() && !this.dead) {
            this.playerThread.setIsJumping();
            this.playerThread.setNoImpact();
        }
    }

    /**
     * donúti hráča vyskočiť
     */
    public void forceJump() {
        this.playerThread.setForcedJump();
    }

    /**
     * eliminuje hráča z hry
     */
    public void die() {
        this.dead = true;
        this.stopRunningAnimation();
        this.playerThread.setImpact();
        Game.getInstance().stop();
        this.playerImage = null;

        Game.getInstance().askToRestart();
    }

    /**
     * Getter pre booleanovskú hodnotu atribútu dead
     * @return vráti true v prípade, že hráč je mŕtvy, false ak žije
     */
    public boolean isPlayerDead() {
        return this.dead;
    }

    /**
     * pripíše určité skóre na konto hráča
     * @param score - hodnota skóre, ktorá bude hráčovi pridaná
     */
    public void addScore(int score) {
        this.score += score;
        this.updateScore();
    }

    /**
     * aktualizuje aktuálne skóre
     */
    public void updateScore() {
        this.digits.clear();
        int total = this.score;
        int digit;
        while (total > 0) {
            digit = total % 10;
            total = total / 10;
            this.digits.add(0, digit);
        }
    }

    /**
     * Getter pre jednotlivé číslice hráčovho skóre
     * @return vráti ArrayList s číslicami, ktoré reprezentujú celkové skóre hráča
     */
    public ArrayList<BufferedImage> getScoreboard() {
        this.scoreboard.clear();
        ArrayList<Integer> numbers = new ArrayList<>(this.digits);

        if (numbers.isEmpty()) {
            this.scoreboard.add(LoadedImages.getNumbers().get(0));
            return this.scoreboard;
        }

        while (!numbers.isEmpty()) {
            this.scoreboard.add(LoadedImages.getNumbers().get(numbers.get(0)));
            numbers.remove(0);
        }
        return this.scoreboard;
    }

    /**
     * nastaví smer pohybu
     * @param direction - smer, buď vľavo alebo vpravo
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * vráti hráča do pôvodného stavu pri reštarte
     * @param platformManager - manažér platforiem
     */
    public void restart(PlatformManager platformManager) {
        this.playerThread.stopRunning();
        this.playerTimer.stop();

        this.dead = false;
        this.playerImage = null;
        this.animatingRunningProcess = false;

        this.platformManager = platformManager;
        this.score = 0;
        this.digits.clear();
        this.direction = Direction.RIGHT;
        this.x = 200;
        this.y = 600;
        this.hitbox = new PlayerHitbox(this.x, this.y);

        Timer newPlayerTimer = new Timer(75, this);
        newPlayerTimer.start();

        this.playerThread = new PlayerThread(this);
        this.playerThread.start();
    }

    /**
     * metóda, ktorá sa vykoná pri kúpe vylepšenia JumpBoost
     * @param level - úroveň vylepšenia
     */
    public void boughtJumpBoost(int level) {
        if (level == 1) {
            this.playerThread.setJumpBoost(1);
        } else if (level == 2) {
            this.playerThread.setJumpBoost(2);
        }
    }

    /**
     * metóda, ktorá sa vykoná pri kúpe vylepšenia FeatherFalling
     * @param level - úroveň vylepšenia
     */
    public void boughtFeatherFalling(int level) {
        if (level == 1) {
            this.playerThread.setFeatherFalling(3);
        }
    }

    /**
     * metóda, ktorá sa vykoná pri kúpe vylepšenia Agility
     * @param level - úroveň vylepšenia
     */
    public void boughtAgility(int level) {
        if (level == 1) {
            Game.setAgilityBoost(2);
        }
        if (level == 2) {
            Game.setAgilityBoost(4);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
