const GET = function (url, data, ok, fail) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function (data) {
            if (data.status === 200) {
                ok(data.data)
            } else {
                fail(data.data)
            }
        },
        error: function (data) {
            fail(data);
        }

    })
}

const POST = function (url, data, ok, fail) {
    $.ajax({
        type: "POST",
        url: url,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (data) {
            if (data.status === 200) {
                ok(data.data)
            } else {
                fail(data.data)
            }
        },
        error: function (data) {
            fail(data);
        }
    })
}