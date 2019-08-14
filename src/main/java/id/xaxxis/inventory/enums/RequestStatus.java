package id.xaxxis.inventory.enums;

public enum RequestStatus {

    TERTUNDA(0),
    REVISI(1),
    DIPROSES(2),
    DIAJUKAN(3),
    DISETUJUI(4),
    DIKIRIM(5),
    SELESAI(6),
    DIBATALKAN(7);

    private int value;

    RequestStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
