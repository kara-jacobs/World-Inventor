package com.androidatc.worldinventor

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlaceDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "placeapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allplaces"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_TERRAIN = "terrain"
        private const val COLUMN_WEATHER = "weather"
        private const val COLUMN_LOREBOX = "lorebox"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_TERRAIN TEXT, $COLUMN_WEATHER TEXT, $COLUMN_LOREBOX TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    //Inserts a place into the list of places
    fun insertPlace(place: Place){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_NAME, place.name)
            put(COLUMN_TERRAIN, place.terrain)
            put(COLUMN_WEATHER, place.weather)
            put(COLUMN_LOREBOX, place.lorebox)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //Gets all of the places
    fun getAllPlaces(): List<Place>{
        val placeList = mutableListOf<Place>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val terrain = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TERRAIN))
            val weather = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEATHER))
            val lorebox = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOREBOX))

            val place = Place(id, name, terrain, weather, lorebox)
            placeList.add(place)
        }
        cursor.close()
        db.close()
        return placeList
    }

    //Updates a place
    fun updatePlace(place: Place){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_NAME, place.name)
            put(COLUMN_TERRAIN, place.terrain)
            put(COLUMN_WEATHER, place.weather)
            put(COLUMN_LOREBOX, place.lorebox)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(place.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    //Gets a single place by ID
    fun getPlaceByID(placeId: Int): Place{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $placeId"
        val cursor = db. rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val terrain = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TERRAIN))
        val weather = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEATHER))
        val lorebox = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOREBOX))

        cursor.close()
        db.close()
        return Place(id, name, terrain, weather, lorebox)
    }

    fun deletePlace(placeId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(placeId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

}