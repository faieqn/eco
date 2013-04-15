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
   
   loadAll();
   
   $("#sourceCycleList").change(function(){
       var option = $(this).children("option:selected").val();
       if(option != "empty"){
            loadCoursesOpen(option);
       } else {
           var courses = $("#courses");
           courses.html("");
       }
   });
   
   $("#import").click(function(){
       var sourceCycleList = $("#sourceCycleList");
       var destCycleList = $("#destCycleList");
       if(sourceCycleList.val() == "empty"){
           alert("Debe seleccionar una fuente de informacion.");
       } else if(destCycleList.children("option:selected").length == 0){
           alert("No hay ciclos disponibles para exportar");
       }
   });
   
});

function loadAll(){
     $.ajax({
        url: 'functions.jsp',
        dataType: 'json',
        type: 'post',
        data: 'function=LIST_ALL_CYCLES',
        success: function(data, textStatus, xhr){
            if(data.hasOwnProperty('error')){
                alert('Error en el servidor.');
                return;
            }
            
            var destCycleList = $("#destCycleList");
            var sourceCycleList = $("#sourceCycleList");
            $.each(data.cycles, function(index, value){
                $("<option></option>")
                    .attr("value", value["cycleId"])
                    .html(value["cycleName"])
                    .appendTo(sourceCycleList);
                    
                $("<option></option>")
                    .attr("value", value["cycleId"])
                    .html(value["cycleName"])
                    .appendTo(destCycleList);
            });
            destCycleList
                .val(data.current_cycle);
        },
        error: function(xhr, textStatus, thrownError){
            
        }
     });
}

function loadCoursesOpen(cycle_id){
    $.ajax({
        url: 'functions.jsp',
        dataType: 'json',
        type: 'post',
        data: 'function=LIST_ALL_COURSES_OPEN_BY_CYCLE'
            + '&cycle=' + cycle_id,
        success: function(data, textStatus, xhr){
            if(data.hasOwnProperty('error')){
                alert('Error en el servidor.');
                return;
            }
            
            var courses = $("#courses");
            courses.html("");
            $.each(data.courses, function(index, value){
                var tr = $("<tr></tr>")
                    .appendTo(courses);
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
                    .html("")
                    .appendTo(tr);
                $("<td></td>")
                    .html("")
                    .appendTo(tr);
                    
                $.each(value["schedules"], function(index, schedule){
                    var tr = $("<tr></tr>")
                        .appendTo(courses);
                    $("<td></td>")
                        .html("")
                        .appendTo(tr);
                    $("<td></td>")
                        .html("")
                        .appendTo(tr);
                    $("<td></td>")
                        .html("")
                        .appendTo(tr);
                    $("<td></td>")
                        .html("")
                        .appendTo(tr);
                    $("<td></td>")
                        .html(schedule["dayName"])
                        .appendTo(tr);
                    $("<td></td>")
                        .html(schedule["startTime"] + " - " + schedule["endTime"])
                        .appendTo(tr);
                });
            });
        },
        error: function(xhr, textStatus, thrownError){

        }
    });
}