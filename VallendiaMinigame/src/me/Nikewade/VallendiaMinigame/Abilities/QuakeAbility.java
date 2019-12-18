package me.Nikewade.VallendiaMinigame.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Ability;
import me.Nikewade.VallendiaMinigame.Utils.AbilityUtils;

public class QuakeAbility implements Ability, Listener{
	//made by Emma
	
	List<Entity> explosions = new ArrayList<>();
	int radius = 8;
	int damage = 10;
	int explosionSize = 4;
	int stunTime = 3;
	int fallDistance = 10;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Quake";
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.PASSIVE;
	}

	@Override
	public List<String> getDescription() {
		// TODO Auto-generated method stub
		return Arrays.asList("if u fall 10 or more blocks whilst sneaking u do damage in a radius");
	}

	@Override
	public ItemStack getGuiItem() {
		// TODO Auto-generated method stub
		return new ItemStack(Material.TNT);
	}

	@Override
	public boolean RunAbility(Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void DisableAbility(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	
	@EventHandler
	//checks if player is falling 10 or more blocks and is sneaking then spawns explosion stuns and does damage
	public void onFall (EntityDamageEvent e)
	{

		
		if(e.getEntity()instanceof Player)
		{
		if(e.getCause()==DamageCause.FALL)
		{
			Player p = (Player) e.getEntity();

			if(VallendiaMinigame.getInstance().abilitymanager.playerHasAbility(p,"Quake"))
			{
			if(p.getFallDistance() >= fallDistance)
			{

				if(p.isSneaking())
				{
					explosions.add(p);
					AbilityUtils.explode(p.getLocation(), p, explosionSize, damage, false, true, true);
					for(Entity en : AbilityUtils.getAoeTargets(p, p.getLocation(), radius, radius, radius))
					{
						AbilityUtils.stun(p, (LivingEntity) en, "Quake", stunTime);
					}
					

					
				}
			}
			
			}
		}
		
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	//spawning falling blocks
	public void onEntityExplode(EntityExplodeEvent e)

	
	{

		e.setYield(0);
		
		if(explosions.contains(e.getEntity()))
		{
			explosions.remove(e.getEntity());

		for (Block b : e.blockList())

		{

			if(b.getType().isSolid()) {

		float x = -2.0F + (float)(Math.random() * 6.0D + 1.0D);

		float y = -3.0F + (float)(Math.random() * 4.0D + 1.0D);

		float z = -2.0F + (float)(Math.random() * 6.0D + 1.0D);
		


		FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
		fb.setDropItem(false);

		((Entity) fb).setVelocity(new Vector(x,y,z));

		}
			
		}
		}


	}
	
    @EventHandler
    //deleting blocks after they land
    public void onBlockFall(EntityChangeBlockEvent e) {
        if ((e.getEntityType() == EntityType.FALLING_BLOCK)) {
        	
        	if(!(e.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)||
        		e.getBlock().getRelative(BlockFace.DOWN).getType().isSolid())
        	{
        	
        	BukkitTask timer = new BukkitRunnable()
        	{

				@Override
				public void run() {
					// TODO Auto-generated method stub

		        	e.getBlock().setType(Material.AIR);
				}
        		
        	}.runTaskLater(VallendiaMinigame.getInstance(), 5*20);

  
        	}


        }
 
    }


	
	
	
	

}