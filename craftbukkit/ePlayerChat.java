package Metasmash;
@SuppressWarnings( "deprecation" )
public class ePlayerChat implements org.bukkit.event.Listener {
  private Main plugin;
  public ePlayerChat( Main port ) {
    this.plugin = port;
  }
  // onPlayerChat \\
  @org.bukkit.event.EventHandler
  public void onPlayerChat( org.bukkit.event.player.PlayerChatEvent e ) {
	  org.bukkit.entity.Player self = e.getPlayer();
    String msg = e.getMessage();
    String suffix;
    if( msg.startsWith( ">>>" ) ) {
      String prefix = msg.substring( 0, 3 );
      suffix = msg.substring( 3 );
      msg = org.bukkit.ChatColor.BLUE.toString() + prefix + suffix;
      net.minecraft.server.v1_12_R1.PacketPlayOutChat packet = new net.minecraft.server.v1_12_R1.PacketPlayOutChat( net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer.a( "[\"\",{\"text\":\"" + msg + "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://boards.4chan.org" + suffix + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"boards.4chan.org" + suffix + "\"}]}}}]" ) );
      for( org.bukkit.entity.Player recipient : e.getRecipients() ) {
    	  net.minecraft.server.v1_12_R1.PlayerConnection connection = ( ( org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer ) recipient ).getHandle().playerConnection;
        connection.sendPacket( packet );
      }
      e.setCancelled( true );
      return;
    }
    if( msg.startsWith( ">" ) ) {
      msg = org.bukkit.ChatColor.GREEN.toString() + msg;
    }
    for( org.bukkit.entity.Player recipient : e.getRecipients() ) {
    	this.plugin.sendMessage( recipient, "%cc_tw%[%cc_t%" + this.plugin.getServerTimeHMS().replace( ":", "%cc_tw%:%cc_t%" ) + "%cc_tw%] %cc_nw%<%cc_n%" + self.getDisplayName() + "%cc_nw%> %cc_b%" + msg );
    }
    e.setCancelled( true );
  }
}
