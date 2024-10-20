package com.sycosoft.jakc.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sycosoft.jakc.database.AppDatabase
import com.sycosoft.jakc.database.entities.Project
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectDaoTest {
// region Test Setup
    private lateinit var db: AppDatabase
    private lateinit var dao: ProjectDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()

        dao = db.projectDao
    }

    @After
    fun teardown() {
        db.close()
    }
// endregion
// region Query Tests

    // region Get All Projects

    @Test
    fun whenGettingAllProjects_givenNoProjectsInDatabase_thenEmptyListShouldBeReturned() = runBlocking {
        val projects = dao.getAllProjects()

        assertEquals(0, projects.size)
    }

    @Test
    fun whenGettingAllProjects_thenAllProjectsShouldBeReturned() = runBlocking {
        val project1 = Project(id = 1, name = "Test Project 1")
        val project2 = Project(id = 2, name = "Test Project 2")

        dao.insertProject(project1)
        dao.insertProject(project2)

        val projects = dao.getAllProjects()

        assertEquals(2, projects.size)
        assertEquals(project1, projects[0])
        assertEquals(project2, projects[1])
    }

    @Test
    fun whenGettingAllProjects_givenLargeNumberOfProjectsInDatabase_thenAllProjectsShouldBeReturned() = runBlocking {
        val projects = List(1000) { Project(id = (it.toLong() + 1), name = "Project $it") }

        projects.forEach { dao.insertProject(it) }

        val retrievedProjects = dao.getAllProjects()

        assertEquals(1000, retrievedProjects.size)
        assertEquals(projects[999], retrievedProjects[999])
    }

    @Test
    fun whenGettingAllProjects_givenProjectsWithSpecialCharacterNames_thenAllProjectsShouldBeReturned() = runBlocking {
        val project = Project(id = 1, name = "Project @#$%^&*()_+")

        dao.insertProject(project)

        val projects = dao.getAllProjects()

        assertEquals(1, projects.size)
        assertEquals("Project @#$%^&*()_+", projects[0].name())
    }

    // endregion

// endregion
}