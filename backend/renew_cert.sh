#!/bin/bash

# Run certbot container to renew certificates
docker compose up certbot

# function that reloads the nginx
reload_nginx() {
  systemctl reload nginx
  if [ $? -eq 0 ]; then
    echo "Nginx reloaded successfully."
    return 0
  else
    echo "Nginx reload failed."
    return 1
  fi
}

# Check if certbot renewal succeeded (exit code 0)
if [ $? -eq 0 ]; then
  echo "Certbot renewal successful, reloading nginx..."
  reload_nginx
else
  echo "Certbot renewal failed, nginx reload skipped."
fi
