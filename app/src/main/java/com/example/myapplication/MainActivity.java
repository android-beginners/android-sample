package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    ListView lv;
    ArrayAdapter adapter;
    ArrayList<User> ds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        lv =findViewById(R.id.lvUser);
        ds = UserDAO.getALL(MainActivity.this);
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,ds);
        lv.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            showDialogCreate();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialogEdit(i);
            }
        });

    }

    private void showDialogEdit(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_user,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText editId = view.findViewById(R.id.editId);
        EditText editName = view.findViewById(R.id.editName);
        EditText editPhone = view.findViewById(R.id.editPhone);
        Button btnSave = view.findViewById(R.id.btnSaveEditUser);
        Button btnDel = view.findViewById(R.id.btnDeleteUser);
        User u = ds.get(pos);
        editId.setText(u.getId());
        editName.setText(u.getName());
        editPhone.setText(u.getPhone());


        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                u.setId(editId.getText().toString());
                u.setName(editName.getText().toString());
                u.setPhone(editPhone.getText().toString());

                if(UserDAO.upadte(MainActivity.this,u)){
                    Toast.makeText(MainActivity.this,"Update success!",Toast.LENGTH_SHORT).show();
                    ds.clear();
                    ds.addAll(UserDAO.getALL(MainActivity.this));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(UserDAO.delete(MainActivity.this,u.getId())){
                    Toast.makeText(MainActivity.this,"Delete success!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogCreate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view = inflater.inflate(R.layout.add_user,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText editId = view.findViewById(R.id.createId);
        EditText editName = view.findViewById(R.id.createName);
        EditText editPhone = view.findViewById(R.id.createPhone);
        Button btnSave = view.findViewById(R.id.btnSaveUser);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = editId.getText().toString();
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                if(UserDAO.insert(MainActivity.this,id,name,phone)){
                    Toast.makeText(MainActivity.this,"Create success!",Toast.LENGTH_SHORT).show();
                    ds.clear();
                    ds.addAll(UserDAO.getALL(MainActivity.this));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}