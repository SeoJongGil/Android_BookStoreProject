package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindID extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.24:1521:xe";
    private static final String USERNAME = "HR";
    private static final String PASSWORD = "1234";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
    // 아이디 찾기
    public void onClick(View view) {
        TextView tvName = findViewById(R.id.NameInput2);
        TextView tvTel = findViewById(R.id.TelInput2);
        TextView tvIDR = findViewById(R.id.IDR);

        String nm = tvName.getText().toString(); // 이름
        String tel = tvTel.getText().toString(); // 전화번호

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT user_id FROM store_customer WHERE user_name = ? AND user_tel = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nm);
            ps.setString(2, tel);

            ResultSet res = ps.executeQuery();

            if (res.next()) {
                String user_id = res.getString("user_id");

                tvIDR.setText("찾은 아이디: " + user_id);
            } else {
                tvIDR.setText("아이디를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 아이디 찾기 취소
    public void onClick2(View view){
        Intent cc = new Intent(this, MainActivity.class);
        cc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cc);
    }
    // 회원가입 바로가기
    public void onClick3(View view){
        Intent su = new Intent(this, SignUp.class);
        su.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(su);
    }
    // 패스워드 바로가기
    public void onClick4(View view) {
        Intent cpw = new Intent(this, FindPW.class);
        cpw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cpw);
    }
}