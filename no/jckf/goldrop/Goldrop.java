package no.jckf.goldrop;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Goldrop extends JavaPlugin implements Listener {
	Random rand;

	public void onEnable() {
		rand = new Random();

		getServer().getPluginManager().registerEvents(this,this);
	}

	public void onDisable() {

	}

	@EventHandler
	public void onKill(EntityDeathEvent event) {
		FileConfiguration conf = getConfig();
		Entity entity = event.getEntity();
		String mob = entity.getType().getName();

		Object drop = conf.get(mob + ".type");
		if (drop != null) {
			int n = conf.getInt(mob + ".min",0) + rand.nextInt(conf.getInt(mob + ".max",1) + 1);
			if (n > 0) {
				entity.getLocation().getWorld().dropItemNaturally(
					entity.getLocation(),
					new ItemStack(Material.getMaterial(Integer.parseInt(drop.toString())),n)
				);
			}
		}
	}
}
