package com.todaylab.photodiary.data.local

import com.todaylab.photodiary.local.model.Solution

interface MagicBookLocalDataSource {
    suspend fun loadSolutions(): List<Solution>
}
