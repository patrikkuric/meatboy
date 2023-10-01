package meatboy.platforms;

import meatboy.Game;
import meatboy.objects.Chest;
import meatboy.objects.Coin;
import meatboy.objects.Enemy;
import meatboy.objects.Spike;
import meatboy.objects.IObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda PlatformManager - "manažér" platforiem, slúži na vytváranie a pracovanie s platformami
 * @author Patrik Kuric
 */

public class PlatformManager {

    private int randomOffset;
    private final int offset;
    private ArrayList<IPlatform> platforms;
    private ArrayList<IObject> objects;
    private int platformsCount;
    private int maximumSpikes;

    /**
     * Konštruktor triedy PlatformManager
     */
    public PlatformManager() {
        this.offset = 500;
        this.randomOffset = ThreadLocalRandom.current().nextInt(100, this.offset);
        this.platforms = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.platformsCount = 1;
        this.maximumSpikes = 1;
    }

    /**
     * kontroluje kedy je vhodné pridať novú platformu
     * @throws IOException vyhodí výnimku IOException v prípade chybného vstupu
     */
    public void checkPlatforms() throws IOException {
        if (this.getLastPlatformX() + this.randomOffset <= 1800) {

            IPlatform platform = this.createPlatform(this.randomOffset);
            this.addNewPlatform(platform);

            this.randomOffset = ThreadLocalRandom.current().nextInt(100, this.offset);
        }
    }

    /**
     * zaradí novú platformu a pridá jej objekty
     * @param platform - platforma, ktorá bude pridaná do zoznamu platforiem
     */
    public void addNewPlatform(IPlatform platform) {
        this.platforms.add(platform);
        this.platformsCount++;
        this.addObjects(platform);
    }

    /**
     * vytvorí platformu
     * @param randomOffset - odchýlka pomocou ktorej sa vypočíta kde bude položená nová platforma
     * @return vráti platformu typu IPlatform
     */
    public IPlatform createPlatform(int randomOffset) {

        int randomX = this.getLastPlatformX() + randomOffset;
        int randomY = ThreadLocalRandom.current().nextInt(700, 900);
        int randomWidth = ThreadLocalRandom.current().nextInt(6, 16);
        int randomColorNumber = 1;

        if (this.platformsCount >= 10) {

            if (this.platformsCount < 20) {
                this.maximumSpikes = 2;

                randomX = this.getLastPlatformX() + (randomOffset * 4) / 3;
                randomY = ThreadLocalRandom.current().nextInt(700, 900);
                randomWidth = ThreadLocalRandom.current().nextInt(2, 13);

                if (this.platformsCount == 10) {
                    randomColorNumber = 7;
                } else {
                    randomColorNumber = ThreadLocalRandom.current().nextInt(1, 9);
                }
            } else if (this.platformsCount < 50) {
                this.maximumSpikes = 4;

                randomY = ThreadLocalRandom.current().nextInt(630, 900);
                randomX = this.getLastPlatformX() + (randomOffset * 7) / 4;
                randomWidth = ThreadLocalRandom.current().nextInt(2, 11);

                if (this.platformsCount == 20) {
                    randomColorNumber = 9;
                } else {
                    randomColorNumber = ThreadLocalRandom.current().nextInt(1, 10);
                }
            } else if (this.platformsCount < 80) {
                this.maximumSpikes = 7;

                Game.getInstance().setCurrentMaxSpeed(10);
                randomY = ThreadLocalRandom.current().nextInt(600, 930);
                randomX = this.getLastPlatformX() + randomOffset * 2;
                randomWidth = ThreadLocalRandom.current().nextInt(2, 10);
                randomColorNumber = ThreadLocalRandom.current().nextInt(1, 10);
            } else {
                this.maximumSpikes = 10;

                Game.getInstance().setCurrentMaxSpeed(12);
                randomY = ThreadLocalRandom.current().nextInt(500, 930);
                randomX = this.getLastPlatformX() + randomOffset * 3;
                randomWidth = ThreadLocalRandom.current().nextInt(3, 9);
                randomColorNumber = ThreadLocalRandom.current().nextInt(1, 10);
            }
        }

        switch (randomColorNumber) {
            case 1: case 2: case 3: case 4: case 5: case 6:
                if (this.getLastPlatform() instanceof PlatformRed) {
                    if (randomWidth > 4) {
                        randomWidth = 5;
                    }
                    return new PlatformRed(randomX, randomY, randomWidth);
                } else if (this.getLastPlatform() instanceof PlatformBlue) {
                    return new PlatformBlue(randomX, randomY, randomWidth);
                } else {
                    return new PlatformGreen(randomX, randomY, randomWidth);
                }

            case 7: case 8:
                if (this.getLastPlatform() instanceof PlatformBlue) {
                    return new PlatformGreen(randomX, randomY, randomWidth);
                } else {
                    return new PlatformBlue(randomX, randomY, randomWidth);
                }

            case 9:
                if (this.getLastPlatform() instanceof PlatformRed) {
                    return new PlatformGreen(randomX, randomY, randomWidth);
                } else {
                    if (randomWidth > 6) {
                        randomWidth = 7;
                    }
                    return new PlatformRed(randomX, randomY, randomWidth);
                }

            default:
                return null;

        }
    }

    /**
     * pridá a vytvorí nové objekty, ktoré budú pridané na platformu
     * @param platform - platforma, ktorej budú pridané objekty
     */
    private void addObjects(IPlatform platform) {

        // Chest
        if (platform.getWidth() == 2) {
            IObject object = new Chest(platform.getX() + 64, platform.getY() - 111);
            object.setX(platform.getX() + object.getWidth() / 2);
            object.setY(platform.getY() - object.getHeight());
            this.objects.add(object);
            platform.addObject(object);

            // Coin
        } else if (platform.getWidth() > 5) {

            int offsetX = ThreadLocalRandom.current().nextInt(platform.getX(), platform.getXRightSide()) - platform.getX();
            int offsetY = ThreadLocalRandom.current().nextInt(150, 500);

            if (platform instanceof PlatformGreen || platform instanceof PlatformRed) {
                offsetY = (offsetY / 2) + ThreadLocalRandom.current().nextInt(0, 100);
            } else if (platform instanceof PlatformBlue) {
                offsetY = (offsetY / 2) + ThreadLocalRandom.current().nextInt(150, 300);
                offsetX = (offsetX / 4) + ThreadLocalRandom.current().nextInt(600, 1300);
            }

            IObject object = new Coin(platform.getX() + offsetX, platform.getY() - offsetY);
            object.setX(platform.getX() + offsetX);
            object.setY(platform.getY() - offsetY);
            this.objects.add(object);
        }

        // Spikes
        if ((platform instanceof PlatformGreen || platform instanceof PlatformBlue) && platform.getWidth() > 2) {
            int numberOfSpikes = 0;

            if (platform instanceof PlatformBlue && this.maximumSpikes >= 4 && platform.getWidth() > 4) {
                numberOfSpikes++;
            }

            for (int i = 0; i < platform.getWidth(); i++) {
                int chance = ThreadLocalRandom.current().nextInt(1, 12);
                if (chance == 1) {
                    numberOfSpikes++;
                }
            }

            if (numberOfSpikes > this.maximumSpikes) {
                numberOfSpikes = this.maximumSpikes;
            }

            for (int j = 0; j < numberOfSpikes; j++) {

                int randomX = ThreadLocalRandom.current().nextInt(platform.getX(), platform.getXRightSide() - 64);

                IObject object = new Spike(randomX , platform.getY() - 32);
                object.setX(randomX);
                object.setY(platform.getY() - 32);
                this.objects.add(object);
                platform.addObject(object);
            }
        }

        // Enemy
        if (platform instanceof PlatformRed && platform.getWidth() > 3) {

            int randomX = ThreadLocalRandom.current().nextInt(platform.getX(), platform.getXRightSide() - 64);
            int randomSpeed = ThreadLocalRandom.current().nextInt(2, 5);

            IObject object = new Enemy(randomX, platform.getY() - 64 - 10, platform, randomSpeed);
            object.setX(randomX);
            object.setY(platform.getY() - 64 - 10);
            this.objects.add(object);
            platform.addObject(object);
        }
    }

    /**
     * Getter pre zoznam platforiem, ktoré sa aktuálne nachádzajú na obrazovke
     * @return vráti zoznam platforiem, ktoré sa aktuálne nachádzajú na obrazovke a vyfiltruje ostatné platformy
     */
    public ArrayList<IPlatform> getPlatforms() {
        ArrayList<IPlatform> platformsLocal = new ArrayList<>();
        for (IPlatform platform : this.platforms) {
            if (platform != null) {
                platformsLocal.add(platform);
            }
        }
        return platformsLocal;
    }

    /**
     * Getter pre zoznam objektov, ktoré sa aktuálne nachádzajú na obrazovke
     * @return vráti zoznam objektov, ktoré sa aktuálne nachádzajú na obrazovke a vyfiltruje ostatné objektoy
     */
    public ArrayList<IObject> getObjects() {
        ArrayList<IObject> objectsLocal = new ArrayList<>();
        for (IObject object : this.objects) {
            if (object != null) {
                objectsLocal.add(object);
            }
        }
        return objectsLocal;
    }

    /**
     * vyfiltruje staré platformy a objekty, ktoré už sú irelevantné, pretože sú typom null
     */
    public void removeNulls() {

        while (true) {
            if (!this.platforms.remove(null) && !this.objects.remove(null)) {
                break;
            }
        }
    }

    /**
     * Setter pre nastavenie platformy na určitú pozíciu do zoznamu platforiem
     * @param i - pozícia, na ktorú zaradíme platformu
     * @param platform - platforma, ktorá bude zaradená do zoznamu platforiem
     */
    public void setPlatformAtIndex(int i, IPlatform platform) {
        this.platforms.set(i, platform);
    }

    /**
     * Setter pre nastavenie objektu na určitú pozíciu do zoznamu objektov
     * @param i - pozícia, na ktorú zaradíme objekt
     * @param object - objekt, ktorý bude zaradený do zoznamu objektov
     */
    public void setObjectAtIndex(int i, IObject object) {
        this.objects.set(i, object);
    }

    /**
     * Getter pre poslednú platformu
     * @return vráti poslednú platformu v zozname platforiem
     */
    public IPlatform getLastPlatform() {
        if (!this.platforms.isEmpty()) {
            return this.platforms.get(this.platforms.size() - 1);
        }
        return null;
    }

    /**
     * Getter pre súradnicu X poslednej platformy
     * @return vráti súradnicu X poslednej platformy v zozname platforiem
     */
    public int getLastPlatformX() {
        if (!this.platforms.isEmpty()) {
            return this.platforms.get(this.platforms.size() - 1).getXRightSide();
        }
        return 0;
    }

    /**
     * pridá prvú platformu do zoznamu platforiem
     * @param firstPlatform - prvá platforma
     */
    public void addFirstPlatform(IPlatform firstPlatform) {
        this.platforms.add(firstPlatform);
    }

    /**
     * zmaže všetky platformy a objekty
     */
    public void deleteEverything() {
        this.platforms = new ArrayList<>();
        this.objects = new ArrayList<>();
    }

    /**
     * vráti celkový počet platforiem, ktoré sa vyskytli v zozname platforiem
     */
    public int getPlatformsTotal() {
        return this.platformsCount;
    }
}