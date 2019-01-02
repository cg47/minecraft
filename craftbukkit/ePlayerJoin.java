package Metasmash;
public class ePlayerJoin implements org.bukkit.event.Listener {
  private Main plugin;
  public ePlayerJoin( Main port ) {
    this.plugin = port;
  }
  // onPlayerJoin \\
  @org.bukkit.event.EventHandler
  public void onPlayerJoin( org.bukkit.event.player.PlayerJoinEvent e ) {
	// player = event.player
    org.bukkit.entity.Player self = e.getPlayer();
    /* motd */
    this.plugin.sendMessage( self, "\\3" + this.plugin.getModList( self ) );
    this.plugin.sendMessage( self, "%cc_nw%<%cc_n%" + self.getDisplayName() + "%cc_nw%>%cc_b% By what name are you known?" );
    this.plugin.sendMessage( self, "%cc_nw%[%cc_n%Tim%cc_nw%]%cc_b% There are some who call me... Tim?" );
    this.plugin.sendMessage( self, "%cc_nw%<%cc_n%" + self.getDisplayName() + "%cc_nw%>%cc_b% Greetings, Tim the Enchanter." );
    this.plugin.sendMessage( self, "%cc_nw%[%cc_n%Tim%cc_nw%]%cc_b% Greetings, " + self.getDisplayName() + "." );
    this.plugin.sendMessage( self, "%cc_nw%<%cc_n%" + self.getDisplayName() + "%cc_nw%>%cc_b% You know my name?" );
    this.plugin.sendMessage( self, "%cc_nw%[%cc_n%Tim%cc_nw%]%cc_b% I do." );
    this.plugin.sendMessage( self, "%cc_nw%<%cc_n%" + self.getDisplayName() + "%cc_nw%>%cc_b% You know many things that are hidden, O', Tim?" );
    this.plugin.sendMessage( self, "%cc_nw%[%cc_n%Tim%cc_nw%]%cc_b% Quite." );
    /* toggle_cid register */
    this.plugin.registerBB( self );
    // force 'fly' setting to stick on (re)join
    self.setAllowFlight( this.plugin.hasFly( self ) );
  }
}
