package com.generation.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import com.generation.entities.Contact;
import com.generation.library.Console;
import com.generation.repository.Database;

public class InserimentoContattiDaCSV {
	
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
		
		
		Console.print("Inserisci il nome del file .csv da leggere: ");
		String fileName = Console.readString();
		
		File f = new File(fileName);
		Scanner reader = null;
		
		try 
		{
			reader = new Scanner(f);
		} 
		catch (FileNotFoundException e) 
		{
			Console.print("File non trovato, inserire nome file corretto e riprovare");
		}
		
		while(reader.hasNextLine()) 
		{
			Contact contact = new Contact();
			
			String contactCSV = reader.nextLine();
			String[] column = contactCSV.split(",");
		
			contact.setId(Integer.parseInt(column[0]));
			contact.setCompany_id(Integer.parseInt(column[1]));
			contact.setName(column[2]);
			contact.setSurname(column[3]);
			contact.setAddress(column[4]);
			contact.setCity(column[5]);
		
			db.insertContact(contact);
		}
		
		
	}

}
