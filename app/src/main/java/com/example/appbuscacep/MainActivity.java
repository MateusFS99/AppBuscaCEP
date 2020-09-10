package com.example.appbuscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView btBuscaCep;
    private EditText etCidade, etComp;
    private Spinner spEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btBuscaCep = findViewById(R.id.btBuscaCep);
        etCidade = findViewById(R.id.etCidade);
        etComp = findViewById(R.id.etCompl);
        spEstado = findViewById(R.id.spEstado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);

        btBuscaCep.setOnClickListener(e->{acessaApi();});
    }

    private void acessaApi() {

        String cidade = etCidade.getText().toString(), estado = spEstado.getSelectedItem().toString(), comp = etComp.getText().toString();
        AcessaWsTask task = new AcessaWsTask();

        try {

            String json = task.execute("https://viacep.com.br/ws/" + estado + "/" + cidade + "/" + comp + "/json/").get();
        } catch (Exception e) {
            Log.e("Erro na conexão!",e.toString());
        }
    }
}