package me.Nikewade.VallendiaMinigame.Upgrades;

import org.bukkit.entity.Player;

import me.Nikewade.VallendiaMinigame.VallendiaMinigame;
import me.Nikewade.VallendiaMinigame.Interface.Upgrade;

public class ArmorUpgrade implements Upgrade{
	VallendiaMinigame Main;
	
	 public ArmorUpgrade(VallendiaMinigame Main)
	  {
	    this.Main = Main;
	  }

	public void upgrade(Player p)
	{
		Double health = p.getMaxHealth();
		p.setMaxHealth(health + 5);
	}
	
	public int getPrice()
	{
		return 550;
	}
	
}
