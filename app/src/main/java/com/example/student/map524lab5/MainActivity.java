package com.example.student.map524lab5;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class MainActivity extends ActionBarActivity {
    String[] colourNames;
    String trim = ""; // the hex color user selected
    PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colourNames = getResources().getStringArray(R.array.listArray);
        final ListView lv = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, R.layout.activity_listview, colourNames);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                //lv.setBackgroundColor(getResources().getColor(Integer.parseInt(getResources().getStringArray(R.array.listValues)[position], 16)));
                trim = "#" + getResources().getStringArray(R.array.listValues)[position].substring(2);
                changeColor(trim);
            }
        });
        registerForContextMenu(lv);
    }
    public void changeColor(String string)
    {
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.main);
        rl.setBackgroundColor(Color.parseColor(string));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "write color to SDCard");
        menu.add(0, v.getId(), 0, "read color from SDCard");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "write color to SDCard") {
            Toast.makeText(getApplicationContext(), "writing color to SDCard", Toast.LENGTH_LONG).show();
            // write on SD card file data in the text box


            try {
                Toast.makeText(getBaseContext(),
                        "Writing...",
                        Toast.LENGTH_SHORT).show();
                File myFile = new File("/sdcard/myColor.txt");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter =
                        new OutputStreamWriter(fOut);
                //myOutWriter.append(trim.getText());
                myOutWriter.append(trim);
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(),
                        "Done writing SD 'myColor.txt'",
                        Toast.LENGTH_SHORT).show();
                //out.close();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }




        } else if (item.getTitle() == "read color from SDCard") {
            Toast.makeText(getApplicationContext(), "reading a color from SDCard", Toast.LENGTH_LONG).show();
            try {

                //File file = new File("/sdcard/myColor.txt");
                File dir = Environment.getExternalStorageDirectory();
                File file = new File(dir,"myColor.txt");
                if(file.exists())   // check if file exist
                {
                    //Read text from file
                    StringBuilder text = new StringBuilder();
                    Toast.makeText(getBaseContext(),
                            "Reading...",
                            Toast.LENGTH_SHORT).show();
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(file, "ISO-8859-15"));
                        String line;

                        while ((line = br.readLine()) != null) {
                            text.append(line);
                            //text.append('n');
                        }
                        Toast.makeText(getBaseContext(),
                                "Done reading SD 'myColor.txt' " + line,
                                Toast.LENGTH_SHORT).show();

                        Log.i("TEST","line is "+ line);
                        if (line != null){
                            changeColor(line);
                        }
                    }
                    catch (IOException e) {
                        //You'll need to add proper error handling here
                    }
                    //Set the text
                }
                else
                {
                    Toast.makeText(getBaseContext(),
                            "Sorry file doesn't exist!!",
                            Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            return false;
        }
        return true;
    }
}
