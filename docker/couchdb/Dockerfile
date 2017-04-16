FROM couchdb
ADD /startup.sh /startup.sh
#CMD ["./startup.sh"]
#ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["couchdb"]
#CMD ["couchdb", "/startup.sh"]
#CMD ["./startup.sh"]
#ENTRYPOINT ["/startup.sh"]
ENTRYPOINT ["tini","--","/docker-entrypoint.sh"]
