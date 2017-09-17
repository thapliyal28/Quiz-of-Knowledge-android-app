package triviality.example.com.mcq_test;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    TextView tv, tv2, tv3, tv4;
    RadioGroup rg;
    Button btn;
    MediaPlayer mp;
    ProgressBar pb;
    int scr, nscr, questions = 0;
    private SQLiteDatabase db;
    private static final String x = "SELECT * FROM questions";
    private Cursor c;
    int qid = 0;
    CountDownTimer timer;
    String n;
    int done[] = new int[11];// unknown


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tv = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        rg = (RadioGroup) findViewById(R.id.radioGroup1);
        btn = (Button) findViewById(R.id.button1);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setProgress(100);
        Bundle data = getIntent().getExtras();
        n = data.getString("Name");
        //tv.setText("");
        openDatabase();
        c = db.rawQuery(x, null);
        c.moveToFirst();
        getQuestion();
        tv2.setText("Score: 0");
        startCount();


        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                final int res = rg.getCheckedRadioButtonId();
                switch (res) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Select one option", Toast.LENGTH_SHORT).show();
                        return;
                }
                if (res == Integer.parseInt(c.getString(6)))//unknown
                {
                    //Toast.makeText(getApplicationContext(), "Correct Answer!", Toast.LENGTH_SHORT).show();
                    //((RadioButton) rg.getChildAt(res)).setTextColor(Color.GREEN);
                    final Toast toast = Toast.makeText(getApplicationContext(), "Correct Answer!", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 10);
                    scr++;
                    tv2.setText("Score: " + scr);
                    for (int i = 0; i < rg.getChildCount(); ++i) {

                        if (i == res) {

                            ((RadioButton) rg.getChildAt(i))
                                    .setTextColor(Color.GREEN);
                        }
                    }


                }
                else {
                    //Toast.makeText(getApplicationContext(), "Incorrect Answer!", Toast.LENGTH_SHORT).show();
                    final Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Answer!", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 10);
                    for (int i = 0; i < rg.getChildCount(); ++i) {

                        if (i !=Integer.parseInt(c.getString(6))) {

                            ((RadioButton) rg.getChildAt(i))
                                    .setTextColor(Color.RED);
                        }

                        if(i== Integer.parseInt(c.getString(6)))
                        {
                            ((RadioButton) rg.getChildAt(i))
                                    .setTextColor(Color.GREEN);

                        }
                    }



                }


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (qid < 5)//unknown
                        { //((RadioButton) rg.getChildAt(res)).setTextColor(Color.BLACK);


                            //c.moveToNext();
                            rg.removeAllViews();
                            getQuestion();
                            timer.cancel();
                            pb.setProgress(100);
                            startCount();
                        } else {
                            Toast.makeText(getApplicationContext(), "You have completed the quiz", Toast.LENGTH_LONG).show();
                            timer.cancel();
                            Intent i = new Intent(getApplicationContext(), FinalActivity.class);
                            i.putExtra("score", scr);
                            i.putExtra("Name", n);
                            startActivity(i);
                        }

                        //Do something after 100ms
                    }
                }, 1000);


            }
        });
    }

    // sequential questions

  protected void openDatabase() {
        db = openOrCreateDatabase("QuizDB.db", Context.MODE_PRIVATE, null);
    }

    protected void getQuestion() {
        qid++;
        c.moveToNext();
        tv.setText(c.getString(1));
        for (int i = 0; i <= 3; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setId(i);
            rb.setText(c.getString(i + 2));
            rg.addView(rb);
        }
        rg.clearCheck();

    }

//random questions
    /*protected  void getQuestion() {
        boolean f = false;
        int qid;
        do {
            qid = (int) Math.floor(Math.random() * 50);
            for (int i = 0; i < questions; i++) {
                if (qid == done[i]) {
                    f = true;
                    break;
                }
            }
        }
        while (f);
        c.moveToFirst();
        boolean flag = true;
        while (flag) {
            if (qid!= Integer.parseInt(c.getString(0)))
            {
                if (!c.isLast())
                    c.moveToNext();
                else
                    c.moveToFirst();
            }
            else
            flag = false;
        }
        tv.setText(c.getString(1));
        for (int i = 0; i <= 3; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setId(i);
            rb.setText(c.getString(i + 2));
            rg.addView(rb);
        }
        rg.clearCheck();
        done[questions] = qid;
        questions++;
    }*/


    protected void startCount() {
        tv4.setText("Seconds Remaining");
        btn.setText("SUBMIT");
        timer = new CountDownTimer(15000, 1000) {


            public void onTick(long millisUntilFinished) {
                tv3.setText("" + millisUntilFinished / 1000);
                //nscr=(int)millisUntilFinished/2000;
                pb.setProgress(pb.getProgress() -7 );
            }

            public void onFinish() {
               pb.setProgress(0);
                tv3.setText("");
                tv4.setText("Sorry...Time Up!");
                //btn.setClickable(false);

                btn.setText("Move to next");
               //rg.removeAllViews();


                       // getQuestion();
                        //startCount();
                        //pb.setProgress(100);
                   }

        }.start();
    }
}