const express = require('express');
const fs = require('fs');

const app = express();
const port = 1337;

let database = undefined;

app.get('/api/cities', (req, res) => {
    let result = {};
    /* loop through all cities */
    for (city in database['cities'])
        result[city] = database['cities'][city];
    console.log('hit end point');
    return res.status(200).send(result);
});

app.get('/api/cities/:city', (req, res) => {
    let city = req.params.city.toLowerCase().trim();
    
    /* see if the city exists */
    if (!Object.keys(database['cities']).includes(city))
        return res.status(404).send({'error': 'data for ' + req.params.city + ' does not exist in the database'});

    return res.status(200).send({[city] : database['cities'][city]});
});

app.get('/api/cities/:city/:day', (req, res) => {
    let city = req.params.city.toLowerCase().trim();
    let day = req.params.day.toLowerCase().trim();
    let hasQuery = req.query.info !== undefined;
    let qp = (req.query.info || "").split(',').map(s => s.trim().toLowerCase());
    
    /* see if the city exists */
    if (!Object.keys(database['cities']).includes(city))
        return res.status(404).send({'error': 'data for ' + req.params.city + ' does not exist in the database'});

    /* see if the day exists */
    if (!Object.keys(database['cities'][city]).includes(day))
        return res.status(404).send({'error': 'data for ' + req.params.day + ' does not exist in the database'});

    let result = {};

    /* loop through query params */
    for (attrib in database['cities'][city][day]) {
        let a = attrib.trim().toLowerCase();
        if (qp.includes(a) || !hasQuery) 
            result[attrib] = database['cities'][city][day][attrib];
    }

    return res.status(200).send({[city] : result});
});

app.listen(port, () => {
    database = JSON.parse(fs.readFileSync('files/database.json', 'utf-8'));
});