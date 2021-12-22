package kz.ukteplo.uktsrepairs.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kz.ukteplo.uktsrepairs.R;
import kz.ukteplo.uktsrepairs.data.api.BaseResponse;
import kz.ukteplo.uktsrepairs.data.models.District;
import kz.ukteplo.uktsrepairs.data.models.RepairData;
import kz.ukteplo.uktsrepairs.data.models.Route;
import kz.ukteplo.uktsrepairs.data.viewmodels.DistrictViewModel;
import kz.ukteplo.uktsrepairs.data.viewmodels.RepairDataViewModel;
import kz.ukteplo.uktsrepairs.data.viewmodels.RouteViewModel;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.OnDialogClickListener;
import kz.ukteplo.uktsrepairs.utils.SelectedItemListener;

public class RepairFragment extends Fragment implements SelectedItemListener, OnDialogClickListener {
    public String id = "0";
    private RepairData repairData;
    private RepairDataViewModel viewModel;
    private RepairFragment context;
    private District district;
    public RepairsListFragment repairsListFragment;
    public String isApproved = "Отказано";
    public String actionType = "";
    //public Boolean dialogResult = false;

    public EditText et_discription, et_d_begin, et_d_end, et_district, et_route, et_failture_reason, et_planId;
    private LinearLayout plane_id_layout, approve, blockFailure;
    private Button btn_save, btn_begin_cal, btn_end_cal, btn_district, btn_route, btn_approve, btn_failture, btn_delete;
    private Calendar dateAndTime = Calendar.getInstance();
    private EditText currentDate = et_d_begin;
    private TextView action;

    public RepairFragment(String id) {
        super(R.layout.fragment_repair);
        this.id = id;
        this.context = this;
    }

    public RepairFragment() {
        super(R.layout.fragment_repair);
        this.context = this;
        repairData = new RepairData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findControls(view);
        setButtonListeners(view);

        if (!id.equals("0")) {
            viewModel = ViewModelProviders.of(this).get(RepairDataViewModel.class);
            viewModel.initData(id);
            viewModel.getRepairData().observe(getViewLifecycleOwner(), getRepairDataResponse -> {
                repairData = getRepairDataResponse.getRepairParams().get(0);
                showControlsWithRole(view);
                showRepairData(repairData);
            });
        } else {
            showControlsWithRole(view);
            showRepairData(repairData);
        }
    }

    private void setButtonListeners(View view) {
        DatePickerDialog.OnDateSetListener d = (view1, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime(currentDate);
        };

        btn_begin_cal.setOnClickListener(v -> {
            currentDate = et_d_begin;
            new DatePickerDialog(getContext(), d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        btn_end_cal.setOnClickListener(v -> {
            currentDate = et_d_end;
            new DatePickerDialog(getContext(), d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        btn_district.setOnClickListener(v -> {
            DistrictsListFragment districtsListFragment = new DistrictsListFragment();
            districtsListFragment.context = context;
            getActivity().getSupportFragmentManager().beginTransaction()
                    .hide(context)
                    .add(R.id.fragment_container_view, districtsListFragment, null)
                    //.addToBackStack(null)
                    .commit();
        });

        btn_route.setOnClickListener(v -> {
            if (context.district == null) {
                Toast.makeText(getContext(), "Не выбран район!", Toast.LENGTH_SHORT).show();
                return;
            }
            RoutesListFragment routesListFragment = new RoutesListFragment();
            routesListFragment.context = context;
            routesListFragment.districtId = context.district.getId().toString();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .hide(context)
                    .add(R.id.fragment_container_view, routesListFragment, null)
                    //.addToBackStack(null)
                    .commit();
        });

        btn_save.setOnClickListener(v -> {
            if (new ConfigManager(getContext()).getRole().equals("3")) {
                repairData.setText(et_discription.getText().toString());
                repairData.setBeginDate(et_d_begin.getText().toString());
                repairData.setEndDate(et_d_end.getText().toString());
                if (repairData.getId() == null) {
                    sendNewRepair();
                } else {
                    sendUpdateRepair();
                }
            } else {
                sendPlanId();
            }

        });

        btn_approve.setOnClickListener(v -> {
            viewModel.setApprove(repairData.getId(), "Y", "")
                    .observe(getViewLifecycleOwner(), new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(BaseResponse baseResponse) {
                            if (baseResponse.getSuccess()) {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        }
                    });
        });

        btn_failture.setOnClickListener(v -> {
            if (et_failture_reason.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Не заполнена причина отказа!" , Toast.LENGTH_SHORT).show();
            } else {
                viewModel.setApprove(repairData.getId(), "N", et_failture_reason.getText().toString())
                        .observe(getViewLifecycleOwner(), new Observer<BaseResponse>() {
                            @Override
                            public void onChanged(BaseResponse baseResponse) {
                                if (baseResponse.getSuccess()) {
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                            }
                        });
            }
        });

        btn_delete.setOnClickListener(v -> {
            showDialog("Внимание!", "Отменить ремонт", "repair");

        });
    }

    private void sendPlanId() {
        if (et_planId.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Plan ID не заполнен!", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.setPlanId(repairData.getId(), et_planId.getText().toString())
                    .observe(getViewLifecycleOwner(), new Observer<BaseResponse>() {
                        @Override
                        public void onChanged(BaseResponse baseResponse) {
                            if (baseResponse.getSuccess()) {
                                getActivity().getSupportFragmentManager().popBackStack();
                            }
                        }
                    });
        }
    }

    private void sendUpdateRepair() {
        viewModel.setRepairData(repairData).observe(getViewLifecycleOwner(), new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse.getSuccess()) {
                    repairsListFragment = null;
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    private void sendNewRepair() {
        viewModel = ViewModelProviders.of(this).get(RepairDataViewModel.class);
        viewModel.addNewRepair(repairData).observe(getViewLifecycleOwner(), new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse.getSuccess()) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    private void findControls(View view) {
        et_discription = view.findViewById(R.id.et_discription);
        et_d_begin = view.findViewById(R.id.et_d_begin);
        et_d_end = view.findViewById(R.id.et_d_end);
        et_district = view.findViewById(R.id.et_district);
        et_route = view.findViewById(R.id.et_route);
        btn_save = view.findViewById(R.id.btn_save);
        plane_id_layout = view.findViewById(R.id.plane_id_layout);
        approve = view.findViewById(R.id.Approve);
        blockFailure = view.findViewById(R.id.blockFailure);
        et_failture_reason = view.findViewById(R.id.et_failture_reason);
        btn_begin_cal = view.findViewById(R.id.btn_begin_cal);
        btn_end_cal = view.findViewById(R.id.btn_end_cal);
        btn_district = view.findViewById(R.id.btn_district);
        btn_route = view.findViewById(R.id.btn_route);
        btn_approve = view.findViewById(R.id.btn_approve);
        btn_failture = view.findViewById(R.id.btn_failture);
        et_planId = view.findViewById(R.id.et_planId);
        btn_delete = view.findViewById(R.id.btn_delete);
        action = view.findViewById(R.id.action);
    }

    private void setInitialDateTime(EditText et) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "dd.MM.yyyy");
        et.setText(sdf.format(dateAndTime.getTime()));
    }

    private void showRepairData(RepairData repairData) {
        et_d_begin.setText(repairData.getBeginDate());
        et_d_end.setText(repairData.getEndDate());
        et_discription.setText(repairData.getText());
        et_planId.setText(repairData.getPlanId());

        //if (this.district == null) {
            if (repairData.getDistrictId() != null) {
                DistrictViewModel dvm = ViewModelProviders.of(this).get(DistrictViewModel.class);
                dvm.getDistrictById(repairData.getDistrictId())
                        .observe(getViewLifecycleOwner(), district -> et_district.setText(district.getName()));
            }
            if (repairData.getRouteId() != null) {
                RouteViewModel rvm = ViewModelProviders.of(this).get(RouteViewModel.class);
                rvm.getRouteById(Integer.parseInt(repairData.getRouteId()))
                        .observe(getViewLifecycleOwner(), route -> et_route.setText(route.getName()));
            }
       // }
    }

    private void showControlsWithRole(View view) {
        String role = new ConfigManager(getContext()).getRole();
        switch (role) {
            case "0":
                showForbidden();
                break;
            case "1":
                showTechDirector();
                break;
            case "2":
                showDispatcher();
                break;
            case "3":
                showDistrictChief();
                break;
        }
        if (!actionType.isEmpty()) {
            action.setVisibility(View.VISIBLE);
            action.setTextColor(Color.BLUE);
            switch (actionType)  {
                case "1":
                    action.setText("Создан новый ремонт");
                    break;
                case "2":
                    action.setText("Ремонт был изменен");
                    break;
                case "3":
                    action.setText("Ремонт был согласован");
                    break;
                case "4":
                    action.setText("В согласовании ремонта отказано");
                    break;
                case "5":
                    action.setText("Ремонт был отменен");
                    break;
            }

        }
    }

    private void showForbidden() {
        et_discription.setEnabled(false);
        et_d_begin.setEnabled(false);
        et_d_end.setEnabled(false);
        et_district.setEnabled(false);
        et_route.setEnabled(false);
        btn_begin_cal.setEnabled(false);
        btn_end_cal.setEnabled(false);
        btn_district.setEnabled(false);
        btn_route.setEnabled(false);
    }

    private void showDistrictChief() {
        et_discription.setEnabled(true);
        et_d_end.setEnabled(true);

        btn_save.setVisibility(View.VISIBLE);

        if (!id.equals("0")) {
            et_district.setEnabled(false);
            et_route.setEnabled(false);
            btn_district.setEnabled(false);
            btn_route.setEnabled(false);
        }

        btn_delete.setVisibility(View.VISIBLE);
    }

    private void showTechDirector() {
        et_discription.setEnabled(false);
        et_d_begin.setEnabled(false);
        et_d_end.setEnabled(false);
        et_district.setEnabled(false);
        et_route.setEnabled(false);
        btn_district.setEnabled(false);
        btn_route.setEnabled(false);
        btn_begin_cal.setEnabled(false);
        btn_end_cal.setEnabled(false);
        approve.setVisibility(View.VISIBLE);
        blockFailure.setVisibility(View.VISIBLE);
    }

    private void showDispatcher() {
        et_discription.setEnabled(false);
        et_d_begin.setEnabled(false);
        et_d_end.setEnabled(false);
        et_district.setEnabled(false);
        et_route.setEnabled(false);
        btn_district.setEnabled(false);
        btn_route.setEnabled(false);
        btn_begin_cal.setEnabled(false);
        btn_end_cal.setEnabled(false);
        plane_id_layout.setVisibility(View.VISIBLE);
        if (repairData.getPlanId() != null) {
            et_planId.setEnabled(false);
        }
        btn_save.setVisibility(View.VISIBLE);
    }

    @Override
    public void selectDistrict(District district) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .show(context)
                .commit();
        Log.d("district", district.getName().toString());
        this.district = district;
        et_district.setText(district.getName());
        repairData.setDistrictId(this.district.getId().toString());
        et_route.setText("");
        repairData.setRouteId("");
    }

    @Override
    public void selectRoute(Route route) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .show(context)
                .commit();
        et_route.setText(route.getName());
        repairData.setRouteId(route.getId().toString());
    }

    @Override
    public void alert(Boolean result) {
        if (result) {
            viewModel.deleteRepair(repairData.getId())
                    .observe(getViewLifecycleOwner(), baseResponse -> {
                        if (baseResponse.getSuccess()) {
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
        }
    }

    public void showDialog(String title, String msg, String tag) {
        AlertDialog dialog = new AlertDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", msg);
        dialog.setArguments(args);
        dialog.show(getActivity().getSupportFragmentManager(), tag);
    }
}
