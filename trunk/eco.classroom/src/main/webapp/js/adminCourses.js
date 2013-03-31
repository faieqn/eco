/* 
 * Copyright (C) 2013 USAC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

$(document).ready(function(){
    
    listAllCourses();
    
});

function listAllCourses(){
    $.ajax({
        url: 'functions.jsp',
        dataType: 'json',
        type: 'post',
        data: 'function=LIST_ALL_COURSES_OPEN',
        success: function(data, textStatus, xhr){
            if(data.hasOwnProperty('error')){
                alert('Error en el servidor.');
                return;
            }
            
            var courses = $("#courses");
            $.each(data.courses, function(index, value){
                var tr = $("<tr></tr>")
                    .appendTo(courses);
                $("<td></td>")
                    .html(value["courseStatus"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["courseId"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["courseName"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["sectionName"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["professorName"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["subscribers"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["connected"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["uri"])
                    .appendTo(tr);
                var actions = $("<td></td>")
                    .appendTo(tr);
                $("<a></a>")
                    .attr("href", "")
                    .html("Deshabilitar")
                    .click(function(){
                        disable(value["course_id"]);
                    })
                    .appendTo(actions);
                $("<a></a>")
                    .attr("href", "")
                    .html("Editar")
                    .appendTo(actions);
            });
        },
        error: function(xhr, textStatus, thrownError){

        }
    });
}

function disable(course_id){
    $.ajax({
        url: 'function.jsp',
        dataType: 'json',
        type: 'post',
        data: 'function=DISABLE_COURSE_OPEN'
            + '&course_id=' + course_id,
        success: function(data, textStatus, xhr){
            
        },
        error: function(xhr, textStatus, thrownError) {
            
        }
    });
}