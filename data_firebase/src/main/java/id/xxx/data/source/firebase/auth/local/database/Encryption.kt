package id.xxx.data.source.firebase.auth.local.database

import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

object Encryption {
    val passphrase = SupportFactory(
        SQLiteDatabase.getBytes("xxx.base.data".toCharArray())
    )
}