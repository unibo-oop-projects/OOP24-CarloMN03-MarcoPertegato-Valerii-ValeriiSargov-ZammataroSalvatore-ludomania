package ludomania.core.impl;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ludomania.core.api.AudioManager;

public final class AudioManagerImpl implements AudioManager {
    private final Map<String, AudioClip> soundEffects;
    private final Map<String, MediaPlayer> backgroundTracks;
    private double masterVolume;

    public AudioManagerImpl(final double masterVolume) {
        soundEffects = new HashMap<>();
        backgroundTracks = new HashMap<>();
        this.masterVolume = masterVolume;
    }

    @Override
    public void initialize() {
        load();
    }

    private void load() {
        loadSoundEffect("click", "/audio/sfx/click.wav");
        loadBackgroundTrack("devilTrigger", "/audio/music/deviltrigger.mp3");
        loadBackgroundTrack("furinaTheme", "/audio/music/furinaTheme.mp3");        
    }

    @Override
    public void loadSoundEffect(final String id, final String filePath) {
        final AudioClip sound = new AudioClip(getClass().getResource(filePath).toExternalForm());
        soundEffects.put(id, sound);
    }

    @Override
    public void loadBackgroundTrack(final String id, final String filePath) {
        final Media media = new Media(getClass().getResource(filePath).toExternalForm());
        final MediaPlayer player = new MediaPlayer(media);
        player.setVolume(masterVolume);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundTracks.put(id, player);
    }

    @Override
    public void playSound(final String id) {
        final AudioClip sound = soundEffects.get(id);
        if (sound != null) {
            sound.setVolume(masterVolume);
            sound.play();
        }
    }

    @Override
    public void playMusic(final String id) {
        final MediaPlayer track = backgroundTracks.get(id);
        if (track != null && track.getStatus() != MediaPlayer.Status.PLAYING) {
            stopAllMusic();
            track.play();
        }

    }

    @Override
    public void stopAllMusic() {
        backgroundTracks.values().forEach(MediaPlayer::stop);
    }

    @Override
    public void setMasterVolume(final double volume) {
        this.masterVolume = volume;
        soundEffects.values().forEach(s -> s.setVolume(volume));
        backgroundTracks.values().forEach(p -> p.setVolume(volume));
    }

    @Override
    public double getMasterVolume() {
        return masterVolume;
    }
}
