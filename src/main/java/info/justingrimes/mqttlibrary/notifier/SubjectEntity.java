package info.justingrimes.mqttlibrary.notifier;

public interface SubjectEntity {
    String getTopic();

    String getMessage();

    void querySubject();
}


