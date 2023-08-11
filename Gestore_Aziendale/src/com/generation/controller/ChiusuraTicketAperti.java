package com.generation.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.generation.entities.Company;
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
		
//		Console.print("Inserisci settore di cui vuoi controllare i ticket aperti");
//		String sector = Console.readString();	
//
//		Company company = new Company();
//		
//		try 
//		{
//			if(db.findCompanyBySector(sector).size() == 1)
//				company = db.findCompanyBySector(sector).get(0);
//		} 
//		catch (SQLException e) 
//		{
//			Console.print("Connessione al database non riuscita, controllare il file config.txt");
//		}
		
		
		
		
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
		
		
	//	List<Ticket> updateTickets = new ArrayList<Ticket>();
		
		
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
		

//							
//		
//		Ticket updateTicket = new Ticket();
//		
//		for(int i = 0; i<contacts.size(); i++)
//			for(int j = 0; j<tickets.size(); j++)
//				if(tickets.get(j).getContact_id() == contacts.get(i).getId() && tickets.get(j).getClosedOn()==null)
//					if(ChronoUnit.DAYS.between(tickets.get(j).getSentOn(), LocalDateTime.now())>=31)
//						{
//							Console.print(tickets.get(j));
//							updateTicket.setId(tickets.get(j).getId());
//							updateTicket.setContact_id(tickets.get(j).getContact_id());
//							updateTicket.setRequest(tickets.get(j).getRequest());
//							updateTicket.setResponse("Grave ritardo nella gestione, si prega di creare un nuovo ticket");
//							updateTicket.setSentOn(tickets.get(j).getSentOn());
//							updateTicket.setClosedOn(LocalDateTime.now());
//						
//							db.updateTicket(updateTicket);
//						}
//		
	}

}
