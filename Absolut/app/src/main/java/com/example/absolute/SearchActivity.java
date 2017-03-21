package com.example.absolute;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class SearchActivity extends AppCompatActivity {
    protected static final String EXTRA_SEARCH = "search";
    private ListView lvRunSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*lvRunSearch = (ListView)findViewById(R.id.lvRunSearch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.searchItems,
                android.R.layout.simple_list_item_1);
        lvRunSearch.setAdapter(adapter);

        lvRunSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.putExtra(EXTRA_SEARCH, MainActivity.SearchItem.RUN_TASK);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    case 2:
                        intent.putExtra(EXTRA_SEARCH, MainActivity.SearchItem.SERIES);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                }
            }
        });*/
    }
}
