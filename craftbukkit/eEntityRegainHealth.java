package Metasmash;
public class eEntityRegainHealth implements org.bukkit.event.Listener {
  private Main plugin;
  public eEntityRegainHealth( Main port ) {
    this.plugin = port;
  }
  // onEntityRegainHealth \\
  @SuppressWarnings( "deprecation" )
  @org.bukkit.event.EventHandler
  public void onEntityRegainHealth( org.bukkit.event.entity.EntityRegainHealthEvent e ) {
	// entity = event.entity
    org.bukkit.entity.Entity ent = e.getEntity();
    // if : entity.type == player : then
    if( ent instanceof org.bukkit.entity.Player ) {
      // entity -> player
      org.bukkit.entity.Player self = ( org.bukkit.entity.Player ) ent;
      /* toggle_cid update */
      this.plugin.updateBB_HealthBar( self, self.getMaxHealth(), ( self.getMaxHealth() - self.getHealth() ) - e.getAmount() );
    }
  }
}
