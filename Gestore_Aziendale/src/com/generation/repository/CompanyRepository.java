package com.generation.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generation.entities.Company;
import com.generation.library.SQLConnection;

public class CompanyRepository 
{
	private static HashMap<String,String> commands = new HashMap<String,String>();
	
	
	static
	{
		String insert = "Insert into [tableName] (id, name, sector, country)" +
						"values ([id], [name], [sector], [country])";
	
		commands.put("insert", insert);
		
		String update = "UPDATE [tableName] set name=[name], sector=[sector], country=[country] WHERE id=[id]";

		commands.put("update", update);
		
		String delete = "DELETE from [tableName] WHERE id = [id]";

		commands.put("delete", delete);
		
		String read = "SELECT*FROM [tableName] [WHERE]";
		
		commands.put("read", read);

	}
	
	private HashMap <Integer, Company> cache = new HashMap <Integer, Company>();
	
	private SQLConnection con;
	
	private String tableName;
	
	public CompanyRepository (SQLConnection con, String tableName)
	{
		this.con = con;
		this.tableName = tableName;
	}
	
	private HashMap<String,String> objectToMap (Company company)
	{
		HashMap<String,String> companyMap = new HashMap<String,String>();
		
		companyMap.put("id", company.getId()+"");
		companyMap.put("name", company.getName());
		companyMap.put("sector", company.getSector());
		companyMap.put("country", company.getCountry());
		
		return companyMap;
	}
	
	private Company mapToObject(Map<String,String> companyMap)
	{
		Company company  = new Company();
		company.setId(Integer.parseInt(companyMap.get("id")));
		company.setName(companyMap.get("name"));
		company.setSector(companyMap.get("sector"));
		company.setCountry(companyMap.get("country"));
		 
		return company;
	}
	
	private List<Company> mapsToObjects(List<Map<String, String>> companiesMaps)
	{
		ArrayList<Company> companies = new ArrayList<Company>();
		
		for(Map<String, String> companyMap : companiesMaps)
			companies.add(mapToObject(companyMap));
		
		return companies;
	}
	
	private String apicizza (String s)
	{
		return "'" + s + "'";
	}
	
	private void save (Company company, boolean update) throws SQLException
	{
		String query;
		Map<String, String> converted = objectToMap(company);
		if(update)
			query = commands.get("update");
		else
			query = commands.get("insert");
		
			for(String key : converted.keySet())
				query = query.replace("[" + key + "]", apicizza(converted.get(key)));
			
			query = query.replace("[tableName]", tableName);
		
			con.executeDML(query);
	}
	
	private void saveMultiple(List<Company> companies, boolean update) throws SQLException
	{
		for(Company company : companies)
			save(company, update);
	}

	public void insert (Company company) throws SQLException
	{
		save(company, false);
	}

	public void insertList (List <Company> company) throws SQLException
	{
		saveMultiple(company, false);
	}

	public void update (Company company) throws SQLException
	{
		save(company, true);
	}
	
	public void updateList (List <Company> companies) throws SQLException
	{
		saveMultiple(companies, true);
	}

	public void delete(Company company) throws SQLException
	{
		String query = commands.get("delete");
		query = query.replace("[id]", company.getId()+"");
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

	public List<Company> findAll () throws SQLException
	{
		return findWhere("1=1");
	}
	

	public List<Company> findWhere (String where) throws SQLException
	{
		String query = commands.get("read");
		query = query.replace("[WHERE]", "WHERE " + where);
		query = query.replace("[tableName]", tableName);
		
		List<Map<String, String>> companiesMaps = con.executeQuery(query);
		
		List<Company> companies = mapsToObjects(companiesMaps);
		
		for(int i = 0; i < companies.size(); i++)
		{
			if(!cache.containsKey(companies.get(i).getId()))
				cache.put(companies.get(i).getId(), companies.get(i));
			else
				companies.set(i, cache.get(companies.get(i).getId()));
		}
		
		return companies;
	}
	

	public Company findByID(int id) throws SQLException
	{
		if(cache.containsKey(id))
			return cache.get(id);
		
		String query = commands.get("read");
		query = query.replace("[WHERE]", "WHERE id =" + id);
		query = query.replace("[tableName]", tableName);
		
		List<Map<String, String>> companyMap = con.executeQuery(query);
		
		Company company;
		
		if(companyMap.size() == 1)
			company = mapToObject(companyMap.get(0));
		else 
			company=null;
		
		return company;
		
	}
	
}
