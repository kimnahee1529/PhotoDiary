package com.todaylab.photodiary.data.local

import com.todaylab.photodiary.data.BaseCRUDDataSource
import com.todaylab.photodiary.local.model.Solution

interface MagicBookLocalDataSource : BaseCRUDDataSource<String> {
    fun loadSolutions(): List<Solution>
}
