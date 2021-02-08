$(document).ready(function () {
    $("#addComment").click(function () {
        $("#commentAddBlock").prop('hidden', false);
        $("#addComment").prop('hidden', true);
    });
    $("#cancelSaveComment").click(function () {
        $("#commentAddBlock").prop('hidden', true);
        $("#eventMessage").val("");
        $("#addComment").prop('hidden', false);
    });
});