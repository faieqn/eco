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
	$("#btLogin").click(function(){
            var username = $("#txtUsername").val();
            var password = $("#txtPassword").val();
            login(username, password);
        });
});

function login(username, password){
    $.ajax({
            url: 'functions.jsp',
            dataType: 'json',
            type: 'post',
            data: 'function=LOGIN'
                    + '&username=' + username
                    + '&password=' + password,
            success: function(data, textStatus, xhr){
                if(data.hasOwnProperty('error')){
                    alert('Error en el servidor.');
                    return;
                }
                
                if(data['logged'] == true){
                    location.href = "home.jsp";
                } else {
                    alert('Usuario/Contrasena incorrecto', 'Error');
                }
            },
            error: function(xhr, textStatus, thrownError){
                alert('Error en el servidor.');
            }
    });
}