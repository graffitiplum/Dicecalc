package org.hacktivity.dicecalc;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.String;
import java.util.ArrayList;

import org.hacktivity.dicecalc.BlumBlumShub.BlumBlumShub;

public class MainActivity extends AppCompatActivity {

    BlumBlumShub bbs = new BlumBlumShub(2310);
    EditText etCalc;
    ArrayList<String> codeStr = new ArrayList<String>();
    int result = 0;
    boolean err = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCalc = (EditText) findViewById(R.id.calc_box);

    }


    public void calcButtonClick(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();

        // TODO: Case: dn[extraneous numbers]
        // TODO: Case: d[n]d[n]

        if ((codeStr.size() > 0) && codeStr.get(codeStr.size()-1).matches("d") &&
                buttonText.matches("d") ) {
            Toast.makeText(this, "ERROR: Illegal Arguments", Toast.LENGTH_SHORT).show();
        }
        else {
            codeStr.add(buttonText);
            etCalc.setText(condenseArray(codeStr));
        }
    }

    public void calcButtonClickBackspace(View view) {
        Button b = (Button) view;

        if (!codeStr.isEmpty()) {
            codeStr.remove(codeStr.size() - 1);
        }
        etCalc.setText(condenseArray(codeStr));
    }

    public void calcButtonRoll(View view) {
        if (!codeStr.isEmpty()) {
            etCalc.setText(Integer.toString(multiPartRoll(condenseArray(codeStr))));
        }
        codeStr.clear();
    }

    public String condenseArray(ArrayList<String> al) {
        String res = "";
        int i;

        for (i = 0; i < al.size(); i++) {
            res += al.get(i);
        }

        return (res);
    }

    public int multiPartRoll(String roll) {
        String[] parts = roll.split("(?=[+-])"); //split by +-, keeping them
        int total = 0;

        for (String partOfRoll : parts) { //roll each dice specified

            if (partOfRoll.matches("-*\\d+d\\d+")) {
                String[] splitString = (partOfRoll.split("d"));
                int times = Integer.parseInt(splitString[0]);
                int die = Integer.parseInt(splitString[1]);
                int i;
                int negative = 1;

                if (times < 0) {
                    negative = -1;
                    times = -times;
                }

                for (i = 0; i < times; i++) {
                    String rollStr = "d" + die;
                    total += negative * singleRoll(rollStr);
                }
            } else {
                total += singleRoll(partOfRoll);
            }
        }
        return total;
    }

    public int singleRoll(String roll) {
        if (roll.equals("") ||
                roll.equals("+") ||
                roll.equals("-")) {
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
