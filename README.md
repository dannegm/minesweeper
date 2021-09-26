# Minesweeper

**Plugin Description**

Add exploding mines to your server

**Features**

- Craft mines with every block you want (configurable)
- Defuse a mine using a banner if you found one
- Configure everything about the plugin
- Permission to avoid explosions

**Permissions**

- `minesweeper.bypass` - avoid to exploit a mine
- `minesweeper.defuse` - allow to defuse a mine
- `minesweeper.craft` - allow to craft a mine
- `minesweeper.place` - allow to place a mine
- `minesweeper.get` - allow to get a mine with a command
- `minesweeper.cleanall` - allow to clean all the mines with a command

**Settings**

```yaml
debug: false

# List of tools that you can use to defuse a mine
defuse:
  tools:
    - BLACK_BANNER
    - RED_BANNER
    - GREEN_BANNER
    - BROWN_BANNER
    - BLUE_BANNER
    - PURPLE_BANNER
    - CYAN_BANNER
    - LIGHT_GRAY_BANNER
    - GRAY_BANNER
    - PINK_BANNER
    - LIME_BANNER
    - YELLOW_BANNER
    - LIGHT_BLUE_BANNER
    - MAGENTA_BANNER
    - ORANGE_BANNER
    - WHITE_BANNER

mines:
  # The name that will appear into your inventory
  displayName: "Mina de <material>"

  # Range of damage of the mine
  explosionRatio: 3.0

  # List of blocks that you can use/carft as a mine
  blocks:
    - DIRT_PATH
    - DEEPSLATE
    - DIRT
    - SAND
    - COBBLESTONE
    - GRAVEL
    - STONE
    - GRASS_BLOCK

# Sounds when you place, defuse or will exploit a mine
sounds:
  onDefuse: ENTITY_CREEPER_DEATH
  onPlace: ENTITY_CREEPER_HURT
  onWillExplode: ENTITY_CREEPER_PRIMED

# This is for translations
messages:
  avoidedExplosion: "Has avoided explosion"
  cleanAllNotAllowed: "You don't have permissions to use this command"
  craftNotAllowed: "You don't have permissions to craft a mine"
  getNotAllowed: "You don't have permissions to use this command"
  invalidPlayer: "Please, provide a valid player name"
  placeNotAllowed: "You don't have permissions to place a mine"
  defuseNotAllowed: "You don't have permissions to defuse this mine. Mine is still can explode."
  playerNotFound: "Given player is not found"
  defusedTitle: "DEFUSED"
```

**Future plans**

- Add a tool to seek a mine
- Activate with every mob
- Save mines into a YML file

**Found a bug?**

Please report the issue into our github repo