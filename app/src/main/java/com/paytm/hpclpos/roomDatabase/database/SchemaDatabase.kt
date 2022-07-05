package com.example.apphpcldb.entity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apphpcldb.entity.dao.SchemaDao
import com.example.apphpcldb.entity.entity.Schema

@Database(entities = [Schema::class], version = 1, exportSchema = false)
abstract class SchemaDatabase : RoomDatabase() {
    abstract fun schemaDao() : SchemaDao
}