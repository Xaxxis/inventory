package id.xaxxis.inventory.enums;

public enum RequestStatus {

    TERTUNDA(0), DIPROSES(4), SELESAI(1), DIBATALKAN(2), REVISI(3);

    private int value;

    private RequestStatus(int value) {
        this.value = value;
    }
}
