$(document).ready(function () {
    if ($("#isUpdateUser").val() == "false") {
        enableFields();
        $("#successUpdateUser").prop('hidden', true);
        $("#unsuccessUpdateUser").prop('hidden', false);
    } else {
        disableFields();
    }
    ;
    if ($("#isUpdateUser").val() == "true") {
        $("#successUpdateUser").prop('hidden', false);
        $("#unsuccessUpdateUser").prop('hidden', true);
    }
    ;
    $("#profileSettings").click(function () {
        enableFields();
    });
    $("#cancel").click(function () {
        disableFields();
    });
});

function enableFields() {
    $("#from-lastname").prop('disabled', false);
    $("#from-name").prop('disabled', false);
    $("#phone").prop('disabled', false);
    $("#saveChange").prop('hidden', false);
    $("#cancel").prop('hidden', false);
    $("#profileSettings").prop('hidden', true);
    $("#changePassword").prop('hidden', true);

};

function disableFields() {
    $("#from-lastname").prop('disabled', true);
    $("#from-name").prop('disabled', true);
    $("#phone").prop('disabled', true);
    $("#saveChange").prop('hidden', true);
    $("#cancel").prop('hidden', true);
    $("#profileSettings").prop('hidden', false);
    $("#changePassword").prop('hidden', false);
};