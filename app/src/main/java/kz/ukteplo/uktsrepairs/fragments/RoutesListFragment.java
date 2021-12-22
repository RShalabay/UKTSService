package kz.ukteplo.uktsrepairs.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.adapters.RouteAdapter;
import kz.ukteplo.uktsrepairs.data.models.Route;
import kz.ukteplo.uktsrepairs.data.viewmodels.RouteViewModel;
import kz.ukteplo.uktsrepairs.utils.SelectedItemListener;

public class RoutesListFragment extends Fragment {
    private List<Route> routeList;
    private RouteViewModel viewModel;
    private Route selectedRoute;
    private SelectedItemListener listener;
    public RepairFragment context;
    public String districtId;

    public RoutesListFragment() { super(R.layout.fragment_routes); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this). get(RouteViewModel.class);
        viewModel.initData(districtId);
        viewModel.getAll().observe(getViewLifecycleOwner(), routes -> {
            routeList = routes;
            if (routeList.isEmpty()) {
                viewModel.downloadRoutes();
            }

            ListView lv = view.findViewById(R.id.lvRoutes);
            RouteAdapter adapter = new RouteAdapter(getContext(), R.layout.list_item_route, routeList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener((parent, view1, position, id) -> {
                selectedRoute = routeList.get(position);
                listener = (SelectedItemListener) context;
                listener.selectRoute(selectedRoute);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, context, null).commit();
            });
        });
    }
}
