package pensa.on.duty.api.defaultData;

import pensa.on.duty.api.newModel.Specializer;

import java.util.ArrayList;
import java.util.List;

public class InitializeSpecializersDefaultData {

    public static List<Specializer> getDefaultSpecializers() {
        List<Specializer> specializerList = new ArrayList<>();
        Specializer specializer1 = new Specializer();
        specializer1.setId(0);
        specializer1.setName("ΧΡΙΣΤΑΝΤΩΝΗ");
        specializer1.setExperience(1);
        specializer1.setActive(Boolean.TRUE);
        specializerList.add(specializer1);

        Specializer specializer2 = new Specializer();
        specializer2.setId(1);
        specializer2.setName("ΠΑΠΑΔΑΚΗ");
        specializer2.setExperience(10);
        specializer2.setActive(Boolean.TRUE);
        specializerList.add(specializer2);

        Specializer specializer3 = new Specializer();
        specializer3.setId(2);
        specializer3.setName("ΚΑΡΑΓΙΑΝΟΠΟΥΛΟΣ");
        specializer3.setExperience(9);
        specializer3.setActive(Boolean.TRUE);
        specializerList.add(specializer3);

        Specializer specializer4 = new Specializer();
        specializer4.setId(3);
        specializer4.setName("ΠΑΝΑΓΙΩΤΙΔΗ");
        specializer4.setExperience(10);
        specializer4.setActive(Boolean.TRUE);
        specializerList.add(specializer4);

        Specializer specializer5 = new Specializer();
        specializer5.setId(4);
        specializer5.setName("ΘΑΝΟΠΟΥΛΟΥ");
        specializer5.setExperience(8);
        specializer5.setActive(Boolean.FALSE);
        specializerList.add(specializer5);

        Specializer specializer6 = new Specializer();
        specializer6.setId(5);
        specializer6.setName("ΜΠΡΑΒΟΥ");
        specializer6.setExperience(7);
        specializer6.setActive(Boolean.TRUE);
        specializerList.add(specializer6);

        Specializer specializer7 = new Specializer();
        specializer7.setId(6);
        specializer7.setName("ΠΕΓΚΟΥ");
        specializer7.setExperience(7);
        specializer7.setActive(Boolean.TRUE);
        specializerList.add(specializer7);

        Specializer specializer8 = new Specializer();
        specializer8.setId(7);
        specializer8.setName("ΜΑΥΡΙΔΟΥ");
        specializer8.setExperience(6);
        specializer8.setActive(Boolean.TRUE);
        specializerList.add(specializer8);

        Specializer specializer9 = new Specializer();
        specializer9.setId(8);
        specializer9.setName("ΜΑΡΓΑΡΙΤΗ");
        specializer9.setExperience(5);
        specializer9.setActive(Boolean.TRUE);
        specializerList.add(specializer9);

        Specializer specializer10 = new Specializer();
        specializer10.setId(9);
        specializer10.setExperience(4);
        specializer10.setName("ΤΣΑΝΑΚΑΛΗΣ");
        specializer10.setActive(Boolean.TRUE);
        specializerList.add(specializer10);

        Specializer specializer11 = new Specializer();
        specializer11.setId(10);
        specializer11.setExperience(4);
        specializer11.setName("ΧΟΝΔΡΟΥ");
        specializer11.setActive(Boolean.TRUE);
        specializerList.add(specializer11);

        Specializer specializer12 = new Specializer();
        specializer12.setId(11);
        specializer12.setExperience(4);
        specializer12.setName("ΟΝΤΖΕ");
        specializer12.setActive(Boolean.TRUE);
        specializerList.add(specializer12);

        return specializerList;
    }


}
