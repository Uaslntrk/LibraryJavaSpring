<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js">

<head>
    <meta charset="utf-8">
    <title>404 - Flat</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/assets/css/flat-404.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>

<body class="flat">
<div class="content">
    <div class="content-box">
        <div class="big-content">
            <div class="list-square">
                <span class="square"></span>
                <span class="square"></span>
                <span class="square"></span>
            </div>
            <div class="list-line">
                <span class="line"></span>
                <span class="line"></span>
                <span class="line"></span>
                <span class="line"></span>
                <span class="line"></span>
                <span class="line"></span>
            </div>
            <i class="fa fa-search" aria-hidden="true"></i>
            <div class="clear"></div>
        </div>
        <h1>${error404}</h1>
        <p>${errorPages}</p>
    </div>
</div>



<script src="<%= request.getContextPath() %>/static/assets/vendor/bootstrap/js/jquery.min.js"></script>
<script src="<%= request.getContextPath() %>/static/assets/vendor/bootstrap/js/bootstrap.js"></script>
</body>

</html>
