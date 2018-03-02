package com.example.awesomefat.csc548_spring2018_armsim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegistersButtonClicked(View v)
    {
        Intent i = new Intent(this, RegisterActivity.class);
        this.startActivity(i);
    }

    public void onRamButtonClicked(View v)
    {
        Intent i = new Intent(this, RamActivity.class);
        this.startActivity(i);
    }

    public void onRunButtonClicked(View v)
    {
        //Support 2 instructions: ADD and ADDI
        //ADD assumes 2 registers
        //ADDI assumes 1 register and one numeric literal
        System.out.println(CORE.X0);
    }
}
