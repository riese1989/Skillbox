$(function(){
    const appendToDo = function(data){
        var toDoCode = '<a href="#" class="toDo-link" data-id="' +
            data.id + '">' + data.name + '</a>' + '              <a href="#" class="delete-course" data-id="' + data.id + '">Удалить курс</a>'
            + '              <a href="#" class="show-form-update-course" data-id="' + data.id +'">Изменить курс</a><br>';
        $('#todo-list')
            .append('<div id = "' + data.id + '">' + toDoCode + '</div>');
        };
    //Loading books on load page
    $.get('/courses/', function(response)
    {
        for(i in response) {
            appendToDo(response[i]);
        }
    });

    //Show adding todo form
    $('#show-add-todo-form').click(function(){
        $('#todo-form').css('display', 'flex');
    });

    $(document).on('click', '.show-form-update-course', function() {
    //$('#show-form-update-course').click(function(){
        var link = $(this);
        var courseId = link.data('id');
        var elem = document.getElementById('update-course');
        elem.setAttribute('data-id', courseId)
        $('#update-course-form').css('display', 'flex');
    });
    //Show updated all courses
    $('#show-update-all-courses-form').click(function(){
        $('#update-all-courses-form').css('display', 'flex');
    });

    $('#show-update-all-courses-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Closing adding book form
    $('#todo-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Delete all courses
    $('#delete-all-courses').click(function() {
        $.ajax({
            method: "DELETE",
            url: '/courses/',
            success: function(response)
                {
                    $("#todo-list").empty();
                },
            error: function(response)
                {

                }
        });
        return false;
    });

       //Update course

       //$(document).on('click', '.update-course', function(event) {
    $('#update-course').click(function() {
        var link = $(this);
        var courseId = link.data('id');
        var data = $('#update-course-form form').serialize();
        $.ajax({
            method: "PUT",
            url: '/courses/' + courseId,
            data: data,
            success: function(response)
                {
                    $('#update-course-form').css('display', 'none');
                    $("#todo-list").empty();
                    for(i in response) {
                        appendToDo(response[i]);
                    }
                }
        });
        return false;
    });


     //Delete course
    $(document).on('click', '.delete-course', function(){
    //$('#delete-course').click(function() {
        var link = $(this);
        var courseId = link.data('id');
        $.ajax({
            method: "DELETE",
            url: '/courses/' + courseId,
            success: function(response)
                {
                $('#' + courseId).empty();
                }

        });
        return false;
    });

    //Getting book
    $(document).on('click', '.toDo-link', function(){
        var link = $(this);
        var courseId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/courses/' + courseId,
            success: function(response)
            {
                var code = '<span>Год:' + response.year + '</span>';
                link.parent().append(code);
            },
            error: function(response)
                {
                    if(response.status == 404) {
                        alert('Курс не найден!');
                    }
                }
            });
        return false;
    });

    //Adding book
    $('#save-todo').click(function(){
        var data = $('#todo-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/courses/',
            data: data,
            success: function(response)
            {
                $('#todo-form').css('display', 'none');
                var toDo = {};
                toDo.id = response;
                var dataArray = $('#todo-form form').serializeArray();
                for(i in dataArray) {
                    toDo[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendToDo(toDo);
            }
        });
        return false;
    });

    $('#save-all-courses').click(function(response) {
            var data = $('#update-all-courses-form form').serialize();
            $.ajax({
                method: "PUT",
                url: '/courses/',
                data: data,
                success: function(response)
                {
                    $('#update-all-courses-form').css('display', 'none');
                    $("#todo-list").empty();
                    for(i in response) {
                                appendToDo(response[i]);
                            }
                }
            });
            return false;
    });
});