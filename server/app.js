const express = require('express');
const fs = require('fs');

const app = express();
const port = 1337;

let database;

app.get('/api', (req, res) => {
    return res.send(database);
})

app.listen(port, () => {
    database = JSON.parse(fs.readFileSync('files/database.json', 'utf-8'));
});