package com.testname.sqlitedbex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextWord;
    private EditText editTextDefinition;
    private DictionaryDatabase db;
    private ListView listView;
    private Button buttonAddUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DictionaryDatabase(this);
        editTextWord = findViewById(R.id.editTextWord);
        editTextDefinition = findViewById(R.id.editTextDefinition);
        buttonAddUpdate = findViewById(R.id.buttonAddUpdate);
        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               saveRecord();
           }//end onClick
        });//end buttonAddUpdate

        //get list view and associate it with its data
        //also allow deletion of records when the user LONG presses
        listView= findViewById(R.id.listView);
        listView.setOnItemClickListener(new
            AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int
                     position, long id) {
                 Toast.makeText(MainActivity.this, db.getDefinition(id),
                         Toast.LENGTH_SHORT).show();
                }
            }
        );//end setOnItemClickListener for the listView

        //for deletion, we long click / long press the listView
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View
                     view, int position, long id) {
                 Toast.makeText(MainActivity.this,
                         "Records deleted = " + db.deleteRecord(id),
                         Toast.LENGTH_SHORT).show();
                 updateWordList();
                 return true;
             }
         });//end of the LONG click listener
        updateWordList();
    }//end onCreate

    private void saveRecord() {
        db.saveRecord(editTextWord.getText().toString(),
                editTextDefinition.getText().toString());
        editTextWord.setText("");  //reset word
        editTextDefinition.setText("");  //reset definition
        updateWordList();
    }//end saveRecord

    private void updateWordList() {
        SimpleCursorAdapter simpleCursorAdapter = new
                SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                db.getWordList(),
                new String[]{"word"},
                new int[]{android.R.id.text1},
                0);
        listView.setAdapter(simpleCursorAdapter);
    }//end updateWordList
}
