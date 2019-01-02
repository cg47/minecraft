package Metasmash;

public class ePlayerCommandPreprocess implements org.bukkit.event.Listener {
  @SuppressWarnings( "unused" )
  private Main plugin;
  
  public ePlayerCommandPreprocess( Main port ) {
    this.plugin = port;
  }
  
  @org.bukkit.event.EventHandler
  public void onPlayerCommandPreprocess( org.bukkit.event.player.PlayerCommandPreprocessEvent e ) {
	String command = e.getMessage().substring( 1 ); // strip command of shebang \\
	String cmdLabel = command.split( " " )[ 0 ];
	switch( cmdLabel.toLowerCase() ) {
	  
	}
  }
}
