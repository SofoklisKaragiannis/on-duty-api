package pensa.on.duty.api.framework;

import pensa.on.duty.api.newModel.Specializer;

import java.util.ArrayList;
import java.util.List;

public class SpecializersList {

    List<Specializer> specializerList;

    SpecializersList () {
        specializerList = new ArrayList<>();
    }

    SpecializersList (List<Specializer> specializerList) {
        this.specializerList = specializerList;
    }
    public List<Specializer> getSpecializerList() {
        return specializerList;
    }

    public boolean containsName(String name) {
        if (specializerList.stream().anyMatch(sp -> sp.getName().equals(name))) return true;
        return false;
    }

    public boolean containsId(Integer id) {
        if (specializerList.stream().anyMatch(sp -> sp.getId() == id)) return true;
        return false;
    }
}
