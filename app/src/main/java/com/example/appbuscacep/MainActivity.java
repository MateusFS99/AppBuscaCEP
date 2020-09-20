package com.example.appbuscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView btBuscaCep;
    private EditText etCidade, etComp;
    private Spinner spEstado;
    public static final String EXTRA_MESSAGE = "com.example.appbuscacep.MESSAGE";

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
    }

    public void onClick_cid(View view)
    {
        if(etCidade.getText().toString().equals("Cidade"))
            etCidade.setText("");
    }

    public void onClick_comp(View view)
    {
        if(etComp.getText().toString().equals("Complemento"))
            etComp.setText("");
    }

    public void sendMessage(View view) {

        Intent intent = new Intent(this, ResultCep.class);
        String cidade = etCidade.getText().toString(), estado = spEstado.getSelectedItem().toString(), comp = etComp.getText().toString();
        String junta = cidade + ";" + estado + ";" + comp;

        intent.putExtra(EXTRA_MESSAGE, junta);
        startActivity(intent);
    }
}