package ludomania.core;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private final Map<String, AudioClip> soundEffects;
    private final Map<String, MediaPlayer> backgroundTracks;
    private double masterVolume = 0.8;

    public AudioManager(double masterVolume) {
        soundEffects = new HashMap<>();
        backgroundTracks = new HashMap<>();
        this.masterVolume = masterVolume;
        load();
    }

    private void load() {
        loadSoundEffect("click", "/audio/sfx/click.wav");
        loadBackgroundTrack("devilTrigger", "/audio/music/deviltrigger.mp3");
    }

    // Carica un effetto sonoro (per suoni brevi) usando MediaPlayer invece di
    // AudioClip
    public void loadSoundEffect(String id, String filePath) {
        AudioClip sound = new AudioClip(getClass().getResource(filePath).toExternalForm());
        soundEffects.put(id, sound);
    }

    // Carica una traccia musicale (per loop/colonne sonore)
    public void loadBackgroundTrack(String id, String filePath) {
        Media media = new Media(getClass().getResource(filePath).toExternalForm());
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(masterVolume);
        player.setCycleCount(MediaPlayer.INDEFINITE); // Loop infinito
        backgroundTracks.put(id, player);
    }

    // Riproduzione effetto sonoro
    public void playSound(String id) {
        AudioClip sound = soundEffects.get(id);
        if (sound != null) {
            sound.setVolume(masterVolume);
            sound.play();
        }
    }

    // Riproduzione musica
    public void playMusic(String id) {
        MediaPlayer track = backgroundTracks.get(id);
        if (track != null && track.getStatus() != MediaPlayer.Status.PLAYING) {
            stopAllMusic();
            track.play();
        }

    }

    // Controlli
    public void stopAllMusic() {
        backgroundTracks.values().forEach(MediaPlayer::stop);
    }

    public void setMasterVolume(double volume) {
        this.masterVolume = volume;
        soundEffects.values().forEach(s -> s.setVolume(volume));
        backgroundTracks.values().forEach(p -> p.setVolume(volume));
    }

    public double getMasterVolume() {
        return masterVolume;
    }
}
