package Metasmash;
public class eBlockBreak implements org.bukkit.event.Listener {
  private Main plugin;
  public eBlockBreak( Main port ) {
    this.plugin = port;
  }
  // onBlockBreak \\
  @org.bukkit.event.EventHandler
  public void onBlockBreak( org.bukkit.event.block.BlockBreakEvent e ) {
	// block = event.block
    org.bukkit.block.Block block = e.getBlock();
    // player = event.player
    org.bukkit.entity.Player self = e.getPlayer();
    /* only operators can destroy command blocks */
    if( this.isCommandBlock( block ) && !self.isOp() ) {
      e.setCancelled( true );
      this.plugin.sendMessage( self, "\\C[Error]\\R I don't think so." );
    }
  }
  private java.lang.Boolean isCommandBlock( org.bukkit.block.Block block ) {
    switch( block.getType() ) {
      case COMMAND: // command_block
    	return( true );
      case COMMAND_CHAIN: // command_block_chain
        return( true );
      case COMMAND_MINECART: // minecart:command_block
        return( true );
      case COMMAND_REPEATING: // command_block_repeating
        return( true );
      default: break;
    }
    return( false );
  }
}
