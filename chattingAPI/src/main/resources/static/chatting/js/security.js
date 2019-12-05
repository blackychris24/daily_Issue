
function getCsrf()
{
    var csrf = JSON.parse($.ajax({
            type: 'GET',
            url: '/chatting/csrf',
            dataType: 'json',
            success: function() { },
            data: {},
            async: false
        }).responseText);

    return csrf;
}