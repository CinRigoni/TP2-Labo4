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

async function conectaApi(id){
        await fetch(`https://restcountries.com/v2/callingcode/${id}`, res)
        .then(res => res.json())
        .then(data => console.log(data))
        .catch(err => console.log(err))
}

for(i=0;i<=5;i++){
    conectaApi()
}

connection.end()