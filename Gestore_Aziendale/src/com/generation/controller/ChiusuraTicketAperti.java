package com.generation.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.generation.entities.Contact;
import com.generation.entities.Ticket;
import com.generation.library.Console;
import com.generation.repository.Database;

public class ChiusuraTicketAperti {
	
	private static Database db;
	
	public static void main(String[] args) throws SQLException 
	{
		
	
		try 
		{
			db = new Database("config.txt","ticket","contact","company");
		} 
		catch (FileNotFoundException | SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
		
		List<Contact> contacts = new ArrayList<Contact>();
		
		try 
		{
			contacts = db.findAllContact();
		} 
		catch (SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
		List<Ticket> tickets = db.findAllTickets();
		
		try 
		{
			tickets = db.findAllTickets();
		}
		catch (SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
	
		for(Contact contact : contacts) 
				for(Ticket ticket : tickets)
					if(ticket.getContact_id() == contact.getId() && ticket.getClosedOn()==null)
						if(ChronoUnit.DAYS.between(ticket.getSentOn(), LocalDateTime.now())>=31)
						{   
							Ticket updateTicket = new Ticket();
							
							updateTicket.setId(ticket.getId());
							updateTicket.setContact_id(ticket.getContact_id());
							updateTicket.setRequest(ticket.getRequest());
							updateTicket.setResponse("Grave ritardo nella gestione, si prega di creare un nuovo ticket");
							updateTicket.setSentOn(ticket.getSentOn());
							updateTicket.setClosedOn(LocalDateTime.now());
							
							db.updateTicket(updateTicket);
							
						}
		
	}

}
