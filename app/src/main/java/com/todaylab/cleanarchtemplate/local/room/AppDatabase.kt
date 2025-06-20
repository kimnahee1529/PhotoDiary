package com.todaylab.cleanarchtemplate.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao

@Database(
    entities = [WeatherLocal::class],
    version = RoomConstant.ROOM_VERSION
)
@TypeConverters(
    DtoConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

/**
 * e: [ksp] InjectProcessingStep was unable to process 'HomeViewModel(com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase,error.NonExistentClass,com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase,com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase)' because 'error.NonExistentClass' could not be resolved.
 *
 * Dependency trace:
 *     => element (CLASS): com.todaylab.cleanarchtemplate.presentation.home.HomeViewModel
 *     => element (CONSTRUCTOR): HomeViewModel(com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase,error.NonExistentClass,com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase,com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase)
 *     => type (EXECUTABLE constructor): (com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase,error.NonExistentClass,com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase,com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase)void
 *     => type (ERROR parameter type): error.NonExistentClass
 *
 * If type 'error.NonExistentClass' is a generated type, check above for compilation errors that may have prevented the type from being generated. Otherwise, ensure that type 'error.NonExistentClass' is on your classpath.
 * e: Error occurred in KSP, check log for detail
 *
 * > Task :app:kspDebugKotlin FAILED
 * [ksp] C:/Users/User/AndroidStudioProjects/clean-arch-template/app/src/main/java/com/todaylab/cleanarchtemplate/local/room/AppDatabase.kt:17: Schema export directory was not provided to the annotation processor so Room cannot export the schema. You can either provide `room.schemaLocation` annotation processor argument by applying the Room Gradle plugin (id 'androidx.room') OR set exportSchema to false.
 *
 */