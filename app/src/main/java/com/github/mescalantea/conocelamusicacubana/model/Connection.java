package com.github.mescalantea.conocelamusicacubana.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Connection {

	private final String DB_NAME = "database.db";
	private final int BUFFER_SIZE = 1024;
	private SQLiteDatabase db;
	private Context context;
	private static Connection INSTANCE = null;

	private boolean checkDB(){

		boolean success = true;

		String path = context.getFilesDir() + File.separator + DB_NAME;
		File dbFile = new File(path);

		if(!dbFile.exists()){
			//copy from assets to internal storage
			try {
				InputStream inputStream = context.getAssets().open("database.db");
				FileOutputStream fileOutputStream = new FileOutputStream(dbFile);

				byte buf[]=new byte[BUFFER_SIZE];
				int len;

				while((len=inputStream.read(buf)) > 0) {
					fileOutputStream.write(buf,0,len);
				}
				fileOutputStream.close();
				inputStream.close();

			} catch (IOException e1) {
				//error copying the file. make a default tile provider with connection false
				success = false;
			}
		}

		return success;
	}


	public boolean closeDB(){

		boolean success = false;

		try {
			if (db != null && db.isOpen()) {
				db.close();
				success = true;
			}
		}
		catch (Exception ex){}

		return success;
	}

	private boolean openDB(){
		boolean success = true;
		try {
			if (db == null || !db.isOpen()) {
				if (checkDB()) {
					db = SQLiteDatabase.openDatabase(context.getFilesDir() + File.separator + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
				}
			}
		}
		catch (Exception ex){success = false;}

		return success;
	}

    public SQLiteDatabase getConnection(){
        if(openDB()){
            return db;
        }
        else{
            return null;
        }
    }

	private Connection(Context c){
		context = c;
	}

	private synchronized static void createInstance(Context c) {
		if (INSTANCE == null) {
			INSTANCE = new Connection(c);
		}
	}

	public static Connection getInstance(Context c) {
		if (INSTANCE == null) createInstance(c);
		return INSTANCE;
	}




}
