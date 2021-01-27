$(document).ready(function () {
    if ($("#isUpdateEvent").val() == "false") {
        enableFields();
        $("#modalSuccessUpdateEvent").modal('hide');

    };
    if ($("#isUpdateEvent").val() == "true") {
        disableFields();
        $("#modalSuccessUpdateEvent").modal('show');

    };

    $("#editEvent").click(function () {
        enableFields();
        document.getElementById('from-eventdesc').value = $("#eventView.getDescription()").val();
    });

    $("#cancel").click(function () {
        $("#from-eventshortdesc").val($("#from-eventshortdesc").attr('previousValue'));
        $('from-eventdesc').val($('from-eventdesc').attr('previousValue'));
        $('from-eventname').val($('from-eventname').attr('previousValue'));
        disableFields();
    });

});

function enableFields() {
    $("#from-eventshortdesc").prop('disabled', false);
    $("#from-eventdesc").prop('disabled', false);
    $("#from-eventname").prop('disabled', false);
    $("#saveChange").prop('hidden', false);
    $("#cancel").prop('hidden', false);
    $("#editEvent").prop('hidden', true);
};

function disableFields() {
    $("#from-eventshortdesc").prop('disabled', true);
    $("#from-eventdesc").prop('disabled', true);
    $("#from-eventname").prop('disabled', true);
    $("#saveChange").prop('hidden', true);
    $("#cancel").prop('hidden', true);
    $("#editEvent").prop('hidden', false);
};