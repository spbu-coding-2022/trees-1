
BASEDIR=$(realpath "$(dirname "$0")")

. "${BASEDIR}/CONTAINER.conf"

# -d
docker run \
  -i \
  --name "$CONTAINER_NAME" \
  --volume=$HOME/neo4j/data:/data \
  --volume=$HOME/neo4j/logs:/logs \
  --publish=7474:7474 --publish=7687:7687 \
  --env NEO4J_AUTH=neo4j/"$PASSWORD" \
  neo4j:latest \
#  -c /bin/bash
#neo4j-admin database dump neo4j --to-path=$HOME/neo4j/data:/data

#docker ps -a

#docker stop "$CONTAINER_NAME"

#cp $HOME/neo4j/data:/data $HOME/

#docker stop neo4j:latest
