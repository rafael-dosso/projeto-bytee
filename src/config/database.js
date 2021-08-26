module.exports = {
    dialect: 'mssql',
    dialectOptions: {
        options: {
            encrypt: false,
            validateBulkLoadParameters: true
        },
    },
    host: 'regulus.cotuca.unicamp.br',
    username: 'BD20154',
    password: 'BD20154',
    database: 'BD20154',
    define: {
        timestamps: true,
        freezeTableName: false,
        underscored: false
    }
};