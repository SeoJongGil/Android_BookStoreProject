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
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.0.24:1521:xe";
    private static final String USERNAME = "HR";
    private static final String PASSWORD = "1234";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
    // 회원가입
    public void onClick(View view) throws SQLException {
        TextView tvName = findViewById(R.id.NameInput);
        TextView tvID = findViewById(R.id.IDInput2);
        TextView tvPW = findViewById(R.id.PWInput2);
        TextView tvPWC = findViewById(R.id.PWCInput);
        TextView tvBD = findViewById(R.id.BDInput);
        TextView tvTel = findViewById(R.id.TelInput);
        TextView tvAD = findViewById(R.id.ADInput);

        String nm = tvName.getText().toString(); // 이름
        String id = tvID.getText().toString(); // 아이디
        String pw = tvPW.getText().toString(); // 패스워드
        String pwc = tvPWC.getText().toString(); // 패스워드 재확인
        String bd = tvBD.getText().toString(); // 생년월일
        String tel = tvTel.getText().toString(); // 전화번호
        String ad = tvAD.getText().toString(); // 주소

        if (isEmpty(nm) || isEmpty(id) || isEmpty(pw) || isEmpty(pwc) || isEmpty(bd) || isEmpty(tel) || isEmpty(ad)) {
            Toast.makeText(this, "올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
            }
        if (!pw.equals(pwc)) {
            Toast.makeText(this, "패스워드와 패스워드 재확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();

            ResultSet res = statement.executeQuery("SELECT MAX(user_num) FROM store_customer");
            int su = 0;
            while (res.next()) {
                su = Integer.parseInt(res.getString(1)) + 1;
            }

            statement.close();

            String sql = "INSERT INTO store_customer(user_name, user_id, user_password, password_c, user_birth, user_tel, user_address, user_num) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, nm);
            ps.setString(2, id);
            ps.setString(3, pw);
            ps.setString(4, pwc);
            ps.setString(5, bd);
            ps.setString(6, tel);
            ps.setString(7, ad);
            ps.setInt(8, su);

            if (ps.executeUpdate() > 0) {
                Intent si = new Intent(SignUp.this, MainActivity.class);
                si.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(si);
                Toast.makeText(this, "가입되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            }
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isEmpty (String text) {
        return text == null || text.trim().isEmpty();
    }
        // 회원가입 취소
        public void onClick2(View view){
            Intent cc = new Intent(this, MainActivity.class);
            cc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(cc);
        }
}