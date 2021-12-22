package kz.ukteplo.uktsrepairs.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.adapters.RepairsAdapter;
import kz.ukteplo.uktsrepairs.data.models.Repair;
import kz.ukteplo.uktsrepairs.data.viewmodels.RepairViewModel;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.FragmentDataListener;

public class RepairsListFragment extends Fragment {
    private List<Repair> repairList = null;
    private RepairViewModel viewModel;
    private Repair selectedRepair;
    private FragmentDataListener listener;
    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public RepairsListFragment() { super(R.layout.fragment_repairs); }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        listener = (FragmentDataListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showRepairsList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void showRepairsList() {
        //if (repairList == null) {
            Log.d("repairs list", "getRepairs");
            viewModel = ViewModelProviders.of(this).get(RepairViewModel.class);
            viewModel.initData();
            viewModel.getAll().observe(getViewLifecycleOwner(), repairs -> {
                repairList = repairs.getRepairs();
                ListView lv = view.findViewById(R.id.lvRepairs);
                RepairsAdapter adapter = new RepairsAdapter(getContext(), R.layout.list_item_repair, repairList);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener((parent, view1, position, id) -> {
                    selectedRepair = repairList.get(position);
                    ConfigManager configManager = new ConfigManager(getContext());
                    switch (configManager.getRole()) {
                        case "1":
                            if (selectedRepair.getIsApproved().equals("Новый ремонт")) {
                                listener.selectRepair(selectedRepair.getId());
                            }
                            break;
                        case "2":
                            if (selectedRepair.getIsApproved().equals("Согласовано") && selectedRepair.getIsCompleted().isEmpty()) {
                                listener.selectRepair(selectedRepair.getId());
                            }
                            break;
                        case "3":
                            if (selectedRepair.getEmployee() != null) {
                                if (selectedRepair.getEmployee().equals(configManager.getUser().getLogin())) {
                                    listener.selectRepair(selectedRepair.getId(), selectedRepair.getIsApproved(), "");
                                }
                            }
                            break;
                    }
                });
            });
        //}
    }

    @Override
    public void onResume() {
        showRepairsList();
        super.onResume();
    }
}
