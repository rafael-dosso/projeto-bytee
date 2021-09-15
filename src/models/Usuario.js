const { Model, DataTypes } = require('sequelize');

class Usuario extends Model {
    static init(sequelize) {
        super.init({
            nome: DataTypes.STRING,
            email: DataTypes.STRING,
            senha: DataTypes.STRING,
        }, {
            sequelize,
            tableName: 'usuarios'
        })
    }

    static associate(models) {
        this.hasMany(models.Receita, {
            foreignKey: 'usuarioId',
            as: 'receitas',
        });
        this.belongsToMany(models.Receita, {
            foreignKey: 'usuarioId',
            through: 'curtidas',
            as: 'curtida'
        });
    }
}

module.exports = Usuario;