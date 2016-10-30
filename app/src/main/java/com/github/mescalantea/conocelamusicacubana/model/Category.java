package com.github.mescalantea.conocelamusicacubana.model;


public class Category
{
	public int categoryId;
	public String name;
	
   
    
	public int getCategoryId()
	{
		return categoryId;
	}
	
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString(){
		return name.toLowerCase();
	}
}
