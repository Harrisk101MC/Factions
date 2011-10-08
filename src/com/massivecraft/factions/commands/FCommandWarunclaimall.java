package com.massivecraft.factions.commands;

import org.bukkit.command.CommandSender;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;

public class FCommandWarunclaimall extends FCommand {
	
	public FCommandWarunclaimall() {
		aliases.add("warunclaimall");
		aliases.add("wardeclaimall");
		
		helpDescription = "Unclaim all warzone land";
	}
	
	@Override
	public boolean hasPermission(CommandSender sender) {
		return P.hasPermManageWarZone(sender);
	}
	
	@Override
	public void perform() {
		
		if( isLocked() ) {
			sendLockMessage();
			return;
		}
		
		Board.unclaimAll(Faction.getWarZone().getId());
		sendMessage("You unclaimed ALL war zone land.");
	}
	
}
