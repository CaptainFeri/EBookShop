package com.example.ebookshop.callback;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ebookshop.model.Book;

import java.util.ArrayList;

public class BooksDiffUtilCallback extends DiffUtil.Callback {

    ArrayList<Book> oldBookArrayList;
    ArrayList<Book> newBookArrayList;

    public BooksDiffUtilCallback(ArrayList<Book> oldBookArrayList, ArrayList<Book> newBookArrayList) {
        this.oldBookArrayList = oldBookArrayList;
        this.newBookArrayList = newBookArrayList;
    }

    @Override
    public int getOldListSize() {
        return oldBookArrayList == null ? 0 : oldBookArrayList.size();
    }

    @Override
    public int getNewListSize() {
        return newBookArrayList == null ? 0 : newBookArrayList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBookArrayList.get(oldItemPosition).getBookID()==newBookArrayList.get(newItemPosition).getBookID();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBookArrayList.get(oldItemPosition).equals(newBookArrayList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
