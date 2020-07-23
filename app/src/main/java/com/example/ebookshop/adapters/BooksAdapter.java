package com.example.ebookshop.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookshop.R;
import com.example.ebookshop.callback.BooksDiffUtilCallback;
import com.example.ebookshop.databinding.BookListItemBinding;
import com.example.ebookshop.model.Book;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private OnItemClickListener mListener;
    private ArrayList<Book> mBooks = new ArrayList<>();

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookListItemBinding bookListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.book_list_item, parent, false);
        return new BookViewHolder(bookListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentBook = mBooks.get(position);
        holder.mBookListItemBinding.setBook(currentBook);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        private BookListItemBinding mBookListItemBinding;

        public BookViewHolder(@NonNull BookListItemBinding bookListItemBinding) {
            super(bookListItemBinding.getRoot());
            mBookListItemBinding = bookListItemBinding;
            mBookListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = getAdapterPosition();
                    if (mListener != null && currentPosition != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(mBooks.get(currentPosition));
                    }
                }
            });
        }
    }

    //setters
    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setBooks(ArrayList<Book> newBooks) {
//        mBooks = books;
//        notifyDataSetChanged();

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new BooksDiffUtilCallback(mBooks,newBooks),false);
        mBooks = newBooks;
        result.dispatchUpdatesTo(BooksAdapter.this);
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
