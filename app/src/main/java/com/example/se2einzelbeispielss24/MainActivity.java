package com.example.se2einzelbeispielss24;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    String hostname = "se2-submission.aau.at";
    int port = 20080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hebt die Einschränkung auf, damit es am Hauptthread durchgeführt werden kann
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Button btn_send = findViewById(R.id.btn_send);
        Button btn_ASCII = findViewById(R.id.btn_ASCII);
        TextView txtResult = findViewById(R.id.txtResult);
        EditText editTextNumber = findViewById(R.id.editTextNumber);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Verbindung
                    Socket socket = new Socket(hostname, port);
                    //Senden
                    PrintWriter send = new PrintWriter(socket.getOutputStream(), true);
                    //Empfangen
                    BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    send.println(editTextNumber.getText().toString());
                    String answer = receive.readLine();
                    txtResult.setText(answer);

                }catch (UnknownHostException e){
                    throw new RuntimeException(e);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });
        btn_ASCII.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matASCII = getASCII(editTextNumber.getText().toString());
                txtResult.setText(matASCII);
            }
        });
    }
    public String getASCII(String s){
        StringBuilder convertedNumb = new StringBuilder();
        int i;

        for(i = 0; i < s.length(); i++){
            char digit = s.charAt(i);
            if(i % 2 == 0){
                convertedNumb.append(digit);
            }else{
                char replacement = (char) ('a' + (digit - '0'));
                convertedNumb.append(replacement);
            }
        }
        return convertedNumb.toString();
    }
}