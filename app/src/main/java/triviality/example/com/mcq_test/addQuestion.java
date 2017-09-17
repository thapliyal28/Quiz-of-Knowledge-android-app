package triviality.example.com.mcq_test;

/**
 * Created by Abhishekt on 11/2/2016.
 */



        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class addQuestion extends AppCompatActivity {

    String questiontxt;
    String  optionatxt;
    String  optionbtxt;
    String  optionctxt;
    String  optiondtxt;
    String answersample;
    int  answertxt;
    Button addtodb;

    private Cursor p;
    private SQLiteDatabase db;

    private EditText qtxt, aop,bop,cop,dop,ans;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_admin);

        addtodb= (Button)findViewById(R.id.ADQ);
        qtxt = (EditText)findViewById(R.id.question);
        aop = (EditText)findViewById(R.id.optionA);
        bop = (EditText)findViewById(R.id.optionB);
        cop = (EditText)findViewById(R.id.optionC);
        dop = (EditText)findViewById(R.id.optionD);
        ans = (EditText)findViewById(R.id.answer);
        createDatabase();
        p=db.rawQuery("SELECT * FROM questions", null);
        if(!p.moveToFirst())
            insertDB();

        addtodb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                p.moveToFirst();

                questiontxt= (qtxt.getText()).toString();
                optionatxt= (aop.getText()).toString();
                optionbtxt= (bop.getText()).toString();
                optionctxt= (cop.getText()).toString();
                optiondtxt= (dop.getText()).toString();
                answersample= (ans.getText()).toString();
                answertxt = Integer.parseInt(answersample);
                insertDB();
                //HomeScreen
            }
        });

    }


    protected void createDatabase()
    {
        db=openOrCreateDatabase("QuizDB.db", Context.MODE_PRIVATE, null);

    }

    protected void insertDB()
    {
        //tv.setText("Getting the quiz ready...");
        db.execSQL("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, question VARCHAR, opA VARCHAR," +
                "opB VARCHAR, opC VARCHAR,opD VARCHAR,answer NUMBER)");
        String query="INSERT INTO questions(question,opA,opB,opC,opD,answer) values('"+questiontxt+"','"+optionatxt+"','"+optionbtxt+
                "','"+optionctxt+"','"+optiondtxt+"','"+answertxt+"');)";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(), "Question added to Database", Toast.LENGTH_LONG).show();

    }


}
