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

public class HoaDonActivity extends AppCompatActivity {
    DataBase dataBase;
    ListView listViewHoaDon;
    ArrayList<HoaDon> hoaDonArrayList;
    HoaDonAdapter hoaDonAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        listViewHoaDon= findViewById(R.id.listViewHoaDon);
        button=findViewById(R.id.buttonThem3);
        hoaDonArrayList = new ArrayList<>();
        hoaDonAdapter = new HoaDonAdapter(this, R.layout.dong_cvhd, hoaDonArrayList);
        listViewHoaDon.setAdapter(hoaDonAdapter);
        hoaDonAdapter.notifyDataSetChanged();

        dataBase = new DataBase(this, "ghichu1.sqlite", null,1);
        dataBase.QueryData("CREATE TABLE IF NOT EXISTS HoaDon(SoHD INTEGER PRIMARY KEY AUTOINCREMENT, NgayLap NVARCHAR(100), NgayGiao NVARCHAR(100), MaKH INTEGER)");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Themhd();
            }
        });
        getDataCV();
    }

    private void getDataCV(){
        Cursor data= dataBase.Getdata("SELECT * FROM HoaDon");
        hoaDonArrayList.clear();
        while (data.moveToNext()){
            String ngaylap = data.getString(1);
            String ngaygiao = data.getString(2);
            int makh = data.getInt(3);
            int sohd = data.getInt(0);
            hoaDonArrayList.add(new HoaDon(sohd,ngaylap,ngaygiao,makh));
        }
        hoaDonAdapter.notifyDataSetChanged();
    }


    private void dialog_Themhd(){
        final Dialog dialogThemhd = new Dialog(HoaDonActivity.this);
        dialogThemhd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogThemhd.setContentView(R.layout.dialog_themhd);
        final EditText editNgayLap=dialogThemhd.findViewById(R.id.editNgayLapT);
        final EditText editNgayGiao=dialogThemhd.findViewById(R.id.editNgayGiaoT);
        final EditText editMaKH=dialogThemhd.findViewById(R.id.editMaKHT);
        Button btnThemhd= dialogThemhd.findViewById(R.id.btnThemhd);
        Button btnHuyhd= dialogThemhd.findViewById(R.id.btnhuyhd);
        btnHuyhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemhd.dismiss();
            }
        });
        btnThemhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaylap = editNgayLap.getText().toString();
                String ngaygiao = editNgayGiao.getText().toString();
                String makh = editMaKH.getText().toString();
                if(ngaylap.equals("")||ngaygiao.equals("")||makh.equals("")) {
                    Toast.makeText(HoaDonActivity.this, "Nhập đầy đủ trước", Toast.LENGTH_LONG).show();
                }else {
                    dataBase.QueryData
                            ("INSERT INTO HoaDon VALUES(null,'"+ngaylap+"','"+ngaygiao+"','"+makh+"')");
                    Toast.makeText(HoaDonActivity.this, "Đã thêm hoá đơn", Toast.LENGTH_LONG).show();
                    dialogThemhd.dismiss();
                    getDataCV();
                }
            }
        });
        dialogThemhd.show();
    }

    public void dialogSuaCV(final int id, String ngaylap, String ngaygiao, String makh){
        final Dialog dialogSuahd = new Dialog(HoaDonActivity.this);
        dialogSuahd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuahd.setContentView(R.layout.dialog_suahd);
        final EditText editNgayLap = dialogSuahd.findViewById(R.id.editNgayLapS);
        final EditText editNgayGiao= dialogSuahd.findViewById(R.id.editNgayGiaoS);
        final EditText editMaKH= dialogSuahd.findViewById(R.id.editMaKHS);

        Button buttonSua = dialogSuahd.findViewById(R.id.btnSua3);
        Button buttonHuy = dialogSuahd.findViewById(R.id.btnHuy3);

        editNgayLap.setText(ngaylap);
        editNgayGiao.setText(ngaygiao);
        editMaKH.setText(makh);

        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaylapMoi = editNgayLap.getText().toString();
                String ngaygiaoMoi = editNgayGiao.getText().toString();
                String makhMoi = editMaKH.getText().toString().trim();
                dataBase.QueryData
                        ("UPDATE HoaDon SET NgayLap = '"+ngaylapMoi+"' WHERE SoHD = '"+id+"'");
                dataBase.QueryData
                        ("UPDATE HoaDon SET NgayGiao = '"+ngaygiaoMoi+"' WHERE SoHD = '"+id+"'");
                dataBase.QueryData
                        ("UPDATE HoaDon SET MaKH = '"+makhMoi+"' WHERE SoHD = '"+id+"'");
                Toast.makeText(HoaDonActivity.this,"Đã cập nhật", Toast.LENGTH_LONG).show();
                dialogSuahd.dismiss();
                getDataCV();
            }
        });

        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuahd.dismiss();
            }
        });

        dialogSuahd.show();
    }

    public void dialogXoaCV(final int id, String ngaylap, String ngaygiao, String makh ){
        final AlertDialog.Builder dialogXoahd = new AlertDialog.Builder(this);
        dialogXoahd.setMessage("Bạn có muốn xóa hoá đơn "+ id +"?");
        dialogXoahd.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoahd.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataBase.QueryData ("DELETE FROM HoaDon WHERE SoHD = '"+id+"'");
                Toast.makeText(HoaDonActivity.this,"Đã xóa", Toast.LENGTH_LONG).show();
                getDataCV();
            }
        });
        dialogXoahd.show();
    }
}
