    $(document).ready(function () {
    if ($("#isInvalidUser").val() === "true") {
    document.getElementById('messageInvalidLoginPassword').hidden = false;
    document.getElementById('messageInvalidPasswordLogin').hidden = false;
    alert("isInvalidUser");
    $("#modal_login").modal('show');
}
    if ($("#isBlockedUser").val() === "true") {
    document.getElementById('messageBlockedUser').hidden = false;
    alert("isInvalidUser");
    $("#modal_login").modal('show');
}
    if ($("#isUnConfirmedUser").val() === "true") {
    document.getElementById('messageUnConfirmedUser').hidden = false;
    alert("isInvalidUser");
    $("#modal_login").modal('show');
}
});
    $('#modal_login').on('hidden.bs.modal', function () {
    $("#login").val('');
    $("#password").val('');
    $("#isInvalid").val('');
    document.getElementById('messageInvalidLoginPassword').hidden = true;
    document.getElementById('messageInvalidPasswordLogin').hidden =  true;
    document.getElementById('messageBlockedUser').hidden =  true;
    document.getElementById('messageUnConfirmedUser').hidden =  true;
});
