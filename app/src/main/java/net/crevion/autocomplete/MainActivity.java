package net.crevion.autocomplete;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String currentMessage;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);

        PeopleAdapter peopleAdapter = new PeopleAdapter(retrievePeople(), new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String message) {
                editText.setText("");
                editText.append(currentMessage.substring(0, currentMessage.lastIndexOf('!')) + message);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(peopleAdapter);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 2) {
                    if(s.toString().contains("!")) {
                        ((PeopleAdapter) recyclerView.getAdapter()).getFilter().filter(s.toString().substring(s.toString().lastIndexOf('!') + 1));
                        currentMessage = s.toString();
                    }
                }
            }
        };
        editText.addTextChangedListener(textWatcher);

    }

    private List<People> retrievePeople() {
        List<People> list = new ArrayList<>();
        list.add(new People("James", 23, 1));
        list.add(new People("Bobby", 25, 2));
        list.add(new People("Jono", 12, 3));
        list.add(new People("John", 17, 4));
        list.add(new People("Brian", 26, 5));
        return list;
    }
}