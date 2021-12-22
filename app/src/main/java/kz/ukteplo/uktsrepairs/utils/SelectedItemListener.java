package kz.ukteplo.uktsrepairs.utils;

import kz.ukteplo.uktsrepairs.data.models.District;
import kz.ukteplo.uktsrepairs.data.models.Route;

public interface SelectedItemListener {
    void selectDistrict(District district);
    void selectRoute(Route route);
}
