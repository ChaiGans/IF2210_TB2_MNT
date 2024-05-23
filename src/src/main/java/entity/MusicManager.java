package entity;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private static Map<String, MediaPlayer> players = new HashMap<>();
    private static Map<String, Media> songs = new HashMap<>();

    // Preload music tracks
    public static void loadMusic(String identifier, String resourcePath) {
        try {
            Media media = new Media(MusicManager.class.getResource(resourcePath).toExternalForm());
            songs.put(identifier, media);
        } catch (NullPointerException e) {
            System.err.println("Could not load music. Check resource path: " + resourcePath);
        }
    }

    // Play a specific track, with an option to loop
    public static void playMusic(String identifier, boolean loop) {
        stopMusic(identifier);  // Stop the current track if it's playing
        Media media = songs.get(identifier);
        if (media != null) {
            MediaPlayer player = new MediaPlayer(media);
            if (loop) {
                player.setCycleCount(MediaPlayer.INDEFINITE);
            }
            player.play();
            players.put(identifier, player);  // Store the player
        }
    }

    // Stop a specific track
    public static void stopMusic(String identifier) {
        MediaPlayer player = players.get(identifier);
        if (player != null) {
            player.stop();
            player.dispose();  // Dispose of the player
            players.remove(identifier);
        }
    }

    // Stop all music
    public static void stopAllMusic() {
        for (MediaPlayer player : players.values()) {
            if (player != null) {
                player.stop();
                player.dispose();
            }
        }
        players.clear();
    }
}
