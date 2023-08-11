package com.generation.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generation.entities.Contact;
import com.generation.library.SQLConnection;

public class ContactRepository 
{
	private static HashMap<String,String> commands = new HashMap<String,String>();
	
	
	static
	{
		String insert = "Insert into [tableName] (id, company_id, name, surname, address, city)" +
						"values ([id], [company_id], [name], [surname], [address], [city])";
	
		commands.put("insert", insert);
		
		String update = "UPDATE [tableName] set company_id=[company_id], name=[name], surname=[surname],address=[address], "+
				 		"city=[city] WHERE id=[id]";

		commands.put("update", update);
		
		String delete = "DELETE from [tableName] WHERE id = [id]";

		commands.put("delete", delete);
		
		String read = "SELECT*FROM [tableName] [WHERE]";
		
		commands.put("read", read);

	}
	
	private HashMap <Integer, Contact> cache = new HashMap <Integer, Contact>();
	
	private SQLConnection con;
	
	private String tableName;
	
	public ContactRepository (SQLConnection con, String tableName)
	{
		this.con = con;
		this.tableName = tableName;
	}
	
	private HashMap<String,String> objectToMap (Contact contact)
	{
		HashMap<String,String> contactMap = new HashMap<String,String>();
		
		contactMap.put("id", contact.getId()+"");
		contactMap.put("company_id", contact.getCompany_id()+"");
		contactMap.put("name", contact.getName());
		contactMap.put("surname", contact.getSurname());
		contactMap.put("address", contact.getAddress());
		contactMap.put("city", contact.getCity());
		
		
		return contactMap;
	}
	
	private Contact mapToObject(Map<String,String> contactMap)
	{
		 Contact contact  = new Contact();
		 contact.setId(Integer.parseInt(contactMap.get("id")));
		 contact.setCompany_id(Integer.parseInt(contactMap.get("company_id")));
		 contact.setName(contactMap.get("name")) ;
		 contact.setSurname(contactMap.get("surname")) ;
		 contact.setAddress(contactMap.get("address"));
		 contact.setCity(contactMap.get("city"));
		 
		
		return contact;
	}
	
	private List<Contact> mapsToObjects(List<Map<String, String>> contactsMaps)
	{
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		for(Map<String, String> contactMap : contactsMaps)
			contacts.add(mapToObject(contactMap));
		
		return contacts;
	}
	
	private String apicizza (String s)
	{
		return "'" + s + "'";
	}
	
	private void save (Contact contact, boolean update) throws SQLException
	{
		String query;
		Map<String, String> converted = objectToMap(contact);
		if(update)
			query = commands.get("update");
		else
			query = commands.get("insert");
		
			for(String key : converted.keySet())
				query = query.replace("[" + key + "]", apicizza(converted.get(key)));
			
			query = query.replace("[tableName]", tableName);
		
			con.executeDML(query);
	}
	
	private void saveMultiple(List<Contact> contacts, boolean update) throws SQLException
	{
		for(Contact contact : contacts)
			save(contact, update);
	}

	public void insert (Contact contact) throws SQLException
	{
		save(contact, false);
	}

	public void insertList (List <Contact> contact) throws SQLException
	{
		saveMultiple(contact, false);
	}

	public void update (Contact contact) throws SQLException
	{
		save(contact, true);
	}
	
	public void updateList (List <Contact> contacts) throws SQLException
	{
		saveMultiple(contacts, true);
	}

	public void delete(Contact contact) throws SQLException
	{
		String query = commands.get("delete");
		query = query.replace("[id]", contact.getId()+"");
		query = query.replace("[tableName]", tableName);
		con.executeDML(query);
	}
	
	public void deleteByID (int id) throws SQLException
	{
		String query = commands.get("delete");
		query = query.replace("[id]", id+"");
		query = query.replace("[tableName]", tableName);
		con.executeDML(query);
	}

	public List<Contact> findAll () throws SQLException
	{
		return findWhere("1=1");
	}
	

	public List<Contact> findWhere (String where) throws SQLException
	{
		String query = commands.get("read");
		query = query.replace("[WHERE]", "WHERE " + where);
		query = query.replace("[tableName]", tableName);
		
		List<Map<String, String>> contactsMaps = con.executeQuery(query);
		
		List<Contact> contacts = mapsToObjects(contactsMaps);
		
		for(int i = 0; i < contacts.size(); i++)
		{
			if(!cache.containsKey(contacts.get(i).getId()))
				cache.put(contacts.get(i).getId(), contacts.get(i));
			else
				contacts.set(i, cache.get(contacts.get(i).getId()));
		}
		
		return contacts;
	}
	

	public Contact findByID(int id) throws SQLException
	{
		if(cache.containsKey(id))
			return cache.get(id);
		
		String query = commands.get("read");
		query = query.replace("[WHERE]", "WHERE id =" + id);
		query = query.replace("[tableName]", tableName);
		
		List<Map<String, String>> contactMap = con.executeQuery(query);
		
		Contact contact;
		
		if(contactMap.size() == 1)
			contact = mapToObject(contactMap.get(0));
		else 
			contact=null;
		
		return contact;
		
	}
	
}
