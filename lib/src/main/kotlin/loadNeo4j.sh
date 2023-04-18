
BASEDIR=$(realpath "$(dirname "$0")")

. "${BASEDIR}/CONTAINER.conf"

docker stop "$CONTAINER_NAME"

neo4j-admin dump --database=neo4j --to=/data/backups/