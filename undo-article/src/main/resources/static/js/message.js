const message = {
    alert: function (bgc, mess) {
        let a = $("<div style='position: fixed;" +
            "width: 20%;" +
            "height: 60px;" +
            "background-color: " + bgc + ";" +
            "text-align: center;" +
            "z-index: 1;" +
            "top:100px;" +
            "left: 40%;" +
            "line-height:60px;" +
            "font-size: 20px;" +
            "color:white;" +
            "border-radius: 5px;" +
            "letter-spacing: 5px;" +
            "display: none'><b>" + mess + "</b></div>")
            .appendTo("body")
            .fadeIn(1000)
        setTimeout(function () {
            $(a).fadeOut(1000)
            setTimeout(function () {
                $(a).remove()
            }, 1000)
        }, 2000)
    },
    ok: function (mess) {
        this.alert("#8cc5ff", mess)
    },
    fail: function (mess) {
        this.alert("#f56c6c", mess)
    }
}