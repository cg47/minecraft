package Metasmash;

//import net.minecraft.server.v1_12_R1.EntityPlayer;
//import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

@SuppressWarnings( "deprecation" )
public class ePlayerChat implements Listener {
  private Main plugin;
  
  public ePlayerChat( Main port ) {
    this.plugin = port;
  }
  
  @EventHandler
  public void onPlayerChat( PlayerChatEvent e ) {
    Player self = e.getPlayer();
    String msg = e.getMessage();
    String suffix;
    if( msg.startsWith( ">>>" ) ) {
      String prefix = msg.substring( 0, 3 );
      suffix = msg.substring( 3 );
      msg = ChatColor.BLUE.toString() + prefix + suffix;
      PacketPlayOutChat packet = new PacketPlayOutChat( ChatSerializer.a( "[\"\",{\"text\":\"" + msg + "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://boards.4chan.org" + suffix + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"boards.4chan.org" + suffix + "\"}]}}}]" ) );
      for( Player recipient : e.getRecipients() ) {
        PlayerConnection connection = ( ( CraftPlayer ) recipient ).getHandle().playerConnection;
        connection.sendPacket( packet );
      }
      e.setCancelled( true );
      return;
    }
    if( msg.startsWith( ">" ) ) {
      msg = ChatColor.GREEN.toString() + msg;
    }
    for( Player recipient : e.getRecipients() ) {
    	this.plugin.sendMessage( recipient, "%cc_tw%[%cc_t%" + this.plugin.getServerTimeHMS().replace( ":", "%cc_tw%:%cc_t%" ) + "%cc_tw%] %cc_nw%<%cc_n%" + self.getDisplayName() + "%cc_nw%> %cc_b%" + msg );
    }
    e.setCancelled( true );
  }
}
