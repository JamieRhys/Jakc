package com.sycosoft.jakc.database.entities

import com.sycosoft.jakc.utils.ProjectType
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.LocalDate

class EntityProjectTest {
    @Test
    fun whenCreatingEntityProject_givenValidData_thenObjectShouldBeCreated() {
        val project = EntityProject(
            id = 1,
            name = "Test Project",
            description = "This is a test project",
            dateCreated = LocalDate.now(),
            timeTaken = 1000,
            dateCompleted = LocalDate.now(),
            isComplete = true,
            type = ProjectType.Crochet,
        )

        assertEquals(1, project.id)
        assertEquals("Test Project", project.name)
        assertEquals("This is a test project", project.description)
        assertEquals(LocalDate.now(), project.dateCreated)
        assertEquals(1000, project.timeTaken)
        assertEquals(LocalDate.now(), project.dateCompleted)
        assertEquals(true, project.isComplete)
        assertEquals(ProjectType.Crochet, project.type)
    }

    @Test
    fun whenCreatingEntityProject_givenDefaultValues_thenObjectShouldBeCreated() {
        val project = EntityProject(name = "Test Project",)

        assertEquals(0, project.id)
        assertEquals("Test Project", project.name)
        assertEquals("", project.description)
        assertEquals(LocalDate.now(), project.dateCreated)
        assertEquals(0, project.timeTaken)
        assertEquals(null, project.dateCompleted)
        assertEquals(false, project.isComplete)
        assertEquals(ProjectType.Knitting, project.type)
    }

    @Test
    fun whenCreatingEntityProject_givenBlankName_thenExceptionShouldBeThrown() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            EntityProject(name = "")
        }

        assertEquals(EntityProject.ExceptionMessages.NAME_CANNOT_BE_BLANK, exception.message)
    }

    @Test
    fun whenCreatingEntityProject_givenNameLongerThanAllowedCharacters_thenExceptionShouldBeThrown() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            EntityProject(name = "a".repeat(EntityProject.NAME_LENGTH + 1))
        }

        assertEquals(EntityProject.ExceptionMessages.NAME_TOO_LONG, exception.message)
    }
}