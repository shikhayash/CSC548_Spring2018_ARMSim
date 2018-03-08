package com.example.awesomefat.csc548_spring2018_armsim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText XET[] = new EditText[CORE.maxRegisters];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CORE.X0 = "2";

        this.XET[0] = (EditText) this.findViewById(R.id.X0ET);
        this.XET[1] = (EditText) this.findViewById(R.id.X1ET);
        this.XET[2] = (EditText) this.findViewById(R.id.X2ET);
        this.XET[3] = (EditText) this.findViewById(R.id.X3ET);
        this.XET[4] = (EditText) this.findViewById(R.id.X4ET);
        this.XET[5] = (EditText) this.findViewById(R.id.X5ET);
        this.XET[6] = (EditText) this.findViewById(R.id.X6ET);
        this.XET[7] = (EditText) this.findViewById(R.id.X7ET);

        for (int i = 0; i < CORE.maxRegisters; i++) {
            this.XET[i].setText(CORE.regVal[i]);
        }

    }

    public void onClickSave(View V) {
        for (int i = 0; i < CORE.maxRegisters; i++) {
            CORE.regVal[i] = XET[i].getText().toString();
        }
    }

    public void onClickReset(View V) {
        for (int i = 0; i < CORE.maxRegisters; i++) {
            CORE.regVal[i] = "0";
            this.XET[i].setText("0");
        }
    }
}