# Spigot/Paper Event API Study (Version 1.21.1+)

This document summarizes the core event handlers for Join, Break, and Place actions used in PaperMC development.

## 1. PlayerJoinEvent
**Package:** `org.bukkit.event.player.PlayerJoinEvent`

Used to handle logic when a player connects to the server.

### Implementation Example
```java
@EventHandler
public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    
    // Set a custom join message using Adventure Components
    event.joinMessage(Component.text("Welcome to the realm, ", true)
        .append(Component.text(player.getName(), underlined())));
}
```

---

## 2. BlockBreakEvent
**Package:** `org.bukkit.event.block.BlockBreakEvent`

Triggered when a block is destroyed by a player. Useful for protection systems or custom loot tables.

### Key Properties
- `setCancelled(true)`: Stops the block from breaking.
- `setDropItems(false)`: Prevents items from dropping.
- `setExpToDrop(double)`: Adjusts experience rewards.

### Implementation Example
```java
@EventHandler
public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    // Permission check example
    if (!player.hasPermission("mine.diamond_ore")) {
        event.setCancelled(true);
        player.sendMessage(Component.text("You don't have permission to mine diamonds!", red()));
        return;
    }

    // Custom drop logic
    event.setDropItems(false);
    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
}
```

---

## 3. BlockPlaceEvent
**Package:** `org.bukkit.event.block.BlockPlaceEvent`

Fired when a player successfully places a block.

### Implementation Example
```java
@EventHandler
public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    // Height restriction example
    if (block.getY() > 100) {
        event.setCancelled(true);
        player.sendMessage(Component.text("The sky is too high to build!", red()));
    }
}
```

## Best Practices
1. **Asynchronous Awareness**: Most block events are synchronous; avoid blocking operations (like DB calls) inside the handler.
2. **Cancellation**: If you cancel an event, subsequent listeners with `ignoreCancelled = true` will skip it.
3. **Adventure API**: Always prefer `Component` over legacy `String` messages where possible.
