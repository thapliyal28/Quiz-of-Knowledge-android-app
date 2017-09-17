package triviality.example.com.mcq_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener{
    private Button login, register,sanku;
    private EditText etEmail, etPass;
    private DbHelper db;
    private Session session;
    public static final String MyPREFERENCES="MyPrefs";
    public static final String Name = "nameKey";
    public static final String Password="passKey";
    public static String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        db = new DbHelper(this);
        session = new Session(this);
        login = (Button)findViewById(R.id.btnLogin);
        sanku = (Button)findViewById(R.id.button);
        register = (Button)findViewById(R.id.btnReg);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        sanku.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnReg:
                startActivity(new Intent(LoginScreen.this,RegisterActivity.class));
                break;
            case R.id.button:
                startActivity(new Intent(LoginScreen.this,Admin.class));
                break;

            default:

        }
    }




    private void login(){
        email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if(db.getUser(email,pass)){
            session.setLoggedin(true);

            Intent i=new Intent(LoginScreen.this, HomeScreen.class);
            i.putExtra("username", email);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
        }
    }
}
