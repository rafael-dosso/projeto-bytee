const Receita = require('../models/Receita');
const Usuario = require('../models/Usuario');

module.exports = {

    async store(req, res) {
        const { usuarioId, receitaId } = req.body;

        const receita = await Receita.findByPk(receitaId);
        const usuario = await Usuario.findByPk(usuarioId);

        if (!receita) {
            return res.status(400).json({ error: 'Receita nao encontrada' })
        }
        if (!usuario) {
            return res.status(400).json({ error: 'Usuario nao encontrado' })
        }

        await receita.addCurtida(usuario);

        return res.json({ usuarioId, receitaId });
    },

    async count(req, res) {
        const { receitaId } = req.params;
        
        const receita = await Receita.findByPk(receitaId, {
            include: { association: 'curtida'}
        });

        if (!receita) {
            return res.status(400).json({ error: 'Receita nao encontrada' })
        }

        const qtd = receita.curtida.length;

        return res.json({ qtd });
    },

    /* 
    async delete(req, res) {
        const { usuarioId, receitaId } = req.body;
        
        const receita = await Receita.findByPk(receitaId, {
            include: { association: 'curtida'},
        });
        const usuario = await Usuario.findByPk(usuarioId);

        if (!receita) {
            return res.status(400).json({ error: 'Receita nao encontrada' })
        }
        if (!usuario) {
            return res.status(400).json({ error: 'Usuario nao encontrado' })
        }


        // await receita.removeCurtida();
        console.log(receita.curtida[0].id);
        // return res.json();
    }
    */
}