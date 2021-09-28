package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.newsapp.object.ConnectionReceiver;
import com.example.newsapp.object.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_USER_TO_MAIN = "KEY_USER_TO_MAIN";
    public static final String KEY_PASSWORD_TO_MAIN = "KEY_PASSWORD_TO_MAIN";

    EditText username, password;
    Button btn_user, btn_guest;
    TextView line, error;
    private Context context;

    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        ArrayList<User> users = this.create_user_list();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_user = findViewById(R.id.btn_login_user);
        btn_guest = findViewById(R.id.btn_login_guest);
        line = findViewById(R.id.line);
        error = findViewById(R.id.error);

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ret = ConnectionReceiver.isConnected();
                if (ret == true){
                    error.setText("");
                    boolean flag = false;
                    String username_text = username.getText().toString();
                    String password_text = password.getText().toString();
                    if (username_text.length() == 0)
                        username.setHint("Bạn chưa nhập tên đăng nhập");
                    if (password_text.length() == 0)
                        password.setHint("Bạn chưa nhập mật khẩu");
                    if (username_text.length() > 0){
                        if (password_text.length() > 0){
                            for (int i = 0; i < users.size(); i++) {
                                System.out.println(users.get(i));
                                if (users.get(i).getUsername().equals(username_text)){
                                    if (users.get(i).getPassword().equals(password_text)){
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (flag){
                            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                            intent.putExtra(KEY_USER_TO_MAIN, username_text);
                            startActivity(intent);
                        }
                        else{
                            username.setText("");
                            password.setText("");
                            username.setHint("Không hợp lệ");
                            password.setHint("Không hợp lệ");
                        }
                    }
                }
                else{
                    error.setText("Thiết bị chưa kết nối internet");
                }
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ret = ConnectionReceiver.isConnected();
                if (ret == true){
                    error.setText("");
                    Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(intent);
                }
                else{
                    error.setText("Thiết bị chưa kết nối internet");
                }
            }
        });
    }
    private ArrayList<User> create_user_list(){
        User user1 = new User("user1", "123456789");
        User user2 = new User("user2", "12345");
        User admin = new User("admin", "11111");

        ArrayList<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(admin);
        return users;
    }
}