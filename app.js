const axios = require("axios");
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

async function llamada(){
    let json = null
    await axios.get(`https://restcountries.com/v2/callingcode/1`, {
        responseType: 'json'
    })
    .then(res => {
        json = res.data
        //console.log(json)
        /* for(i=0;i<=5;i++){
            console.log(json[i].name)
        } */
    })
    .catch(function(err) {
        console.log("No pudo conectarse");
    })
    return json
}

const datos = llamada()
console.log(datos)

/* for(i=0;i<=300;i++){
    let nombrePais = json[i].name
    let capPais = json[i].capital
    let region = json[i].region
    let poblac = json[i].population
    let lat = json[i].latlng[0]
    let long = json[i].latlng[1]
    let codPais = json[i].numericCode
    let pais = null
    //Busco si está ese país
    connection.query(`SELECT * FROM pais WHERE codigoPais = ${codPais}`, (err, row) => {
        pais = row
    })
    if(pais){
        //Actualizo pais encontrado
        connection.query(`UPDATE ${pais} SET nombrePais = ${nombrePais}, capitalPais = ${capPais}, region = ${poblac}, latitud = ${lat}, longitud = ${long} WHERE codigoPais = ${codPais}`, (err, row) =>{
            if(err) throw err
            console.log("País actualizado")
        })
    }else{
        //Agrego pais
        connection.query(`INSERT INTO pais (nombrePais, capitalPais, region, poblacion, latitud, longitud, codigoPais) VALUES (${nombrePais}, ${capPais}, ${region}, ${poblac}, ${lat}, ${long}, ${codPais})`, (err, row) =>{
            if(err) throw err
            console.log("País insertado")
        })
    }
} */

connection.end()
