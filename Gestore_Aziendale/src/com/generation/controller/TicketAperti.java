package com.generation.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.generation.entities.Company;
import com.generation.entities.Contact;
import com.generation.entities.Ticket;
import com.generation.library.Console;
import com.generation.repository.Database;

public class TicketAperti {
	
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
		
		Console.print("Inserisci settore di cui vuoi controllare i ticket aperti");
		String sector = Console.readString();	

		Company company = new Company();
		
		try 
		{
			if(db.findCompanyBySector(sector).size() == 1)
				company = db.findCompanyBySector(sector).get(0);
		} 
		catch (SQLException e) 
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
			if(contact.getCompany_id() == company.getId())
				for(Ticket ticket : tickets)
					if(ticket.getContact_id() == contact.getId() && ticket.getClosedOn()==null)
						Console.print(ticket.toString());
		
		
	}

}
