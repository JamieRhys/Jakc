package com.sycosoft.jakc.database.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sycosoft.jakc.database.entities.EntityProject
import com.sycosoft.jakc.database.entities.Project

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects")
    suspend fun getAllProjects(): List<EntityProject>

    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getProjectById(id: Long): EntityProject?

    @Insert
    suspend fun insertProject(project: EntityProject): Long

    @Update
    suspend fun updateProject(project: EntityProject): Int

    @Query("SELECT EXISTS (SELECT 1 FROM projects WHERE id = :id)")
    suspend fun doesProjectExist(id: Long): Boolean

    @Query("DELETE FROM projects WHERE id = :id")
    suspend fun deleteProject(id: Long): Int
}