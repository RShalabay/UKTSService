package kz.ukteplo.uktsrepairs.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UKTSApiService {
    //Регистрация
    @POST("?signUp")
    Call<BaseResponse> regUser(@Body RegUserRequest request);

    //Авторизация
    @POST("?logIn")
    Call<AuthResponse> login(@Body AuthRequest request);

    //Справочник районов
    @POST("?getDistrictsList")
    Call<GetDistrictsResponse> getDistricts(@Body BaseRequest request);

    //Справочник магистралей
    @POST("?getRoutesList")
    Call<GetRoutesResponse> getRoutes(@Body BaseRequest request);

    //График ремонтов
    @POST("?getRepairsList")
    Call<GetRepairsListResponse> getRepairs(@Body BaseRequest request);

    //Информация по ремонту
    @POST("?getRepairData")
    Call<GetRepairDataResponse> getRepairData(@Body GetRepairDataRequest request);

    //Добавление нового ремонта
    @POST("?addNewRepair")
    Call<BaseResponse> addNewRepair(@Body AddNewRepairRequest request);

    //Передача информации по указанному ремонту
    @POST("?setRepairData")
    Call<BaseResponse> setRepairData(@Body AddNewRepairRequest request);

    //Согласование
    @POST("?setRepairApproveStatus")
    Call<BaseResponse> setRepairApproveStatus(@Body SetRepairApproveStatusRequest request);

    //Передача плана ремонта
    @POST("?setRepairPlanId")
    Call<BaseResponse> setRepairPlanId(@Body SetRepairPlanIdRequest request);

    //Передача плана ремонта
    @POST("?cancelRepair")
    Call<BaseResponse> deleteRepair(@Body GetRepairDataRequest request);

}
