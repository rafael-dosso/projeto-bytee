const Usuario = require('../models/Usuario');

module.exports = {
    async list(req, res) {
        const usuarios = await Usuario.findAll();

        return res.json(usuarios);
    },

    async index(req, res) {
        const { id } = req.params;

        const usuario = await Usuario.findByPk(id);

        if (!usuario) {
            return res.status(404).json({ error: 'Usuario nao encontrado' })
        }

        return res.json(usuario);
    },

    async store(req, res) {
        const { nome, email, senha, imagem } = req.body;

        const usuario = await Usuario.create({ nome, email, senha, imagem });

        return res.json(usuario);
    },

    async indexEmail(req, res) {
        const { email } = req.params;

        const usuarios = await Usuario.findAll();

        for (let i=0; i < usuarios.length; i++) {
            if (usuarios[i].email == email) {
                return res.json(usuarios[i]);
            }
        }

        return res.status(404).json({error: 'Nao existe nenhum usuario com este email'});
    },
}