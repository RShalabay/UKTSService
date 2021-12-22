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
import kz.ukteplo.uktsrepairs.adapters.DistrictAdapter;
import kz.ukteplo.uktsrepairs.data.models.District;
import kz.ukteplo.uktsrepairs.data.viewmodels.DistrictViewModel;
import kz.ukteplo.uktsrepairs.utils.SelectedItemListener;

public class DistrictsListFragment extends Fragment {
    private List<District> districtsList;
    private DistrictViewModel viewModel;
    private District selectedDistrict;
    private SelectedItemListener listener;
    public RepairFragment context;

    public DistrictsListFragment() { super(R.layout.fragment_districts); }

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

        viewModel = ViewModelProviders.of(this).get(DistrictViewModel.class);
        viewModel.initData();
        viewModel.getAll().observe(getViewLifecycleOwner(), districts -> {
            districtsList = districts;
            if (districtsList.isEmpty() || districtsList == null) {
                viewModel.downloadDistricts();
            }

            ListView lv = view.findViewById(R.id.lvDistricts);
            DistrictAdapter adapter = new DistrictAdapter(getContext(), R.layout.list_item_district, districtsList);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener((parent, view1, position, id) -> {
                selectedDistrict = districtsList.get(position);
                listener = (SelectedItemListener) context;
                listener.selectDistrict(selectedDistrict);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, context, null).commit();
            });
        });
    }
}
