package ludomania.core;

public interface AudioManager {

    void initialize();

    void loadSoundEffect(String id, String filePath);

    void loadBackgroundTrack(String id, String filePath);

    void playSound(String id);

    void playMusic(String id);

    void stopAllMusic();

    void setMasterVolume(double volume);

    double getMasterVolume();

}