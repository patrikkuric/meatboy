package meatboy;

import meatboy.objects.Coin;
import meatboy.platforms.PlatformManager;
import meatboy.player.Player;
import meatboy.objects.IObject;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Date: 3/19/2022
 * Time: 10:14 PM
 *
 * Trieda Screen - obrazovka
 * @author : Patrik Kuric
 */

public class Screen {

    private static final int SCREEN_WIDTH = 1800;
    private static final int SCREEN_HEIGHT = 1000;

    private final BufferedImage backgroundImage;
    private final BufferedImage woodenSign;
    private final Coin placeholderCoin;

    private final Player player;
    private final PlatformManager platformManager;

    private final JPanel jPanel;


    /**
     * Konštruktor triedy Screen, ktorý slúži na vykresľovanie hráča, všetkých platforiem a objektov na obrazovku
     * @param player - hráč
     * @param platformManager - manažér platforiem
     */
    public Screen(Player player, PlatformManager platformManager) {

        this.jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {

                super.paint(g);
                Graphics2D g2D = (Graphics2D)g;


                g2D.drawImage(Screen.this.backgroundImage, 0, 0, null);
                g2D.drawImage(Screen.this.woodenSign, 40, 40, null);


                for (int i = Screen.this.platformManager.getPlatforms().size() - 1; i >= 0; i--) {
                    if (Screen.this.platformManager.getPlatforms().get(i) != null) {
                        g2D.drawImage(Screen.this.platformManager.getPlatforms().get(i).getImage(),
                                Screen.this.platformManager.getPlatforms().get(i).getX(),
                                Screen.this.platformManager.getPlatforms().get(i).getY(), null);
                    }
                }

                for (IObject object : Screen.this.platformManager.getObjects()) {
                    if (object != null) {
                        g2D.drawImage(object.getImage(), object.getX(), object.getY(), null);
                    }
                }

                g2D.drawImage(Screen.this.player.getImage(), Screen.this.player.getX(), Screen.this.player.getY(), null);

                int offset = 0;
                for (int i = Screen.this.player.getScoreboard().size() - 1; i >= 0; i--) {
                    g2D.drawImage(Screen.this.player.getScoreboard().get(i), 1650 - offset, 50, null);
                    offset += 35;
                }
                g2D.drawImage(Screen.this.placeholderCoin.getImage(), Screen.this.placeholderCoin.getX(), Screen.this.placeholderCoin.getY(), null);

                g2D.dispose();
            }
        };

        this.jPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.jPanel.setDoubleBuffered(true);
        this.backgroundImage = LoadedImages.getBackgroundImage();
        this.woodenSign = LoadedImages.getWoodenSign();
        this.placeholderCoin = new Coin(1710, 45);

        this.player = player;
        this.platformManager = platformManager;

    }

    /**
     * prekreslí obrazovku
     */
    public void shouldRepaint() {
        this.jPanel.repaint();
        this.jPanel.revalidate();
        this.platformManager.removeNulls();
    }

    /**
     * Getter pre JPanel
     * @return vráti JPanel
     */
    public JPanel getJPanel() {
        return this.jPanel;
    }
}
