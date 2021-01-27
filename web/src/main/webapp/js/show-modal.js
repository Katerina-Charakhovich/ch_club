$(document).ready(function () {
    if ($("#showModal").val() === "true") {
        $("#modalLogin").modal('show');
        if ($("#incorrectLoginOrPassword").val() === "true") {
            document.getElementById('incorrectData').hidden = false;
        }
        if ($("#loginWithoutConfirm").val() === "true") {
            document.getElementById('incorrectLoginWithoutConfirm').hidden = false;
        }
        if ($("#blockedUser").val() === "true") {
            document.getElementById('messageBlockedUser').hidden = false;
        }
    }
    $('#modalLogin').on('hidden.bs.modal', function () {
        $("#login").val('');
        $("#password").val('');
        $("#incorrectLoginOrPassword").val('');
        $("#loginWithoutConfirm").val('');
        $("#blockedUser").val('');
        document.getElementById('incorrectData').hidden = true;
        document.getElementById('incorrectLoginWithoutConfirm').hidden = true;
        document.getElementById('messageBlockedUser').hidden = true;
    });
    if ($("#showModalContactUs").val() === "true") {
        $("#modalContactUs").modal('show');
    } else {
        $("#modalContactUs").modal('hide');
    }
});

$(document).ready(function () {
    $("#phone").mask("+375 99 999-99-99");
});

$(document).ready(function () {
    if ($("#showRegistrationMessage").val() === "true") {
        $("#modalRegistration").modal('show');
    }
    if ($("#showConfirmRegistrationMessage").val() === "true") {
        $("#modalRegistration").modal('show');
    }
});