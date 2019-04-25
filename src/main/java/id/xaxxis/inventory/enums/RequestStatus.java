package id.xaxxis.inventory.enums;

public enum RequestStatus {

    TERTUNDA(0), SELESAI(1), DIBATALKAN(2), REVISI(3), DIPROSES(4);

    private int value;

    private RequestStatus(int value) {
        this.value = value;
    }
}
