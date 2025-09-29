package com.todaylab.photodiary.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todaylab.photodiary.local.model.DiaryLocal
import com.todaylab.photodiary.local.model.LuckyResultLocal
import com.todaylab.photodiary.local.model.WeatherLocal
import com.todaylab.photodiary.local.room.dao.DiaryDao
import com.todaylab.photodiary.local.room.dao.LuckyResultDao
import com.todaylab.photodiary.local.room.dao.WeatherDao

@Database(
    entities = [WeatherLocal::class, LuckyResultLocal::class, DiaryLocal::class],
    version = RoomConstant.ROOM_VERSION
)
@TypeConverters(
    DtoConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun luckyDao(): LuckyResultDao
    abstract fun diaryDao(): DiaryDao
}

/**
 * e: [ksp] InjectProcessingStep was unable to process 'HomeViewModel(com.todaylab.photodiary.domain.usecase.GetWeatherUseCase,error.NonExistentClass,com.todaylab.photodiary.domain.usecase.SaveBirthDateUseCase,com.todaylab.photodiary.domain.usecase.GetBirthDateUseCase)' because 'error.NonExistentClass' could not be resolved.
 *
 * Dependency trace:
 *     => element (CLASS): com.todaylab.photodiary.presentation.home.HomeViewModel
 *     => element (CONSTRUCTOR): HomeViewModel(com.todaylab.photodiary.domain.usecase.GetWeatherUseCase,error.NonExistentClass,com.todaylab.photodiary.domain.usecase.SaveBirthDateUseCase,com.todaylab.photodiary.domain.usecase.GetBirthDateUseCase)
 *     => type (EXECUTABLE constructor): (com.todaylab.photodiary.domain.usecase.GetWeatherUseCase,error.NonExistentClass,com.todaylab.photodiary.domain.usecase.SaveBirthDateUseCase,com.todaylab.photodiary.domain.usecase.GetBirthDateUseCase)void
 *     => type (ERROR parameter type): error.NonExistentClass
 *
 * If type 'error.NonExistentClass' is a generated type, check above for compilation errors that may have prevented the type from being generated. Otherwise, ensure that type 'error.NonExistentClass' is on your classpath.
 * e: Error occurred in KSP, check log for detail
 *
 * > Task :app:kspDebugKotlin FAILED
 * [ksp] C:/Users/User/AndroidStudioProjects/clean-arch-template/app/src/main/java/com/todaylab/photodiary/local/room/AppDatabase.kt:17: Schema export directory was not provided to the annotation processor so Room cannot export the schema. You can either provide `room.schemaLocation` annotation processor argument by applying the Room Gradle plugin (id 'androidx.room') OR set exportSchema to false.
 *
 */