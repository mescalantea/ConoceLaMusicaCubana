package com.github.mescalantea.conocelamusicacubana.dao;

import android.content.Context;
import android.database.Cursor;
import com.github.mescalantea.conocelamusicacubana.model.Category;
import com.github.mescalantea.conocelamusicacubana.model.Connection;

public class CategoryDAO
{
	private final String TABLE_NAME = "category";
	private final String NAME = "text";
	private final String ID = "_id";
	private Context c;
	
	public CategoryDAO(Context c)
	{
		this.c = c;
	}


	private Category getCategoryFromCursor(Cursor cursor)
	{
		if(cursor != null && cursor.moveToNext()) {

			Category category = new Category();
			category.setCategoryId(cursor.getInt(cursor.getColumnIndex(ID)));
			//name
			String resource = cursor.getString(cursor.getColumnIndex(NAME));
			resource = c.getString(c.getResources().getIdentifier(resource, "string", c.getPackageName()));
			category.setName(resource);

			return category;
		}
		return null;
	}
	
	public Category getCategory(int id)
	{

		String query = "SELECT " +
							ID+","+
							NAME+
						" FROM "+
							TABLE_NAME +
						" WHERE "+
							ID+" = "+id+
						" LIMIT 1";


		Category category =  getCategoryFromCursor( Connection.getInstance(c).getConnection().rawQuery(query,null) );
		Connection.getInstance(c).closeDB();
		return category;

	}
}
