package org.hacktivity.dicecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.lang.String;
import java.util.ArrayList;

import org.hacktivity.dicecalc.BlumBlumShub.BlumBlumShub;

public class MainActivity extends AppCompatActivity {

    BlumBlumShub bbs = new BlumBlumShub(2310);
    EditText etCalc;
    String codeStr = "";
    int result=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCalc = (EditText) findViewById(R.id.calc_box);

    }


    public void calcButtonClick(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();

        codeStr = codeStr + buttonText;
        etCalc.setText(codeStr);

    }

    public void calcButtonRoll(View view) {
        if (codeStr.length() > 0) {
            etCalc.setText(Integer.toString(multiPartRoll(codeStr)));
        }
        codeStr = "";
    }

    public int multiPartRoll(String roll) {
        String[] parts = roll.split("(?=[+-])"); //split by +-, keeping them
        int total = 0;


        for (String partOfRoll : parts) { //roll each dice specified

            if (partOfRoll.matches("-*\\d+d\\d+")) {
                String[] splitString = (partOfRoll.split("d"));
                int times = Integer.parseInt(splitString[0]);
                int die = Integer.parseInt(splitString[1]);
                int i; int negative = 1;

                if (times < 0) {
                    negative = -1;
                    times = -times;
                }

                for (i = 0; i < times; i++) {
                    String rollStr = "d" + die;
                    total += negative * singleRoll(rollStr);
                }
            }
            else
            {
                total += singleRoll(partOfRoll);
            }
        }
        return total;
    }

    public int singleRoll(String roll) {
        if (roll.equals("")) {
            return 0;
        }

        int di = roll.indexOf('d');
        if (di == -1) //case where has no 'd'
            return Integer.parseInt(roll);
        int diceSize = Integer.parseInt(roll.substring(di + 1)); //value of string after 'd'
        int result = bbs.randInt(diceSize) + 1; //roll the dice
        if (roll.startsWith("-")) //negate if nessasary
            result = -result;
        return result;
    }
}
