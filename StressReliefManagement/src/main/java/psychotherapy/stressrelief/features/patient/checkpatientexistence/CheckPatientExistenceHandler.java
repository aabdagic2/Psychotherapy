package psychotherapy.stressrelief.features.patient.checkpatientexistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import psychotherapy.stressrelief.configuration.ApplicationConfig;

@Service
public class CheckPatientExistenceHandler {
    @Autowired
    private ApplicationConfig appConfig;
    @Autowired
    private RestTemplate restTemplate;

    public boolean handle(String patientId) {
        var serviceUrl = appConfig.appointmentsServiceUrl;
        try {
            PatientResponse response = restTemplate
                    .getForObject(serviceUrl + "patients/find/" + patientId, PatientResponse.class);
            return true;
        }
        catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw ex;
        }
    }
}
