package com.sycosoft.jakc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sycosoft.jakc.database.entities.EntityPart
import com.sycosoft.jakc.database.entities.Part

@Dao
interface PartDao {

    @Query("SELECT * FROM parts WHERE owning_project_id = :projectId")
    suspend fun getPartsByProjectId(projectId: Long): List<EntityPart>

    @Insert
    suspend fun insertPart(part: EntityPart): Long

    @Update
    suspend fun updatePart(part: EntityPart): Int

    @Query("SELECT EXISTS (SELECT 1 FROM parts WHERE id = :id)")
    suspend fun doesPartExist(id: Long): Boolean

    @Query("DELETE FROM parts WHERE id = :id")
    suspend fun deletePart(id: Long): Int
}