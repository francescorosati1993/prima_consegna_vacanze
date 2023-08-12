package com.generation.entities;

import java.time.LocalDateTime;

public class Ticket 
{
	private int id;
	private int contact_id;
	private Contact contact;
	private String request;
	private String response;
	private LocalDateTime sentOn;
	private LocalDateTime closedOn;
	
	public Ticket() {}
	
	public Ticket(int id, int contact_id, String request, String response, LocalDateTime sentOn,
			LocalDateTime closedOn) 
	{
		this.id = id;
		this.contact_id = contact_id;
		this.request = request;
		this.response = response;
		this.sentOn = sentOn;
		this.closedOn = closedOn;
	}

	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public int getContact_id() 
	{
		return contact_id;
	}
	
	public void setContact_id(int contact_id) 
	{
		this.contact_id = contact_id;
	}
	
	public Contact getContact() 
	{
		return contact;
	}
	
	public void setContact(Contact contact) 
	{
		this.contact = contact;
	}
	
	public String getRequest() 
	{
		return request;
	}
	
	public void setRequest(String request) 
	{
		this.request = request;
	}
	
	public String getResponse() 
	{
		return response;
	}
	
	public void setResponse(String response) 
	{
		this.response = response;
	}
	
	public LocalDateTime getSentOn() 
	{
		return sentOn;
	}
	
	public void setSentOn(LocalDateTime sentOn) 
	{
		this.sentOn = sentOn;
	}
	
	public LocalDateTime getClosedOn() 
	{
		return closedOn;
	}
	
	public void setClosedOn(LocalDateTime closedOn) 
	{
		this.closedOn = closedOn;
	}

	
	public String toString() 
	{
		return "{id:'" + id + "', request:'" + request +"', contact_id:'"+ contact_id + "', contact:'" + contact.getName() + " " + contact.getSurname() + "', response:'" + response + "', sentOn:'" + sentOn + "', closedOn:'" + closedOn + "'}";
	}
	
	
	//"', contact_id:'"+ contact_id + "', contact:'" + contact.getName() + " " + contact.getSurname() +
}
