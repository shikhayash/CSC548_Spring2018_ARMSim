package com.example.awesomefat.csc548_spring2018_armsim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

enum returnCode {NO_ERROR, INVALID_FORMAT , UNSUPPORTED_INSTRUCTION, INVALID_NUM_OF_REGS, UNSUPPORTED_REG}


public class MainActivity extends AppCompatActivity {

    private EditText inputET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.inputET = (EditText)this.findViewById(R.id.inputET);
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

        if (this.inputET.getText().toString().equals(""))
                    {
                                Toast.makeText(MainActivity.this, "Enter instruction",
                                                Toast.LENGTH_SHORT).show();
                    return;
                }

                        String str = inputET.getText().toString();

                       returnCode ret =  parseInstruction(str);

                       switch(ret)
               {
                           case NO_ERROR:
                   {
                               Toast.makeText(MainActivity.this, "instruction processed successfully",
                                               Toast.LENGTH_SHORT).show();
                       break;
                   }
                   case INVALID_FORMAT:
                   {
                               Toast.makeText(MainActivity.this, "Invalid instruction format",
                                               Toast.LENGTH_SHORT).show();
                      break;
                   }
                   case UNSUPPORTED_INSTRUCTION:
                   {
                               Toast.makeText(MainActivity.this, "Unsupported instruction",
                                               Toast.LENGTH_SHORT).show();
                       break;
                   }
                   case INVALID_NUM_OF_REGS:
                   {
                               Toast.makeText(MainActivity.this, "Invalid number of registers for the instruction",
                                               Toast.LENGTH_SHORT).show();
                       break;
                   }
                   case UNSUPPORTED_REG:
                   {
                               Toast.makeText(MainActivity.this, "Unsupported register has been used",
                                               Toast.LENGTH_SHORT).show();
                      break;
                   }
                   default:
                   {
                               Toast.makeText(MainActivity.this, "something went wrong !",
                                               Toast.LENGTH_SHORT).show();
                       break;
                   }
               }

    }

    private int getNumFromStr(String str)
    {
                int num = 0;
                int len = str.length();
                int i = 0;

                        while (i < len)
                    {
                                char ch = str.charAt(i);
                    if (ch >= '0' && ch <= '9') {
                            num = num * 10 + ch - '0';
                       }
                    i++;
                }
                return num;
            }

            private ArrayList<String>  getTokensFromStr(String str)
    {
                ArrayList<String> list = new ArrayList<String>();
                int len = str.length();
                int j = 0;

                        for (int i = 0; i < len; i++)
                    {
                                if (str.charAt(i) == ',')
                        {
                                    list.add(str.substring(j, i));
                        j = i + 1;
                    }
                }

                        list.add(str.substring(j, len ));

                        return list;
            }

            private returnCode addInstr(String str)
   {
                int dstReg = 0, reg1 = 0, reg2 = 0;

                        ArrayList<String> list = getTokensFromStr(str);


                                if (list.size() != 3)
                    {
                                return returnCode.INVALID_FORMAT;
                }

                        for (int i = 0; i < 3; i++)
                    {
                                if (!list.get(i).contains("X"))
                            return returnCode.INVALID_FORMAT;
                }

                        dstReg = getNumFromStr(list.get(0));
                reg1 = getNumFromStr(list.get(1));
                reg2 = getNumFromStr(list.get(2));

                        if (dstReg >= CORE.maxRegisters  || reg1 >= CORE.maxRegisters   ||  reg2 >= CORE.maxRegisters )
                    {
                                return returnCode.UNSUPPORTED_REG;
                }
                CORE.regVal[dstReg] = "" + (Integer.parseInt(CORE.regVal[reg1]) + Integer.parseInt(CORE.regVal[reg2]));

                        return returnCode.valueOf("NO_ERROR");
            }

            private returnCode addiInstr(String str) {
                int dstReg = 0, reg1 = 0;
                int immVal;

                        ArrayList<String> list = getTokensFromStr(str);

                        if (list.size() != 3) {
                        return returnCode.INVALID_FORMAT;
                    }

                        for (int i = 0; i < 2; i++) {
                        if (!list.get(i).contains("X"))
                                return returnCode.INVALID_FORMAT;
                    }

                        dstReg = getNumFromStr(list.get(0));
                reg1 = getNumFromStr(list.get(1));

                        if (list.get(2).contains("X"))
                        return returnCode.INVALID_FORMAT;

                        immVal = getNumFromStr(list.get(2));

                        if (dstReg >= CORE.maxRegisters || reg1 >= CORE.maxRegisters) {
                        return returnCode.UNSUPPORTED_REG;
                    }


                                CORE.regVal[dstReg] = "" + (Integer.parseInt(CORE.regVal[reg1]) + immVal);

                        return returnCode.NO_ERROR;
            }

            private returnCode parseInstruction(String str)
    {
                String uStr = str.toUpperCase();
                int i = 0;

                   if (uStr.contains("ADDI"))
                   {


                                       if ((uStr.length() > 4) && uStr.charAt(uStr.indexOf("ADDI") + 4) != ' ')
                       {
                                   return returnCode.UNSUPPORTED_INSTRUCTION;
                   }

                          return addiInstr(uStr);
               }
               if (uStr.contains("ADD"))
                   {

                                       if ((uStr.length() > 3) && uStr.charAt(uStr.indexOf("ADD") + 3) != ' ')
                       {
                                  return returnCode.UNSUPPORTED_INSTRUCTION;
                   }

                   return addInstr(uStr);
               }

                return returnCode.UNSUPPORTED_INSTRUCTION;


                            }
}
