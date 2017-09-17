package triviality.example.com.mcq_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class Score extends Activity {
    Button g,b1;
    private SQLiteDatabase db1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        g = (Button) findViewById(R.id.button5);
        b1 = (Button) findViewById(R.id.button6);
        createDatabase();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Score.this, addQuestion.class);
                startActivity(i);

            }
        });




        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor ch1=getAlldata();
                if(ch1.getCount()==0)
                { showmessage("Scores of Rounds","Nothing Found FOR THIS USERNAME");
                    // write comment
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                // buffer.append("candName :"+ ch1.getString(0)+" ");
                while(ch1.moveToNext())
                {
                    buffer.append("name :"+ ch1.getString(0)+" ");


                    buffer.append("roundNo :"+ ch1.getString(1)+" ");
                    buffer.append("score :"+ ch1.getString(2)+"\n");


                }
                showmessage("Scores of Rounds",buffer.toString());


            }


        });
    }

    protected void createDatabase()
    {
        db1=openOrCreateDatabase("Sall.db", Context.MODE_PRIVATE, null);

    }


    public void showmessage(String title, String message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    public Cursor getAlldata()
    {
        Cursor ch = db1.rawQuery("select * from scoreOfAll",null);
        return ch;
    }
}