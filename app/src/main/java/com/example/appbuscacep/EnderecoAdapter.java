package com.example.appbuscacep;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EnderecoAdapter extends ArrayAdapter<Endereco> {

    private int layout;

    public EnderecoAdapter(Context context, int resource, List<Endereco> list) {

        super(context, resource, list);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout,parent,false);
        }

        TextView tvcep = convertView.findViewById(R.id.tvCep);
        TextView tvrua = convertView.findViewById(R.id.tvRua);
        Endereco end = this.getItem(position);

        tvcep.setText(end.getCep());
        tvrua.setText(end.getLogradouro());

        if (position % 2 == 0)
            convertView.setBackgroundColor(Color.parseColor("#34ACBC"));
        else
            convertView.setBackgroundColor(Color.parseColor("#85C3CF"));

        return convertView;
    }
}
