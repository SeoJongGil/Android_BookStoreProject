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

public class PWCG extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.24:1521:xe";
    private static final String USERNAME = "HR";
    private static final String PASSWORD = "1234";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwcg);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
    // 패스워드 변경(새 패스워드)
    public void onClick(View view) {
        TextView tvNpw = findViewById(R.id.NPWInput);
        TextView tvNpwc = findViewById(R.id.NPWCInput);

        String npw = tvNpw.getText().toString(); // 새 패스워드
        String npwc = tvNpwc.getText().toString(); // 새 패스워드 재입력
        String userId = getIntent().getStringExtra("user_id"); // 아이디 지정

        if (!npw.equals(npwc)) {
            Toast.makeText(this, "새 패스워드와 새 패스워드 재확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String sql = "UPDATE store_customer SET user_password = ?, password_c = ? WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, npw);
            ps.setString(2, npwc);
            ps.setString(3, userId);

            if (ps.executeUpdate() > 0) {
                Intent cpw = new Intent(PWCG.this, MainActivity.class);
                cpw.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cpw);

                Toast.makeText(this, "변경되었습니다.", Toast.LENGTH_SHORT).show();
            }
            ps.close();

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