    var table = $('table#prs').DataTable({
        'ajax': '/app/purchasing/request/prlist',
        'serverSide': true,
        language: {
            "lengthMenu": "Menampilkan _MENU_ record per halaman",
            "zeroRecords": "Data tidak ditemukan",
            "info": "Menampilkan halaman _PAGE_ dari _PAGES_",
            "infoEmpty": "Tidak ada data ditemukan.",
            "infoFiltered": "(ditemukan _MAX_ jumlah records)",
            "processing": "Proses menampilkan data..."
        },
        columns: [
            {
                data: 'purchaseRequestNumber',
                render : function (data) {
                    return '<a href="getPR?number='+data+'">'+data+'</a>';      }
            },
            {
                data: 'requestStatus'
            },
            {
                data: 'createdDate',
                render: function (data) {
                    var date = new Date(data);
                    var month = date.getMonth() + 1;
                    return date.getDate() + "-" + (month.length > 1 ? month : +month) + "-" + date.getFullYear() + "  " + date.getHours() + ":" + date.getMinutes();
                }
            },
            {
                data: 'remarks',
                render: function (data) { return data ? data : '-'; }
            },
            {
                data: 'createdBy'
            },
            {
                data: 'masterLocation.locationName',
                render: function (data) { return data ? data : '-'; }
            }
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