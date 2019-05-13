package com.example.quanlydonhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class KhachHangAdapter extends BaseAdapter {

    private KhachHangActivity context;
    private int layout;
    private List<KhachHang> listKhachHang;

    public KhachHangAdapter(KhachHangActivity context, int layout, List<KhachHang> listKhachHang) {
        this.context = context;
        this.layout = layout;
        this.listKhachHang = listKhachHang;
    }



    @Override
    public int getCount() {
        return listKhachHang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView textViewTen, textViewDiaChi,textViewSdt;
        ImageView xoa, sua;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder= new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.textViewTen= convertView.findViewById(R.id.tenkh);
            holder.textViewDiaChi= convertView.findViewById(R.id.diachi);
            holder.textViewSdt= convertView.findViewById(R.id.sdt);
            holder.xoa = convertView.findViewById(R.id.xoa);
            holder.sua = convertView.findViewById(R.id.sua);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        final KhachHang khachHang= listKhachHang.get(position);
        holder.textViewTen.setText(khachHang.getTen());
        holder.textViewDiaChi.setText(khachHang.getDiachi());
        holder.textViewSdt.setText(khachHang.getSdt());
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Xác nhận xoá "+khachHang.getTen(), Toast.LENGTH_LONG).show();
                context.dialogXoaCV(khachHang.getTen(),khachHang.getMa(),khachHang.getDiachi(),khachHang.getSdt());

            }
        });
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sửa "+khachHang.getTen(), Toast.LENGTH_LONG).show();
                context.dialogSuaCV(khachHang.getTen(),khachHang.getMa(), khachHang.getDiachi(), khachHang.getSdt());
            }
        });


        return convertView;
    }


}
