package triviality.example.com.mcq_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Admin extends Activity {
    Button b;
    EditText t;
    String s;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        b = (Button) findViewById(R.id.button4);
        t = (EditText) findViewById(R.id.editText);
      //  b1 = (Button) findViewById(R.id.button6);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s=t.getText().toString();
                if(s.equals("abhi"))
                {
                    Intent i=new Intent(Admin.this, Score.class);
                    startActivity(i);}

                else {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
            }


        });



    }
}

