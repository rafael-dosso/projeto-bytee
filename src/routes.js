const express = require('express');
const UsuarioController = require('./controllers/UsuarioController');
const CategoriaController = require('./controllers/CategoriaController');
const ReceitaController = require('./controllers/ReceitaController');


const routes = express.Router();

// usuarios
routes.get('/usuarios', UsuarioController.index);
routes.post('/usuarios', UsuarioController.store);

// categorias 
routes.get('/categorias', CategoriaController.index);
routes.post('/categorias', CategoriaController.store);

//receitas
routes.get('/usuarios/:usuarioId/receitas', ReceitaController.indexUsuario);
routes.post('/usuarios/:usuarioId/receitas', ReceitaController.store);
routes.get('/categorias/:categoriaId/receitas', ReceitaController.indexCategoria);


module.exports = routes;