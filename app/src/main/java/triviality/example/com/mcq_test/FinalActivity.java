package triviality.example.com.mcq_test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinalActivity extends Activity {

    TextView tv,tv2;

    String ename1=LoginScreen.email;
    int round;
    private SQLiteDatabase db;
    private Cursor c;
    Button btn;
    int s;String n,query;
    private static final String x="SELECT * FROM scores";
    String use;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        use=LoginScreen.email;
        tv=(TextView)findViewById(R.id.textView3);
        tv2=(TextView)findViewById(R.id.textView2);
        btn=(Button)findViewById(R.id.button1);
        Bundle data=getIntent().getExtras();
        s=data.getInt("score");
        n=data.getString("Name");
        tv.setText(use+" "+"Score: "+s);
        createDatabase2();
        findRoundNumber();
        ++round;
        insertDB2();
        //createdatabase();
       // c=db.rawQuery(x, null);
       /* if(!c.moveToFirst())
        {
            query="INSERT INTO scores VALUES('"+n+"','"+s+"')";
            db.execSQL(query);
            tv2.setText("You got a high score!");
        }
        else
        {
            c.moveToLast();
            if(s>Integer.parseInt(c.getString(1)))
            {
                query="UPDATE scores SET name='"+n+"', score='"+s+"' where score='"+c.getString(1)+"'";
                db.execSQL(query);
                tv2.setText("You got a high score!");
            }
        }*/
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(), HomeScreen.class);
                i.putExtra("Name", n);
                startActivity(i);
            }
        });
    }

    protected void createdatabase()
    {
        db=openOrCreateDatabase("ScoresDB.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS scores(name VARCHAR, score NUMBER)");
    }

    protected void createDatabase2()
    {
        db=openOrCreateDatabase("Sall.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS scoreOfAll(candName VARCHAR, roundNo NUMBER, scoreCand NUMBER)");

    }

    protected void findRoundNumber()
    {


        Cursor cursor=db.rawQuery("SELECT * FROM scoreOfALL WHERE candName=?",new String[] {ename1});
        round=cursor.getCount();



    }
    protected void insertDB2()
    {
        //tv.setText("Getting the quiz ready...");

        String query="INSERT INTO scoreOfAll(candName,roundNo,scoreCand) values('"+ename1+"','"+round+"','"+s+"');)";

        db.execSQL(query);
        Toast.makeText(getApplicationContext(), "Score added to database!", Toast.LENGTH_SHORT).show();


    }
}
