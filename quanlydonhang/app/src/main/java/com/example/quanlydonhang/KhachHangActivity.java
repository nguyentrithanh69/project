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

public class KhachHangActivity extends AppCompatActivity {
    DataBase dataBase;
    ListView listViewKhachHang;
    ArrayList<KhachHang> khachHangArrayList;
    KhachHangAdapter khachHangAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        listViewKhachHang= findViewById(R.id.listViewKhachHang);
        button=findViewById(R.id.buttonThem);
        khachHangArrayList= new ArrayList<>();
        khachHangAdapter= new KhachHangAdapter(this,R.layout.dong_cv,khachHangArrayList);
        listViewKhachHang.setAdapter(khachHangAdapter);
        dataBase = new DataBase(this, "ghichu1.sqlite", null,1);
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS KhachHang(Ma INTEGER PRIMARY KEY AUTOINCREMENT, TenKH NVARCHAR(250), DiaChiKH NVARCHAR(200), SDTKH NVARCHAR(10))");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Them();
            }
        });
        getDataCV();

    }

    private void getDataCV(){
        Cursor data= dataBase.Getdata("SELECT* FROM KhachHang");
        khachHangArrayList.clear();
        while (data.moveToNext()){
            String ten= data.getString(1);
            String diachi= data.getString(2);
            String sdt=data.getString(3);
            int ma= data.getInt(0);
            khachHangArrayList.add(new KhachHang(ma,ten,diachi,sdt));
        }
        khachHangAdapter.notifyDataSetChanged();
    }


    private void dialog_Them(){
        final Dialog dialogThem = new Dialog(KhachHangActivity.this);
        dialogThem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogThem.setContentView(R.layout.dialog_them);
        final EditText editTen=dialogThem.findViewById(R.id.editTenT);
        final EditText editDiaChi=dialogThem.findViewById(R.id.editDiaChiT);
        final EditText editSdt=dialogThem.findViewById(R.id.editSdtT);
        Button btnThem= dialogThem.findViewById(R.id.btnThem);
        Button btnHuy= dialogThem.findViewById(R.id.btnhuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThem.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten= editTen.getText().toString();
                String diachi= editDiaChi.getText().toString();
                String sdt= editSdt.getText().toString();
                if(ten.equals("")||diachi.equals("")||sdt.equals("")) {
                    Toast.makeText(KhachHangActivity.this, "Nhập đầy đủ trước", Toast.LENGTH_LONG).show();
                }else {
                    dataBase.QueryData
                            ("INSERT INTO KhachHang VALUES(null,'"+ten+"','"+diachi+"','"+sdt+"')");
                    Toast.makeText(KhachHangActivity.this, "Đã thêm khách hàng", Toast.LENGTH_LONG).show();
                    dialogThem.dismiss();
                    getDataCV();
                }
            }
        });
        dialogThem.show();
    }

    public void dialogSuaCV(String ten, final int id, String diaChi, String Sdt){
        final Dialog dialogSua= new Dialog(KhachHangActivity.this);
        dialogSua.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSua.setContentView(R.layout.dialog_sua);
        final EditText editTen= dialogSua.findViewById(R.id.editTenS);
        final EditText editDiaChi= dialogSua.findViewById(R.id.editDiaChiS);
        final EditText editSdt= dialogSua.findViewById(R.id.editSdtS);
        Button buttonSua=dialogSua.findViewById(R.id.btnSua1);
        Button buttonHuy=dialogSua.findViewById(R.id.btnHuy1);
        editTen.setText(ten);
        editDiaChi.setText(diaChi);
        editSdt.setText(Sdt);
        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diaChiMoi= editDiaChi.getText().toString();
                String sdtMoi= editSdt.getText().toString();
                String tenMoi= editTen.getText().toString().trim();
                dataBase.QueryData
                        ("UPDATE KhachHang SET TenKH = '"+tenMoi+"' WHERE Ma = '"+id+"'");
                dataBase.QueryData
                        ("UPDATE KhachHang SET DiaChiKH = '"+diaChiMoi+"' WHERE Ma = '"+id+"'");
                dataBase.QueryData
                        ("UPDATE KhachHang SET SDTKH = '"+sdtMoi+"' WHERE Ma = '"+id+"'");
                Toast.makeText(KhachHangActivity.this,"Đã cập nhật", Toast.LENGTH_LONG).show();
                dialogSua.dismiss();
                getDataCV();
            }
        });
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSua.dismiss();
            }
        });
        dialogSua.show();
    }

    public void dialogXoaCV(String ten, final int id, String diaChi, String sdt){
        final AlertDialog.Builder dialogXoa= new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa khách hàng "+ten +"?");
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataBase.QueryData ("DELETE FROM KhachHang WHERE Ma = '"+id+"'");
                Toast.makeText(KhachHangActivity.this,"Đã xóa", Toast.LENGTH_LONG).show();
                getDataCV();
            }
        });
        dialogXoa.show();
    }
}
