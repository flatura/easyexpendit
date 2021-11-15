package code.flatura.easyexpendit.model;

public enum Status {
    PLANNING("Запланирован"),
    INCOMING("Ожидается"),
    AVAILABLE("Доступен"),
    IN_WORK("В работе"),
    END_OF_WORK("Использован"),
    DELETED("Списан");

    public final String label;

    Status(String label) {
        this.label = label;
    }
}
