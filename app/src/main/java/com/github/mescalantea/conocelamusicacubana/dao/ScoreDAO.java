package com.github.mescalantea.conocelamusicacubana.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import com.github.mescalantea.conocelamusicacubana.model.Connection;
import com.github.mescalantea.conocelamusicacubana.model.Score;

import java.util.ArrayList;

public class ScoreDAO
{

	private final String TABLE_NAME = "score";
	private final String PERCENT = "percent";
	private final String PLAYER = "player";
	private Context c;

	public ScoreDAO(Context c)
	{
		this.c = c;
	}


	private Score getScoreFromCursor(Cursor cursor)
	{
		Score score = null;
		if(cursor != null && cursor.moveToNext()) {
			score = new Score();
			score.setPlayer(cursor.getString(cursor.getColumnIndex(PLAYER)));
			score.setPercent(cursor.getInt(cursor.getColumnIndex(PERCENT)));
		}
		return score;
	}

    private ArrayList<Score> getScoresFromCursor(Cursor cursor)
    {
        ArrayList<Score> list = new ArrayList<>();
        if(cursor != null) {
            while (cursor.moveToNext()){
                Score s = new Score();
                s.setPlayer(cursor.getString(cursor.getColumnIndex(PLAYER)));
                s.setPercent(cursor.getInt(cursor.getColumnIndex(PERCENT)));
                if(s != null){
                    list.add(s);
                }
            }
        }
        return list;
    }

	public ArrayList<Score> getScores()
	{
		String query =
				"SELECT " +
				PLAYER+","+
				PERCENT+
				" FROM "+
				TABLE_NAME +
				" ORDER BY "+PERCENT+" DESC";

        ArrayList<Score>list =  getScoresFromCursor(Connection.getInstance(c).getConnection().rawQuery(query,null));
        Connection.getInstance(c).closeDB();
        return list;
	}

    /**
     * Insert or update a score if exist in the database
     * @param score
     */
	public void createScore(Score score)
    {
        ContentValues cv = new ContentValues();
        cv.put(PLAYER,score.getPlayer());
        cv.put(PERCENT,score.getPercent());

        int affectedRows = Connection.getInstance(c).getConnection().update(TABLE_NAME, cv, PLAYER + " LIKE ?", new String[]{score.getPlayer()});

        if (affectedRows < 1)
        {
            Connection.getInstance(c).getConnection().insertOrThrow(TABLE_NAME,null,cv);
        }

        Connection.getInstance(c).closeDB();

    }

}
