package com.example.mvvm.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mvvm.model.Person;

/**
 * Room 数据库类。
 * 这是一个抽象类，继承自 RoomDatabase。
 * @Database 注解用于标识这是一个 Room 数据库。
 *   - entities: 包含数据库中的所有实体类（表）。
 *   - version: 数据库的版本号。当数据库结构发生变化时，需要增加版本号并提供迁移策略。
 *   - exportSchema: 是否将数据库的 schema 导出到文件中。建议设置为 false，除非需要进行版本控制或检查。
 */
@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class PersonDatabase extends RoomDatabase {

    // 数据库的名称
    private static final String DATABASE_NAME = "person_db";

    // 数据库实例的单例模式，确保只有一个数据库实例
    private static PersonDatabase databaseInstance;

    /**
     * 获取数据库实例的静态方法 (单例模式)。
     * 确保在整个应用程序生命周期中只有一个数据库实例，避免资源浪费和潜在问题。
     * synchronized 关键字确保线程安全。
     * @param context 应用程序上下文，用于构建数据库。
     * @return PersonDatabase 的单例实例。
     */
    public static synchronized PersonDatabase getInstance(Context context) {
        // 如果数据库实例为空，则创建新的实例
        if (databaseInstance == null) {
            databaseInstance = Room
                    // databaseBuilder 用于创建 RoomDatabase 实例。
                    // context.getApplicationContext(): 使用应用程序上下文，避免内存泄漏。
                    // PersonDatabase.class: 数据库类。
                    // DATABASE_NAME: 数据库文件的名称。
                    .databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DATABASE_NAME)
                    // .fallbackToDestructiveMigration() // 可选：当数据库版本升级时，如果缺少迁移策略，则销毁并重建数据库。
                    .build(); // 构建数据库实例
        }
        return databaseInstance;
    }

    /**
     * 抽象方法，用于获取 PersonDao 的实例。
     * Room 会自动实现这个方法，并返回 PersonDao 的具体实现。
     * @return PersonDao 的实例，用于执行数据库操作。
     */
    public abstract PersonDao personDao();
}
