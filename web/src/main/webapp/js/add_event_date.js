$(document).ready(function () {
    if ($("#isInvalidData").val() == "true") {
        $("#modalEventDate").modal('show');
    }else {
        $("#modalEventDate").modal('hide');
    } ;

    if ($("#isInvalidDate").val() == "true") {
        $("#invalidDate").prop('hidden', false);
    }
    ;
    if ($("#isInvalidTime").val() == "true") {
        $("#invalidTime").prop('hidden', false);
    }
    ;
    if ($("#isExistDate").val() == "true") {
        $("#existDate").prop('hidden', false);
    }
    ;
    if ($("#isInvalidCountTicket").val() == "true") {
        $("#invalidCountTicket").prop('hidden', false);
    }
    ;
    if ($("#isInvalidCostTicket").val() == "true") {
        $("#invalidCostTicket").prop('hidden', false);
    }
    ;
    $("#eventAddDate").click(function () {
        $("#dateTime").val('');
        $("#countTickets").val(0);
        $("#cosTickets").val('');
    });

});


