package edu.iastate.cs.palab.connectivitycollector;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Main activity just saves the database to a public directory where you can download
 * it to your computer
 *
 * @author Danilo Dominguez Perez
 *         Program Analysis Lab
 *         Deparment of Computer Science
 *         Iowa State University
 */
public class ExporterActivity extends AppCompatActivity {
    //private static final String LOG_TAG = "ExporterActivity";
    Button exportCSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exporter);

        exportCSV = (Button) findViewById(R.id.exportcsv);
        exportCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToPublicDirectory();
            }
        });
    }

    private void saveToPublicDirectory() {
        try {
            //File sd = Environment.getExternalStorageDirectory();
            File externalStorage = Environment.getExternalStoragePublicDirectory("connectivitydbs");
            if (!externalStorage.exists()) {
                externalStorage.mkdirs();
            }
            if (!externalStorage.exists()) {
                Toast.makeText(ExporterActivity.this, "External storage " + externalStorage + " folder does not exist.", Toast.LENGTH_SHORT).show();
                return;
            }
            File backupFile = new File(
                    Environment.getExternalStoragePublicDirectory("connectivitydbs"), "connectivitybackup.db");

            if (externalStorage.canWrite()) {
                //String currentDBPath = "/data/data/" + getPackageName() + "/databases/" + ConnectivityEntry.CONNECTIVITY_TABLE_NAME;
                String currentDBPath = new ConnectivityDBOpenHelper(this).getReadableDatabase().getPath();
                File currentDB = new File(currentDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupFile).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(ExporterActivity.this, "Database saved.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExporterActivity.this, "Database " + currentDBPath + " does not exists.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ExporterActivity.this, "No permissions to write database backup file: " + backupFile + ".", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ExporterActivity.this, "Database does not exists or there is a problem.", Toast.LENGTH_SHORT).show();
        }
    }
}
