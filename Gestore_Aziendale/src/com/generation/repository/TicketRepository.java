package com.generation.repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generation.entities.Ticket;
import com.generation.library.SQLConnection;

public class TicketRepository 
{
	private static HashMap<String,String> commands = new HashMap<String,String>();
	
	
	static
	{
		String insert = "Insert into [tableName] (id, contact_id, request, response, sentOn, closedOn)" +
						"values ([id], [contact_id], [request], [response], [sentOn], [closedOn])";
	
		commands.put("insert", insert);
		
		String update = "UPDATE [tableName] set contact_id=[contact_id], request=[request],response=[response], "+
				 		"sentOn=[sentOn], closedOn= [closedOn] WHERE id=[id]";

		commands.put("update", update);
		
		String delete = "DELETE from [tableName] WHERE id = [id]";

		commands.put("delete", delete);
		
		String read = "SELECT*FROM [tableName] [WHERE]";
		
		commands.put("read", read);

	}
	
	private HashMap <Integer, Ticket> cache = new HashMap <Integer, Ticket>();
	
	private SQLConnection con;
	
	private String tableName;
	
	public TicketRepository (SQLConnection con, String tableName)
	{
		this.con = con;
		this.tableName = tableName;
	}
	
	private HashMap<String,String> objectToMap (Ticket ticket)
	{
		HashMap<String,String> ticketMap = new HashMap<String,String>();
		
		ticketMap.put("id", ticket.getId()+"");
		ticketMap.put("contact_id", ticket.getContact_id()+"");
		ticketMap.put("request", ticket.getRequest());
		ticketMap.put("response", ticket.getResponse());
		ticketMap.put("sentOn", ticket.getSentOn()+"");
		ticketMap.put("closedOn", ticket.getClosedOn()+"");
		
		
		return ticketMap;
	}
	
	private Ticket mapToObject(Map<String,String> ticketMap)
	{
		 Ticket ticket  = new Ticket();
		 ticket.setId(Integer.parseInt(ticketMap.get("id")));
		 ticket.setContact_id(Integer.parseInt(ticketMap.get("contact_id")));
		 ticket.setRequest(ticketMap.get("request"));
		 ticket.setResponse(ticketMap.get("response"));
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
		 
		 if(ticketMap.get("senton") != null)
			ticket.setSentOn(LocalDateTime.parse(ticketMap.get("senton"), formatter));
		 
		 if(ticketMap.get("closedon") != null)
			 ticket.setClosedOn(LocalDateTime.parse(ticketMap.get("closedon"), formatter));
		 
		 
		return ticket;
	}
	
	private List<Ticket> mapsToObjects(List<Map<String, String>> ticketsMaps)
	{
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		for(Map<String, String> ticketMap : ticketsMaps)
			tickets.add(mapToObject(ticketMap));
		
		return tickets;
	}
	
	private String apicizza (String s)
	{
		return "'" + s + "'";
	}
	
	private void save (Ticket ticket, boolean update) throws SQLException
	{
		String query;
		Map<String, String> converted = objectToMap(ticket);
		if(update)
			query = commands.get("update");
		else
			query = commands.get("insert");
		
			for(String key : converted.keySet())
				query = query.replace("[" + key + "]", apicizza(converted.get(key)));
			
			query = query.replace("[tableName]", tableName);
		
			con.executeDML(query);
	}
	
	private void saveMultiple(List<Ticket> tickets, boolean update) throws SQLException
	{
		for(Ticket ticket : tickets)
			save(ticket, update);
	}

	public void insert (Ticket ticket) throws SQLException
	{
		save(ticket, false);
	}

	public void insertList (List <Ticket> ticket) throws SQLException
	{
		saveMultiple(ticket, false);
	}

	public void update (Ticket ticket) throws SQLException
	{
		save(ticket, true);
	}
	
	public void updateList (List <Ticket> tickets) throws SQLException
	{
		saveMultiple(tickets, true);
	}

	public void delete(Ticket ticket) throws SQLException
	{
		String query = commands.get("delete");
		query = query.replace("[id]", ticket.getId()+"");
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

	public List<Ticket> findAll () throws SQLException
	{
		return findWhere("1=1");
	}
	

	public List<Ticket> findWhere (String where) throws SQLException
	{
		String query = commands.get("read");
		query = query.replace("[WHERE]", "WHERE " + where);
		query = query.replace("[tableName]", tableName);
		
		List<Map<String, String>> ticketsMaps = con.executeQuery(query);
		
		List<Ticket> tickets = mapsToObjects(ticketsMaps);
		
		for(int i = 0; i < tickets.size(); i++)
		{
			if(!cache.containsKey(tickets.get(i).getId()))
				cache.put(tickets.get(i).getId(), tickets.get(i));
			else
				tickets.set(i, cache.get(tickets.get(i).getId()));
		}
		
		return tickets;
	}
	

	public Ticket findByID(int id) throws SQLException
	{
		if(cache.containsKey(id))
			return cache.get(id);
		
		String query = commands.get("read");
		query = query.replace("[WHERE]", "WHERE id =" + id);
		query = query.replace("[tableName]", tableName);
		
		List<Map<String, String>> ticketMap = con.executeQuery(query);
		
		Ticket ticket;
		
		if(ticketMap.size() == 1)
			ticket = mapToObject(ticketMap.get(0));
		else 
			ticket=null;
		
		return ticket;
		
	}
	
}
