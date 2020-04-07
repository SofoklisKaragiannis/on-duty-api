package pensa.on.duty.api.framework;

import pensa.on.duty.api.newModel.Specializer;

import java.util.ArrayList;
import java.util.List;

public class SpecializersList {

    List<Specializer> specializerList;

    public SpecializersList () {
        specializerList = new ArrayList<>();
    }

    public SpecializersList (List<Specializer> specializerList) {
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

    public void add(Specializer specializer) {
        specializerList.add(specializer);
    }

    public Specializer getSpecializerById(Integer id) {
        return specializerList.stream().filter(sp->sp.getId() == id).findFirst().get();
    }

    public int getNewSpecializerId() {
        if  (specializerList.size() == 0) return 0;
        return specializerList.get(specializerList.size()-1).getId() +1;
    }
}
