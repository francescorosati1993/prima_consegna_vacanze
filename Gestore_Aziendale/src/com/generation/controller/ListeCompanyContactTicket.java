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

public class ListeCompanyContactTicket {
	
	private static Database db;
	
	public static void main(String[] args) throws SQLException 
	{
		
		//Main per capire se tutto funziona correttamente
		//Anche per capire perché non prende il nome della Company 
		//Nei contact che ancora non carico sul database
		//Non prende il Company.getName() perché non li ho ancora "linkati"
		//Mentre i contact già inseriti sul database sono "linkati" nella repository Database
	
		try 
		{
			db = new Database("config.txt","ticket","contact","company");
		} 
		catch (FileNotFoundException | SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
		List<Company> companies = new ArrayList<Company>();
		
		try 
		{
			companies = db.findAllCompany();
		} 
		catch (SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
		 
		for(Company company : companies)
			Console.print(company.toString());
		
		
		
		List<Contact> contacts = new ArrayList<Contact>();
		
		try 
		{
			contacts = db.findAllContact();
		} 
		catch (SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
		 
		for(Contact contact : contacts)
			Console.print(contact.toString());
		
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		try 
		{
			tickets = db.findAllTickets();
		} 
		catch (SQLException e) 
		{
			Console.print("Connessione al database non riuscita, controllare il file config.txt");
		}
		
		
		for(Ticket ticket : tickets)
			Console.print(ticket.toString());
		
	}

}
