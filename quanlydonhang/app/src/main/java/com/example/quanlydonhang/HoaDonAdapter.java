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


public class HoaDonAdapter extends BaseAdapter {

    private HoaDonActivity context;
    private int layout;
    private List<HoaDon> listHoaDon;

    public HoaDonAdapter(HoaDonActivity context, int layout, List<HoaDon> listHoaDon) {
        this.context = context;
        this.layout = layout;
        this.listHoaDon = listHoaDon;
    }

    @Override
    public int getCount() {
        return listHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView textViewSoHD, textViewNgayLap, textViewNgayGiao, textViewMaKH;
        ImageView xoa, sua;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textViewNgayLap = convertView.findViewById(R.id.ngaylap);
            holder.textViewNgayGiao = convertView.findViewById(R.id.ngaygiao);
            holder.textViewMaKH = convertView.findViewById(R.id.makh);
            holder.xoa = convertView.findViewById(R.id.xoahd);
            holder.sua = convertView.findViewById(R.id.suahd);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HoaDon hoaDon = listHoaDon.get(position);
        holder.textViewNgayLap.setText(hoaDon.getNgaylap());
        holder.textViewNgayGiao.setText(hoaDon.getNgaygiao());
        holder.textViewMaKH.setText(String.valueOf(hoaDon.getMakh()));
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Xác nhận xoá " + hoaDon.getSohd(), Toast.LENGTH_LONG).show();
                context.dialogXoaCV(hoaDon.getSohd(), hoaDon.getNgaylap(), hoaDon.getNgaygiao(), String.valueOf(hoaDon.getMakh()));

            }
        });
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sửa " + hoaDon.getSohd(), Toast.LENGTH_LONG).show();
                context.dialogSuaCV(hoaDon.getSohd(), hoaDon.getNgaylap(), hoaDon.getNgaygiao(), String.valueOf(hoaDon.getMakh()));
            }
        });


        return convertView;
    }


}
