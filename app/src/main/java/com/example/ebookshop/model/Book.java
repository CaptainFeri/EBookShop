package com.example.ebookshop.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "books_table", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id",
        onDelete = CASCADE))
public class Book extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int mBookID;

    @ColumnInfo(name = "book_name")
    private String mBookName;

    @ColumnInfo(name = "unit_price")
    private String mUnitPrice;

    @ColumnInfo(name = "category_id")
    private int mCategoryID;

    public Book() {
    }

    public Book(int bookID, String bookName, String unitPrice, int categoryID) {
        mBookID = bookID;
        mBookName = bookName;
        mUnitPrice = unitPrice;
        mCategoryID = categoryID;
    }

    @Bindable
    public int getBookID() {
        return mBookID;
    }

    public void setBookID(int bookID) {
        mBookID = bookID;
        notifyPropertyChanged(BR.bookID);
    }

    @Bindable
    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String bookName) {
        mBookName = bookName;
        notifyPropertyChanged(BR.bookName);
    }

    @Bindable
    public String getUnitPrice() {
        return mUnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        mUnitPrice = unitPrice;
        notifyPropertyChanged(BR.unitPrice);
    }

    @Bindable
    public int getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(int categoryID) {
        mCategoryID = categoryID;
        notifyPropertyChanged(BR.categoryID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getBookID() == book.getBookID() &&
                getCategoryID() == book.getCategoryID() &&
                getBookName().equals(book.getBookName()) &&
                getUnitPrice().equals(book.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookID(), getBookName(), getUnitPrice(), getCategoryID());
    }
}
