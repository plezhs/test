package test.test;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public final class Test extends JavaPlugin implements Listener {
    ConsoleCommandSender consol = Bukkit.getConsoleSender();
    @Override
    public void onEnable() {
        // 시작시, 작동할 코드
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("plugin ON!");


    }

    //    @EventHandler
//    public void onBreak(BlockBreakEvent e){
//        e.setCancelled(true);
//    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player p =(Player) e.getDamager();
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10,0));
        }
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        Block b = e.getPlayer().getTargetBlock((Set)null,50);
        if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
            if(p.getItemInHand().getType().equals(Material.FEATHER)){
                b.getLocation().add(0.0,1.0,0.0).createExplosion(5.0f);
            }
            if(p.getItemInHand().getType().equals(Material.NETHERITE_SWORD)){
                BukkitRunnable bk = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for(World w : Bukkit.getWorlds()){
                            for(Player p : w.getPlayers()){
                                p.setGliding(true);
                            }
                        }
                    }
                };bk.runTaskTimer(this,0,0);
            }
        }
    }
    @EventHandler
    public void baseball(PrePlayerAttackEntityEvent e){
        Player p = e.getPlayer();
        Entity en = (Entity) e.getAttacked();
        en.setVelocity(p.getLocation().getDirection().multiply(7));
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(ChatColor.YELLOW + "Welcome");

    }
    @Override
    public void onDisable() {
        // 종료시, 작동할 코드
        getLogger().info("plugin off..");
    }
}
