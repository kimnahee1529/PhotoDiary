package com.todaylab.photodiary.local.impl

import android.content.Context
import com.todaylab.photodiary.R
import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.core.UserPreferenceManager
import com.todaylab.photodiary.data.local.MagicBookLocalDataSource
import com.todaylab.photodiary.local.model.Solution
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject


class MagicBookLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreferenceManager: UserPreferenceManager,
) : MagicBookLocalDataSource {

    override suspend fun getAll(): DataResource<List<String>> {
        TODO("Not yet implemented")
//        try {
//            val solution = userPreferenceManager.readMagicBookSolution()
//            return
//        } catch (e: Exception) {
//            return DataResource.error(Throwable("local layer error - ${e.message}"))
//        }
    }

    suspend fun getSolution(): DataResource<String> {
        TODO("Not yet implemented")
//        val solution = userPreferenceManager.readMagicBookSolution()
//        return solution
    }

    override fun loadSolutions(): List<Solution> {
        val inputStream = context.resources.openRawResource(R.raw.magic_book_solutions)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }

    override suspend fun getById(id: String): DataResource<String> {
        TODO("Not yet implemented")
    }

    override suspend fun save(item: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: String): Boolean {
        TODO("Not yet implemented")
    }

}
