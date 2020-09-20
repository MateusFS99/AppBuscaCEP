package com.example.appbuscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultCep extends AppCompatActivity {

    private ListView lista;
    public static final String EXTRA_MESSAGE = "com.example.appbuscacep.MESSAGE";
    private JSONArray jarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cep);

        lista = (ListView) findViewById(R.id.lista);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String cidade = message.split(";")[0], estado = message.split(";")[1];
        String comp = "";
        try {
            comp = message.split(";")[2];
        }catch (Exception e)
        {
            System.out.println(e);
        }

        AcessaWsTask task = new AcessaWsTask();

        try {

            String js = task.execute("https://viacep.com.br/ws/" + estado + "/" + cidade + "/" + comp + "/json/").get();
            jarray = new JSONArray(js);
            JSONObject el;
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < jarray.length(); i++) {

                el = new JSONObject(jarray.getString(i));
                list.add(el.getString("cep"));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    onClick_List(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick_List(int index) {

        JSONObject job = null;

        try {

            job = new JSONObject(jarray.getString(index));
            String aoba = job.getString("cep") +
                    "/" + job.getString("logradouro") +
                    "/" + job.getString("complemento") +
                    "/" + job.getString("bairro") +
                    "/" + job.getString("localidade") +
                    "/" + job.getString("uf") +
                    "/" + job.getString("ddd");
            Intent intent = new Intent(this, DadosCep.class);
            intent.putExtra(EXTRA_MESSAGE, aoba);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}