#!/bin/bash

# Variables
CONTAINER_NAME="${CONTAINER_NAME}"
DB_NAME="${DB_NAME}"
DB_USER="${DB_USER}"
DB_PASS="${MYSQL_ROOT_PASSWORD}"
BUCKET_NAME=${S3_BUCKET}
TIMESTAMP=$(date +%F_%H-%M-%S)
BACKUP_NAME="mysql-backup/mysql-backup-$TIMESTAMP.sql"
COMPRESSED_NAME="$BACKUP_NAME.gz"
LOG_FILE="/var/log/mysql/mysql_backup.log"

{
  echo "[$(date)] Starting backup for database: $DB_NAME"

  # Dump database
  if docker exec $CONTAINER_NAME sh -c "exec mysqldump -u$DB_USER -p$DB_PASS --single-transaction --quick --skip-lock-tables $DB_NAME" > "$BACKUP_NAME"; then
    echo "[$(date)] - Database dumped successfully to $BACKUP_NAME"
  else
    echo "[$(date)] - Error: Failed to dump database"
    exit 1
  fi

  # Compress backup
  if gzip "$BACKUP_NAME"; then
    echo "[$(date)] - Compressed to $COMPRESSED_NAME"
  else
    echo "[$(date)] - Error: Failed to compress backup"
    exit 1
  fi

  # Upload to S3
  if aws s3 cp "$COMPRESSED_NAME" "s3://$BUCKET_NAME/backups/mysql/$COMPRESSED_NAME"; then
    echo "[$(date)] - Uploaded to S3: $COMPRESSED_NAME"
  else
    echo "[$(date)] - Error: Failed to upload to S3"
    exit 1
  fi

  # Clean up
  rm "$COMPRESSED_NAME" && echo "[$(date)] - Local backup cleaned up"

} >> "$LOG_FILE" 2>&1
