'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.addColumn('usuarios', 'imagem', {
      type: Sequelize.STRING, allowNull: true
    });

    await queryInterface.addColumn('receitas', 'imagem', {
      type: Sequelize.STRING, allowNull: true
    });
  },

  down: async (queryInterface, Sequelize) => {
    await queryInterface.removeColumn('usuarios', 'imagem', { /* query options */ });
    await queryInterface.removeColumn('receitas', 'imagem', { /* query options */ });
  }
};
