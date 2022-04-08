const { default: axios } = require("axios");
const mysql = require('mysql')

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

function llamaAxios(){
    axios.get(`https://restcountries.com/v2/callingcode/1`, {
        responseType: 'json'
    })
    .then(res => {
        //console.log(res)
        let json = res.data
        console.log(json[0].name)
        /* for(i=0;i<=1;i++){
            console.log(json[i].name)
            let nombrePais = json[i].name
            let capPais = json[i].capital
            let region = json[i].region
            let poblac = json[i].population
            let lat = json[i].latlng[0]
            let long = json[i].latlng[1]
            let codPais = json[i].numericCode
            console.log(`${nombrePais} - ${capPais} - ${region} - ${poblac} - ${lat} - ${long} - ${codPais}`)
        } */
    })
    .catch(function(err) {
        console.log("No pudo conectarse");
    })
}

llamaAxios()

connection.end()