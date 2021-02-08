$(document).ready(function () {
    document.getElementById('questId').setAttribute('value', '${eventView.getEventId()}');
    if ($("#isInvalidQuestData").val() === "true") {
        alert("invalid quest date");
        $("#modalQuestDate").modal('show');
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
});

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
