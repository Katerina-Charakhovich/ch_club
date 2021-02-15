$(document).ready(function () {
    if ($("#isInvalidData").val() === "true") {
        $("#modalEventDate").modal('show');
        alert("!!!!!!!!!");
    } else {
        $("#modalEventDate").modal('hide');
    }
    ;

    if ($("#isInvalidDate").val() === "true") {
        $("#invalidDate").prop('hidden', false);
    }
    ;
    if ($("#isInvalidTime").val() === "true") {
        $("#invalidTime").prop('hidden', false);
    }
    ;
    if ($("#isExistDate").val() === "true") {
        $("#existDate").prop('hidden', false);
    }
    ;
    if ($("#isInvalidCountTicket").val() === "true") {
        $("#invalidCountTicket").prop('hidden', false);
    }
    ;
    if ($("#isInvalidCostTicket").val() === "true") {
        $("#invalidCostTicket").prop('hidden', false);
    }
    ;
    $("#eventAddDate").click(function () {
        $("#dateTime").val('');
        $("#countTickets").val(0);
        $("#cosTickets").val('');
        $("#invalidDate").prop('hidden', true);
        $("#invalidTime").prop('hidden', true);
        $("#existDate").prop('hidden', true);
        $("#invalidCountTicket").prop('hidden', true);
        $("#invalidCostTicket").prop('hidden', true);
    });
    if ($("#isInvalidQuestData").val() === "true") {
        $("#modalQuestDate").modal('show');
        alert("!!!!!!!!!");
    } else {
        $("#modalQuestDate").modal('hide');
    }
    ;

    if ($("#isInvalidQuestDate").val() === "true") {
        $("#oldQuestDate").prop('hidden', false);
    }
    ;
    if ($("#isInvalidQuestTime").val() === "true") {
        $("#incorrectQuestStartTime").prop('hidden', false);
        $("#incorrectQuestEndTime").prop('hidden', false);
    }
    ;
    if ($("#isExistQuestDate").val() === "true") {
        $("#existQuestDate").prop('hidden', false);
    }
    ;
    if ($("#isInvalidQuestCostTicket").val() == "true") {
        $("#incorrectQuestCostTicket").prop('hidden', false);
    }
    ;
    $("#questAddDate").click(function () {
        $("#date").val('');
        $("#costQuestTicket").val(0);
        $("#questStartTime").val('');
        $("#questEndTime").val('');
        $("#oldQuestDate").prop('hidden', true);
        $("#incorrectQuestStartTime").prop('hidden', true);
        $("#incorrectQuestEndTime").prop('hidden', true);
        $("#incorrectQuestCostTicket").prop('hidden', true);
    });
});

