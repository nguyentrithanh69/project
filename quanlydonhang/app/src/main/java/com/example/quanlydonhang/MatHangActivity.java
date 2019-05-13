package com.example.quanlydonhang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MatHangActivity extends AppCompatActivity {
    DataBase dataBase;
    ListView listViewMatHang;
    ArrayList<MatHang> matHangArrayList;
    MatHangAdapter matHangAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang);
        listViewMatHang= findViewById(R.id.listViewMatHang);
        button=findViewById(R.id.buttonThem2);
        matHangArrayList= new ArrayList<>();
        matHangAdapter= new MatHangAdapter(this, R.layout.dong_cvmh, matHangArrayList);
        listViewMatHang.setAdapter(matHangAdapter);
        matHangAdapter.notifyDataSetChanged();

        dataBase = new DataBase(this, "ghichu1.sqlite", null,1);
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS MatHang(MaHang INTEGER PRIMARY KEY AUTOINCREMENT, TenHang NVARCHAR(250), DVT NVARCHAR(20), DonGia INTEGER)");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Themmh();
            }
        });
        getDataCV();
    }

    private void getDataCV(){
        Cursor data= dataBase.Getdata("SELECT * FROM MatHang");
        matHangArrayList.clear();
        while (data.moveToNext()){
            String tenhang= data.getString(1);
            String donvitinh= data.getString(2);
            int dongia = data.getInt(3);
            int mahang = data.getInt(0);
            matHangArrayList.add(new MatHang(mahang,tenhang,donvitinh,dongia));
        }
        matHangAdapter.notifyDataSetChanged();
    }


    private void dialog_Themmh(){
        final Dialog dialogThemmh = new Dialog(MatHangActivity.this);
        dialogThemmh.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogThemmh.setContentView(R.layout.dialog_themmh);
        final EditText editTenHang=dialogThemmh.findViewById(R.id.editTenHangT);
        final EditText editDVT=dialogThemmh.findViewById(R.id.editDVTT);
        final EditText editDonGia=dialogThemmh.findViewById(R.id.editDonGiaT);
        Button btnThemmh= dialogThemmh.findViewById(R.id.btnThemmh);
        Button btnHuymh= dialogThemmh.findViewById(R.id.btnhuymh);
        btnHuymh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemmh.dismiss();
            }
        });
        btnThemmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenhang = editTenHang.getText().toString();
                String dvt = editDVT.getText().toString();
                String dongia = editDonGia.getText().toString();
                if(tenhang.equals("")||dvt.equals("")||dongia.equals("")) {
                    Toast.makeText(MatHangActivity.this, "Nhập đầy đủ trước", Toast.LENGTH_LONG).show();
                }else {
                    dataBase.QueryData
                            ("INSERT INTO MatHang VALUES(null,'"+tenhang+"','"+dvt+"','"+dongia+"')");
                    Toast.makeText(MatHangActivity.this, "Đã thêm mặt hàng", Toast.LENGTH_LONG).show();
                    dialogThemmh.dismiss();
                    getDataCV();
                }
            }
        });
        dialogThemmh.show();
    }

    public void dialogSuaCV(String tenhang, final int id, String dvt, int dongia){
        final Dialog dialogSuamh= new Dialog(MatHangActivity.this);
        dialogSuamh.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuamh.setContentView(R.layout.dialog_suamh);
        final EditText editTenHang= dialogSuamh.findViewById(R.id.editTenHangS);
        final EditText editDVT= dialogSuamh.findViewById(R.id.editDVTS);
        final EditText editDonGia= dialogSuamh.findViewById(R.id.editDonGiaS);
        Button buttonSua=dialogSuamh.findViewById(R.id.btnSua2);
        Button buttonHuy=dialogSuamh.findViewById(R.id.btnHuy2);
        editTenHang.setText(tenhang);
        editDVT.setText(dvt);
        editDonGia.setText(String.valueOf(dongia));
        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenHangMoi= editTenHang.getText().toString();
                String dvtMoi= editDVT.getText().toString();
                String dongiaMoi= editDonGia.getText().toString().trim();
                dataBase.QueryData
                        ("UPDATE MatHang SET TenHang = '"+tenHangMoi+"' WHERE Ma = '"+id+"'");
                dataBase.QueryData
                        ("UPDATE MatHang SET DVT = '"+dvtMoi+"' WHERE Ma = '"+id+"'");
                dataBase.QueryData
                        ("UPDATE MatHang SET DonGia = '"+dongiaMoi+"' WHERE Ma = '"+id+"'");
                Toast.makeText(MatHangActivity.this,"Đã cập nhật", Toast.LENGTH_LONG).show();
                dialogSuamh.dismiss();
                getDataCV();
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuamh.dismiss();
            }
        });
        dialogSuamh.show();
    }

    public void dialogXoaCV(String tenhang, final int id, String dvt, String dongia){
        final AlertDialog.Builder dialogXoamh = new AlertDialog.Builder(this);
        dialogXoamh.setMessage("Bạn có muốn xóa mặt hàng "+tenhang +"?");
        dialogXoamh.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoamh.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataBase.QueryData ("DELETE FROM MatHang WHERE Mahang = '"+id+"'");
                Toast.makeText(MatHangActivity.this,"Đã xóa", Toast.LENGTH_LONG).show();
                getDataCV();
            }
        });
        dialogXoamh.show();
    }
}
