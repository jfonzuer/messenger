#!/usr/bin/env bash
set -xe

# creation database
curl -X PUT http://root:password@messenger-couchdb:5984/images

# creation user
#curl -X PUT http://root:password@messenger-couchdb:5984/_users/org.couchdb.user:couchdb-user \
#-H "Accept: application/json" \
#-H "Content-Type: application/json" \
#-d '{"name": "couchdb-user", "password": "couchdb-user", "roles": ["images"], "type": "user"}'

## application des droits sur la base
#curl -X PUT http://root:password@messenger-couchdb:5984/images/_security \
#     -H "Content-Type: application/json" \
#     -d '{"admins": { "names": [], "roles": [] }, "members": { "names": ["couchdb-user"], "roles": [] } }'