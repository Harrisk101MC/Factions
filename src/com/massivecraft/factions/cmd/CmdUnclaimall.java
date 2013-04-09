package com.massivecraft.factions.cmd;

import org.bukkit.Bukkit;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.ConfServer;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Perm;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.integration.SpoutFeatures;

public class CmdUnclaimall extends FCommand
{	
	public CmdUnclaimall()
	{
		this.aliases.add("unclaimall");
		this.aliases.add("declaimall");
		
		//this.requiredArgs.add("");
		//this.optionalArgs.put("", "");
		
		this.permission = Perm.UNCLAIM_ALL.node;
		this.disableOnLock = true;
		
		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeOfficer = true;
		senderMustBeLeader = false;
	}
	
	@Override
	public void perform()
	{
		if (Econ.shouldBeUsed())
		{
			double refund = Econ.calculateTotalLandRefund(myFaction.getLandRounded());
			if(ConfServer.bankEnabled && ConfServer.bankFactionPaysLandCosts)
			{
				if ( ! Econ.modifyMoney(myFaction, refund, "to unclaim all faction land", "for unclaiming all faction land")) return;
			}
			else
			{
				if ( ! Econ.modifyMoney(fme      , refund, "to unclaim all faction land", "for unclaiming all faction land")) return;
			}
		}

		LandUnclaimAllEvent unclaimAllEvent = new LandUnclaimAllEvent(myFaction, fme);
	Bukkit.getServer().getPluginManager().callEvent(unclaimAllEvent);
		// this event cannot be cancelled

		Board.unclaimAll(myFaction.getId());
		myFaction.msg("%s<i> unclaimed ALL of your faction's land.", fme.describeTo(myFaction, true));
		SpoutFeatures.updateTerritoryDisplayLoc(null);

		if (ConfServer.logLandUnclaims)
			Factions.get().log(fme.getName()+" unclaimed everything for the faction: "+myFaction.getTag());
	}
	
}
