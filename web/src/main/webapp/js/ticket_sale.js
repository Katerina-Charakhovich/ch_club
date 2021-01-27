$(document).ready(function () {
    if ($("#showModalSuccessSaleTicket").val() == 'true') {
        $("#modalSuccessSaleTicket").modal('show');
    } else {
        $("#modalSuccessSaleTicket").modal('hide');
    }
});

$("#buttonQuestBuy").click(function () {
    document.getElementById('questName').setAttribute('value', '${eventView.getName()}');
    document.getElementById('timeModal').setAttribute('value', '${time}');
    document.getElementById('dateModal').setAttribute('value', '${date}');
    document.getElementById('eventDateId').setAttribute('value', q);
    document.getElementById('eventId').setAttribute('value', '${eventView.getEventId()}');
    document.getElementById('eventType').setAttribute('value', '${eventView.getEventType()}');
});


