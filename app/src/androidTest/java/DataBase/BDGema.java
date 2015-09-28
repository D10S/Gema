package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDGema extends SQLiteOpenHelper{

	public BDGema(Context context){
		super(context, "DatosUsuarioGema", null, 1);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		dataBase.execSQL("create table Escuela(idEscuela integer, nameEscuela TEXT not null, primary key(idEscuela));");
		dataBase.execSQL("create table Usuario(Mail TEXT not null, Name TEXT not null, Password TEXT not null, Fecha_Nacimiento TEXT not null, IsActive INT default 0, idEscuela integer not null, primary key(Mail), foreign key(idEscuela) references Escuela(idEscuela));");
	}
	
	public boolean checkUsersExistance(){
		boolean wasIn = false;
		SQLiteDatabase db = getReadableDatabase();
		String table = "Usuario";
		String[] columns = null;
		String selection = null;
		String[] selectionArgs = null;
		String groupBy = null;
		String having = null;
		String orderBy = null;
		wasIn = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy).getCount() > 0;
		db.close();
		return wasIn;
	}
	
	public boolean checkUserByKeyValue(String keyValue){
		boolean isIn = false;
		SQLiteDatabase db = getReadableDatabase();
		String[] selectionArgs = {keyValue};
		Cursor c = db.query("Usuario", null, "Mail = ?", selectionArgs, null, null, null);
		if( c.getCount() == 0 ){
			c = db.query("Usuario", null, "Name = ?", selectionArgs, null, null, null);
			if ( c.getCount() != 0 )
				isIn = true;
		}
		db.close();
		return isIn;
	}
	
	public boolean addNewUser(String mail, String name, String psswd, String fechaNacimiento, String nameEscuela){
		boolean success = false;
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		String nullColumnHack = "---";
		String table = "Usuario";
		values.put("Mail", mail);
		values.put("Name", name);
		values.put("Password", psswd);
		values.put("Fecha_Nacimiento", fechaNacimiento);
		values.put("idEscuela", getEscuelaId(nameEscuela));
		success = db.insert(table, nullColumnHack, values) != -1;
		db.close();
		return success;
	}
	
	public void clearActiveUser(){
		SQLiteDatabase db = getWritableDatabase();
		String table = "Usuario";
		ContentValues values = new ContentValues();
		String whereClause = "IsActive = ?";
		String[] whereArgs = {"1"};
		values.put("IsAvtive", "0");
		db.update(table, values, whereClause, whereArgs);
	}
	
	public boolean setActiveUser(String mail){
		boolean success = false;
		SQLiteDatabase db = getWritableDatabase();
		String table = "Usuario";
		ContentValues values = new ContentValues();
		String whereClause = "Mail = ?";
		String[] whereArgs = {mail};
		values.put("IsActive", "1");
		db.update(table, values, whereClause, whereArgs);
		return success;
	}

	public String[] getActiveUserData(){
		String[] data;
		SQLiteDatabase db = getReadableDatabase();
		String table = "Usuario";
		String[] columns = {"Name","Password"};
		String selection = "IsActive = ?";
		String[] selectionArgs = {"1"};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		if( c.getCount() != 0 ){
			c.moveToFirst();
			data = new String[2];
			data[0] = c.getString(c.getColumnIndex("Name"));
			data[1] = c.getString(c.getColumnIndex("Password"));
		}else
			data = null;
		db.close();
		return data;
	}
	
	public boolean validateUser(String userId, String psswd){
		boolean wasIn = false;
		SQLiteDatabase db = getReadableDatabase();
		String table = "Usuario";
		String[] columns = {"Name"};
		String selection = "(Name = ? and Password = ?) or (Mail = ? and Password = ?)";
		String[] selectionArgs = {userId,psswd,userId,psswd};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		if(db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy).getCount() > 0)
			wasIn = true;
		db.close();
		return wasIn;
	}
	
	public String getUserMailAddress(String name){
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = {"Mail"};
		String[] selectionArgs = {name};
		Cursor c = db.query("Usuario", columns, "Name = ?", selectionArgs, null, null, null);
		String mail = null;
		if( c.getCount() != 0){
			c.moveToFirst();
			mail = c.getString(c.getColumnIndex("Mail"));
		}
		db.close();
		return mail;
	}

    public boolean validaEscuela(String escuela){
        boolean isPresent = false;
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = null;
        String[] selArgs = {escuela};
        Cursor c = db.query("Escuela",columns,"nameEscuela=?",selArgs,null,null,null);
        if(c.getCount() > 0)
			isPresent = true;
		db.close();
        return isPresent;
    }

	public int getEscuelaId(String name){
		int id;
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = {"idEscuela"};
		String[] selArgs = {name};
		Cursor c = db.query("Escuela",columns,"nameEscuela=?",selArgs,null,null,null);
		id = c.getInt(c.getColumnIndex("idEscuela"));
		db.close();
		return id;
	}

	public boolean setNewEscuela(String name){
		boolean success = false;
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nameEscuela", name);
		if(db.insert("Escuela","---",values) != -1)
			success = true;
		db.close();
		return success;
	}
}