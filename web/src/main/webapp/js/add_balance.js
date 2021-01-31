$(document).ready(function () {
    $("#modalSuccessAddBalance").modal('hide');
    $("#modalUnSuccessAddBalance").modal('hide');
    $("#modalAmountInvalid").modal('hide');
    if ($("#isUserAddBalance").val() === 'true') {
        alert("isUserAddBalance");
        $("#modalSuccessAddBalance").modal('show');
    }

    if ($("#isUserAddBalance").val() === 'true') {
        alert("sUserAddBalance");
        $("#modalUnSuccessAddBalance").modal('show');
    }

    if ($("#isAmountValid").val() ==='false') {
        alert("#isAmountValid");
        $("#modalAmountInvalid").modal('show');
    }


});
function addUserBalance(userId) {
    document.getElementById('userAddBalanceId').setAttribute('value',userId);

}
