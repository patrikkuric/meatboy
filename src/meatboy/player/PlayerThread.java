package meatboy.player;


/**
 * 5/6/2022 - 4:00 PM
 *
 * Trieda PlayerThread - vlákno pre pohyb hráča
 * @author Patrik Kuric
 */

public class PlayerThread extends Thread {

    private boolean playerIsInJumpingProcess;
    private boolean impact;
    private boolean falling;
    private boolean forcedJump;

    private final Player player;
    private int velocity;

    private int jumpBoost;
    private int featherfallingeffect;
    private boolean running;

    /**
     * Konštruktor triedy PlayerThread
     * @param player - príslušný hráč
     */
    public PlayerThread(Player player) {
        this.running = true;

        this.playerIsInJumpingProcess = false;
        this.impact = false;
        this.falling = false;
        this.forcedJump = false;
        this.velocity = 0;
        this.player = player;

        this.jumpBoost = 0;
        this.featherfallingeffect = 0;
    }

    /**
     * spracováva pohyb hráča - stúpanie, klesanie a rýchlosť týchto činností
     */
    public void run() {

        while (this.running) {

            if (!this.playerIsInJumpingProcess && !this.forcedJump) {
                this.player.playerStatus();

                try {
                    this.wait16Millis();
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int incrementTimer = 1;
            if (this.falling) {
                this.velocity = -1;
            } else {
                this.velocity = 13;
            }

            while (!this.impact) {

                // incrementTimer: čím väčší, tým pomalšie sa mení i
                // jumpboost: čím väčší, tým vyššie skáče
                // featherfalling effect: čím väčší, tým pomalšie padá k zemi
                if (this.velocity >= 0 && incrementTimer == 3 + this.jumpBoost ||
                     this.velocity < 0 && incrementTimer == 2 + this.featherfallingeffect) {

                    this.velocity--;
                    incrementTimer = 1;
                } else {
                    incrementTimer++;
                }

                this.falling = this.velocity < 0;

                this.player.setY(this.player.getY() - this.velocity);
                this.player.playerStatus();

                try {
                    this.wait16Millis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if (!this.running) {
                    break;
                }
            }

            if (this.forcedJump) {
                this.setNoImpact();
                this.forcedJump = false;
                this.falling = false;
                continue;
            }

            this.playerIsInJumpingProcess = false;
            this.falling = false;
        }
    }

    /**
     * zastaví toto vlákno na 16 milisekúnd
     * @throws InterruptedException - nutná výnimka pre vykonanie metódy Thread.sleep()
     */
    private void wait16Millis() throws InterruptedException {
        sleep(16);
    }

    /**
     * nastaví status hráča, že hráč skáče
     */
    public void setIsJumping() {
        this.playerIsInJumpingProcess = true;
    }

    /**
     * nastaví status hráča, že hráč sa nedotýka platformy
     */
    public void setNoImpact() {
        this.impact = false;
    }

    /**
     * nastaví status hráča, že hráč sa dotýka platformy
     */
    public void setImpact() {
        this.impact = true;
    }

    /**
     * inicializuje padanie hráča
     */
    public void startFalling() {
        this.playerIsInJumpingProcess = true;
        this.falling = true;
        this.impact = false;
    }

    /**
     * Getter pre stav hráča, konkrétne či padá
     * @return vráti hodnotu true v prípade, že hráč padá, false ak nie
     */
    public boolean isFalling() {
        return this.falling;
    }

    /**
     * pošle ďalej správu, že hráčovi bolo prikázané vyskočiť
     */
    public void setForcedJump() {
        this.forcedJump = true;
    }

    /**
     * Getter pre stav hráča, konkrétne či hráč je vo vzduchu, teda či sa pohybuje nahor alebo padá
     * @return vráti hodnotu true v prípade, že hráč je v procese výskoku alebo padania, false ak nie
     */
    public boolean isPlayerIsInJumpingProcess() {
        return this.playerIsInJumpingProcess;
    }

    /**
     * Setter pre vylepšenie JumpBoost
     * @param amount - množstvo pixelov o ktoré bude hráč vyššie skákať
     */
    public void setJumpBoost(int amount) {
        this.jumpBoost = amount;
    }

    /**
     * Setter pre vylepšenie FeatherFalling
     * @param amount - množstvo pixelov o ktoré bude hráč rýchlejšie padať
     */
    public void setFeatherFalling(int amount) {
        this.featherfallingeffect = amount;
    }

    /**
     * zastaví vlákno
     */
    public void stopRunning() {
        this.running = false;
    }
}