package com.github.mescalantea.conocelamusicacubana.dao;

import android.content.Context;
import android.database.Cursor;
import com.github.mescalantea.conocelamusicacubana.model.Connection;
import com.github.mescalantea.conocelamusicacubana.model.Question;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO
{
	private final String TABLE_NAME = "question";
	private final String ID = "_id";
	private final String CATEGORY = "category";
	private final String ANSWER = "answer";
	private final String OPTIONS = "options";
	private final String CLUE = "clue";
	private final String FILE = "file";
	private Context c;

	public QuestionDAO(Context c)
	{
		this.c = c;
	}

	private Question getQuestionFromCursor(Cursor cursor)
	{
		Question question = null;

		if(cursor.moveToNext()) {

			question = new Question();
			question.setQuestionId(cursor.getInt(cursor.getColumnIndex(ID)));
			//file
			String resource = cursor.getString(cursor.getColumnIndex(FILE));
			int resourceId = c.getResources().getIdentifier(resource, "raw", c.getPackageName());
			question.setFile(resourceId);
			//options
			resource = cursor.getString(cursor.getColumnIndex(OPTIONS));
			resource = c.getString(c.getResources().getIdentifier(resource, "string", c.getPackageName()));
			List<String> l = new ArrayList<String>();
			for (String s : resource.split(";")) {
				l.add(s);
			}
			question.setOptions(l);
			//answer
			resource = cursor.getString(cursor.getColumnIndex(ANSWER));
			resource = c.getString(c.getResources().getIdentifier(resource, "string", c.getPackageName()));
			question.setAnswer(resource);
			//clue
			resource = cursor.getString(cursor.getColumnIndex(CLUE));
			resource = c.getString(c.getResources().getIdentifier(resource, "string", c.getPackageName()));
			question.setClue(resource);
			//category
			int catId = cursor.getInt(cursor.getColumnIndex(CATEGORY));
			question.setCategory(new CategoryDAO(c).getCategory(catId));
		}
		return question;
	}

	public Question getQuestion(int id ){

		String query =
				"SELECT "+
					ID + ","+
					CATEGORY + ","+
					ANSWER + ","+
					OPTIONS + ","+
					CLUE + ","+
					FILE +
				" FROM "+
					TABLE_NAME +
				" WHERE "+ID + " = "+id+
				" LIMIT 1";

		Question q = getQuestionFromCursor( Connection.getInstance(c).getConnection().rawQuery(query,null) );
		Connection.getInstance(c).closeDB();
		return q;
	}

}
