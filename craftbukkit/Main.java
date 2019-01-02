package Metasmash;

public class Main extends org.bukkit.plugin.java.JavaPlugin {
  public final java.util.logging.Logger logger = java.util.logging.Logger.getLogger( "Minecraft" );
  public static org.bukkit.plugin.Plugin plugin;
  public final org.bukkit.plugin.PluginDescriptionFile pdf = this.getDescription();
  public java.util.Map< java.lang.String, java.lang.String > locale = new java.util.HashMap< java.lang.String, java.lang.String >();
  public void localization() {
    this.locale.put( "onDisable.logger.info",    "[Tim] I *warned* you, but did you listen to me? Oh, no, you *knew*, didn't you? Oh, it's just a harmless little *bunny*, isn't it?" );
    this.locale.put( "onEnable.logger.info",     "[Tim] There are some who call me... Tim?" );
    this.locale.put( "str_error_player_offline", "Player '<player>' cannot be found" );
    this.locale.put( "str_gm_changed_recipient", "Your game mode has been updated to <Mode> Mode" );
    this.locale.put( "str_gm_changed_sender",    "Set <player>'s game mode to <Mode> Mode" ); // only applies if sender != recipient
    this.locale.put( "str_gm_error_integer",     "The number you have entered (<number>) is too [small/big], it must be at [least/most] <cap>" );
    this.locale.put( "str_gm_error_string",      "'<string>' is not a valid number" );
  }
  java.util.concurrent.ScheduledExecutorService executor;
  public void onDisable() {
    this.localization();
    this.logger.info( this.locale.get( "onDisable.logger.info" ) );
    this.executor.shutdown();
    for( org.bukkit.entity.Player player : this.getServer().getOnlinePlayers() ) {
      this.unregisterBB( player );
    }
    /*
    for( CraftBossBar cbb : bb ) {
      for( Player cbbp : cbb.getPlayers() ) {
    	PlayerConnection connection = ( ( CraftPlayer ) cbbp ).getHandle().playerConnection;
    	connection.sendPacket( new net.minecraft.server.v1_12_R1.PacketPlayOutBoss( Action.REMOVE, new BossBattleServer() ) );
      }
      cbb.removeAll();
      bb.remove( cbb );
    }
    */
    /*
    try {
      java.lang.reflect.Field byIdField = Enchantment.class.getDeclaredField( "byId" );
      java.lang.reflect.Field byNameField = Enchantment.class.getDeclaredField( "byName" );
      byIdField.setAccessible( true );
      byNameField.setAccessible( true );
      @SuppressWarnings( "unchecked" )
      java.util.HashMap < Integer, Enchantment > byId = ( java.util.HashMap < Integer, Enchantment > ) byIdField.get( null );
      @SuppressWarnings( "unchecked" )
	  java.util.HashMap < Integer, Enchantment > byName = ( java.util.HashMap < Integer, Enchantment > ) byNameField.get( null );
      if( byId.containsKey( 888 ) ) {
    	byId.remove( 888 );
      }
      if( byName.containsKey( "Thor" ) ) {
    	byName.remove( "Thor" );
      }
    }
    catch( Exception ex ) {
      // do nothing \\
    }
    */
  }
  public void onEnable() {
    this.localization();
    this.logger.info( this.locale.get( "onEnable.logger.info" ) );
    this.executor = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
    this.registerConfig();
    this.registerEvents();
    this.registerEnchantments();
    for( org.bukkit.entity.Player player : this.getServer().getOnlinePlayers() ) {
      this.registerBB( player );
    }
  }
  public void registerConfig() {
    if( !new java.io.File( this.getDataFolder().toString() + "\\config.yml" ).exists() ) {
      this.getConfig().options().copyDefaults( true );
      this.saveConfig();
    }
    this.cc = this.getConfig().getString( "chat" ).toCharArray();
    this.settings.put( "demigod",    this.getConfig().getStringList( "demigod" )  );
    this.settings.put( "fly",        this.getConfig().getStringList( "fly" )      );
    this.settings.put( "god",        this.getConfig().getStringList( "god" )      );
    this.settings.put( "toggle_cid", this.getConfig().getStringList( "toggle_cid" ) );
  }
  public void registerEvents() {
    org.bukkit.plugin.PluginManager plugman = this.getServer().getPluginManager();
    plugman.registerEvents( new eBlockBreak( this ),              this );
    plugman.registerEvents( new eEntityDamage( this ),            this );
    plugman.registerEvents( new eEntityRegainHealth( this ),      this );
    plugman.registerEvents( new ePlayerAnimation( this ),         this );
    plugman.registerEvents( new ePlayerChat( this ),              this );
    plugman.registerEvents( new ePlayerCommandPreprocess( this ), this );
    plugman.registerEvents( new ePlayerGameModeChange( this ),    this );
    plugman.registerEvents( new ePlayerJoin( this ),              this );
    plugman.registerEvents( new ePlayerQuit( this ),              this );
    plugman.registerEvents( new eSignChange( this ),              this );
  }
  public void registerEnchantments() {
	/*
    try {
      java.lang.reflect.Field f = Enchantment.class.getDeclaredField( "acceptingNew" );
      f.setAccessible( true );
      f.set( null, true );
    } catch( Exception ex ) {
      ex.printStackTrace();
    }
    Enchantment.registerEnchantment( new ieThor( 100 ) );
    */
  }
  public java.lang.String capFirstChar( java.lang.String string ) {
	return( string.substring( 0, 1 ).toUpperCase() + string.substring( 1 ) );
  }
  public java.lang.String getModList( org.bukkit.entity.Player player ) {
    return( "fly: " + this.hasFly( player ).toString().toLowerCase() + "; god: " + this.hasGodMode( player ).toString().toLowerCase() + "; demigod: " + this.hasDemiGodMode( player ).toString().toLowerCase() );
  }
  public java.util.Map< org.bukkit.entity.Player, org.bukkit.craftbukkit.v1_12_R1.boss.CraftBossBar > bb = new java.util.HashMap< org.bukkit.entity.Player, org.bukkit.craftbukkit.v1_12_R1.boss.CraftBossBar >();
  @SuppressWarnings( "deprecation" )
  public void registerBB( org.bukkit.entity.Player player ) {
    if( !this.bb.containsKey( player ) ) {
      org.bukkit.craftbukkit.v1_12_R1.boss.CraftBossBar cbb = new org.bukkit.craftbukkit.v1_12_R1.boss.CraftBossBar( this.getModList( player ), org.bukkit.boss.BarColor.BLUE, org.bukkit.boss.BarStyle.SEGMENTED_20 );
      cbb.addPlayer( player );
      cbb.setProgress( ( double ) player.getHealth() / player.getMaxHealth() );
      cbb.setVisible( this.cid.indexOf( player.getDisplayName() ) > -1 );
      this.bb.put( player, cbb );
    }
  }
  public void unregisterBB( org.bukkit.entity.Player player ) {
	if( this.bb.containsKey( player ) ) {
	  this.bb.get( player ).removeAll();
	  this.bb.remove( player );
	}
  }
  public void updateBB_ModList( org.bukkit.entity.Player player ) {
	if( this.bb.containsKey( player ) ) {
	  this.bb.get( player ).setTitle( this.getModList( player ) );
	}
  }
  public void updateBB_HealthBar( org.bukkit.entity.Player player, double health, double damage ) {
	if( this.bb.containsKey( player ) ) {
	  this.bb.get( player ).setProgress( ( ( double ) ( health - damage ) / health ) );
	}
  }
  public java.util.List< java.lang.String > cid = new java.util.ArrayList< java.lang.String >();
  public void toggleBB( org.bukkit.entity.Player player ) {
    boolean value = !( this.cid.indexOf( player.getDisplayName() ) > -1 );
    if( !value ) {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "toggle_cid" );
      a.remove( player.getDisplayName() );
      this.cid = a;
      this.getConfig().set( "toggle_cid", a );
      this.saveConfig();
    }
    else {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "toggle_cid" );
      a.add( player.getDisplayName() );
      this.cid = a;
      this.getConfig().set( "toggle_cid", a );
      this.saveConfig();
    }
    if( this.bb.containsKey( player ) ) {
      this.bb.get( player ).setVisible( value );
    }
    else {
      this.registerBB( player );
    }
  }
  public String getServerTimeHMS() {
	java.util.Calendar calendar = java.util.Calendar.getInstance();
	calendar.setTimeZone( java.util.TimeZone.getTimeZone( "Europe/Paris" ) );
    long ms = calendar.getTimeInMillis();
    long s = ( ms / 1000 ) % 60;
    long m = ( ms / ( 1000 * 60 ) ) % 60;
    long h = ( ms / ( 1000 * 60 * 60 ) ) % 24;
    return( ( h < 10 ? "0" + h : new StringBuilder().append( h ).toString() ) + ":" + ( m < 10 ? "0" + m : new StringBuilder().append( m ).toString() ) + ":" + ( s < 10 ? "0" + s : new StringBuilder().append( s ).toString() ) );
  }
  @SuppressWarnings( { "deprecation", "unused" } )
  public boolean onCommand( org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] ARGV ) {
    int ARGC = ARGV.length;
    java.lang.String msg = new java.lang.String();
    org.bukkit.entity.Player self = ( org.bukkit.entity.Player ) sender;
    int lvl = self.getLevel();
    float exp = self.getExp();
    org.bukkit.inventory.ItemStack item = self.getInventory().getItemInHand();
    org.bukkit.entity.Player targetPlayer = self;
    org.bukkit.Server server = this.getServer();
    switch( commandLabel.toLowerCase() ) {
      case "toggle_cid":
    	this.toggleBB( self );
    	return( true );
      case "enchant":
        java.lang.String eID = "all";
        int eLVL = java.lang.Integer.MAX_VALUE;
        if( ARGC >= 1 ) {
          targetPlayer = server.getPlayer( ARGV[ 0 ] );
        }
        item = targetPlayer.getInventory().getItemInHand();
        if( ARGC >= 2 ) {
          eID = ARGV[ 1 ];
        }
        if( ARGC >= 3 ) {
          eLVL = java.lang.Integer.parseInt( ARGV[ 2 ] );
        }
        if( !this.hasPermission( self, "enchant" ) ) {
          this.sendMessage( self, "\\5[Tim]\\R I don't think so." );
          return( true );
        }
        org.bukkit.enchantments.Enchantment ench = null;
        if( !eID.equalsIgnoreCase( "all" ) ) {
          try {
            ench = org.bukkit.enchantments.Enchantment.getByName( eID );
          }
          catch( java.lang.Exception ex ) {}
          if( ench == null ) {
            try {
              ench = org.bukkit.enchantments.Enchantment.getById( java.lang.Integer.parseInt( eID ) );
            }
            catch( java.lang.Exception ex ) {}
          }
          if( ench == null ) {
            this.sendMessage( self, "\\5[Tim]\\R I *warned* you, but did you listen to me? Oh, no, you *knew*, didn't you? Oh, it's just a harmless little *bunny*, isn't it?" );
            return( true );
          }
          if( eLVL > ench.getMaxLevel() ) {
            eLVL = ench.getMaxLevel();
          }
        }
        if( eLVL < 0 ) {
          eLVL = 0;
        }
        int level = eLVL;
        if( eLVL == 0 ) {
          if( eID.equalsIgnoreCase( "all" ) ) {
            switch( this.removeAllEnchantments( item ) ) {
              case SUCCESS: 
                msg = "\\5[Tim]\\R Dispelled to the best of my abilities.";
                break;
              case INVALID_ITEM: 
                msg = "\\5[Tim]\\R That didn't work...";
                break;
              case FAILED: 
                msg = "\\5[Tim]\\R Death awaits you all with nasty, big, pointy teeth...";
                break;
            }
          }
          else {
            switch( this.removeEnchantment( item, ench ) ) {
              case SUCCESS: 
                msg = "\\5[Tim]\\R Item dispelled. I... am an enchanter.";
                break;
              case INVALID_ITEM: 
                msg = "\\5[Tim]\\R That didn't work...";
                break;
              case FAILED: 
                msg = "\\5[Tim]\\R Death awaits you all with nasty, big, pointy teeth...";
                break;
            }
          }
        }
        else if( eID.equalsIgnoreCase( "all" ) ) {
          switch( this.addAllEnchantments( item ) ) {
            case SUCCESS: 
              msg = "\\5[Tim]\\R Enchanted to the best of my abilities.";
              break;
            case INVALID_ITEM: 
              msg = "\\5[Tim]\\R That didn't work...";
              break;
            case FAILED: 
              msg = "\\5[Tim]\\R Death awaits you all with nasty, big, pointy teeth...";
              break;
          }
        }
        else {
          switch( this.addEnchantment( item, ench, level ) ) {
            case SUCCESS: 
              msg = "\\5[Tim]\\R Item enchanted. I... am an enchanter.";
              break;
            case INVALID_ITEM: 
              msg = "\\5[Tim]\\R That didn't work...";
              break;
            case FAILED: 
              msg = "\\5[Tim]\\R Death awaits you all with nasty, big, pointy teeth...";
              break;
          }
        }
        if( msg != new String() ) {
          this.sendMessage( targetPlayer, msg );
          if( targetPlayer != self ) {
            this.sendMessage( self, msg );
          }
          return( true );
        }
        return( false );
      case "db":
        if( ARGC == 1 ) {
          switch( ARGV[ 0 ].toLowerCase() ) {
            case "enchant":
              this.sendMessage( self, "\\2# typing the 'minecraft:' prefix in addition to 'name' is only for precision purposes" );
              this.sendMessage( self, "\\5  id ... max . minecraft:name" );
              this.sendMessage( self, "\\6Armour:"                                         );
              this.sendMessage( self, "  0 .... 4 ....... minecraft:protection"            );
              this.sendMessage( self, "  1 .... 4 ....... minecraft:fire_protection"       );
              this.sendMessage( self, "  2 .... 4 ....... minecraft:feather_falling"       );
              this.sendMessage( self, "  3 .... 4 ....... minecraft:blast_protection"      );
              this.sendMessage( self, "  4 .... 4 ....... minecraft:projectile_protection" );
              this.sendMessage( self, "  5 .... 3 ....... minecraft:respiration"           );
              this.sendMessage( self, "  6 .... 1 ....... minecraft:aqua_affinity"         );
              this.sendMessage( self, "  7 .... 3 ....... minecraft:thorns"                );
              this.sendMessage( self, "  8 .... 3 ....... minecraft:depth_strider"         );
              this.sendMessage( self, "  9 .... 2 ....... minecraft:frost_walker"          );
              this.sendMessage( self, "\\6Weapons:"                                        );
              this.sendMessage( self, "  16 . 5 ....... minecraft:sharpness"               );
              this.sendMessage( self, "  17 . 5 ....... minecraft:smite"                   );
              this.sendMessage( self, "  18 . 5 ....... minecraft:bane_of_arthropods"      );
              this.sendMessage( self, "  19 . 2 ....... minecraft:knockback"               );
              this.sendMessage( self, "  20 . 2 ....... minecraft:fire_aspect"             );
              this.sendMessage( self, "  21 . 3 ....... minecraft:looting"                 );
              this.sendMessage( self, "\\6Tools:"                                          );
              this.sendMessage( self, "  32 . 5 ....... minecraft:efficiency"              );
              this.sendMessage( self, "  33 . 1 ....... minecraft:silk_touch"              );
              this.sendMessage( self, "  35 . 3 ....... minecraft:fortune"                 );
              this.sendMessage( self, "\\6Bows:"                                           );
              this.sendMessage( self, "  48 . 5 ....... minecraft:power"                   );
              this.sendMessage( self, "  49 . 2 ....... minecraft:punch"                   );
              this.sendMessage( self, "  50 . 1 ....... minecraft:flame"                   );
              this.sendMessage( self, "  51 . 1 ....... minecraft:infinity"                );
              this.sendMessage( self, "\\6Fishing Rods:"                                   );
              this.sendMessage( self, "  61 . 3 ....... minecraft:luck_of_the_sea"         );
              this.sendMessage( self, "  62 . 3 ....... minecraft:lure"                    );
              this.sendMessage( self, "\\6Universal:"                                      );
              this.sendMessage( self, "  34 . 3 ....... minecraft:unbreaking"              );
              this.sendMessage( self, "  70 . 1 ....... minecraft:mending"                 );
              return( true );
            default: break;
          }
        }
        return( false );
      case "cls":
        if( ARGC >= 1 ) {
          if( self.isOp() ) {
        	targetPlayer = server.getPlayer( ARGV[ 0 ] );
          }
          else {
        	this.sendMessage( self, this.locale.get( "str_error_permission" ) );
        	return( true );
          }
        }
        for( int k = 0; k < 404; k++ ) {
          this.sendMessage( targetPlayer, new String() );
        }
        return( true );
      case "command_block_output":
    	  org.bukkit.Bukkit.getServer().dispatchCommand( self, "gamerule commandBlockOutput " + ARGV[ 0 ].toLowerCase() );
    	  return( true );
      case "do_daylight_cycle":
    	  org.bukkit.Bukkit.getServer().dispatchCommand( self, "gamerule doDaylightCycle " + ARGV[ 0 ].toLowerCase() );
    	  return( true );
      case "fly":
        if( ARGC >= 1 ) {
          targetPlayer = server.getPlayer( ARGV[ 0 ] );
        }
        java.lang.Boolean fly = !this.hasFly( targetPlayer );
        this.setFly( targetPlayer, fly );
        this.sendMessage( targetPlayer, "\\6fly == " + fly.toString().toLowerCase() );
        if( targetPlayer != self ) {
          this.sendMessage( self, "\\6fly[ '" + targetPlayer.getDisplayName() + "' ] == " + fly.toString().toLowerCase() );
        }
        return( true );
      case "god":
        if( ARGC >= 1 ) {
          targetPlayer = server.getPlayer( ARGV[ 0 ] );
        }
        java.lang.Boolean god = !this.hasGodMode( targetPlayer );
        this.setGodMode( targetPlayer, god );
        this.sendMessage( targetPlayer, "\\6god == " + god.toString().toLowerCase() );
        if( targetPlayer != self ) {
          this.sendMessage( self, "\\6god[ '" + targetPlayer.getDisplayName() + "' ] == " + god.toString().toLowerCase() );
        }
        return( true );
      case "demigod":
        if( ARGC >= 1 ) {
          targetPlayer = server.getPlayer( ARGV[ 0 ] );
        }
        java.lang.Boolean demigod = !this.hasDemiGodMode( targetPlayer );
        this.setDemiGodMode( targetPlayer, demigod );
        this.sendMessage( targetPlayer, "\\6demigod == " + demigod.toString().toLowerCase() );
        if( targetPlayer != self ) {
          this.sendMessage( self, "\\6demigod[ '" + targetPlayer.getDisplayName() + "' ] == " + demigod.toString().toLowerCase() );
        }
        return( true );
      case "gibby":
        if( ARGC == 0 ) {
          if( lvl >= 30 ) {
            self.setLevel( lvl - 30 );
            // do gibby here
            this.sendMessage( self, "\\5[Tim]\\R Should I really trust you with this?" );
          }
          else if( lvl < 30 ) {
            this.sendMessage( self, "\\5[Tim]\\R You must be level 30 or higher." );
          }
          else if( item.getType() != org.bukkit.Material.BOW ) {
            this.sendMessage( self, "\\5[Tim]\\R You must have a bow equipped." );
          }
          else {
            this.sendMessage( self, "\\5[Tim]\\R That didn't work..." );
          }
        }
        return( true );
      case "gm":
    	if( ARGC == 0 ) {
          this.sendMessage( self, "You are currently in '" + this.capFirstChar( self.getGameMode().toString().toLowerCase() ) + " Mode'" );
    	}
    	else if( self.isOp() ) {
          org.bukkit.GameMode gm = null;
          if( ARGC >= 1 ) {
        	java.lang.String arg_mode = ARGV[ 0 ];
        	for( org.bukkit.GameMode mode : org.bukkit.GameMode.values() ) {
        	  if( arg_mode.equalsIgnoreCase( mode.toString() ) ) {
        	    gm = mode;
        	  }
        	}
        	if( gm == null ) {
        	  java.lang.Integer int_mode;
        	  try {
        		int_mode = java.lang.Integer.valueOf( arg_mode );
        	  }
        	  catch( java.lang.Exception ex ) {
        		this.sendMessage( self, this.locale.get( "str_gm_error_string" ).replace( "<string>", arg_mode ) );
        		return( true );
        	  }
        	  if( int_mode < 0 ) {
        		this.sendMessage( self, this.locale.get( "str_gm_error_integer" ).replace( "<number>", int_mode.toString() ).replace( "[small/big]", "small" ).replace( "[least/most]", "least" ).replace( "<cap>", "0" ) );
        		return( true );
        	  }
        	  if( int_mode > org.bukkit.GameMode.values().length - 1 ) {
        		this.sendMessage( self, this.locale.get( "str_gm_error_integer" ).replace( "<number>", int_mode.toString() ).replace( "[small/big]", "big" ).replace( "[least/most]", "most" ).replace( "<cap>", java.lang.String.valueOf( org.bukkit.GameMode.values().length - 1 ) ) );
        		return( true );
        	  }
        	  gm = org.bukkit.GameMode.getByValue( int_mode );
        	}
          }
          if( ARGC >= 2 ) {
        	java.lang.String arg_player = ARGV[ 1 ];
        	targetPlayer = server.getPlayer( arg_player );
        	if( targetPlayer == null ) {
              this.sendMessage( self, this.locale.get( "str_error_player_offline" ).replace( "<player>", arg_player ) );
              return( true );
        	}
          }
          java.lang.String msg_recipient = this.locale.get( "str_gm_changed_recipient" ).replace( "<Mode>", this.capFirstChar( gm.toString().toLowerCase() ) );
          java.lang.String msg_sender = this.locale.get( "str_gm_changed_sender" ).replace( "<player>", targetPlayer.getDisplayName() ).replace( "<Mode>", this.capFirstChar( gm.toString().toLowerCase() ) );
          if( ARGC == 1 ) {
        	self.setGameMode( gm );
        	this.sendMessage( self, msg_recipient );
          }
          else if( ARGC >= 2 ) {
        	targetPlayer.setGameMode( gm );
        	this.sendMessage( self, msg_sender );
        	this.sendMessage( targetPlayer, msg_recipient );
          }
        }
    	return( true );
      case "mercy":
        if( self.isOp() ) {
          self.setHealth( self.getMaxHealth() );
          self.setFoodLevel( 20 );
        }
        return( true );
      case "tphere":
      	if( ARGC == 1 ) {
          if( self.isOp() ) {
            org.bukkit.Bukkit.getServer().dispatchCommand( org.bukkit.Bukkit.getConsoleSender(), "tp " + ARGV[ 0 ] + " " + self.getDisplayName() );
          }
      	}
      	return( true );
      case "tpto":
    	if( ARGC == 1 ) {
          if( self.isOp() ) {
            org.bukkit.Bukkit.getServer().dispatchCommand( org.bukkit.Bukkit.getConsoleSender(), "tp " + self.getDisplayName() + " " + ARGV[ 0 ] );
          }
    	}
    	return( true );
      case "smite":
        if( ARGC == 1 ) {
          if( self.isOp() ) {
        	targetPlayer = server.getPlayer( ARGV[ 0 ] );
            org.bukkit.Bukkit.getServer().dispatchCommand( org.bukkit.Bukkit.getConsoleSender(), "execute " + targetPlayer.getDisplayName() + " ~ ~ ~ summon lightning_bolt" );
            this.sendMessage( self, "\\6" + targetPlayer.getDisplayName() + "\\R has been \\5thunderstruck!" );
          }
        }  
        return( true );
      default: break;
    }
    return( false );
  }
  public static enum enchantResult {
    INVALID_ITEM, FAILED, SUCCESS;
  }
  @SuppressWarnings( "deprecation" )
  public boolean canEnchant( org.bukkit.inventory.ItemStack item ) {
    if( item.getTypeId() != 0 ) {
      return( true );
    }
    return( false );
  }
  public enchantResult addAllEnchantments( org.bukkit.inventory.ItemStack item ) {
    if( !this.canEnchant( item ) ) {
      return( enchantResult.INVALID_ITEM );
    }
    org.bukkit.enchantments.Enchantment[] arrayOfEnchantment;
    int j = ( arrayOfEnchantment = org.bukkit.enchantments.Enchantment.values() ).length;
    for( int i = 0; i < j; i++ ) {
      org.bukkit.enchantments.Enchantment ench = arrayOfEnchantment[ i ];
      if( item.containsEnchantment( ench ) ) {
    	item.removeEnchantment( ench );
      }
      if( !ench.getName().toLowerCase().contains( "curse" ) ) {
        item.addUnsafeEnchantment( ench, ench.getMaxLevel() );
      }
    }
    return( enchantResult.SUCCESS );
  }
  public enchantResult addEnchantment( org.bukkit.inventory.ItemStack item, org.bukkit.enchantments.Enchantment ench, int level ) {
    if( !this.canEnchant( item ) ) {
      return( enchantResult.INVALID_ITEM );
    }
    if( item.containsEnchantment( ench ) ) {
      item.removeEnchantment( ench );
    }
    item.addUnsafeEnchantment( ench, level );
    return( enchantResult.SUCCESS );
  }
  public enchantResult removeAllEnchantments( org.bukkit.inventory.ItemStack item ) {
    if( !this.canEnchant( item ) ) {
      return( enchantResult.INVALID_ITEM );
    }
    org.bukkit.enchantments.Enchantment[] arrayOfEnchantment;
    int j = ( arrayOfEnchantment = org.bukkit.enchantments.Enchantment.values() ).length;
    for( int i = 0; i < j; i++ ) {
      org.bukkit.enchantments.Enchantment ench = arrayOfEnchantment[ i ];
      if( item.containsEnchantment( ench ) ) {
        item.removeEnchantment( ench );
      }
    }
    return( enchantResult.SUCCESS );
  }
  public enchantResult removeEnchantment( org.bukkit.inventory.ItemStack item, org.bukkit.enchantments.Enchantment ench ) {
    if( !this.canEnchant( item ) ) {
      return( enchantResult.INVALID_ITEM );
    }
    if( item.containsEnchantment( ench ) ) {
      item.removeEnchantment( ench );
      return( enchantResult.SUCCESS );
    }
    return( enchantResult.FAILED );
  }
  public java.util.Map< java.lang.String, java.util.List< java.lang.String > > settings = new java.util.HashMap< java.lang.String, java.util.List< java.lang.String > >();
  public java.util.List< java.lang.String > aFly = new java.util.ArrayList< java.lang.String >();
  public java.lang.Boolean hasFly( org.bukkit.entity.Player self ) {
    if( this.aFly.indexOf( self.getDisplayName() ) > -1 ) {
      return( true );
    }
    return( false );
  }
  public void setFly( org.bukkit.entity.Player self, boolean value ) {
    self.setAllowFlight( value );
    if( !value ) {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "fly" );
      a.remove( self.getDisplayName() );
      this.aFly = a;
      this.getConfig().set( "fly", a );
      this.saveConfig();
    }
    else {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "fly" );
      a.add( self.getDisplayName() );
      this.aFly = a;
      this.getConfig().set( "fly", a );
      this.saveConfig();
    }
    this.updateBB_ModList( self );
  }
  public java.util.List< java.lang.String > aGod = new java.util.ArrayList< java.lang.String >();
  public java.lang.Boolean hasGodMode( org.bukkit.entity.Player self ) {
    if( this.aGod.indexOf( self.getDisplayName() ) > -1 ) {
      return( true );
    }
    return( false );
  }
  public void setGodMode( org.bukkit.entity.Player self, boolean value ) {
    if( !value ) {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "god" );
      a.remove( self.getDisplayName() );
      this.aGod = a;
      this.getConfig().set( "god", a );
      this.saveConfig();
    }
    else {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "god" );
      a.add( self.getDisplayName() );
      this.aGod = a;
      this.getConfig().set( "god", a );
      this.saveConfig();
    }
    this.updateBB_ModList( self );
  }
  public java.util.List< String > aDemiGod = new java.util.ArrayList< java.lang.String >();
  public java.lang.Boolean hasDemiGodMode( org.bukkit.entity.Player self ) {
    if( this.aDemiGod.indexOf( self.getDisplayName() ) > -1 ) {
      return( true );
    }
    return( false );
  }
  public void setDemiGodMode( org.bukkit.entity.Player self, boolean value ) {
    if( !value ) {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "demigod" );
      a.remove( self.getDisplayName() );
      this.aDemiGod = a;
      this.getConfig().set( "demigod", a );
      this.saveConfig();
    }
    else {
      java.util.List< java.lang.String > a = this.getConfig().getStringList( "demigod" );
      a.add( self.getDisplayName() );
      this.aDemiGod = a;
      this.getConfig().set( "demigod", a );
      this.saveConfig();
    }
    this.updateBB_ModList( self );
  }
  public java.lang.Boolean hasPermission( org.bukkit.entity.Player self, java.lang.String perm ) {
    if( self.isOp() ) {
      return( true );
    }
    return( false );
  }
  char[] cc = { '5', '8', '5', '8', '5' };
  public java.lang.String code_char = "\\";
  public java.lang.String decodeText( java.lang.String str ) {
    java.lang.String chat_color_body = code_char + cc[ 0 ];
    java.lang.String chat_color_time_wrapper = code_char + cc[ 1 ];
    java.lang.String chat_color_time = code_char + cc[ 2 ];
    java.lang.String chat_color_name_wrapper = code_char + cc[ 3 ];
    java.lang.String chat_color_name = code_char + cc[ 4 ];
	str = /* chat codes  */ str.replace( "%cc_b%", chat_color_body ).replace( "%cc_tw%", chat_color_time_wrapper ).replace( "%cc_t%", chat_color_time ).replace( "%cc_nw%", chat_color_name_wrapper ).replace( "%cc_n%", chat_color_name );
	str = /* color codes */ str.replace( code_char + "0", org.bukkit.ChatColor.COLOR_CHAR + "0" ).replace( code_char + "1", org.bukkit.ChatColor.COLOR_CHAR + "1" ).replace( code_char + "2", org.bukkit.ChatColor.COLOR_CHAR + "2" ).replace( code_char + "3", org.bukkit.ChatColor.COLOR_CHAR + "3" ).replace( code_char + "4", org.bukkit.ChatColor.COLOR_CHAR + "4" ).replace( code_char + "5", org.bukkit.ChatColor.COLOR_CHAR + "5" ).replace( code_char + "6", org.bukkit.ChatColor.COLOR_CHAR + "6" ).replace( code_char + "7", org.bukkit.ChatColor.COLOR_CHAR + "7" ).replace( code_char + "8", org.bukkit.ChatColor.COLOR_CHAR + "8" ).replace( code_char + "9", org.bukkit.ChatColor.COLOR_CHAR + "9" ).replace( code_char + "A", org.bukkit.ChatColor.COLOR_CHAR + "A" ).replace( code_char + "B", org.bukkit.ChatColor.COLOR_CHAR + "B" ).replace( code_char + "C", org.bukkit.ChatColor.COLOR_CHAR + "C" ).replace( code_char + "D", org.bukkit.ChatColor.COLOR_CHAR + "D" ).replace( code_char + "E", org.bukkit.ChatColor.COLOR_CHAR + "E" ).replace( code_char + "F", org.bukkit.ChatColor.COLOR_CHAR + "F" );
	str = /* magic code  */ str.replace( code_char + "k", org.bukkit.ChatColor.COLOR_CHAR + "k" ).replace( code_char + "K", org.bukkit.ChatColor.COLOR_CHAR + "K" );
	str = /* reset code  */ str.replace( code_char + "r", org.bukkit.ChatColor.COLOR_CHAR + "r" ).replace( code_char + "R", org.bukkit.ChatColor.COLOR_CHAR + "R" );
	str = /* html codes  */ str.replace( code_char + "b", org.bukkit.ChatColor.BOLD.toString() ).replace( code_char + "i", org.bukkit.ChatColor.ITALIC.toString() ).replace( code_char + "u", org.bukkit.ChatColor.UNDERLINE.toString() ).replace( code_char + "s", org.bukkit.ChatColor.STRIKETHROUGH.toString() );
	return( str );
  }
  public void sendMessage( org.bukkit.entity.Player self, java.lang.String msg ) {
    self.sendMessage( this.decodeText( msg ) );
  }
}
