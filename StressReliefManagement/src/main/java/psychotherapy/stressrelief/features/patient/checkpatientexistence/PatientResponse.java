package psychotherapy.stressrelief.features.patient.checkpatientexistence;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PatientResponse implements Serializable {
    private String userId;
    private Integer age;
    private String selectedPsychologistId;
}
