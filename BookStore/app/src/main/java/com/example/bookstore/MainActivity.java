package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MainActivity extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.24:1521:xe";
    private static final String USERNAME = "HR";
    private static final String PASSWORD = "1234";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    // 회원가입 바로가기
    public void onClick(View view){
        Intent su = new Intent(this, SignUp.class);
        su.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(su);
    }

    // 아이디 찾기 바로가기
    public void onClick2(View view){
        Intent fid = new Intent(this, FindID.class);
        fid.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(fid);
    }
    // 패스워드 변경 바로가기
    public void onClick3(View view){
        Intent cpw = new Intent(this, FindPW.class);
        cpw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cpw);
    }
    // 로그인
    public void onClick4(View view){
        TextView tvID = findViewById(R.id.IDInput);
        TextView tvPassword = findViewById(R.id.PWInput);

        String id = tvID.getText().toString(); // 아이디
        String pw = tvPassword.getText().toString(); // 패스워드

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT *\n" +
                    "FROM store_customer \n" +
                    "WHERE user_id = ? AND user_password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, pw);

            if (ps.executeUpdate()>0) {
                Intent idpw = new Intent(MainActivity.this, BookStoreMain.class);
                idpw.putExtra("user_id", id);
                idpw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(idpw);
            }else{
                Toast.makeText(this, "아이디 또는 패스워드가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}