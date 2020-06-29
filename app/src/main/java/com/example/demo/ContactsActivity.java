package com.example.demo;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.adapter.ContactListAdapter;
import com.example.demo.model.ContactDataModel;
import com.example.demo.utils.MyDividerItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ContactsActivity extends AppCompatActivity {
    private Context mContext;
    private String TAG = ContactsActivity.class.getSimpleName();
    ArrayList<ContactDataModel> contactDataModels = new ArrayList<>();
    private ArrayList<ContactDataModel> filterList = new ArrayList<>();

    private RecyclerView rvData;
    private Toolbar toolbar;
    Cursor phones;
    private ContactListAdapter contactListAdapter;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private SearchView searchView;
    private TextView tvCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mContext = ContactsActivity.this;

        init();
        applyInit();
    }

    private void applyInit() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.contacts_toolbar_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        showContacts();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rvData = findViewById(R.id.rvData);
        tvCount = findViewById(R.id.tvCount);
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            phones = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            LoadContact loadContact = new LoadContact();
            loadContact.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class LoadContact extends AsyncTask<Void, Void, Void> implements ContactListAdapter.ItemClick {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {

                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    ContactDataModel selectUser = new ContactDataModel();
                    selectUser.name = name;
                    selectUser.phone = phoneNumber;
                    selectUser.color = getRandomMaterialColor("400");
                    contactDataModels.add(selectUser);


                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // sortContacts();
            int count = contactDataModels.size();
            ArrayList<ContactDataModel> removed = new ArrayList<>();
            ArrayList<ContactDataModel> contacts = new ArrayList<>();
            for (int i = 0; i < contactDataModels.size(); i++) {
                ContactDataModel contactDataModel = contactDataModels.get(i);

                if (contactDataModel.name.matches("\\d+(?:\\.\\d+)?") || contactDataModel.name.trim().length() == 0) {
                    removed.add(contactDataModel);
                    Log.d("Removed Contact", new Gson().toJson(contactDataModel));
                } else {
                    contacts.add(contactDataModel);
                }
            }
            contacts.addAll(removed);
            contactDataModels = contacts;

            tvCount.setText(removeDuplicates(contactDataModels).size()+"");
            contactListAdapter = new ContactListAdapter(mContext, removeDuplicates(contactDataModels), this);
            rvData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvData.setItemAnimator(new DefaultItemAnimator());
            rvData.addItemDecoration(new MyDividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            rvData.setAdapter(contactListAdapter);
        }
        public ArrayList<ContactDataModel>  removeDuplicates(ArrayList<ContactDataModel> list){
            Set<ContactDataModel> set = new TreeSet(new Comparator<ContactDataModel>() {

                @Override
                public int compare(ContactDataModel o1, ContactDataModel o2) {
                    if(o1.name.equalsIgnoreCase(o2.name)){
                        return 0;
                    }
                    return 1;
                }
            });
            set.addAll(list);

            final ArrayList newList = new ArrayList(set);
            return newList;
        }
        @Override
        public void onItemClick(int pos) {
            ContactDataModel contactDataModel = contactDataModels.get(pos);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, contactDataModel.phone);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "www.technxt.net");
            startActivity(Intent.createChooser(shareIntent, "Share using"));
        }
    }

    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                contactListAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


}
