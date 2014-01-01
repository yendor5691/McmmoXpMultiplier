package com.massivecraft.mcmmoxpmultiplier;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.gmail.nossr50.util.Misc;
import com.gmail.nossr50.util.Permissions;
import com.massivecraft.mcore.MPlugin;
import com.massivecraft.mcore.util.PermUtil;
//import com.massivecraft.mcore.util.Txt;

public class McmmoXpMultiplier extends MPlugin
{

	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static McmmoXpMultiplier i;

	public static McmmoXpMultiplier get()
	{
		return i;
	}

	public McmmoXpMultiplier()
	{
		McmmoXpMultiplier.i = this;
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public void onEnable()
	{
		if (!preEnable()) return;

		postEnable();
	}

	// -------------------------------------------- //
	// LISTENER
	// -------------------------------------------- //

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMcMMOPlayerXPGain(McMMOPlayerXpGainEvent event)
	{
		Player player = event.getPlayer();
		SkillType skill = event.getSkill();

		// Must be a player with the corresponding mcMMO Skill enabled
		if (Misc.isNPCEntity(player)
				|| !Permissions.skillEnabled(player, skill)) return;
//		String bc = Txt.parse("<i>Old rawXp is <h>%.2f%% <i>", event.getRawXpGained());
//		player.sendMessage(bc);
		event.setRawXpGained(event.getRawXpGained() * mcmmoXpMultiplierCalc(player));
//		String bc2 = Txt.parse("<i>New rawXp is <h>%.2f%% <i>", event.getRawXpGained());
//		player.sendMessage(bc2);
		return;

	}

	public static float mcmmoXpMultiplierCalc(Player player)
	{
		Float multiplier = 1.0f;
		for(int i = 400; i >= 0; i--)
		{
			String xp = "mcmmoxpmultiplier." + Integer.toString(i);
			if (PermUtil.has(player, xp))
			{
//				player.sendMessage(xp);
				multiplier = multiplier * (i / 100.0f);
				break;  // Remove break to add all permission multipliers instead of using the largest.
			}
		}

		return multiplier;
	}
}
