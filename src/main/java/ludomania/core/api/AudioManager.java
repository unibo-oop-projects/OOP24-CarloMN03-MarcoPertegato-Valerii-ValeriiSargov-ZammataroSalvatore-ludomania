package ludomania.core.api;

/**
 * Defines the audio management interface for loading, playing,
 * and controlling sound effects and background music.
 * <p>
 * Implementations of this interface handle audio playback and volume control
 * across the application.
 */
public interface AudioManager {
    /**
     * Initializes the audio system.
     */
    void initialize();

    /**
     * Loads a sound effect with the given identifier.
     *
     * @param id       the unique identifier for the sound
     * @param filePath the path to the audio file
     */
    void loadSoundEffect(String id, String filePath);

    /**
     * Loads a background music track with the given identifier.
     *
     * @param id       the unique identifier for the music
     * @param filePath the path to the audio file
     */
    void loadBackgroundTrack(String id, String filePath);

    /**
     * Plays the sound effect associated with the given identifier.
     *
     * @param id the sound effect identifier
     */
    void playSound(String id);

    /**
     * Plays the background music track associated with the given identifier.
     *
     * @param id the background track identifier
     */
    void playMusic(String id);

    /**
     * Stops all currently playing background music.
     */
    void stopAllMusic();

    /**
     * Sets the global audio volume.
     *
     * @param volume the master volume (0.0 to 1.0)
     */
    void setMasterVolume(double volume);

    /**
     * Returns the current master volume level.
     *
     * @return the volume level (0.0 to 1.0)
     */
    double getMasterVolume();

}
