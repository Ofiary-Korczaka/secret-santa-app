db = db.getSiblingDB('secret-santa-app');

db.createUser({
  user: 'secret-santa-app-user',
  pwd: 'secret-santa-app-password',
  roles: [
    { role: 'readWrite', db: 'secret-santa-app' }
  ]
});