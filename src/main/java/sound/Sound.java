package sound;



import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[40];
    FloatControl fc;
    public int volumeScale = 3;
    float volume;

    public Sound(){
        soundURL[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/sounds/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sounds/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sounds/swish11.wav");
        soundURL[8] = getClass().getResource("/sounds/levelup.wav");
        soundURL[9] = getClass().getResource("/sounds/cursor.wav");
        soundURL[10] = getClass().getResource("/sounds/burning.wav");
        soundURL[11] = getClass().getResource("/sounds/cuttree.wav");
        soundURL[12] = getClass().getResource("/sounds/gameover.wav");
        soundURL[13] = getClass().getResource("/sounds/stairs.wav");
        soundURL[14] = getClass().getResource("/sounds/sleep.wav");
        soundURL[15] = getClass().getResource("/sounds/blocked.wav");
        soundURL[16] = getClass().getResource("/sounds/parry.wav");
        soundURL[17] = getClass().getResource("/sounds/speak.wav");
        soundURL[18] = getClass().getResource("/sounds/Merchant.wav");
        soundURL[19] = getClass().getResource("/sounds/Dungeon.wav");
        soundURL[20] = getClass().getResource("/sounds/chipwall.wav");
        soundURL[21] = getClass().getResource("/sounds/dooropen.wav");
        soundURL[22] = getClass().getResource("/sounds/FinalBattle.wav");
        soundURL[23] = getClass().getResource("/sounds/boolaugh.wav");
        soundURL[24] = getClass().getResource("/sounds/thedeadlaugh.wav");
        soundURL[25] = getClass().getResource("/sounds/thunder.wav");
    }
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        }catch (Exception e){
            System.out.println("Sound not found");
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
    public void playMusic(int i){
        setFile(i);
        play();
        loop();
    }
    public void stopMusic(){
        stop();
    }
    //Sound Effects
    public void playSE(int i){
        setFile(i);
        play();
    }
    public void checkVolume(){
        switch (volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }


}
