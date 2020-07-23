package com.example.ebookshop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.ebookshop.R;
import com.example.ebookshop.databinding.ActivityAddAndEditBinding;
import com.example.ebookshop.model.Book;

public class AddAndEditActivity extends AppCompatActivity {

    public static String BOOK_NAME = "bookName";
    public static String BOOK_ID = "bookId";
    public static String UNIT_PRICE = "unitPrice";

    private Book mBook;

    private ActivityAddAndEditBinding mBinding;

    private AddAndEditActivityClickHandler mAddAndEditActivityClickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);

        mBook = new Book();
        Intent intent = getIntent();
        if (intent.hasExtra(BOOK_ID)) {
            setTitle("Edit Book");
            mBook.setBookName(intent.getStringExtra(BOOK_NAME));
            mBook.setUnitPrice(intent.getStringExtra(UNIT_PRICE));
            mBook.setBookID(intent.getIntExtra(BOOK_ID,0));
        } else {
            setTitle("Add New Book");
            mBook.setBookName("No name");
            mBook.setUnitPrice("$ -");
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_and_edit);
        mBinding.setBook(mBook);

        mAddAndEditActivityClickHandler = new AddAndEditActivityClickHandler(this);
        mBinding.setAddEndEditActivityClickHandler(mAddAndEditActivityClickHandler);

    }

    public class AddAndEditActivityClickHandler {
        Context mContext;

        public AddAndEditActivityClickHandler(Context context) {
            mContext = context;
        }

        public void submitButtonClicked(View view) {


            if (mBinding.editTextTextBookName.getText().toString().equals("")) {
                Toast.makeText(mContext, "the book name cant empty !!", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent();
                intent.putExtra(BOOK_NAME, mBinding.getBook().getBookName());
                intent.putExtra(UNIT_PRICE, mBinding.getBook().getUnitPrice());
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}