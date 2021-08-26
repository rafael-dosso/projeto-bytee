const Categoria = require('../models/Categoria');

module.exports = {
    async index(req, res) {
        const categorias = await Categoria.findAll();

        return res.json(categorias);
    },

    async store(req, res) {
        const { nome } = req.body;

        const categoria = await Categoria.create({ nome });

        res.json(categoria);
    }
}