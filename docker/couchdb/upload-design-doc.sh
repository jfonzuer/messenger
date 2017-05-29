curl -X PUT http://root:fcRUVa7w@localhost:5984/_users/org.couchdb.user:couchdb-user -H "Accept: application/json" -H "Content-Type: application/json" -d '{"name": "couchdb-user", "password": "couchdb-user", "roles": [], "type": "user"}'

curl -X PUT http://127.0.0.1:5984/images/_design/validation --data-binary @design-validation.json
