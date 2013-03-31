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
   
   listAllUsers();
   
});

function listAllUsers(){
    $.ajax({
        url: 'functions.jsp',
        dataType: 'json',
        type: 'post',
        data: 'function=LIST_ALL_USERS',
        success: function(data, textStatus, xhr){
            if(data.hasOwnProperty('error')){
                alert('Error en el servidor.');
                return;
            }
            
            var users = $("#users");
            $.each(data.users, function(index, value){
                var tr = $("<tr></tr>")
                    .appendTo(users);
                $("<td></td>")
                    .html(value["userId"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["user"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["userName"])
                    .appendTo(tr);
                $("<td></td>")
                    .html(value["userProfileName"])
                    .appendTo(tr);
                var actions = $("<td></td>")
                    .appendTo(tr);
                $("<a></a>")
                    .attr("href", "")
                    .html("Editar")
                    .click(function(){
                        
                    })
                    .appendTo(actions);
            });
        },
        error: function(xhr, textStatus, thrownError){
            
        }
    });
}