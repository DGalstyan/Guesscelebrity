package com.guess.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	    
	    // Database Version
		private static final int DATABASE_VERSION = 1;
		// Database Name
		private static final String DATABASE_NAME = "GuessDB";
		// Contacts table name
		private static final String TAGS_TABLE = "db_tags";

		public MySQLiteHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			// SQL statement to create TAGS_TABLE table
			db.execSQL("create table "
					+ TAGS_TABLE
					+ " (tag_id integer primary key, name text, image text, tag_status integer, sex integer, type integer)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// Drop older TAGS_TABLE table if existed
			db.execSQL("DROP TABLE IF EXISTS " + TAGS_TABLE);
			// create TAGS_TABLE table
			this.onCreate(db);
		}
		
		public boolean updateStatus(String tag_id, int status) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues newValues = new ContentValues();
			newValues.put("tag_status", status);

			db.update(TAGS_TABLE, newValues, "tag_id='" + tag_id + "'", null);

			return true;
		}

		public boolean insertTagsEasy(String name, String image, int status, int sex) {
			return insertTags(name, image, status, sex, 1);
		}
		
		public boolean insertTags(String name, String image, int status, int sex, int type) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();

			values.put("name", name);
			values.put("image", image);
			values.put("tag_status", status);
			values.put("sex", sex);
			values.put("type", type);
			db.insert(TAGS_TABLE, null, values);
			
			return true;
		}
		
		public List<Tag> getAllTag(int type, int limit) {
			List<Tag> itemsList = new ArrayList<Tag>();
			Cursor cursor = null;
			try {
				SQLiteDatabase db = getReadableDatabase();
				// get all rows				
				cursor = db.query(TAGS_TABLE, null, "type ="
						+ type, null, null, null, null);
				if (cursor.moveToFirst()) {
					do {
						Tag c = new Tag();
						c.setName(cursor.getString(cursor.getColumnIndex("name")));
						c.setStatus(cursor.getInt(cursor.getColumnIndex("tag_status")));
						c.setId(cursor.getInt(cursor.getColumnIndex("tag_id")));
						c.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
						c.setType(cursor.getInt(cursor.getColumnIndex("type")));
						c.setImage(cursor.getString(cursor.getColumnIndex("image")));				
						itemsList.add(c);
					} while (cursor.moveToNext());					
				}
			} catch (SQLiteException e) {
				e.printStackTrace();
			} finally {
				if(cursor != null)
				 cursor.close();
			}
			return itemsList;
		}
		
		public List<Tag> getAllTagBySex(Tag tag) {
			List<Tag> itemsList = new ArrayList<Tag>();
			Cursor cursor = null;
			try {
				SQLiteDatabase db = getReadableDatabase();
				// get all rows				
				cursor = db.query(TAGS_TABLE, null, "sex ="
						+ tag.getSex(), null, null, null, null);
				if (cursor.moveToFirst()) {
					do {
						Tag c = new Tag();
						c.setName(cursor.getString(cursor.getColumnIndex("name")));
						c.setStatus(cursor.getInt(cursor.getColumnIndex("tag_status")));
						c.setId(cursor.getInt(cursor.getColumnIndex("tag_id")));
						c.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
						c.setType(cursor.getInt(cursor.getColumnIndex("type")));
						c.setImage(cursor.getString(cursor.getColumnIndex("image")));				
						itemsList.add(c);
					} while (cursor.moveToNext());					
				}
			} catch (SQLiteException e) {
				e.printStackTrace();
			} finally {
				if(cursor != null)
				 cursor.close();
			}
			return itemsList;
		}
		
		public Tag getTag(int id){
			 
		    // 1. get reference to readable DB
		    SQLiteDatabase db = this.getReadableDatabase();
		 
		    // 2. build query
		    Cursor cursor = 
		            db.query(TAGS_TABLE, // a. table
		            null, // b. column names
		            " tag_id = ?", // c. selections 
		            new String[] { String.valueOf(id) }, // d. selections args
		            null, // e. group by
		            null, // f. having
		            null, // g. order by
		            null); // h. limit
		 
		    // 3. if we got results get the first one
		    if (cursor != null)
		        cursor.moveToFirst();
		 
		    // 4. build book object
		    Tag tag = new Tag();
		    tag.setName(cursor.getString(cursor.getColumnIndex("name")));
		    tag.setStatus(cursor.getInt(cursor.getColumnIndex("tag_status")));
		    tag.setId(cursor.getInt(cursor.getColumnIndex("tag_id")));
		    tag.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
		    tag.setType(cursor.getInt(cursor.getColumnIndex("type")));
		    tag.setImage(cursor.getString(cursor.getColumnIndex("image")));
		 
		    //log 
		
		 
		    // 5. return book
		    return tag;
		}
}
