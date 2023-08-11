package com.generation.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.generation.entities.Contact;
import com.generation.library.Console;
import com.generation.repository.Database;

public class CancellazioneBugliano {
	
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
		
		List<Contact> buglianoContacts = new ArrayList<Contact>();
		
		for(Contact contact : contacts)
			if(contact.getCity().equalsIgnoreCase("Bugliano"))
				buglianoContacts.add(contact);
		
		for(Contact contact : buglianoContacts)
			db.deleteContact(contact);
		
	}

}
