const express = require('express');
const UsuarioController = require('./controllers/UsuarioController');
const CategoriaController = require('./controllers/CategoriaController');
const ReceitaController = require('./controllers/ReceitaController');
const CurtidaController = require('./controllers/CurtidaController');

const routes = express.Router();

// usuarios
routes.get('/usuarios/:id', UsuarioController.index);
routes.get('/usuarios', UsuarioController.list);
routes.post('/usuarios', UsuarioController.store);

// categorias
routes.get('/categorias/:id', CategoriaController.index);
routes.get('/categorias', CategoriaController.list);
routes.post('/categorias', CategoriaController.store);

// receitas
routes.get('/receitas', ReceitaController.list);
routes.get('/usuarios/:usuarioId/receitas', ReceitaController.indexUsuario);
routes.post('/usuarios/:usuarioId/receitas', ReceitaController.store);
routes.get('/categorias/:categoriaId/receitas', ReceitaController.indexCategoria);

// curtidas
routes.post('/curtidas', CurtidaController.store);
routes.get('/receitas/:receitaId/curtidas/count', CurtidaController.count);
routes.delete('/curtidas', CurtidaController.delete);

module.exports = routes;