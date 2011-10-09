package com.massivecraft.factions.commands;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.Permission;

public class FCommandReload extends FCommand
{
	
	public FCommandReload()
	{
		super();
		this.aliases.add("reload");
		
		//this.requiredArgs.add("");
		this.optionalArgs.put("file", "all");
		
		this.permission = Permission.COMMAND_RELOAD.node;
		
		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}
	
	@Override
	public void perform()
	{
		long timeInitStart = System.currentTimeMillis();
		String file = this.argAsString(0, "all").toLowerCase();
		
		String fileName;
		
		if (file.startsWith("c"))
		{
			Conf.load();
			fileName = "conf.json";
		}
		else if (file.startsWith("b"))
		{
			Board.load();
			fileName = "board.json";
		}
		else if (file.startsWith("f"))
		{
			Factions.i.loadFromDisc();
			fileName = "factions.json";
		}
		else if (file.startsWith("p"))
		{
			FPlayers.i.loadFromDisc();
			fileName = "players.json";
		}
		else if (file.startsWith("a"))
		{
			fileName = "all";
			Conf.load();
			FPlayers.i.loadFromDisc();
			Factions.i.loadFromDisc();
			Board.load();
		}
		else
		{
			P.p.log("RELOAD CANCELLED - SPECIFIED FILE INVALID");
			sendMessageParsed("<b>Invalid file specified. <i>Valid files: all, conf, board, factions, players");
			return;
		}
		
		long timeReload = (System.currentTimeMillis()-timeInitStart);
		
		sendMessageParsed("reloaded %s from disk, took %dms", fileName, timeReload);
	}
	
}
