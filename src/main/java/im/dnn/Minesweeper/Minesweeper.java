package im.dnn.Minesweeper;

import im.dnn.Minesweeper.Commands.CleanAllCommand;
import im.dnn.Minesweeper.Commands.GetMineCommand;
import im.dnn.Minesweeper.Constants.Commands;
import im.dnn.Minesweeper.Listeners.ExplosionListener;
import im.dnn.Minesweeper.Listeners.MineListener;
import im.dnn.Minesweeper.Managers.ExplosionManager;
import im.dnn.Minesweeper.Managers.MineManager;
import im.dnn.Minesweeper.Models.Glow;
import im.dnn.Minesweeper.Models.Mine;
import im.dnn.Minesweeper.Utils.Logger;
import im.dnn.Minesweeper.Utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Minesweeper extends JavaPlugin {
    private MineManager mineManager;
    private ExplosionManager explosionManager;

    @Override
    public void onEnable() {
        Settings.setupConfig(this);
        Logger.setPlugin(this);

        Logger.info("Enabled plugin");
        setup();
    }

    @Override
    public void onDisable() {
        Logger.info("Disabled plugin");
    }

    private void setup () {
        this.mineManager = new MineManager(this);
        this.explosionManager = new ExplosionManager(this);

        registerEnchantments();
        registerCommands();
        registerEvents();
        registerRecipes();
    }

    private void registerCommands () {
        getCommand(Commands.CLEAN_ALL).setExecutor(new CleanAllCommand(this.explosionManager));
        getCommand(Commands.GET).setExecutor(new GetMineCommand(this));
    }

    private void registerEvents () {
        MineListener mineListener = new MineListener(this, this.explosionManager, this.mineManager.getMines());
        getServer().getPluginManager().registerEvents(mineListener, this);

        ExplosionListener explosionListener = new ExplosionListener(this, this.explosionManager);
        getServer().getPluginManager().registerEvents(explosionListener, this);
    }

    private void registerRecipes () {
        for (Mine mine: this.mineManager.getMines()) {
            Bukkit.addRecipe(mine.getRecipe());
        }
    }

    private void registerEnchantments () {
        Glow.registerEnchantment(this);
    }
}
