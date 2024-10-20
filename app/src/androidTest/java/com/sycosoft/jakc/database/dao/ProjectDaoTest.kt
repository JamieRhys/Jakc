package com.sycosoft.jakc.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sycosoft.jakc.database.AppDatabase
import com.sycosoft.jakc.database.entities.Project
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
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
    // region Get Project By ID

    @Test
    fun whenGettingProjectById_givenValidId_thenProjectShouldBeReturned() = runBlocking {
        val project = Project(id = 1, name = "Test Project")

        dao.insertProject(project)

        val retrievedProject = dao.getProjectById(project.id())

        assertEquals(project, retrievedProject)
    }

    @Test
    fun whenGettingProjectById_givenMultipleProjects_thenCorrectProjectShouldBeReturned() = runBlocking {
        val project1 = Project(id = 1, name = "Test Project 1")
        val project2 = Project(id = 2, name = "Test Project 2")

        dao.insertProject(project1)
        dao.insertProject(project2)

        val retrievedProject = dao.getProjectById(project1.id())

        assertEquals(project1, retrievedProject)
    }

    @Test
    fun whenGettingProjectById_givenInvalidId_thenNullShouldBeReturned() = runBlocking {
        val project = Project(id = 1, name = "Test Project")

        dao.insertProject(project)

        val retrievedProject = dao.getProjectById(project.id() + 1)

        assertEquals(null, retrievedProject)
    }

    @Test
    fun whenGettingProjectById_givenIdIsZero_thenNullShouldBeReturned() = runBlocking {
        val retrievedProject = dao.getProjectById(0)

        assertEquals(null, retrievedProject)
    }

    @Test
    fun whenGettingProjectById_givenIdIsNegative_thenNullShouldBeReturned() = runBlocking {
        val retrievedProject = dao.getProjectById(-1)

        assertEquals(null, retrievedProject)
    }

    @Test
    fun whenGettingProjectById_givenMaxLongId_thenNullShouldBeReturned() = runBlocking {
        val retrievedProject = dao.getProjectById(Long.MAX_VALUE)

        assertEquals(null, retrievedProject)
    }

    // endregion
    // region Insert Project

    @Test
    fun whenInsertingProject_givenValidProject_thenIdShouldBeReturned() = runBlocking {
        val project = Project(id = 1, name = "Test Project")

        dao.insertProject(project)

        val retrievedProject = dao.getProjectById(project.id())

        assertEquals(project, retrievedProject)
    }

    @Test
    fun whenInsertingProject_givenProjectWithoutId_thenIdShouldBeReturned() = runBlocking {
        val id = dao.insertProject(Project(name = "Test Project"))

        val project = dao.getProjectById(1)

        assertEquals(1, id)
        assertEquals("Test Project", project?.name())
    }

    @Test
    fun whenInsertingProject_givenMultipleIdLessProjects_thenIdsShouldBeReturned() = runBlocking {
        val id1 = dao.insertProject(Project(name = "Test Project 1"))
        val id2 = dao.insertProject(Project(name = "Test Project 2"))

        assertTrue(id2 > id1)
    }

    @Test
    fun whenInsertingProject_givenDuplicateId_thenExceptionShouldBeThrown() = runTest {
        val project1 = Project(id = 1, name = "Test Project 1")
        val project2 = Project(id = 1, name = "Test Project 2")

        dao.insertProject(project1)

        assertThrows(SQLiteConstraintException::class.java) {
            runBlocking {
                dao.insertProject(project2) // Should throw the exception.
            }

        }
    }

    // endregion
    // region Does Project Exist Tests



    // endregion

// endregion
}