package im.dnn.Minesweeper.Utils;

import im.dnn.Minesweeper.Constants.Keys;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    static Settings singleton = null;
    private static FileConfiguration config;

    public static void setupConfig (JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        singleton = new Settings();

        Settings.config = plugin.getConfig();

        setupDefaults();
        plugin.saveConfig();
    }

    private static void setupDefaults () {
        Settings.config.addDefault(Keys.CONFIG_DEBUG, false);

        List<String> defaultDefuseTools = new ArrayList<>();
        defaultDefuseTools.add("RED_BANNER");
        Settings.config.addDefault(Keys.DEFUSED_TOOLS, defaultDefuseTools);

        Settings.config.addDefault(Keys.SOUND_ON_WILL_EXPLODE, "ENTITY_CREEPER_PRIMED");
        Settings.config.addDefault(Keys.SOUND_ON_PLACE, "ENTITY_CREEPER_HURT");
        Settings.config.addDefault(Keys.SOUND_ON_DEFUSE, "ENTITY_CREEPER_PRIMED");

        Settings.config.addDefault(Keys.MINES_DISPLAY_NAME, "Mina de <material>");
        Settings.config.addDefault(Keys.MINES_EXPLOSION_RATIO, 3.0);

        List<String> defaultBlocksAvailables = new ArrayList<>();
        defaultDefuseTools.add("DIRT");
        Settings.config.addDefault(Keys.MINES_BLOCKS, defaultBlocksAvailables);

        Settings.config.addDefault(Keys.MESSAGE_BYPASSED_MINE, "Has avoided explosion");
        Settings.config.addDefault(Keys.MESSAGE_NOPERM_CRAFT, "You don't have permissions to craft a mine");
        Settings.config.addDefault(Keys.MESSAGE_NOPERM_PLACE, "You don't have permissions to place a mine");
        Settings.config.addDefault(Keys.MESSAGE_NOPERM_GET, "You don't have permissions to use this command");
        Settings.config.addDefault(Keys.MESSAGE_NOPERM_DEFUSE, "You don't have permissions to defuse this mine. Mine is still can explode.");
        Settings.config.addDefault(Keys.MESSAGE_NOPERM_CLEAN_ALL, "You don't have permissions to use this command");
        Settings.config.addDefault(Keys.MESSAGE_INVALID_PLAYER, "Please, provide a valid player name");
        Settings.config.addDefault(Keys.MESSAGE_PLAYER_NOT_FOUND, "Given player is not found");
        Settings.config.addDefault(Keys.MESSAGE_DEFUSED_TITLE, "DEFUSED");
    }

    public static boolean isDebug () {
        return Settings.config.getBoolean(Keys.CONFIG_DEBUG);
    }

    public static double getDouble (String path) {
        return Settings.config.getDouble(path);
    }
    public static String getString (String path) {
        return Settings.config.getString(path);
    }

    public static List<String> getStringList (String path) {
        return Settings.config.getStringList(path);
    }
}
