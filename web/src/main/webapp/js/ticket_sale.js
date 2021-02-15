$(document).ready(function () {
    if ($("#isTicketPaid").val() === 'true') {
        alert("isTicketPaid");
        $("#modalSuccessPaidTicket").modal('show');
    } else {
        $("#modalSuccessPaidTicket").modal('hide');
    }

    if ($("#isTicketBooked").val() === 'true') {
        alert("isTicketBooked");
        $("#modalSuccessBookTicket").modal('show');
    } else {
        $("#modalSuccessBookTicket").modal('hide');
    }

    if ($("#isTicketSale").val() === 'false') {
        alert("isFalseTicketSale");
        $("#modalUnSuccessSaleTicket").modal('show');
    } else {
        $("#modalUnSuccessSaleTicket").modal('hide');
    }
});





