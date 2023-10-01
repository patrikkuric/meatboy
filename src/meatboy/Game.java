package meatboy;

import meatboy.platforms.PlatformGreen;
import meatboy.platforms.PlatformManager;
import meatboy.player.Player;
import meatboy.other.Direction;
import meatboy.platforms.IPlatform;
import meatboy.other.Upgrades;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 3/19/2022
 * Time: 10:14 PM
 *
 * Trieda Game - hra
 * @author : Patrik Kuric
 */

public class Game implements KeyListener, ActionListener {

    private int elapsedTime;
    private Screen screen;

    private static Game singleton;

    private Set<Integer> pressedKeys;
    private int restartedAt;

    private IPlatform firstPlatform;
    private PlatformManager platformManager;
    private MovingThread movingThread;
    private boolean running;
    private final Player player;
    private static int agilityBoost;

    private final Upgrades upgrades;

    private final JFrame jFrame;

    static {
        singleton = new Game();
    }

    /**
     * Main metóda, inicializuje hru
     * @param args - argumenty
     */
    public static void main(String[] args) {
        getInstance();
    }

    /**
     * Konštruktor Singletonovskej tiedy Hra
     */
    public Game() {

        this.jFrame = new JFrame();
        this.jFrame.setName("MeatBoy");

        this.firstPlatform = new PlatformGreen(150, 880, 16);
        this.platformManager = new PlatformManager();
        this.platformManager.addFirstPlatform(this.firstPlatform);

        this.movingThread = new MovingThread(this.platformManager);
        this.movingThread.start();

        this.player = new Player(this.platformManager);
        this.screen = new Screen(this.player, this.platformManager);
        this.jFrame.add(this.screen.getJPanel());

        this.jFrame.setResizable(false);
        this.jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.jFrame.setVisible(true);

        this.jFrame.pack();
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.addKeyListener(this);

        this.pressedKeys = new HashSet<>();

        Timer timer = new Timer(10, this);
        timer.start();
        this.running = true;

        this.elapsedTime = 0;
        this.startCountingTime();
        this.restartedAt = 0;
        this.upgrades = new Upgrades();
        agilityBoost = 0;


        this.jFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                Game.getInstance().askToContinue();
            }

        });
    }

    /**
     * Spýta sa uživateľa, či chce hrať ďalej
     */
    public void askToContinue() {

        if (JOptionPane.showConfirmDialog(this.jFrame, "Chceš naozaj skončiť?", "Ukončenie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            this.restart();
        }
    }

    /**
     * Spýta sa uživateľa, či chce reštartovať hru
     */
    public void askToRestart() {

        if (JOptionPane.showConfirmDialog(this.jFrame, "Koniec hry! \n" +
                "Nahraté skóre: " + this.getNumberOfPlatformsTotal() + "\n" +
                "Doba trvania hry: " + this.getTime() + " sekúnd.\n\n" +
                "Chceš reštartovať hru?", "Koniec", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            this.restart();
        } else {
            System.exit(0);
        }
    }

    /**
     * začne počítať čas
     */
    private void startCountingTime() {
        Timer timeCounter = new Timer(1000, e -> Game.this.elapsedTime += 1);
        timeCounter.start();
    }

    /**
     * Getter pre Singletonovskú inštanciu triedy Hra
     * @return vráti inštanciu triedy Hra
     */
    public static Game getInstance() {
        if (singleton == null) {
            singleton = new Game();
        }

        return singleton;
    }

    @Override
    public void keyTyped(KeyEvent e) {    }

    /**
     * priradí stlačenú klávesu k príslušnému vylepšeniu
     * @param e - vstup z klávesnice
     */
    @Override
    public void keyPressed(KeyEvent e) {
        this.pressedKeys.add(e.getKeyCode());

        if (e.getKeyCode() == 49) {
            if (this.upgrades.getJumpBoost1()) {
                if (this.player.getScore() >= 20) {
                    if (this.upgrades.buyJumpBoost2()) {
                        this.player.setScore(this.player.getScore() - 20);
                        this.player.updateScore();
                        this.player.boughtJumpBoost(2);
                    }
                }
            } else if (this.player.getScore() >= 10) {
                if (this.upgrades.buyJumpBoost1()) {
                    this.player.setScore(this.player.getScore() - 10);
                    this.player.updateScore();
                    this.player.boughtJumpBoost(1);
                }
            }
        }

        if (e.getKeyCode() == 50) {
            if (this.player.getScore() >= 30) {
                if (this.upgrades.buyFeatherFalling()) {
                    this.player.setScore(this.player.getScore() - 30);
                    this.player.updateScore();
                    this.player.boughtFeatherFalling(1);
                }
            }
        }

        if (e.getKeyCode() == 51) {
            if (this.upgrades.getAgility1()) {
                if (this.player.getScore() >= 20) {
                    if (this.upgrades.buyAgility2()) {
                        this.player.setScore(this.player.getScore() - 20);
                        this.player.updateScore();
                        this.player.boughtAgility(2);
                    }
                }
            } else if (this.player.getScore() >= 10) {
                if (this.upgrades.buyAgility1()) {
                    this.player.setScore(this.player.getScore() - 10);
                    this.player.updateScore();
                    this.player.boughtAgility(1);
                }
            }
        }

        if (e.getKeyCode() == 67) {
            this.player.addScore(100);
        }
    }

    /**
     * pošle správu hráčovi, že sa má otočiť doľava/doprava
     * @param e - vstup z klávesnice
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
            if (!this.player.isPlayerDead()) {
                this.player.setDirection(Direction.RIGHT);
            }
        }
        this.pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (this.pressedKeys.contains(32) || this.pressedKeys.contains(38)  || this.pressedKeys.contains(87)) {
            this.player.jump();
        }

        if (this.pressedKeys.contains(37) || this.pressedKeys.contains(65)) {
            this.player.setX(this.player.getX() - 4 - agilityBoost);
            this.player.setDirection(Direction.LEFT);
        }

        if (this.pressedKeys.contains(39) || this.pressedKeys.contains(68)) {
            this.player.setX(this.player.getX() + 2 + agilityBoost);
            this.player.setDirection(Direction.RIGHT);
        }

        if (this.pressedKeys.contains(82)) {
            if (this.restartedAt + 2 <= this.elapsedTime) {
                this.restartedAt = this.elapsedTime;
                this.restart();
            }
        }
    }

    /**
     * reštartuje hru
     */
    private void restart() {
        this.platformManager.deleteEverything();
        this.pressedKeys = new HashSet<>();
        this.elapsedTime = 0;

        this.running = true;
        this.firstPlatform = new PlatformGreen(150, 880, 14);
        this.platformManager = new PlatformManager();
        this.platformManager.addFirstPlatform(this.firstPlatform);

        this.movingThread.end();
        this.movingThread = new MovingThread(this.platformManager);
        this.movingThread.start();

        this.upgrades.reset();
        agilityBoost = 0;
        this.player.restart(this.platformManager);
        this.screen = new Screen(this.player, this.platformManager);
        this.jFrame.add(this.screen.getJPanel());
    }

    /**
     * zrýchli hru
     */
    public void speedUp() {
        this.movingThread.speedUp();
    }

    /**
     * pridá hráčovi množstvo skóre uvedené v parametri
     * @param i - množstvo skóre, ktoré hráčovi pridá
     */
    public void addScore(int i) {
        this.player.addScore(i);
    }

    /**
     * eliminuje hráča
     */
    public void playerDies() {
        this.player.die();
    }

    /**
     * prekreslí obrazovku
     */
    public void repaintTheScreen() {
        this.screen.shouldRepaint();
    }

    /**
     * donúti hráča vyskočiť
     */
    public void forcePlayerToJump() {
        this.player.forceJump();
    }

    /**
     * Getter pre informáciu, či hra beží
     * @return vráti true, ak hra beží, false ak nie
     */
    public boolean isTheGameRunning() {
        return this.running;
    }

    /**
     * zastaví hru
     */
    public void stop() {
        this.running = false;
    }

    /**
     * nastaví vylepšenie pre svižnejší pohyb hráča
     * @param amount - množstvo, o ktoré sa bude hráč rýchlejšie hýbať do strán
     */
    public static void setAgilityBoost(int amount) {
        agilityBoost = amount;
    }

    /**
     * Setter, ktorý nastaví hodnotu maximálnej rýchlosti hry
     * @param speed - hodnota maximálnej rýchlosti hry
     */
    public void setCurrentMaxSpeed(int speed) {
        this.movingThread.setCurrentMaxSpeed(speed);
    }

    /**
     * Getter pre počet platforiem, ktoré boli počas hry vytvorené
     * @return vráti celkový počet platforiem
     */
    public int getNumberOfPlatformsTotal() {
        return this.platformManager.getPlatformsTotal();
    }

    /**
     * vráti uplynutý čas
     * @return uplynutý čas v sekundách
     */
    public int getTime() {
        return this.elapsedTime;
    }
}
