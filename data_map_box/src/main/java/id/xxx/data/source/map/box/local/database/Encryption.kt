package id.xxx.data.source.map.box.local.database

import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

object Encryption {
    val passphrase = SupportFactory(
        SQLiteDatabase.getBytes("xxx.base.data".toCharArray())
    )
}