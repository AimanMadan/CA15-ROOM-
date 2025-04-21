package com.example.room1

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class NewsDatabaseTest{
    private lateinit var db:NewsDatabase
    private lateinit var articleDao: ArticleDao
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        articleDao = db.articleDao()
        initData()
    }

    private fun initData() {
        val articleNumber = 5
        for (i in 0 until articleNumber) {
            val article = Article(title = "title$i",
                content = "content$i",
                time = System.currentTimeMillis()
            )
            articleDao.insertArticle(article)
        }
        val article = articleDao.loadAllArticles()
        assertEquals(5, article.size)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun updateArticle() {
        val article = articleDao.loadAllArticles()[0]
        articleDao.updateArticle(article.copy(title = "new title"))
        assertEquals("new title", articleDao.loadAllArticles()[0].title)
    }
}
