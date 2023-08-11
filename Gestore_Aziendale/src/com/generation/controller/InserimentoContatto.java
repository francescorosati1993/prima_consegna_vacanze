package com.generation.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.generation.entities.Contact;
import com.generation.entities.Company;
import com.generation.library.Console;
import com.generation.repository.Database;

public class InserimentoContatto {
	
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
			Console.print(company.shortToString());
		

		Console.print("Inserisci ID dell'azienda a cui vuoi aggiungere un nuovo impiegato");
		int id = Console.readInt();
		
		Console.print("Inserisci cognome del'impiegato che vuoi aggiungere");
		String surname = Console.readString();
		
		List<Contact> contacts = db.findAllContact();
		
		Contact contact = new Contact();
		
		contact.setId(contacts.size()+1);
		contact.setSurname(surname);
		contact.setCompany_id(id);
		
		db.insertContact(contact);
		
	
	}

}
