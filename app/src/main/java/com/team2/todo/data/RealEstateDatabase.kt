package com.team2.todo.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.team2.todo.data.daos.SubTodoDao
import com.team2.todo.data.daos.TodoDao
import com.team2.todo.data.datautils.ImageDataConverter
import com.team2.todo.data.datautils.LocalDateTimeConverter
import com.team2.todo.data.entities.Images
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.entities.Todo

@Database(
    entities = [Todo::class, SubTodo::class, Images :: class], version = 1, exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class, ImageDataConverter::class)
abstract class RealEstateDatabase : RoomDatabase() {

   internal abstract fun todoDao(): TodoDao
    internal abstract fun subTodoDao(): SubTodoDao

    //Static Object
    companion object {

        //atomicity (changes visible to all the execution threads)
        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        //singleton
        fun getInstance(context: Context): RealEstateDatabase {

            //only one thread of execution at a time
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateDatabase::class.java,
                        "realestate_db"

                    ).build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }

}