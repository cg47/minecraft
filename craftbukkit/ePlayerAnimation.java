package Metasmash;
public class ePlayerAnimation implements org.bukkit.event.Listener {
  @SuppressWarnings( "unused" )
  private Main plugin;
  public ePlayerAnimation( Main port ) {
    this.plugin = port;
  }
  // onPlayerAnimation \\
  @org.bukkit.event.EventHandler
  public void onPlayerAnimation( org.bukkit.event.player.PlayerAnimationEvent e ) {
	// player = event.player
    org.bukkit.entity.Player self = e.getPlayer();
    /* item.enchantment.thunder == true */
    if( e.getAnimationType() == org.bukkit.event.player.PlayerAnimationType.ARM_SWING ) {
      if( self.getInventory().getItemInMainHand().getType() == org.bukkit.Material.DIAMOND_SWORD ) {
        if( !ms ) {
          ms2 = java.lang.System.currentTimeMillis();
          long ms0 = ms2 - ms1;
          if( ms0 >= cd ) {
            use = true;
            ms = !ms;
          }
        }
        else {
          ms1 = java.lang.System.currentTimeMillis();
          long ms0 = ms1 - ms2;
          if( ms0 >= cd ) {
            use = true;
            ms = !ms;
          }
        }
        if( use ) {
          org.bukkit.Location loc = getTarget( self ).getLocation();
          org.bukkit.Bukkit.getServer().dispatchCommand( org.bukkit.Bukkit.getConsoleSender(), "execute @e[x=" + loc.getBlockX() + ",y=" + loc.getBlockY() + ",z=" + loc.getBlockZ() + ",r=" + rng + "] ~ ~ ~ summon lightning_bolt" );
          use = false;
        }
      }
    }
  }
  public long cd = 625;
  public java.lang.Boolean ms = false;
  public long ms1 = 0;
  public long ms2 = 9000;
  public java.lang.Integer rng = 1;
  public java.lang.Boolean use = false;
  public static org.bukkit.entity.Entity getTarget( final org.bukkit.entity.Player player ) {
    assert player != null;
    org.bukkit.entity.Entity target = null;
    double targetDistanceSquared = 0;
    final double radiusSquared = 1;
    final org.bukkit.util.Vector l = player.getEyeLocation().toVector(), n = player.getLocation().getDirection().normalize();
    final double cos45 = Math.cos( Math.PI / 4 );
    for( final org.bukkit.entity.LivingEntity other : player.getWorld().getEntitiesByClass( org.bukkit.entity.LivingEntity.class ) ) {
      if( other == player ) {
        continue;
      }
      if( target == null || targetDistanceSquared > other.getLocation().distanceSquared( player.getLocation() ) ) {
        final org.bukkit.util.Vector t = other.getLocation().add( 0, 1, 0 ).toVector().subtract( l );
        if( n.clone().crossProduct( t ).lengthSquared() < radiusSquared && t.normalize().dot( n ) >= cos45 ) {
          target = other;
          targetDistanceSquared = target.getLocation().distanceSquared( player.getLocation() );
        }
      }
    }
    return( target );
  }
}
