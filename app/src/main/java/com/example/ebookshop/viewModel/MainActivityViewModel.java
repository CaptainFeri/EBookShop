package com.example.ebookshop.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ebookshop.model.Book;
import com.example.ebookshop.model.Category;
import com.example.ebookshop.repository.EBookShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private EBookShopRepository mEBookShopRepository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Book>> mBooksOfTheSelectedCategory;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mEBookShopRepository = new EBookShopRepository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = mEBookShopRepository.getCategories();
        return allCategories;
    }

    public LiveData<List<Book>> getBooksOfTheSelectedCategory(int categoryId) {
        mBooksOfTheSelectedCategory = mEBookShopRepository.getBooks(categoryId);
        return mBooksOfTheSelectedCategory;
    }

    public void addNewBook(Book book) {
        mEBookShopRepository.insertBook(book);
    }

    public void updateBook(Book book) {
        mEBookShopRepository.updateBook(book);
    }

    public void deleteBook(Book book) {
        mEBookShopRepository.deleteBook(book);
    }


}
