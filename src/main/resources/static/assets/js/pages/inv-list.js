var table = $('table#inv').DataTable({
    'ajax': '/app/inventory/list',
    'serverSide': true,
    language: {
        "lengthMenu": "Menampilkan _MENU_ record per halaman",
        "zeroRecords": "Data tidak ditemukan",
        "info": "Menampilkan halaman _PAGE_ dari _PAGES_",
        "infoEmpty": "Tidak ada data ditemukan.",
        "infoFiltered": "",
        "processing": "Proses menampilkan data..."
    },
    columns: [
        {
            data: 'masterItem.itemBarcode',
            render : function (data) {
                return '<a href="getPR?number='+data+'">'+data+'</a>';      }
        },
        {
            data: 'masterItem.itemName'
        },
        {
            data: 'sellPrice',
        },
        {
            data: 'stock',
            render: function (data) { return data ? data : '-'; }
        },
        {
            data: 'masterLocation.locationName'
        },
        {
            data: 'outlet.outletName',
            render: function (data) { return data ? data : '-'; }
        },
        {
            data: 'suplier.suplierName',
        },
    ]
})

$('select#locations').change(function() {
    var filter = '';
    $('select#locations option:selected').each(function() {
        filter += $(this).text() + "+";
    });
    filter = filter.substring(0, filter.length - 1);
    table.column(5).search(filter).draw();
});

$('select#status').change(function() {
    var filter = '';
    $('select#status option:selected').each(function() {
        filter += $(this).text() + "+";
    });
    filter = filter.substring(0, filter.length - 1);
    table.column(1).search(filter).draw();
});