const Receita = require('../models/Receita');
const Usuario = require('../models/Usuario');
const Categoria = require('../models/Categoria')

module.exports = {

    async list(req, res) {
        const receitas = await Receita.findAll();

        return res.json(receitas);
    },

    async indexUsuario(req, res) {
        const { usuarioId } = req.params;

        const usuario = await Usuario.findByPk(usuarioId, {
            include: { association: 'receitas' }
        });

        if (!usuario) {
            return res.status(400).json({ error: 'Usuario nao encontrado' })
        }

        return res.json(usuario.receitas);
    },

    async indexCategoria(req, res) {
        const { categoriaId } = req.params;

        const categoria = await Categoria.findByPk(categoriaId, {
            include: { association: 'receitas' }
        });

        if (!categoria) {
            return res.status(400).json({ error: 'Categoria nao encontrada' })
        }

        return res.json(categoria.receitas);
    },

    async store(req, res) {
        const { usuarioId } = req.params;

        const { categoriaId, nome, ingredientes, preparo, rendimento, imagem } = req.body;

        const usuario = await Usuario.findByPk(usuarioId);

        if (!usuario) {
            return res.status(400).json({ error: 'Usuario nao encontrado' })
        }

        const categoria = await Categoria.findByPk(categoriaId);

        if (!categoria) {
            return res.status(400).json({ error: 'Categoria nao encontrada' })
        }

        const receita = await Receita.create({
            usuarioId,
            categoriaId,
            nome,
            ingredientes,
            preparo,
            rendimento,
            imagem,
        })

        return res.json(receita);
    }
}