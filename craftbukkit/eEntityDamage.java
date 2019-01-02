package Metasmash;
public class eEntityDamage implements org.bukkit.event.Listener {
  private Main plugin;
  public eEntityDamage( Main port ) {
    this.plugin = port;
  }
  // onEntityDamage \\
  @SuppressWarnings( "deprecation" )
  @org.bukkit.event.EventHandler
  public void onEntityDamage( org.bukkit.event.entity.EntityDamageEvent e ) {
	// entity = event.entity
    org.bukkit.entity.Entity ent = e.getEntity();
    // if : entity.type == player : then
    if( ent instanceof org.bukkit.entity.Player ) {
      // entity -> player
      org.bukkit.entity.Player self = ( org.bukkit.entity.Player ) ent;
      /* god == true */
      if( this.plugin.hasGodMode( self ) ) {
        e.setCancelled( true );
      }
      /* demigod == true */
      else if( this.plugin.hasDemiGodMode( self ) ) {
        switch( e.getCause() ) {
          default:
            e.setCancelled( true );
            break;
          case STARVATION: break;
        }
      }
      /* else */
      else {
    	/* toggle_cid update */
    	this.plugin.updateBB_HealthBar( self, self.getMaxHealth(), e.getDamage() );
      }
    }
  }
}
