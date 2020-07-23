package com.example.ebookshop.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.ebookshop.model.Book;
import com.example.ebookshop.model.BookDAO;
import com.example.ebookshop.model.BookDataBase;
import com.example.ebookshop.model.Category;
import com.example.ebookshop.model.CategoryDAO;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EBookShopRepository {

    //fields
    private CategoryDAO mCategoryDAO;
    private BookDAO mBookDAO;
    private LiveData<List<Category>> mCategories;
    private LiveData<List<Book>> mBooks;
    private LiveData<List<Book>> mAllBooks;

    //constructor
    public EBookShopRepository(Application application) {
        BookDataBase bookDataBase = BookDataBase.getInstance(application);
        mCategoryDAO = bookDataBase.mCategoryDAO();
        mBookDAO = bookDataBase.mBookDAO();
    }

    //getters
    public LiveData<List<Category>> getCategories() {
        return mCategoryDAO.getAllCategories();
    }

    public LiveData<List<Book>> getBooks(int categoryID) {
        return mBookDAO.getBooks(categoryID);
    }

    public LiveData<List<Book>> getAllBooks() {
        return mBookDAO.getAllBooks();
    }

    //insert Methods
    public void insertCategory(final Category category) {
       //new InsertCategoryAsyncTask(mCategoryDAO).execute(category);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.insert(category);
            }
        });
    }

    public void insertBook(final Book book) {
        //new InsertBookAsyncTask(mBookDAO).execute(book);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mBookDAO.insert(book);
            }
        });
    }

    //Delete Methods
    public void deleteCategory(final Category category) {
        //new DeleteCategoryAsyncTask(mCategoryDAO).execute(category);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.delete(category);
            }
        });
    }

    public void deleteBook(final Book book) {
        //new DeleteBookAsyncTask(mBookDAO).execute(book);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mBookDAO.delete(book);
            }
        });
    }

    //update Methods
    public void updateCategory(final Category category) {
        //new UpdateCategoryAsyncTask(mCategoryDAO).execute(category);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mCategoryDAO.update(category);
            }
        });
    }

    public void updateBook(final Book book) {
        //new UpdateBookAsyncTask(mBookDAO).execute(book);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mBookDAO.update(book);
            }
        });
    }

    //insert AsyncTask
    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDAO mCategoryDAO;

        public InsertCategoryAsyncTask(CategoryDAO categoryDAO) {
            mCategoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoryDAO.insert(categories[0]);
            return null;
        }
    }

    private static class InsertBookAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDAO mBookDAO;

        public InsertBookAsyncTask(BookDAO bookDAO) {
            mBookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            mBookDAO.insert(books[0]);
            return null;
        }
    }

    //delete AsyncTask
    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDAO mCategoryDAO;

        public DeleteCategoryAsyncTask(CategoryDAO categoryDAO) {
            mCategoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoryDAO.delete(categories[0]);
            return null;
        }
    }

    private static class DeleteBookAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDAO mBookDAO;

        public DeleteBookAsyncTask(BookDAO bookDAO) {
            mBookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            mBookDAO.delete(books[0]);
            return null;
        }
    }

    //update AsyncTask
    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDAO mCategoryDAO;

        public UpdateCategoryAsyncTask(CategoryDAO categoryDAO) {
            mCategoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoryDAO.update(categories[0]);
            return null;
        }
    }

    private static class UpdateBookAsyncTask extends AsyncTask<Book, Void, Void> {

        private BookDAO mBookDAO;

        public UpdateBookAsyncTask(BookDAO bookDAO) {
            mBookDAO = bookDAO;
        }

        @Override
        protected Void doInBackground(Book... books) {
            mBookDAO.update(books[0]);
            return null;
        }
    }
}
