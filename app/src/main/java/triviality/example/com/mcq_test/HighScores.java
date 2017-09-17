package triviality.example.com.mcq_test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class HighScores extends Activity {

    private SQLiteDatabase db;
    private Cursor c;
    TextView tv,tv2;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        a =LoginScreen.email;
        tv=(TextView)findViewById(R.id.textView1);
        tv2=(TextView)findViewById(R.id.textView2);
        createDatabase();
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




    protected void createDatabase()
    {
        db=openOrCreateDatabase("Sall.db", Context.MODE_PRIVATE, null);

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

             Cursor ch = db.rawQuery("select * from scoreOfAll where candName=?",new String[] {LoginScreen.email});
        return ch;
    }

}
