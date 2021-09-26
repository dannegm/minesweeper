package im.dnn.Minesweeper.Managers;

import im.dnn.Minesweeper.Constants.Keys;
import im.dnn.Minesweeper.Minesweeper;
import im.dnn.Minesweeper.Models.Mine;
import im.dnn.Minesweeper.Utils.Settings;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MineManager {
    private final List<Mine> mines;

    public MineManager (Minesweeper context) {
        List<String> minesStr = Settings.getStringList(Keys.MINES_BLOCKS);

        this.mines = new ArrayList<>();

        for (String mineStr : minesStr) {
            Material mineMaterial = Material.getMaterial(mineStr);
            Mine mine = new Mine(context, mineMaterial);
            this.mines.add(mine);
        }
    }

    public List<Mine> getMines () {
        return this.mines;
    }
}
