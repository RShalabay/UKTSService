package kz.ukteplo.uktsrepairs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.data.models.District;

public class DistrictAdapter extends ArrayAdapter<District> {
    private final int layout;
    private final List<District> districts;
    private final LayoutInflater inflater;

    public DistrictAdapter(@NonNull Context context, int resourse, List<District> districts) {
        super(context, resourse, districts);
        this.layout = resourse;
        this.districts = districts;
        this.inflater =LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextView tvDistrict = convertView.findViewById(R.id.tv_district);
        District district = districts.get(position);
        viewHolder.tvDistrict.setText(district.getName());

        return convertView;
    }

    public List<District> getDistricts() {
        return districts;
    }

    private class ViewHolder {
        final TextView tvDistrict;

        ViewHolder(View view) {
            tvDistrict = view.findViewById(R.id.tv_district);
        }
    }
}
