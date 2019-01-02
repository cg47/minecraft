package Metasmash;
public class ePlayerQuit implements org.bukkit.event.Listener {
  private Main plugin;
  public ePlayerQuit( Main port ) {
    this.plugin = port;
  }
  // onPlayerQuit \\
  @org.bukkit.event.EventHandler
  public void onPlayerQuit( org.bukkit.event.player.PlayerQuitEvent e ) {
	// player = event.player
    org.bukkit.entity.Player self = e.getPlayer();
    /* toggle_cid unregister */
    this.plugin.unregisterBB( self );
  }
}
