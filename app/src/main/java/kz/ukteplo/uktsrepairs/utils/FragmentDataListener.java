package kz.ukteplo.uktsrepairs.utils;

import kz.ukteplo.uktsrepairs.data.models.User;

public interface FragmentDataListener {
    void signUp(boolean result, String errorMsg, User user);
    void signIn(boolean result, String errorMsg);
    void selectRepair(String id);
    void selectRepair(String id, String isApproved, String actionType);
    void alert(Boolean result);
}
