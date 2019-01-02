package Metasmash;

public class ePlayerQuit implements org.bukkit.event.Listener {
  private Main plugin;
  
  public ePlayerQuit( Main port ) {
    this.plugin = port;
  }
  
  @org.bukkit.event.EventHandler
  public void onPlayerQuit( org.bukkit.event.player.PlayerQuitEvent e ) {
    org.bukkit.entity.Player self = e.getPlayer();
    this.plugin.unregisterBB( self );
  }
}
