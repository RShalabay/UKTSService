package kz.ukteplo.uktsrepairs.data.api;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.Route;

public class GetRoutesResponse extends BaseResponse {
    private List<Route> routes;


    public GetRoutesResponse(Boolean success, Float errorCode, String errorText, String errorDescription) {
        super(success, errorCode, errorText, errorDescription);
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
