const Sequelize = require('sequelize');
const dbConfig = require('../config/database');

const Usuario = require('../models/Usuario');
const Categoria = require('../models/Categoria');
const Receita = require('../models/Receita');

const connection = new Sequelize(dbConfig);

Usuario.init(connection);
Categoria.init(connection);
Receita.init(connection);

Receita.associate(connection.models);
Usuario.associate(connection.models);
Categoria.associate(connection.models);

module.exports = connection;