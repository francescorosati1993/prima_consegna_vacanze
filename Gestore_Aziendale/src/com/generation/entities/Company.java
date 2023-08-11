package com.generation.entities;

import java.util.ArrayList;
import java.util.List;

public class Company 
{
	private int id;
	private String name;
	private String sector;
	private String country;
	private ArrayList<Contact> contacts = new ArrayList<Contact>();
	
	public Company() {}
	
	public Company(int id, String name, String sector, String country) {
		this.id = id;
		this.name = name;
		this.sector = sector;
		this.country = country;
	}

	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getSector() 
	{
		return sector;
	}
	
	public void setSector(String sector) 
	{
		this.sector = sector;
	}
	
	public String getCountry() 
	{
		return country;
	}
	
	public void setCountry(String country) 
	{
		this.country = country;
	}
	
	public List<Contact> getContacts() 
	{
		return contacts;
	}
	
	public void assume(Contact contact) 
	{
		if(!contacts.contains(contact))
			contacts.add(contact);
	}
	
	public void fire(int id) 
	{
		if(contacts.contains(contacts.get(id)))
			contacts.remove(contacts.get(id));
	}

	@Override
	public String toString() 
	{
		return "{id:'" + id + "', name:'" + name + "', sector:'" + sector + "', country:'" + country + "', contacts:'"
				+ contacts + "'}";
	}
	
	public String shortToString() 
	{
		return "{id:'" + id + "', name:'" + name + "'}";
	}
	
	
	
	
}
