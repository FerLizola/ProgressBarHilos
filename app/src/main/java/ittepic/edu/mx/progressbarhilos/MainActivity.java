package ittepic.edu.mx.progressbarhilos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar1,progressBar2;
    Button btnAccion,btnAccion2;
    EditText val,val1;
    int pgb=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1=(ProgressBar)findViewById(R.id.pgb_Avance);
        btnAccion=(Button) findViewById(R.id.btn_iniciar);
        val=(EditText)findViewById(R.id.edt_val);
        progressBar2=(ProgressBar)findViewById(R.id.pgb_Avance2);
        btnAccion2=(Button) findViewById(R.id.button2);
        val1=(EditText)findViewById(R.id.editText2);

        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar1.setMax(Integer.parseInt(String.valueOf(val.getText())));
                AsyncTarea asyncTarea = new AsyncTarea(progressBar1,Integer.parseInt(String.valueOf(val.getText())));
                asyncTarea.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                pgb=1;
            }
        });
        btnAccion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar2.setMax(Integer.parseInt(String.valueOf(val1.getText())));
                AsyncTarea asyncTarea = new AsyncTarea(progressBar2,Integer.parseInt(String.valueOf(val1.getText())));
                asyncTarea.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                pgb=2;
            }
        });
    }
    private void UnSegundo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class  AsyncTarea extends AsyncTask<Integer, Integer,Boolean> {
        ProgressBar bar;
        int limite;
        public AsyncTarea(ProgressBar bar,int limite){
            this.bar=bar;
            this.limite=limite;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setMax(limite);

        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            for (int i=1; i<=limite; i++){

                publishProgress(i);
                UnSegundo();
                if (isCancelled()){
                    break;
                }
            }
            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            //Actualizar la barra de progreso

                bar.setProgress(values[0].intValue());

        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            //super.onPostExecute(aVoid);

            if (aVoid){
                Toast.makeText(getApplicationContext(),"Tarea finaliza AsyncTask",Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getApplicationContext(),"Tarea NO finaliza AsyncTask",Toast.LENGTH_SHORT).show();

        }


    }
}
