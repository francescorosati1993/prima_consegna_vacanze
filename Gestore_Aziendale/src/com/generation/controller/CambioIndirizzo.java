package com.generation.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.generation.entities.Contact;
import com.generation.library.Console;
import com.generation.repository.Database;

public class CambioIndirizzo {
	
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
		
		 
		for(Contact contact : contacts)
			Console.print(contact.shortToString());
		

		Console.print("Inserisci ID del contact di cui vuoi cambiare la residenza");
		int id = Console.readInt();
		
		Console.print("Inserisci il nuovo indirizzo");
		String newAddress = Console.readString();
		
		Console.print("Inserisci la nuova città");
		String newCity = Console.readString();
		
		Contact contactOld = db.findContactByID(id);
		
		Contact contactUpdate = new Contact();
		
		contactUpdate.setId(contactOld.getId());
		contactUpdate.setCompany_id(contactOld.getCompany_id());
		contactUpdate.setName(contactOld.getName());
		contactUpdate.setSurname(contactOld.getSurname());
		contactUpdate.setAddress(newAddress);
		contactUpdate.setCity(newCity);
		
		db.updateContact(contactUpdate);
		
		
	}

}
