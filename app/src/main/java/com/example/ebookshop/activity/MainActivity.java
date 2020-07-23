package com.example.ebookshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebookshop.R;
import com.example.ebookshop.adapters.BooksAdapter;
import com.example.ebookshop.databinding.ActivityMainBinding;
import com.example.ebookshop.model.Book;
import com.example.ebookshop.model.Category;
import com.example.ebookshop.viewModel.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_BOOK_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_REQUEST_CODE = 2;

    private MainActivityViewModel mMainActivityViewModel;

    private MainActivityClickHandlers mHandlers;

    private ActivityMainBinding mActivityMainBinding;

    private ArrayList<Category> mCategoryArrayList;

    private ArrayList<Book> mBookArrayList;

    private Category selectedCategory;

    private RecyclerView mBooksRecyclerView;

    private BooksAdapter mBooksAdapter;

    private int selectedBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mHandlers = new MainActivityClickHandlers();
        mActivityMainBinding.setClickHandlers(mHandlers);

        mMainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                mCategoryArrayList = (ArrayList<Category>) categories;
                for (Category c : categories) {
                    Log.i("MyTAG", c.getCategoryName());
                }
                showOnSpinner();
            }
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_item, mCategoryArrayList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        mActivityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    private void loadBooksArrayList(int categoryID) {
        mMainActivityViewModel.getBooksOfTheSelectedCategory(categoryID).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                mBookArrayList = (ArrayList<Book>) books;
                loadRecyclerView();
            }
        });
    }

    private void loadRecyclerView(){
        mBooksRecyclerView = mActivityMainBinding.secondaryLayout.rvBooks;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mBooksRecyclerView.setLayoutManager(layoutManager);
        mBooksRecyclerView.setHasFixedSize(true);

        mBooksAdapter = new BooksAdapter();
        mBooksAdapter.setBooks(mBookArrayList);

        mBooksRecyclerView.setAdapter(mBooksAdapter);

        mBooksAdapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                selectedBookId = book.getBookID();
                Intent intent = new Intent(MainActivity.this,AddAndEditActivity.class);
                intent.putExtra(AddAndEditActivity.BOOK_ID,selectedBookId);
                intent.putExtra(AddAndEditActivity.BOOK_NAME,book.getBookName());
                intent.putExtra(AddAndEditActivity.UNIT_PRICE,book.getUnitPrice());
                startActivityForResult(intent,EDIT_BOOK_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Book book = mBookArrayList.get(viewHolder.getAdapterPosition());
                mMainActivityViewModel.deleteBook(book);
            }
        }).attachToRecyclerView(mBooksRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_BOOK_REQUEST_CODE && resultCode==RESULT_OK){

            Book book = new Book();
            book.setCategoryID(selectedCategory.getId());
            book.setBookName(data.getStringExtra(AddAndEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            mMainActivityViewModel.addNewBook(book);

        }else if (requestCode==EDIT_BOOK_REQUEST_CODE && resultCode==RESULT_OK){

            Book book = new Book();
            book.setCategoryID(selectedCategory.getId());
            book.setBookName(data.getStringExtra(AddAndEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            book.setBookID(data.getIntExtra(AddAndEditActivity.BOOK_ID,0));

            mMainActivityViewModel.updateBook(book);
        }

    }

    public class MainActivityClickHandlers {

        public void onFABClicked(View view) {
           // Toast.makeText(MainActivity.this, "Fab clicked!", Toast.LENGTH_SHORT).show();
           // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            Intent intent = new Intent(MainActivity.this,AddAndEditActivity.class);
            startActivityForResult(intent,ADD_BOOK_REQUEST_CODE);
        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
            selectedCategory = (Category) parent.getItemAtPosition(pos);

            String message = "ID is " + selectedCategory.getId() + "\nName is " + selectedCategory.getCategoryName() + "\nDes is " + selectedCategory.getCategoryDescription();

            Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT).show();

            loadBooksArrayList(selectedCategory.getId());
        }
    }
}