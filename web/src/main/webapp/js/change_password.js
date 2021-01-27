$(document).ready(function () {
    if ($("#isCorrectChangePassword").val() === "true") {
        $("#modalCorrectPasswordChange").modal('show');
    };
    if ($("#isCorrectChangePassword").val() === "false") {
        alert ("isCorrectChangePassword");
        $("#modalChangePassword").modal('show');
    };

    if ($("#isNonequivalent").val() === "true") {
        $("#nonequivalentRepeatedPassword").prop('hidden', false);

    };
    if ($("#isIncorrectOldPassword").val() === "true") {
        $("#incorrectOldPassword").prop('hidden', false);
    };
});

$('#modalChangePassword').on('hidden.bs.modal', function () {
    $("#oldPassword").val('');
    $("#newPassword").val('');
    $("#repeatedNewPassword").val('');
    $("#nonequivalentRepeatedPassword").prop('hidden', true);
    $("#incorrectOldPassword").prop('hidden', true);
});