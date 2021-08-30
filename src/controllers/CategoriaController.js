const { beforeFindAfterExpandIncludeAll } = require('../models/Categoria');
const Categoria = require('../models/Categoria');

module.exports = {
    async list(req, res) {
        const { id } = req.params;

        const categoria = await Categoria.findByPk(id);

        return res.json(categoria);
    },

    async list(req, res) {
        const categorias = await Categoria.findAll();

        return res.json(categorias);
    },

    async store(req, res) {
        const { nome } = req.body;

        const categoria = await Categoria.create({ nome });

        res.json(categoria);
    }
}