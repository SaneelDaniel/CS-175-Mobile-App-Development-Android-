package edu.sjsu.android.mycontentprovider;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickAddName(View view) {
    // Add a new student record
        ContentValues values = new ContentValues();
        values.put(StudentsProvider.NAME, ((EditText)findViewById(R.id.txtName)).getText().toString());
        values.put(StudentsProvider.GRADE, ((EditText)findViewById(R.id.txtGrade)).getText().toString());
        Uri uri = getContentResolver().insert( StudentsProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }

    /*
    public void onClickDeleteStudent(View view) {
        // Add a new student record
        String id = null;
        ContentValues values = new ContentValues();
        values.put(StudentsProvider.NAME, ((EditText)findViewById(R.id.txtName)).getText().toString());
        values.put(StudentsProvider.GRADE, ((EditText)findViewById(R.id.txtGrade)).getText().toString());

        String URL = "content://" + getString(R.string.provider) + "/students";
        Uri students = Uri.parse(URL);
        Cursor c = getContentResolver().query(students, null, null, null,
                "name");

        if (c.moveToFirst()) {
            do{
                id = c.getString(c.getColumnIndex(StudentsProvider._ID));
                if(((EditText)findViewById(R.id.txtGrade)).getText().toString().equals
                        (c.getString(c.getColumnIndex(StudentsProvider.NAME)))){
                    break;
                }
            }
            while (c.moveToNext());
        }

        String whereClause = ZooDbHelper.ID_COLUMN + "=?";
        String[] whereArgs = {"4"};
        Uri uri = getContentResolver().delete( StudentsProvider.CONTENT_URI, "_id=" + id , null);

        Toast.makeText(getBaseContext(),
                uri.toString(), Toast.LENGTH_LONG).show();
    }
     */

    public void onClickRetrieveStudents(View view) {
    // Retrieve student records
        String URL = "content://" + getString(R.string.provider) + "/students";
        Uri students = Uri.parse(URL);
        Cursor c = getContentResolver().query(students, null, null, null,
                "name");
        if (c.moveToFirst()) {
            do{
                Toast.makeText(this, c.getString(c.getColumnIndex(StudentsProvider._ID)) +
                        ", " + c.getString(c.getColumnIndex( StudentsProvider.NAME)) + ", " + c.getString(c.getColumnIndex( StudentsProvider.GRADE)), Toast.LENGTH_SHORT).show();
            }
            while (c.moveToNext());
        }
    }
}