package com.example.myapplication

import android.provider.ContactsContract.Data
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun creatingItem_isCorrect(){
        var item = Item(name = "sample", phone = "sample", balance = 123,active = false)
        assertTrue(item.name == "sample")
    }

    @Test
    fun creatingUser_isCorrect(){
        var user = User(name = "sample", password = "sample")
        assertTrue(user.name == "sample")
    }

    @Test
    fun addingItemToDataManagerList_returnTrue(){
        var item = Item(name = "sample", phone = "sample", balance = 123,active = false)
        DataManager.messages.add(item)
        assertEquals(item,DataManager.messages.last())
    }
}