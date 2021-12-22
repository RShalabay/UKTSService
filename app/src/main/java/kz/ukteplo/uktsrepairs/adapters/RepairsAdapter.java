package kz.ukteplo.uktsrepairs.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import java.util.List;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.data.models.Repair;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;

public class RepairsAdapter extends ArrayAdapter<Repair> {
    private final int layout;
    private final List<Repair> repairs;
    private final LayoutInflater inflater;
    private ConfigManager configManager;

    public RepairsAdapter(@NonNull Context context, int resourse, List<Repair> repairs) {
        super(context, resourse, repairs);
        this.layout = resourse;
        this.repairs = repairs;
        this.inflater = LayoutInflater.from(context);
        this.configManager = new ConfigManager(context);
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

        TextView tvDb = convertView.findViewById(R.id.date_begin);
        TextView tvDe = convertView.findViewById(R.id.date_end);
        TextView tvDs = convertView.findViewById(R.id.description);
        TextView tvEmpl = convertView.findViewById(R.id.employee);
        TextView tvAppr = convertView.findViewById(R.id.is_approved);

        Repair repair = repairs.get(position);

        viewHolder.tvDb.setText(repair.getBeginDate());
        viewHolder.tvDe.setText(repair.getEndDate());
        viewHolder.tvDs.setText(repair.getText());
        viewHolder.tvEmpl.setText(repair.getEmployeeName());
        viewHolder.tvAppr.setText(repair.getIsApproved());
        if (repair.getIsApproved().equals("Согласовано")) {
            viewHolder.setCvColor(Color.rgb(0, 120, 0));
        } else {
            viewHolder.setCvColor(Color.rgb(160, 0, 0));
            viewHolder.tv_failture_reason.setVisibility(View.VISIBLE);
            viewHolder.tv_failture_reason.setText(repair.getComment());
        }

        if (repair.getIsCancelled().equals("Y")) {
            viewHolder.setCardBackgroundColor("#f5dcdc");
        } else {
            viewHolder.setCardBackgroundColor("#ffffff");
        }

        if (configManager.getRole().equals("3")) {
            if (configManager.getUser().getLogin().equals(repair.getEmployee())) {
                viewHolder.setColorToAll(Color.BLACK);
            } else {
                viewHolder.setColorToAll(Color.GRAY);
            }
        }

        if (configManager.getRole().equals("2")) {
            if (repair.getPlanId() == null) {
                viewHolder.setColorToAll(Color.BLACK);
            } else {
                viewHolder.setColorToAll(Color.GRAY);
            }
        }

        if (configManager.getRole().equals("1")) {
            if (repair.getIsApproved().isEmpty()) {
                viewHolder.setColorToAll(Color.BLACK);
            } else {
                viewHolder.setColorToAll(Color.GRAY);
            }
        }

        return convertView;
    }


    private class ViewHolder {
        final TextView tvDb, tvDe, tvDs, tvEmpl, tvAppr, tv_failture_reason;
        final CardView cv;

        ViewHolder(View view) {
            cv = view.findViewById(R.id.cv);
            tvDb = view.findViewById(R.id.date_begin);
            tvDe = view.findViewById(R.id.date_end);
            tvDs = view.findViewById(R.id.description);
            tvEmpl = view.findViewById(R.id.employee);
            tvAppr = view.findViewById(R.id.is_approved);
            tv_failture_reason = view.findViewById(R.id.tv_failture_reason);
        }

        void setColorToAll(int color) {
            tvDb.setTextColor(color);
            tvDe.setTextColor(color);
            tvDs.setTextColor(color);
            tvEmpl.setTextColor(color);
        }

        void setCvColor(int color) {
            tvAppr.setTextColor(color);
        }

        void setCardBackgroundColor(String color) { cv.setCardBackgroundColor(Color.parseColor(color)); }
    }
}
