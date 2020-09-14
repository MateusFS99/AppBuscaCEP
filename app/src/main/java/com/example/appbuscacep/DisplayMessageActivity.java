package com.example.appbuscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        ListView lista = (ListView) findViewById(R.id.lista);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String cidade = message.split(" ")[0], estado = message.split(" ")[1], comp = message.split(" ")[2];
        AcessaWsTask task = new AcessaWsTask();

        try {

            List<String> list = null;
            String json = task.execute("https://viacep.com.br/ws/" + estado + "/" + cidade + "/json/").get();
            JSONObject jobj = new JSONObject(json);
            JSONArray array = jobj.getJSONArray("GetCitiesResult");

            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

            lista.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}