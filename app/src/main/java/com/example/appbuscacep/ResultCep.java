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
    private List<Endereco> list = new ArrayList<Endereco>();
    public static final String EXTRA_MESSAGE = "com.example.appbuscacep.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_cep);

        lista = (ListView)findViewById(R.id.lista);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String cidade = message.split(";")[0], estado = message.split(";")[1];
        String comp = "";

        try {
            comp = message.split(";")[2];
        } catch (Exception e) {
            System.out.println(e);
        }

        AcessaWsTask task = new AcessaWsTask();

        try {

            String js = task.execute("https://viacep.com.br/ws/" + estado + "/" + cidade + "/" + comp + "/json/").get();
            JSONObject el;
            JSONArray jarray = new JSONArray(js);

            for (int i = 0; i < jarray.length(); i++) {

                el = new JSONObject(jarray.getString(i));
                list.add(new Endereco(el.getString("cep"),el.getString("logradouro"),el.getString("complemento"),
                        el.getString("bairro"),el.getString("localidade"),el.getString("uf"),el.getString("ddd")));
            }

            EnderecoAdapter adapter = new EnderecoAdapter(this, R.layout.item_lista, list);

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

        Endereco end = list.get(index);
        String aoba = end.getCep() + "/" + end.getLogradouro() + "/" + end.getComplemento() + "/" + end.getBairro() +
                "/" + end.getLocalidade() + "/" + end.getUf() + "/" + end.getDdd();

        Intent intent = new Intent(this, DadosCep.class);
        intent.putExtra(EXTRA_MESSAGE, aoba);
        startActivity(intent);
    }
}