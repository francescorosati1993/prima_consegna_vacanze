package com.generation.entities;

import java.util.ArrayList;
import java.util.List;

public class Contact 
{
	private int id;
	private int company_id;
	private Company company;
	private String name;
	private String surname;
	private String address;
	private String city;
	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	
	public Contact() {}
	
	public Contact(int id, int company_id, String name, String surname, String address, String city) 
	{
		this.id = id;
		this.company_id = company_id;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.city = city;
	}

	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public int getCompany_id() 
	{
		return company_id;
	}
	
	public void setCompany_id(int company_id) 
	{
		this.company_id = company_id;
	}
	
	public Company getCompany() 
	{
		return company;
	}
	
	public void setCompany(Company company) 
	{
		this.company = company;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getSurname() 
	{
		return surname;
	}
	
	public void setSurname(String surname) 
	{
		this.surname = surname;
	}
	
	public String getAddress() 
	{
		return address;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	public String getCity() 
	{
		return city;
	}
	
	public void setCity(String city) 
	{
		this.city = city;
	}
	
	public List<Ticket> getTickets() 
	{
		return tickets;
	}
	
	public void addTicket(Ticket ticket)
	{
		if(!tickets.contains(ticket))
			tickets.add(ticket);
	}
	
	public void removeTicket(Ticket ticket)
	{
		if(tickets.contains(ticket))
			tickets.remove(ticket);
	}

	@Override
	public String toString() 
	{
		return "{id:'" + id + "', company_id:'" + company_id + "', name:'" + name
				+ "', surname:'" + surname + "', address:'" + address + "', city:'" + city + "', tickets:'" + tickets
				+ "'}";
	}
	
	public String shortToString() 
	{
		return "{id:'" + id + "', name:'" + name+ "', surname:'" + surname + "', address:'" + address + "', city:'" + city + "'}";
	}
	
	
	
	
}
