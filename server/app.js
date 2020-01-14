const express = require('express');
const app = express();
const port = 1337;

app.get('/', (req, res) => {
    return res.send({
        "message" : "body"
    });
});

app.listen(port, () => console.log(`Example app listening on port ${port}!`));