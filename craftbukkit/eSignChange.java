package Metasmash;

public class eSignChange implements org.bukkit.event.Listener {
  private Main plugin;
  
  public eSignChange( Main port ) {
    this.plugin = port;
  }
  
  @org.bukkit.event.EventHandler
  public void onSignChange( org.bukkit.event.block.SignChangeEvent e ) {
    for( int i = 0; i < e.getLines().length; i++ ) {
      String ln = e.getLine( i );
      if( ln.contains( this.plugin.code_char ) ) {
    	e.setLine( i, this.plugin.decodeText( ln ) );
      }
    }
  }
}
