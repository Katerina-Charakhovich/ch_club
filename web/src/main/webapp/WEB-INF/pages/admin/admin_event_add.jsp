<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="message"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="main.title"/></title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
    <link href="http://bootstrapformhelpers.com/assets/css/bootstrap-formhelpers.min.css" rel="stylesheet"
          media="screen">
    <script src="http://bootstrapformhelpers.com/assets/js/bootstrap-formhelpers.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/themes/explorer-fas/theme.css" media="all" rel="stylesheet"
          type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/piexif.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/sortable.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/WEB-INF/js/fileinput.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/locales/fr.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/locales/es.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/themes/fas/theme.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/themes/explorer-fas/theme.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:import url="../common/header_admin.jsp"/>
<!-- MAIN -->
<div class="container">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <form class="load_pic" action="${pageContext.request.contextPath}/do" enctype="multipart/form-data"
          method="POST">
        <input type="hidden" name="command" value="EVENT_ADD"/>
        </br>
        <h2><fmt:message key="title.event.add"/></h2>
        <br>
        <c:if test="${empty mainPicture}">
        <h4>
                <fmt:message key="event_add.h4.chooseMainPicture"/>
            <div class="file-loading">
                <input id="file-0c" required class="file" type="file" data-theme="fas" name="main"
                       formenctype="multipart/form-data">
            </div>
            </c:if>
            </br>
            <hr>
            <fmt:message key="event_add.h4.chooseAddPictures"/>
            <div class="form-group">
                <div class="file-loading">
                    <input id="file-4" required name="additional" type="file" class="file" multiple
                           data-min-file-count="3" data-upload-url="#" data-theme="fas"
                           formenctype="multipart/form-data">
                </div>
            </div>
            <hr>
            <c:if test="${not empty mainPicture}">
            <div class="card-header">
                <input type="hidden" name="mainPicture" value="${mainPicture}">
                <img class="card-img-top" src="${mainPicture}" alt="Card image cap">
            </div>
            </c:if>
            </br>
            <div class="form-group">
                <label class="control-label col-xs-3" for="eventName"><fmt:message key="event.label.name"/></label>
                <div class="required col-xs-10">
                    <c:choose>
                        <c:when test="${not empty mapPageParams && mapPageParams['eventName'].equals('error')}">
                            <input type="text" required class="form-control input-md" id="eventName" name="eventName"
                                   placeholder=
                                       <fmt:message key="event.placeholder.name"/>>
                            </br>
                            <label class="control-label col-xs-3" for="eventName"><fmt:message
                                    key="label.error.input"/></label>
                        </c:when>
                        <c:when test="${not empty mapPageParams && !mapPageParams['eventName'][0].equals('error')}">
                            <input type="text" required class="form-control input-md" id="eventName" name="eventName"
                                   placeholder=
                                       <fmt:message
                                               key="event.placeholder.name"/> value= ${mapPageParams['eventName'][0]}>
                        </c:when>
                        <c:otherwise>
                            <input type="text" required class="form-control input-md" id="eventName" name="eventName"
                                   placeholder=<fmt:message key="event.placeholder.name"/>>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>
            </br>
            <div class="form-group">
                <label class="control-label col-xs-3" name="typeEvent" id="typeEvent"><fmt:message
                        key="event.typeEvent"/></label>
            </div>
            <div class="form-check form-check-inline">
                <label class="radio-inline">
                    <input formenctype="text/plain;charset= UTF-8" type="radio" checked name="eventType"
                           value="theatre"> <fmt:message
                        key="event.typeEvent.theatre"/>
                </label>
            </div>

            <div class="form-check form-check-inline">
                <label class="radio-inline">
                    <input type="radio" name="eventType" value="quest"> <fmt:message key="event.typeEvent.quest"/>
                </label>
            </div>
          <!--  <div class="form-check form-check-inline">
                <label class="radio-inline">
                    <input type="radio" name="eventType" value="quest"><fmt:message
                        key="event.typeEvent.creativeWorkShop"/>
                </label>
            </div>-->
            </br>
            <div class="form-group">
                <label class="control-label col-xs-3" for="eventShortDescription"><fmt:message
                        key="event.short.description"/></label>
                <div class="col-xs-10">
                    <input formenctype="text/plain;charset= UTF-8" type="text" required class="form-control input-md"
                           id="eventShortDescription"
                           name="eventShortDescription" placeholder=<fmt:message key="event.description.hint"/>>
                </div>
            </div>
            <div class="form-group">
                </br>
                <label class="control-label col-xs-3" for="eventDescription"><fmt:message
                        key="event.description"/></label>
                <div class="col-xs-10">
                <textarea formenctype="text/plain;charset= UTF-8" rows="8" required class="form-control"
                          id="eventDescription" name="eventDescription"
                          placeholder=<fmt:message key="event.description.hint"/>></textarea>
                </div>
            </div>
            </br>
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <input type="submit" class="button" value="Сохранить">
                    <input type="reset" class="button" value="Отмена ">
                </div>
            </div>
    </form>
</div>
</div>
</body>
<script src="${pageContext.request.contextPath}/js/compress.js"></script>
<script>
    $('#file-fr').fileinput({
        theme: 'fas',
        language: 'fr',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif']
    });
    $('#file-es').fileinput({
        theme: 'fas',
        language: 'es',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif']
    });
    $("#file-0").fileinput({
        theme: 'fas',
        uploadUrl: '#'
    }).on('filepreupload', function (event, data, previewId, index) {
        alert('The description entered is:\n\n' + ($('#description').val() || ' NULL'));
    });
    $("#file-1").fileinput({
        theme: 'fas',
        uploadUrl: '#', // you must set a valid URL here else you will get an error
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    });
    /*
     $(".file").on('fileselect', function(event, n, l) {
     alert('File Selected. Name: ' + l + ', Num: ' + n);
     });
     */
    $("#file-3").fileinput({
        theme: 'fas',
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-primary btn-lg",
        fileType: "any",
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreview: [
            "http://lorempixel.com/1920/1080/transport/1",
            "http://lorempixel.com/1920/1080/transport/2",
            "http://lorempixel.com/1920/1080/transport/3"
        ],
        initialPreviewConfig: [
            {caption: "transport-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
            {caption: "transport-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
            {caption: "transport-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3}
        ]
    });
    $("#file-4").fileinput({
        theme: 'fas',
        uploadExtraData: {kvId: '10'}
    });
    $(".btn-warning").on('click', function () {
        var $el = $("#file-4");
        if ($el.attr('disabled')) {
            $el.fileinput('enable');
        } else {
            $el.fileinput('disable');
        }
    });
    $(".btn-info").on('click', function () {
        $("#file-4").fileinput('refresh', {previewClass: 'bg-info'});
    });
    /*
     $('#file-4').on('fileselectnone', function() {
     alert('Huh! You selected no files.');
     });
     $('#file-4').on('filebrowse', function() {
     alert('File browse clicked for #file-4');
     });
     */
    $(document).ready(function () {
        $("#test-upload").fileinput({
            'theme': 'fas',
            'showPreview': false,
            'allowedFileExtensions': ['jpg', 'png', 'gif'],
            'elErrorContainer': '#errorBlock'
        });
        $("#kv-explorer").fileinput({
            'theme': 'explorer-fas',
            'uploadUrl': '#',
            overwriteInitial: false,
            initialPreviewAsData: true,
            initialPreview: [
                "http://lorempixel.com/1920/1080/nature/1",
                "http://lorempixel.com/1920/1080/nature/2",
                "http://lorempixel.com/1920/1080/nature/3"
            ],
            initialPreviewConfig: [
                {caption: "nature-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
                {caption: "nature-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
                {caption: "nature-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3}
            ]
        });
        /*
         $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
         alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
         });
         */
    });
</script>
</html>
-