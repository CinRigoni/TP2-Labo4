const { NODATA } = require('dns')
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

for(i=0;i<=300;i++){
    fetch(`https://restcountries.com/v2/callingcode/${i}`)
    .then(res => res.json())
    .then(dato => {
        if(dato){
            let nombrePais = dato.name
            let capPais = dato.capital
            let region = dato.region
            let poblac = dato.population
            let lat = dato.latlng[0]
            let long = dato.latlng[1]
            let codPais = dato.codigoPais

            //Busco si está ese país
            let pais = connection.query(`SELECT * FROM pais WHERE codigoPais = ${codPais}`, (err, rows) => {
                if(pais){
                    //Actualizo pais encontrado
                    connection.query(`UPDATE ${pais} SET nombrePais = ${nombrePais}, capitalPais = ${capPais}, region = ${poblac}, latitud = ${lat}, longitud = ${long} WHERE codigoPais = ${codPais}`, (err, rows) =>{
                        if(err) throw err
                        console.log("País actualizado")
                    })
                }else{
                    connection.query(`INSERT INTO pais (nombrePais, capitalPais, region, poblacion, latitud, longitud, codigoPais) VALUES (${nombrePais}, ${capPais}, ${region}, ${poblac}, ${lat}, ${long}, ${codPais})`, (err, row) =>{
                        if(err) throw err
                        console.log("País insertado")
                    })
                }
            })

            
        }
    })
    .catch(err => console.log(err))
}

connection.end()