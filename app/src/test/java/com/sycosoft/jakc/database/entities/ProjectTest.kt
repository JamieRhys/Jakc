package com.sycosoft.jakc.database.entities

import com.sycosoft.jakc.utils.ProjectType
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test
import java.time.LocalDate

class ProjectTest {
// region Project Constructor Tests

    // Tests the constructor of the Project class with default values.
    @Test
    fun project_initialisesWithDefaultProperties() {
        val project = Project(
            id = 1,
            name = "Test Project"
        )

        assertEquals(1, project.id())
        assertEquals("Test Project", project.name())
        assertEquals("", project.description())
        assertEquals(0, project.timeTaken())
        assertEquals(LocalDate.now(), project.dateCreated())
        assertEquals(false, project.isComplete())
        assertEquals(null, project.dateCompleted())
        assertEquals(ProjectType.Knitting, project.type())
    }

    // Tests to make sure that when the constructor is given a blank name, it throws an exception
    @Test(expected = IllegalArgumentException::class)
    fun whenProjectConstructor_givenBlankName_thenExceptionIsThrow() {
        Project(id = 1, name = "")
    }

    // Tests the constructor of the Project class with custom values.
    @Test
    fun project_initialisesWithCustomProperties() {
        val project = Project(
            id = 1,
            name = "Test Project",
            description = "This is a test project.",
            timeTaken = 1000,
            dateCompleted = LocalDate.now(),
            isComplete = true,
            type = ProjectType.Crochet,
        )

        assertEquals(1, project.id())
        assertEquals("Test Project", project.name())
        assertEquals("This is a test project.", project.description())
        assertEquals(1000, project.timeTaken())
        assertEquals(LocalDate.now(), project.dateCreated()) // This is a default value, so it should not be changed.
        assertEquals(true, project.isComplete())
        assertEquals(LocalDate.now(), project.dateCompleted())
        assertEquals(ProjectType.Crochet, project.type())
    }

// endregion
// region Project Property Tests

    @Test
    fun project_name_isCorrectlySetWhenGivenNewValue() {
        val project = Project(id = 1, name = "Test Project")

        project.name("New Name")

        assertEquals("New Name", project.name())
    }

    @Test
    fun project_name_constructorIsCorrectlyHandledWhenGivenBlankString() {
        try {
            val project = Project(id = 1, name = "")
            fail("Expected IllegalArgumentException to be thrown")
        } catch(e: IllegalArgumentException) {
            assertEquals(Project.ExceptionMessages.NAME_CANNOT_BE_BLANK, e.message)
        }
    }

    @Test
    fun project_name_isCorrectlyHandledWhenGivenBlankString() {
        try {
            val project = Project(id = 1, name = "Test Project")
            project.name("")
            fail("Expected an IllegalArgumentException to be raised.")
        } catch(e: IllegalArgumentException) {
            assertEquals(Project.ExceptionMessages.NAME_CANNOT_BE_BLANK, e.message)
        }
    }

    @Test
    fun project_description_isCorrectlySetWhenGivenNewValue() {
        val project = Project(id = 1, name = "Test Project")

        project.description("New Description")

        assertEquals("New Description", project.description())
    }

    @Test
    fun project_timeTaken_isCorrectlySetWhenGivenNewValue() {
        val project = Project(id = 1, name = "Test Project")

        project.timeTaken(1000)

        assertEquals(1000, project.timeTaken())
    }

    @Test
    fun project_dateCompleted_isCorrectlySetWhenGivenNewValue() {
        val project = Project(id = 1, name = "Test Project")

        project.dateCompleted(LocalDate.now())

        assertEquals(LocalDate.now(), project.dateCompleted())
    }

    @Test
    fun project_isComplete_isCorrectlySetWhenGivenNewValue() {
        val project = Project(id = 1, name = "Test Project")

        project.isComplete(true)

        assertEquals(true, project.isComplete())
    }

    @Test
    fun project_type_isCorrectlySetWhenGivenNewValue() {
        val project = Project(id = 1, name = "Test Project")

        project.type(ProjectType.Crochet)

        assertEquals(ProjectType.Crochet, project.type())
    }

// endregion
}