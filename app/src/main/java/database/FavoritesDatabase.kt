package com.example.photogallery.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.photogallery.network.PicsumPhoto

class FavoritesDatabase(context: Context) :
    SQLiteOpenHelper(context, "favorites.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE favorites(
                id INTEGER PRIMARY KEY,
                title TEXT NOT NULL,
                thumbnail TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS favorites")
        onCreate(db)
    }

    fun addFavorite(photo: PicsumPhoto) {
        writableDatabase.execSQL(
            "INSERT OR REPLACE INTO favorites(id,title,thumbnail) VALUES(?,?,?)",
            arrayOf(
                photo.id,
                photo.title,
                photo.thumbnail
            )
        )
    }

    fun getFavorites(): List<PicsumPhoto> {

        val list = mutableListOf<PicsumPhoto>()

        val cursor = readableDatabase.rawQuery(
            "SELECT id,title,thumbnail FROM favorites",
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                list.add(
                    PicsumPhoto(
                        id = it.getInt(0),
                        title = it.getString(1),
                        thumbnail = it.getString(2)
                    )
                )
            }
        }

        return list
    }

    fun clearFavorites() {
        writableDatabase.execSQL(
            "DELETE FROM favorites"
        )
    }
}