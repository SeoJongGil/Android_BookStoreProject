package com.example.bookstore;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BookStoreMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_store_main);
    }
    // 회원정보 수정 바로가기
    public void onClick(View view){
        Intent ei = getIntent();
        String id = ei.getStringExtra("user_id");
        Intent ei2 = new Intent(this, EditInfo.class);
        ei2.putExtra("user_id", id);
        startActivity(ei2);
    }
    // 로그아웃
    public void onClick2(View view){
        // 대화창
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage("로그아웃하시겠습니까?");

        // "예" 버튼
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent lo = new Intent(getApplicationContext(), MainActivity.class);
                lo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(lo);
            }
        });
        // "아니요" 버튼
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 대화창 뛰우기
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    // 도서 검색 바로가기
//    public void onClick3(View view){
//        Intent bs = new Intent(this, BookSearch.class);
//
//        startActivity(bs);
//    }
    // 추천 도서 바로가기
//    public void onClick4(View view){
//        Intent bb = new Intent(this, BestBook.class);
//
//        startActivity(bb);
//    }
}