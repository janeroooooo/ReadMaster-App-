package com.example.readmaster;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.example.readmaster.databinding.ActivityShowPdfBinding;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShowPdfActivity extends AppCompatActivity {

    ActivityShowPdfBinding binding;
    private Dialog dialog;
    private String pdfUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        pdfUrl = getIntent().getStringExtra("url");


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
        }

        dialog.show();

        if(pdfUrl == null){

            Toast.makeText(this, "PDF Do not exist.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }else{

            new RetrievePDFfromUrl().execute(pdfUrl);
        }

    }

    class RetrievePDFfromUrl extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = null;

            try{

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                if (urlConnection.getResponseCode() == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            return inputStream;
        }


        @Override
        protected void onPostExecute(InputStream inputStream) {

            binding.pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener(){

                @Override
                public void loadComplete(int nbPages) {
                    dialog.dismiss();
                }
            }).load();

        }
    }

}