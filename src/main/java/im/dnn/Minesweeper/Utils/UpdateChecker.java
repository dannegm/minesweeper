package im.dnn.Minesweeper.Utils;

import im.dnn.Minesweeper.Constants.Commons;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (
                    InputStream inputStream = new URL(Commons.SPIGOT_UPDATE_API +this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)
            ) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                Logger.info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }

    public static void checkForUpdates (JavaPlugin plugin) {
        new UpdateChecker(plugin, Commons.RESOURCE_ID).getVersion(version -> {
            if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                Logger.importantInfo("There is a new update available.");
            }
        });
    }
}
