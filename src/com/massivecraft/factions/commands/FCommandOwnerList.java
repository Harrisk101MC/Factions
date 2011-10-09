package com.massivecraft.factions.commands;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.struct.Permission;


public class FCommandOwnerList extends FCommand
{
	
	public FCommandOwnerList()
	{
		super();
		this.aliases.add("ownerlist");
		
		//this.requiredArgs.add("");
		//this.optionalArgs.put("", "");
		
		this.permission = Permission.COMMAND_OWNERLIST.node;
		
		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}
	
	@Override
	public void perform()
	{
		boolean hasBypass = fme.isAdminBypassing(); 

		if ( ! hasBypass && ! assertHasFaction())
		{
			return;
		}

		if ( ! Conf.ownedAreasEnabled)
		{
			fme.sendMessageParsed("<b>Owned areas are disabled on this server.");
			return;
		}

		FLocation flocation = new FLocation(fme);

		if (Board.getIdAt(flocation) != myFaction.getId())
		{
			if (!hasBypass)
			{
				fme.sendMessageParsed("<b>This land is not claimed by your faction.");
				return;
			}

			myFaction = Board.getFactionAt(flocation);
			if (!myFaction.isNormal())
			{
				fme.sendMessageParsed("<i>This land is not claimed by any faction, thus no owners.");
				return;
			}
		}

		String owners = myFaction.getOwnerListString(flocation);

		if (owners == null || owners.isEmpty())
		{
			fme.sendMessageParsed("<i>No owners are set here; everyone in the faction has access.");
			return;
		}

		fme.sendMessageParsed("<i>Current owner(s) of this land: %s", owners);
	}
}
