$(document).ready(function () {
    $("#addComment").click(function () {
        $("#commentAddBlock").prop('hidden', false);
        $("#addComment").prop('hidden', true);
    });
    $("#cancel").click(function () {
        $("#commentAddBlock").prop('hidden', true);
        $("#eventMessage").val("");
        $("#addComment").prop('hidden', false);
    });
});