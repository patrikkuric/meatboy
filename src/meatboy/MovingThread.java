package meatboy;

import meatboy.platforms.PlatformManager;
import meatboy.objects.IObject;
import meatboy.platforms.IPlatform;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Date: 3/19/2022
 * Time: 10:14 PM
 *
 * Trieda MovingThread - vlákno pre pohyb platforiem
 * @author : Patrik Kuric
 */

public class MovingThread extends Thread implements ActionListener {

    private int distancePerFrame;
    private final Timer slowDown;
    private final PlatformManager platformManager;
    private boolean ended;
    private int currentMaxSpeed;

    /**
     * Konštruktor triedy MovingThread
     * @param platformManager - manažér platforiem
     */
    public MovingThread(PlatformManager platformManager) {
        this.ended = false;
        this.distancePerFrame = 8;
        this.slowDown = new Timer(200, this);
        this.platformManager = platformManager;
        this.currentMaxSpeed = 8;
    }

    /**
     * začne vlákno s hlavným Game Loopom nutným pre priebeh hry
     */
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();

        while (!this.ended) {
            long curTime = System.nanoTime();
            delta += (curTime - lastTime) / (1000000000.0 / 60);
            lastTime = curTime;

            if (delta >= 1) {
                try {
                    this.moveWorld();
                    this.platformManager.checkPlatforms();
                    Game.getInstance().repaintTheScreen();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta--;
            }
        }
    }

    /**
     * posunie hráča, všetky platformy a objekty smerom doľava
     * @throws IOException - vyhodí výnimku IOException v prípade, že sa stala chyba vo vstupe
     */
    private void moveWorld() throws IOException {
        if (Game.getInstance().isTheGameRunning()) {
            try {
                for (IPlatform platform : this.platformManager.getPlatforms()) {
                    if (platform == null) {
                        continue;
                    }

                    if (platform.getXRightSide() <= 0) {
                        platform.stopAnimation();
                        int index = this.platformManager.getPlatforms().indexOf(platform);
                        this.platformManager.setPlatformAtIndex(index, null);
                    } else {
                        platform.setX(platform.getX() - this.distancePerFrame);
                    }
                }

                if (this.platformManager.getObjects() != null) {
                    for (IObject object : this.platformManager.getObjects()) {
                        if (object == null) {
                            continue;
                        }

                        if (object.getXRightSide() <= 0) {
                            object.stopAnimation();
                            int index = this.platformManager.getObjects().indexOf(object);
                            this.platformManager.setObjectAtIndex(index, null);
                        } else {
                            object.setX(object.getX() - this.distancePerFrame);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * zrýchli priebeh hry
     */
    public void speedUp() {
        this.distancePerFrame += 7;
        this.slowDown.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.distancePerFrame == this.currentMaxSpeed) {
            this.slowDown.stop();
        } else {
            this.distancePerFrame--;
        }
    }

    /**
     * nastaví maximálnu rýchlosť hry
     * @param speed - rýchlosť typu int
     */
    public void setCurrentMaxSpeed(int speed) {
        this.currentMaxSpeed = speed;
        this.distancePerFrame = speed;
    }

    /**
     * ukončí vlákno, teda zastaví pohyb všetkého
     */
    public void end() {
        this.ended = true;
    }
}
