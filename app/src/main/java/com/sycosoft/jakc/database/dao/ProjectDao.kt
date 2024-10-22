package com.sycosoft.jakc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sycosoft.jakc.database.entities.Project

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<Project>

    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getProjectById(id: Long): Project?

    @Insert
    suspend fun insertProject(project: Project): Long

    @Update
    suspend fun updateProject(project: Project): Int

    @Query("SELECT EXISTS (SELECT 1 FROM projects WHERE id = :id)")
    suspend fun doesProjectExist(id: Long): Boolean

    @Query("DELETE FROM projects WHERE id = :id")
    suspend fun deleteProject(id: Long): Int
}