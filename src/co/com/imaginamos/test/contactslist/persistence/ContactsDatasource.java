package co.com.imaginamos.test.contactslist.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ContactsDatasource {

  // Database fields
  private SQLiteDatabase database;
  private DatabaseOpenHelper dbHelper;
  private String[] allColumns = { DatabaseOpenHelper.COLUMN_ID,
		  DatabaseOpenHelper.COLUMN_NAME, DatabaseOpenHelper.COLUMN_PHONE, DatabaseOpenHelper.COLUMN_EMAIL, DatabaseOpenHelper.COLUMN_IMAGE };

  public ContactsDatasource(Context context) {
	if(dbHelper == null){
		dbHelper = new DatabaseOpenHelper(context);
	}    
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public void createContact(ContactsDTO contact) {
    ContentValues values = new ContentValues();
    values.put(DatabaseOpenHelper.COLUMN_ID, getMaxId() + 1);
    values.put(DatabaseOpenHelper.COLUMN_NAME, contact.getName());
    values.put(DatabaseOpenHelper.COLUMN_PHONE, contact.getPhone());
    values.put(DatabaseOpenHelper.COLUMN_EMAIL, contact.getEmail());
    values.put(DatabaseOpenHelper.COLUMN_IMAGE, contact.getImage());
    database.insert(DatabaseOpenHelper.TABLE_CONTACTS, null,
        values);    
  }
  
  public int getMaxId(){
	  Cursor cursor = database.rawQuery("SELECT MAX("+DatabaseOpenHelper.COLUMN_ID+") FROM "+DatabaseOpenHelper.TABLE_CONTACTS, null);
	  cursor.moveToFirst();
	  return cursor.getInt(0);
  }

//  public void deleteComment(Comment comment) {
//    long id = comment.getId();
//    System.out.println("Comment deleted with id: " + id);
//    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
//        + " = " + id, null);
//  }

  public List<ContactsDTO> getAllContacts() {
    List<ContactsDTO> contacts = new ArrayList<ContactsDTO>();

    Cursor cursor = database.query(DatabaseOpenHelper.TABLE_CONTACTS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      ContactsDTO contact = cursorToContact(cursor);
      contacts.add(contact);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return contacts;
  }

  private ContactsDTO cursorToContact(Cursor cursor) {
    ContactsDTO contact = new ContactsDTO();
    contact.setId(cursor.getInt(0));
    contact.setName(cursor.getString(1));
    contact.setPhone(cursor.getString(2));
    contact.setEmail(cursor.getString(3));
    contact.setImage(cursor.getBlob(4));
    return contact;
  }
  
  public void updateContact(ContactsDTO contact){
	  
	  ContentValues values = new ContentValues();
	    values.put(DatabaseOpenHelper.COLUMN_NAME, contact.getName());
	    values.put(DatabaseOpenHelper.COLUMN_PHONE, contact.getPhone());
	    values.put(DatabaseOpenHelper.COLUMN_EMAIL, contact.getEmail());
	    values.put(DatabaseOpenHelper.COLUMN_IMAGE, contact.getImage());
	    
	  database.update(DatabaseOpenHelper.TABLE_CONTACTS, values, DatabaseOpenHelper.COLUMN_ID +"=?", new String[] { String.valueOf(contact.getId())});
	}
  
  public boolean deleteContact(int id) 
  {
      return database.delete(DatabaseOpenHelper.TABLE_CONTACTS, DatabaseOpenHelper.COLUMN_ID + "=" + id, null) > 0;
  }
  
} 
