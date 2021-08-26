const { Model, DataTypes } = require('sequelize');

class Receita extends Model {
    static init(sequelize) {
        super.init({
            nome: DataTypes.STRING,
            ingredientes: DataTypes.STRING,
            preparo: DataTypes.STRING,
            rendimento: DataTypes.INTEGER,
        }, {
            sequelize,
            tableName: 'receitas'
        })
    }

    static associate(models) {
        this.belongsTo(models.Usuario, {
            foreignKey: 'usuarioId',
            as: 'usuario'
        });
        this.belongsTo(models.Categoria, {
            foreignKey: 'categoriaId',
            as: 'categoria'
        });
    }
}

module.exports = Receita;