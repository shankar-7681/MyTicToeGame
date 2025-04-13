package com.music.mytictoegame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    int flag = 0; // 0 = X, 1 = O
    int count = 0;
    boolean gameOver = false;

    Animation bounceAnim;
    LinearLayout mainLayout;  // Changed type to match XML's LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Assign mainLayout using the existing field and set background color once
        mainLayout = findViewById(R.id.main);
        mainLayout.setBackgroundColor(getResources().getColor(R.color.neutralBg));
    }

    private void init() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
    }

    public void check(View view) {
        if (gameOver) return;

        Button clickedButton = (Button) view;

        if (!clickedButton.getText().toString().isEmpty()) return;

        clickedButton.startAnimation(bounceAnim);
        count++;

        // Mark, color and switch player
        if (flag == 0) {
            clickedButton.setText("X");
            clickedButton.setTextColor(Color.WHITE);
            clickedButton.setBackgroundResource(R.drawable.button_background_x);
            // Set main layout background to indicate O's turn next
            mainLayout.setBackgroundColor(getResources().getColor(R.color.bgO));
            flag = 1;
        } else {
            clickedButton.setText("O");
            clickedButton.setTextColor(Color.WHITE);
            clickedButton.setBackgroundResource(R.drawable.button_background_o);
            // Set main layout background to indicate X's turn next
            mainLayout.setBackgroundColor(getResources().getColor(R.color.bgX));
            flag = 0;
        }

        // Win Logic
        if (count >= 5) {
            String b1 = btn1.getText().toString();
            String b2 = btn2.getText().toString();
            String b3 = btn3.getText().toString();
            String b4 = btn4.getText().toString();
            String b5 = btn5.getText().toString();
            String b6 = btn6.getText().toString();
            String b7 = btn7.getText().toString();
            String b8 = btn8.getText().toString();
            String b9 = btn9.getText().toString();

            if (checkWinner(b1, b2, b3)) {
                declareWinner(b1);
            } else if (checkWinner(b4, b5, b6)) {
                declareWinner(b4);
            } else if (checkWinner(b7, b8, b9)) {
                declareWinner(b7);
            } else if (checkWinner(b1, b4, b7)) {
                declareWinner(b1);
            } else if (checkWinner(b2, b5, b8)) {
                declareWinner(b2);
            } else if (checkWinner(b3, b6, b9)) {
                declareWinner(b3);
            } else if (checkWinner(b1, b5, b9)) {
                declareWinner(b1);
            } else if (checkWinner(b3, b5, b7)) {
                declareWinner(b3);
            } else if (count == 9) {
                Toast.makeText(this, "Game Draw!", Toast.LENGTH_SHORT).show();
                gameOver = true;
            }
        }
    }

    private boolean checkWinner(String s1, String s2, String s3) {
        return s1.equals(s2) && s2.equals(s3) && !s3.isEmpty();
    }

    private void declareWinner(String player) {
        Toast.makeText(this, "Player " + player + " Won!", Toast.LENGTH_LONG).show();
        gameOver = true;
        mainLayout.setBackgroundColor(getResources().getColor(R.color.bgWin));
    }

    public void resetGame(View view) {
        Button[] allButtons = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for (Button b : allButtons) {
            b.setText("");
            b.setBackgroundResource(android.R.drawable.btn_default); // Reset background to default style
        }

        mainLayout.setBackgroundColor(getResources().getColor(R.color.neutralBg));
        flag = 0;
        count = 0;
        gameOver = false;
    }
}
