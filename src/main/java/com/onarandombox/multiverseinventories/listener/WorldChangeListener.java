package com.onarandombox.multiverseinventories.listener;

import com.onarandombox.multiverseinventories.MultiverseInventories;
import com.onarandombox.multiverseinventories.api.ShareHandler;
import com.onarandombox.multiverseinventories.util.MVILog;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

/**
 * PlayerListener for MultiverseInventories.
 */
public class WorldChangeListener implements Listener {

    private MultiverseInventories plugin;

    public WorldChangeListener(MultiverseInventories plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player changes worlds.
     *
     * @param event The world change event.
     */
    @EventHandler
    public void playerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World fromWorld = event.getFrom();
        World toWorld = player.getWorld();

        // A precaution..  Will this ever be true?
        if (fromWorld.equals(toWorld)) {
            MVILog.debug("PlayerChangedWorldEvent fired when player travelling in same world.");
            return;
        }
        // Do nothing if dealing with non-managed worlds
        if (this.plugin.getCore().getMVWorldManager().getMVWorld(toWorld) == null
                || this.plugin.getCore().getMVWorldManager().getMVWorld(fromWorld) == null) {
            MVILog.debug("The from or to world is not managed by Multiverse!");
            return;
        }

        new ShareHandler(this.plugin, player, fromWorld, toWorld).handleSharing();
    }
}
