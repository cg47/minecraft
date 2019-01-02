package Metasmash;
public class ePlayerGameModeChange implements org.bukkit.event.Listener {
  private Main plugin;
  public ePlayerGameModeChange( Main port ) {
    this.plugin = port;
  }
  // onPlayerGameModeChange \\
  @org.bukkit.event.EventHandler
  public void onPlayerGameModeChange( org.bukkit.event.player.PlayerGameModeChangeEvent e ) {
	// player = event.player
    org.bukkit.entity.Player self = e.getPlayer();
    // force 'fly' setting to stick on GameModeChange
    java.lang.Boolean fly = this.plugin.hasFly( self );
    this.plugin.executor.schedule( new java.lang.Runnable() {
      @Override
      public void run() {
        self.setAllowFlight( fly );
      }
    }, 1, java.util.concurrent.TimeUnit.MILLISECONDS );
  }
}
