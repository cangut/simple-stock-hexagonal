print('start ~~~~');

db = db.getSiblingDB('stock');
db.createUser(
    {
        user: 'stockuser',
        pwd: '12345',
        roles: [{ role: 'readWrite', db: 'stock' }],
    },
);
print('end ~~~~');