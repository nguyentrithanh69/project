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


public class MatHangAdapter extends BaseAdapter {

    private MatHangActivity context;
    private int layout;
    private List<MatHang> listMatHang;

    public MatHangAdapter(MatHangActivity context, int layout, List<MatHang> listMatHang) {
        this.context = context;
        this.layout = layout;
        this.listMatHang = listMatHang;
    }

    @Override
    public int getCount() {
        return listMatHang.size();
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
        TextView textViewTenHang, textViewDVT,textViewDonGia;
        ImageView xoa, sua;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder= new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.textViewTenHang= convertView.findViewById(R.id.tenhang);
            holder.textViewDVT= convertView.findViewById(R.id.dvt);
            holder.textViewDonGia= convertView.findViewById(R.id.dongia);
            holder.xoa= convertView.findViewById(R.id.xoamh);
            holder.sua= convertView.findViewById(R.id.suamh);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        final MatHang matHang= listMatHang.get(position);
        holder.textViewTenHang.setText(matHang.getTenhang());
        holder.textViewDVT.setText(matHang.getDvt());
        holder.textViewDonGia.setText(String.valueOf(matHang.getDongia()));
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Xác nhận xoá "+matHang.getTenhang(), Toast.LENGTH_LONG).show();
                context.dialogXoaCV(matHang.getTenhang(),matHang.getMahang(),matHang.getDvt(), String.valueOf(matHang.getDongia()));

            }
        });
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sửa "+matHang.getTenhang(), Toast.LENGTH_LONG).show();
                context.dialogSuaCV(matHang.getTenhang(),matHang.getMahang(), matHang.getDvt(), matHang.getDongia());
            }
        });


        return convertView;
    }


}
