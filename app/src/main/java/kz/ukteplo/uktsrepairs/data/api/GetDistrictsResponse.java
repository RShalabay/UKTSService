package kz.ukteplo.uktsrepairs.data.api;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.District;

public class GetDistrictsResponse extends BaseResponse {
    private List<District> districts;

    public GetDistrictsResponse(Boolean success, Float errorCode, String errorText, String errorDescription, List<District> districts) {
        super(success, errorCode, errorText, errorDescription);
        this.districts = districts;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
