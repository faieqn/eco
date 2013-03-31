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
   
});

function loadAll(){
    $.ajax({
        url: 'functions.jsp',
        dataType: 'json',
        type: 'post',
        data: 'function=LIST_ALL_COURSES_CYCLES_SECTIONS_PERIODS',
        success: function(data, textStatus, xhr){
            if(data.hasOwnProperty('error')){
                alert('Error en el servidor.');
                return;
            }
            
            var courseList = $("#courseList");
            $.each(data.courses, function(index, value){
                $("<option></option>")
                    .attr("value", value["courseId"])
                    .html(value["courseName"])
                    .appendTo(courseList);
            });
            
            var cycleList = $("#cycleList");
            $.each(data.cycles, function(index, value){
                $("<option></option>")
                    .attr("value", value["cycleId"])
                    .html(value["cycleName"])
                    .appendTo(cycleList);
            });
            
            var sectionList = $("#sectionList");
            $.each(data.sections, function(index, value){
                $("<option></option>")
                    .attr("value", value["sectionId"])
                    .html(value["sectionName"])
                    .appendTo(sectionList);
            });
            
            var periodList = $("#periodList");
            $.each(data.periods, function(index, value){
                $("<option></option>")
                    .attr("value", value["periodId"])
                    .html(value["periodName"])
                    .appendTo(periodList);
            });
            
            var cycleYear = $("#cycleYear");
            $.each(data.years, function(index, value){
                $("<option></option>")
                    .attr("value", value)
                    .html(value)
                    .appendTo(cycleYear);
            });
        },
        error: function(xhr, textStatus, thrownError){
            
        }
    });
}