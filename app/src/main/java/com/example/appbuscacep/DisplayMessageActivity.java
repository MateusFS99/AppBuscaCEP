package com.example.appbuscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {

    public static final String CEP = "com.example.appbuscacep.MESSAGE";
    JSONArray jarray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        ListView lista = (ListView) findViewById(R.id.lista);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String cidade = message.split(";")[0], estado = message.split(";")[1], comp = message.split(";")[2];
        AcessaWsTask task = new AcessaWsTask();

        try {


            String js = task.execute("https://viacep.com.br/ws/" + estado + "/" + cidade + "/"+ comp +"/json/").get();
            jarray = new JSONArray(js);
            JSONObject el;

            List<String> list = new ArrayList<String>();;
            for (int i = 0; i < jarray.length(); i++) {
                el = new JSONObject(jarray.getString(i));
                list.add(el.getString("cep"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

            lista.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick_List(View view) throws JSONException {
        ListView lista = (ListView) findViewById(R.id.lista);
        int index = lista.getSelectedItemPosition();
        JSONObject job = new JSONObject(jarray.getString(index));
        String aoba = job.getString("cep") +
                "/" + job.getString("logradouro") +
                "/" + job.getString("complemento") +
                "/" + job.getString("bairro") +
                "/" + job.getString("localidade") +
                "/" + job.getString("uf") +
                "/" + job.getString("ddd");

        Intent intent = new Intent(this, DadosCep.class);
        intent.putExtra(CEP, aoba);
        startActivity(intent);

    }

}