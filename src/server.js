const porta = 3333

const express = require('express');
const routes = require('./routes');

require('./database');

const app = express();

app.use(express.json());
app.use(routes);

app.listen(porta, () => {
    console.log('Servidor rodando em http://localhost:' + porta);
})