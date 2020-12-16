package com.example.pr9

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.Serializable


@Entity
data class OperatorDB (
    @PrimaryKey(autoGenerate = true) val uid:Int = 0,
            @ColumnInfo(name="image") val image: Bitmap?,
            @ColumnInfo(name="name") val name: String?,
            @ColumnInfo(name="type") val type:String?,
            @ColumnInfo(name="area") val area:Int?,
            @ColumnInfo(name="subPrice") val subPrice:Int? ) : Serializable

@Dao
interface OperatorDao {
    @Query ("SELECT * FROM OperatorDB")
    fun getAll():List<OperatorDB>

    @Insert
    fun insert(operatorDB: OperatorDB)

    @Delete
    fun delete(operatorDB: OperatorDB)

    @Update
    fun update(operatorDB: OperatorDB)
}

@Database(entities = [OperatorDB::class],version = 1)
@TypeConverters(BitmapConverter::class)
abstract class OperatorDatabase:RoomDatabase(){
    abstract fun operatorDao(): OperatorDao
}
class BitmapConverter {
    @TypeConverter
    fun bytesToBitmap(data: ByteArray?): Bitmap? {
        return if (data == null) {
            null
        } else BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    @TypeConverter
    fun bitmapToBytes(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) {
            return null
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        bitmap.recycle()
        try {
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteArray
    }
}
