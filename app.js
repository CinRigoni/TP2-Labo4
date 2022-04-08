
const { NODATA } = require('dns')
const mysql = require('mysql')
const XMLHttpRequest = require('xhr2');
const request = new XMLHttpRequest();

//Conexion a DB
const connection = mysql.createConnection({
    host:'localhost',
    user:'admin',
    password:'admin',
    database:'tp2labo4'
})

connection.connect((error) => {
    if(error) throw error
    console.log("Conexion correcta!")
})

function onRequestHandler(){
    if(this.readyState === 4 && this.status === 200){
        const dato = JSON.parse(this.response)
        console.log(dato)
    } 
}

for(id=0;id<=5;id++){
    request.addEventListener("load", onRequestHandler())
    request.open('GET', `https://restcountries.com/v2/callingcode/${id}`);
    request.responseType = 'json';
    request.send();
}

connection.end()