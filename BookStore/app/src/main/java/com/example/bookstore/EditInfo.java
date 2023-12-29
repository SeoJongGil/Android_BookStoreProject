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

public class EditInfo extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.24:1521:xe";
    private static final String USERNAME = "HR";
    private static final String PASSWORD = "1234";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
    // 회원정보 수정
    public void onClick(View view) {
        Intent it = getIntent();
        TextView tvName = findViewById(R.id.NameInput4);
        TextView tvBD = findViewById(R.id.BDInput2);
        TextView tvTel = findViewById(R.id.TelInput4);
        TextView tvAD = findViewById(R.id.ADInput2);

        String nm = tvName.getText().toString(); // 이름
        String bd = tvBD.getText().toString(); // 생년월일
        String tel = tvTel.getText().toString(); // 전화번호
        String ad = tvAD.getText().toString(); // 주소
        String userId = it.getStringExtra("user_id");

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE store_customer SET user_name = ?, user_birth = TO_DATE(?, 'YYYY-MM-DD'), user_tel = ?, user_address = ? WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nm);
            ps.setString(2, bd);
            ps.setString(3, tel);
            ps.setString(4, ad);
            ps.setString(5, userId);

            if (ps.executeUpdate() > 0) {
                Intent ei = new Intent(EditInfo.this, BookStoreMain.class);
                ei.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ei);
                Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 회원정보 수정 취소
    public void onClick2(View view){
        Intent cc = new Intent(this, BookStoreMain.class);
        cc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(cc);
    }
}