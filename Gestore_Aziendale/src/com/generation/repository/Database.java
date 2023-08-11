package com.generation.repository;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.generation.entities.Company;
import com.generation.entities.Contact;
import com.generation.entities.Ticket;
import com.generation.library.SQLConnection;

public class Database 
{
	private TicketRepository repoTicket;
	private ContactRepository repoContact;
	private CompanyRepository repoCompany;
	
	public Database(String fileName, String ticketTable, String contactTable, String companyTable) throws FileNotFoundException, SQLException
	{
		SQLConnection connection = new SQLConnection(fileName);
		repoTicket = new TicketRepository(connection, ticketTable);
		repoContact = new ContactRepository(connection, contactTable);
		repoCompany = new CompanyRepository(connection, companyTable);
	}
	
	//link Ticket con il padre Contact
	private void linkTicketToContact(Ticket ticket) throws SQLException
	{
		Contact contact = repoContact.findByID(ticket.getContact_id());
		ticket.setContact(contact);
		contact.addTicket(ticket);
	}
	
	//link Contact con il padre Company 
	private void linkContactToCompany(Contact contact) throws SQLException
	{
		Company company = repoCompany.findByID(contact.getCompany_id());
		contact.setCompany(company);
		company.assume(contact);
	}
	
	//Link figli Contact
	private void linkContactToTicket(Contact contact) throws SQLException 
	{
		List<Ticket> tickets = repoTicket.findWhere("contact_id = " + contact.getId());
		for(Ticket ticket : tickets)
		{
			contact.addTicket(ticket);
			ticket.setContact(contact);
		}
	}
	
	//Link figli Company
	private void linkCompanyToContact(Company company) throws SQLException 
	{
		List<Contact> contacts = repoContact.findWhere("company_id = " + company.getId());
		for(Contact contact : contacts)
		{
			company.assume(contact);
			contact.setCompany(company);
		}
	}
	
	public Ticket findTicketByID(int id) throws SQLException
	{
		Ticket ticket = repoTicket.findByID(id);
		linkTicketToContact(ticket);
		return ticket;
	}
	
	public Contact findContactByID(int id) throws SQLException 
	{
		Contact contact = repoContact.findByID(id);
		linkContactToTicket(contact);
		linkContactToCompany(contact);
		return contact;
	}
	
	public Company findCompanyByID(int id) throws SQLException 
	{
		Company company = repoCompany.findByID(id);
		linkCompanyToContact(company);
		return company;
	}
	
	public List<Company> findCompanyBySector(String sector) throws SQLException 
	{
		String where = "sector = '" + sector +"'";
		List<Company> companies = repoCompany.findWhere(where);
		
		for(Company company : companies)
			linkCompanyToContact(company);
		
		return companies;
	}
	
	public List<Ticket> findAllTickets() throws SQLException
	{
		List<Ticket> tickets = repoTicket.findAll();
//		for(Ticket ticket : tickets)
//			linkTicketToContact(ticket);
		
		return tickets;
	}
	
	public List<Contact> findAllContact() throws SQLException
	{
		List<Contact> contacts = repoContact.findAll();
		
		for(Contact contact : contacts)
		{
			linkContactToTicket(contact);
			linkContactToCompany(contact);
		}
			
		return contacts;
	}
	
	public List<Company> findAllCompany() throws SQLException
	{
		List<Company> companies = repoCompany.findAll();
		
		for(Company company : companies)
			linkCompanyToContact(company);
		
		return companies;
	}
	

	public List<Ticket> findWhereTickets(String where) throws SQLException
	{
		List<Ticket> tickets = repoTicket.findWhere(where);
		
		for(Ticket ticket : tickets)
			linkTicketToContact(ticket);
		
		return tickets;
	}

	public List<Contact> findWhereContact(String where) throws SQLException
	{
		List<Contact> contacts = repoContact.findWhere(where);
		
		for(Contact contact : contacts)
		{
			linkContactToTicket(contact);
			linkContactToCompany(contact);
		}
			
		return contacts;
	}

	public List<Company> findWhereCompany(String where) throws SQLException
	{
		List<Company> companies = repoCompany.findWhere(where);
		
		for(Company company : companies)
			linkCompanyToContact(company);
		
		return companies;
	}
	
	public void deleteTicket(Ticket ticket) throws SQLException
	{
		repoTicket.delete(ticket);
	}
	
	public void deleteTicketByID(int id) throws SQLException
	{
		repoTicket.deleteByID(id);
	}
	
	
	public void deleteContact(Contact contact) throws SQLException
	{
		repoContact.delete(contact);
	}
	
	public void deleteContactByID(int id) throws SQLException
	{
		repoContact.deleteByID(id);
	}
	
	
	public void deleteCompany(Company company) throws SQLException
	{
		repoCompany.delete(company);
	}
	
	public void deleteCompanyByID(int id) throws SQLException
	{
		repoCompany.deleteByID(id);
	}
	
	public void insertTicket(Ticket ticket) throws SQLException
	{
		repoTicket.insert(ticket);
	}
	
	public void insertTickets(List<Ticket> tickets) throws SQLException
	{
		repoTicket.insertList(tickets);
	}
	
	public void insertContact(Contact contact) throws SQLException
	{
		repoContact.insert(contact);
	}
	
	public void insertContacts(List<Contact> contacts) throws SQLException
	{
		repoContact.insertList(contacts);
	}
	
	public void insertCompany(Company company) throws SQLException
	{
		repoCompany.insert(company);
	}
	
	public void insertCompanies(List<Company> companies) throws SQLException
	{
		repoCompany.insertList(companies);
	}
	
	public void updateTicket(Ticket ticket) throws SQLException
	{
		repoTicket.update(ticket);
	}
	
	public void updateTickets(List<Ticket> tickets) throws SQLException
	{
		repoTicket.updateList(tickets);
	}
	
	public void updateContact(Contact contact) throws SQLException
	{
		repoContact.update(contact);
	}
	
	public void updateContacts(List<Contact> contacts) throws SQLException
	{
		repoContact.updateList(contacts);
	}
	
	public void updateCompany(Company company) throws SQLException
	{
		repoCompany.update(company);
	}
	
	public void updateCompanies(List<Company> companies) throws SQLException
	{
		repoCompany.updateList(companies);
	}
	
	
}
