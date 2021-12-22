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
import kz.ukteplo.uktsrepairs.data.models.Route;

public class RouteAdapter extends ArrayAdapter<Route> {
    private final int layout;
    private final List<Route> routes;
    private final LayoutInflater inflater;

    public RouteAdapter(@NonNull Context context, int resourse, List<Route> routes) {
        super(context, resourse, routes);
        this.layout = resourse;
        this.routes = routes;
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
        TextView tvDistrict = convertView.findViewById(R.id.tv_route);
        Route route = routes.get(position);
        viewHolder.tvDRoutes.setText(route.getName());

        return convertView;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    private class ViewHolder {
        final TextView tvDRoutes;

        ViewHolder(View view) {
            tvDRoutes = view.findViewById(R.id.tv_route);
        }
    }
}
