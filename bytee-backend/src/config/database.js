module.exports = {
    dialect: 'mssql',
    dialectOptions: {
        options: {
            encrypt: false,
            validateBulkLoadParameters: true
        },
    },
    host: 'regulus.cotuca.unicamp.br',
    username: 'BD20149',
    password: 'BD20149',
    database: 'BD20149',
    define: {
        timestamps: true,
        freezeTableName: false,
        underscored: false
    }
};