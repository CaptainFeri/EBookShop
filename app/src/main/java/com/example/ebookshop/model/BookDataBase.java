package com.example.ebookshop.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Book.class, Category.class}, version = 1)
public abstract class BookDataBase extends RoomDatabase {

    public abstract BookDAO mBookDAO();

    public abstract CategoryDAO mCategoryDAO();

    private static BookDataBase instance;

    public static synchronized BookDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), BookDataBase.class, "book_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(sCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback sCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private BookDAO mBookDAO;

        private CategoryDAO mCategoryDAO;

        public InitialDataAsyncTask(BookDataBase dataBase) {
            mBookDAO = dataBase.mBookDAO();
            mCategoryDAO = dataBase.mCategoryDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            Category category2 = new Category();
            category2.setCategoryName("Novel");
            category2.setCategoryDescription("Novel Books description");
            mCategoryDAO.insert(category2);

            Category category1 = new Category();
            category1.setCategoryName("Text Books");
            category1.setCategoryDescription("Text Books Description");
            mCategoryDAO.insert(category1);

            Category category3 = new Category();
            category3.setCategoryName("Programming");
            category3.setCategoryDescription("Programming Books description");
            mCategoryDAO.insert(category3);


            for (int i = 0; i < 20; i++) {
                Book book = new Book();
                book.setBookName("book " + (i + 1));
                int x = i % 5;
                book.setUnitPrice("$ " + (i * x));
                book.setCategoryID((i % 3) + 1);
                mBookDAO.insert(book);
            }

            return null;
        }
    }

}
