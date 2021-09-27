const Usuario = require('../models/Usuario');

module.exports = {
    async list(req, res) {
        const usuarios = await Usuario.findAll();

        return res.json(usuarios);
    },

    async index(req, res) {
        const { id } = req.params;

        const usuario = await Usuario.findByPk(id);

        return res.json(usuario);
    },

    async store(req, res) {
        const { nome, email, senha, imagem } = req.body;

        const usuario = await Usuario.create({ nome, email, senha, imagem });

        return res.json(usuario);
    },
}