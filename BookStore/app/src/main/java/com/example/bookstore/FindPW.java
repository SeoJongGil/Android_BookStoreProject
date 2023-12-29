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
import java.sql.ResultSet;

public class FindPW extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.24:1521:xe";
    private static final String USERNAME = "HR";
    private static final String PASSWORD = "1234";
    private Connection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
    // 패스워드 변경(아이디 입력)
    public void onClick(View view) {
        TextView tvID = findViewById(R.id.IDInput3);
        TextView tvName = findViewById(R.id.NameInput3);
        TextView tvTel = findViewById(R.id.TelInput3);

        String id = tvID.getText().toString(); // 아이디
        String nm = tvName.getText().toString(); // 이름
        String tel = tvTel.getText().toString(); // 전화번호

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "SELECT user_password FROM store_customer WHERE user_id = ? AND user_name = ? AND user_tel = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, nm);
            ps.setString(3, tel);

            ResultSet res = ps.executeQuery();

            if (res.next()) {
                String pw = res.getNString("user_password");

                Intent cpw = new Intent(FindPW.this, PWCG.class);
                cpw.putExtra("user_id", id);
                cpw.putExtra("recovered_password", pw);
                cpw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cpw);
            } else {
                Toast.makeText(this, "등록된 정보가 없거나 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 패스워드 변경 취소
    public void onClick2(View view){
        Intent cc = new Intent(this, MainActivity.class);
        cc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cc);
    }
}