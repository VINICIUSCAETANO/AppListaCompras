package com.example.vinicius.applistadecompras;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtNome;
    private EditText edtQuant;
    private Button btLimpar;
    private Button btGravar;
    private TextView txtCompras;
    private static final String nomeArq = "compras.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtQuant = (EditText) findViewById(R.id.edtQuant);
        btLimpar = (Button) findViewById(R.id.btLimpar);
        btGravar = (Button) findViewById(R.id.btGravar);
        txtCompras = (TextView) findViewById(R.id.txtCompras);

        btGravar.setOnClickListener(this);
        btLimpar.setOnClickListener(this);

        recuperaCompras();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btGravar) {
            String nome = edtNome.getText().toString();
            String quant = edtQuant.getText().toString();

            //se algum campo está vazio
            if(nome.trim().isEmpty() || quant.trim().isEmpty()) {
                Toast.makeText(this, "Informe corretamente os dados", Toast.LENGTH_LONG).show();
                return;
            }

            String compras = txtCompras.getText().toString() +
                    nome + " - " + quant + "\n";
            txtCompras.setText(compras);

            edtNome.setText("");
            edtQuant.setText("");
            edtNome.requestFocus();

            gravaCompras();
            //se o botao chamador é o limar
        } else {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Limpar Lista de Compras");
            builder.setMessage("Confirma Exclusao da Lista?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    txtCompras.setText("");
                    gravaCompras();
                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alert = builder.show();
        }
    }

    private void recuperaCompras() {
        File file = new File(nomeArq);

        try {
            FileInputStream fs = openFileInput(nomeArq);
            Scanner entrada = new Scanner(fs);
            StringBuilder linhas = new StringBuilder();
            while (entrada.hasNextLine()) {
                String linha = entrada.nextLine();
                linhas.append(linha + System.lineSeparator());
            }
            txtCompras.setText(linhas);
        } catch (IOException e) {
            Toast.makeText(this, "Erro - " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void gravaCompras() {
        String compras = txtCompras.getText().toString();

        try {
            FileOutputStream fs = openFileOutput(nomeArq, MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fs);
            pw.print(compras);
            pw.close();
        } catch (IOException e) {
            Toast.makeText(this, "Erro - " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
